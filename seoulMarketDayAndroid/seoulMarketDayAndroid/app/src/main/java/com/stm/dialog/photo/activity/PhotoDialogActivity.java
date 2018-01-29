package com.stm.dialog.photo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.adapter.PhotoDialogAdapter;
import com.stm.dialog.photo.presenter.PhotoDialogPresenter;
import com.stm.dialog.photo.presenter.impl.PhotoDialogPresenterImpl;
import com.stm.dialog.photo.view.PhotoDialogView;
import com.stm.repository.local.SharedPrefersManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class PhotoDialogActivity extends Activity implements PhotoDialogView, ViewPager.OnPageChangeListener {

    private PhotoDialogPresenter photoDialogPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private PhotoDialogAdapter photoDialogAdapter;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.vp_photodialog)
    ViewPager vp_photodialog;

    @BindView(R.id.iv_photodialog)
    ImageView iv_photodialog;

    @BindString(R.string.cloud_front_story_image)
    String storyImageUrl;

    @BindString(R.string.cloud_front_comment_image)
    String commentImageUrl;

    @BindString(R.string.cloud_front_user_avatar)
    String userAvatarUrl;

    @BindString(R.string.cloud_front_user_cover)
    String userCoverUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodialog);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        String avatar = getIntent().getStringExtra("avatar");
        String cover = getIntent().getStringExtra("cover");
        List<File> files = (ArrayList<File>) getIntent().getSerializableExtra("files");
        File file = (File) getIntent().getSerializableExtra("file");
        int position = getIntent().getIntExtra("position", 0);
        User user = sharedPrefersManager.getUser();

        this.photoDialogPresenter = new PhotoDialogPresenterImpl(this);
        this.photoDialogPresenter.init(user, avatar, cover, files, file, position);

    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showStoryImage(File file) {
        String fileName = file.getName();
        String fileExt = file.getExt();
        String url = fileName + "." + fileExt;
        Glide.with(this).load(storyImageUrl + url).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_photodialog);
    }

    @Override
    public void showCommentImage(File file) {
        String fileName = file.getName();
        String fileExt = file.getExt();
        String url = fileName + "." + fileExt;
        Glide.with(this).load(commentImageUrl + url).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_photodialog);
    }

    @Override
    public void showUserAvatar(String message) {
        Glide.with(this).load(userAvatarUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_photodialog);
    }

    @Override
    public void showUserCover(String message) {
        Glide.with(this).load(userCoverUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_photodialog);
    }

    @Override
    public void showPhoto() {
        iv_photodialog.setVisibility(View.VISIBLE);
    }

    @Override
    public void gonePhoto() {
        iv_photodialog.setVisibility(View.GONE);
    }

    @Override
    public void showViewPager() {
        vp_photodialog.setVisibility(View.VISIBLE);
    }

    @Override
    public void showExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFlag.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @Override
    public void goneProgressDialog() {
        progressDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 10);
    }

    @Override
    public void setViewPagerOnPageChangeListener() {
        vp_photodialog.addOnPageChangeListener(this);
    }


    @Override
    public void setPhotoDialogAdapterItem(List<File> files) {
        photoDialogAdapter = new PhotoDialogAdapter(photoDialogPresenter, files, this, R.layout.item_photodialog);
        vp_photodialog.setAdapter(photoDialogAdapter);
    }

    @Override
    public void setViewPagerCurrentItem(int position) {
        vp_photodialog.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        photoDialogPresenter.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionFlag.WRITE_EXTERNAL_STORAGE:
                photoDialogPresenter.onRequestPermissionsResultForWriteExternalStorage(grantResults);
                break;
        }
    }

    @Override
    @OnClick(R.id.ib_photodialog_close)
    public void onClickClose() {
        photoDialogPresenter.onClickClose();
    }

    @Override
    @OnClick(R.id.ib_photodialog_download)
    public void onClickDownload() {
        int position = vp_photodialog.getCurrentItem();
        String filePath = Environment.getExternalStorageDirectory() + "/DCIM/CAMERA/stm_" + System.currentTimeMillis() + ".jpg";
        photoDialogPresenter.onClickDownload(filePath, position);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void sendBroadcastToScanFile(final String path) {
         sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }


}
