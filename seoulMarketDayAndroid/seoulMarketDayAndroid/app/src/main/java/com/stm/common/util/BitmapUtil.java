package com.stm.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.stm.common.flag.LogFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Dev-0 on 2017-06-20.
 */

public class BitmapUtil {
    private Context context;
    private static final Logger logger = LoggerFactory.getLogger(BitmapUtil.class);

    public BitmapUtil(Context context) {
        this.context = context;
    }

    public Bitmap getBitmapByView(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public Bitmap getVideoThumbnailBitmapByContentUri(Uri uri) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            FileDescriptor fileDescriptor = (new FileInputStream(new File((new MediaStorePathUtil(context)).getVideoPath(uri)))).getFD();
            mediaMetadataRetriever.setDataSource(fileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST);
        mediaMetadataRetriever.release();
        return bitmap;
    }


    public Bitmap getVideoThumbnailBitmapByUri(Uri uri) {
//        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
//        mmr.setDataSource(uri.toString(), new HashMap<String, String>());
//        Bitmap b = mmr.getFrameAtTime(10); // frame at 2 seconds

//        mmr.release();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(uri.toString(), new HashMap<String, String>());
        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(10, MediaMetadataRetriever.OPTION_CLOSEST);
        mediaMetadataRetriever.release();
        return bitmap;
    }

    public Bitmap getRotationOrientedBitmapByUriAndPath(Uri uri, String path) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            ExifInterface exifInterface = new ExifInterface(path);
            int exifOrientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifDegree = exifOrientationToDegrees(exifOrientation);
            bitmap = getRotatedBitmapByBitmapAndDegree(bitmap, exifDegree);
        } catch (IOException e) {
            log(e);
        }
        return bitmap;
    }

    public Bitmap getRotatedBitmapByBitmapAndDegree(Bitmap bitmap, int degree) {
        if (degree != 0 && bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);
            try {
                Bitmap convertedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (bitmap != convertedBitmap) {
                    bitmap.recycle();
                    bitmap = convertedBitmap;
                }
            } catch (OutOfMemoryError e) {
                log(e);
            }
        }
        return bitmap;
    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public String getCacheFilePathByBitmap(Bitmap bitmap) {
        File cacheDir = context.getCacheDir();
        String fileName = System.currentTimeMillis() + ".jpg";
        File tempFile = new File(cacheDir, fileName);
        try {
            tempFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            log(e);
        } catch (IOException e) {
            log(e);
        }

        return tempFile.getAbsolutePath();
    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }
}
