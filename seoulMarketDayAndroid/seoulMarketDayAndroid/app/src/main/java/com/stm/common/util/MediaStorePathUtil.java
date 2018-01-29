package com.stm.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.stm.common.flag.LogFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dev-0 on 2017-07-13.
 */

public class MediaStorePathUtil {
    private Context context;
    private static final Logger logger = LoggerFactory.getLogger(MediaStorePathUtil.class);

    public MediaStorePathUtil(Context context) {
        this.context = context;
    }

    public String getImagePath(Uri uri) {
        String path = null;
        try {
            path = getImagePathFromURI_API19(uri);
        } catch (Exception e) {
            log(e);
            path = getImagePathFromURI_API11to18(uri);
        }
        return path;
    }

    @SuppressLint("NewApi")
    public String getImagePathFromURI_API19(Uri uri) {
        String path = null;
        String documentId = DocumentsContract.getDocumentId(uri);
        String id = documentId.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, selection, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            path = cursor.getString(columnIndex);
        }
        cursor.close();
        return path;
    }


    public String getImagePathFromURI_API11to18(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        String path = null;

        CursorLoader cursorLoader = new CursorLoader(context, uri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
        }

        return path;
    }


    public String getVideoPath(Uri uri) {
        String path = null;
        try {
            path = getVideoPathFromURI_API19(uri);
        } catch (Exception e) {
            log(e);
            path = getVideoPathFromURI_API11to18(uri);
        }
        return path;
    }


    @SuppressLint("NewApi")
    public String getVideoPathFromURI_API19(Uri uri) {
        String path = null;
        String documentId = DocumentsContract.getDocumentId(uri);
        String id = documentId.split(":")[1];
        String[] column = {MediaStore.Video.Media.DATA};
        String selection = MediaStore.Video.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, column, selection, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            path = cursor.getString(columnIndex);
        }
        cursor.close();
        return path;
    }

    public String getVideoPathFromURI_API11to18(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        String path = null;

        CursorLoader cursorLoader = new CursorLoader(context, uri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
        }

        return path;
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
