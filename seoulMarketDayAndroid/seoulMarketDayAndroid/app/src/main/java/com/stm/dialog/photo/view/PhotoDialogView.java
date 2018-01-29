package com.stm.dialog.photo.view;

import com.stm.common.dao.File;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface PhotoDialogView {
    void showMessage(String message);

    void setPhotoDialogAdapterItem(List<File> files);

    void setViewPagerCurrentItem(int position);

    void navigateToBack();

    void showStoryImage(File file);

    void showCommentImage(File file);

    void showUserAvatar(String message);

    void showUserCover(String message);

    void showPhoto();

    void gonePhoto();

    void showViewPager();

    void onClickClose();


    void onClickDownload();

    void showExternalStoragePermission();

    void showProgressDialog();

    void goneProgressDialog();

    void setViewPagerOnPageChangeListener();

    void sendBroadcastToScanFile(String path);
}
