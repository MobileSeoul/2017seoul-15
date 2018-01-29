package com.stm.dialog.photo.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.dialog.photo.interactor.PhotoDialogInteractor;
import com.stm.dialog.photo.interactor.impl.PhotoDialogInteractorImpl;
import com.stm.dialog.photo.presenter.PhotoDialogPresenter;
import com.stm.dialog.photo.view.PhotoDialogView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class PhotoDialogPresenterImpl implements PhotoDialogPresenter {
    private PhotoDialogInteractor photoDialogInteractor;
    private PhotoDialogView photoDialogView;


    public PhotoDialogPresenterImpl(PhotoDialogView photoDialogView) {
        this.photoDialogInteractor = new PhotoDialogInteractorImpl(this);
        this.photoDialogView = photoDialogView;
    }

    @Override
    public void init(User user, String avatar, String cover, List<File> files, File file, int position) {
        photoDialogInteractor.setUser(user);
        photoDialogView.showExternalStoragePermission();

        photoDialogInteractor.setAvatar(avatar);
        photoDialogInteractor.setCover(cover);
        photoDialogInteractor.setFile(file);
        photoDialogInteractor.setFiles(files);
        photoDialogInteractor.setPosition(position);
        photoDialogView.setViewPagerOnPageChangeListener();

        if(user != null){
            String accessToken = user.getAccessToken();
            photoDialogInteractor.setFileRepository(accessToken);
        } else {
            photoDialogInteractor.setFileRepository();
        }

        if (avatar != null) {
            photoDialogView.showPhoto();
            photoDialogView.showUserAvatar(avatar);
        }

        if (cover != null) {
            photoDialogView.showPhoto();
            photoDialogView.showUserCover(cover);
        }

        if (files != null) {
            photoDialogView.showViewPager();
            photoDialogView.setPhotoDialogAdapterItem(files);
            photoDialogView.setViewPagerCurrentItem(position);
        }

        if (file != null) {
            photoDialogView.showPhoto();
            int postCategoryId = file.getPostCategoryId();
            if (postCategoryId == DefaultFileFlag.STORY_POST_FILE) {
                photoDialogView.showStoryImage(file);
            }
            if (postCategoryId == DefaultFileFlag.STORY_COMMENT_POST_FILE) {
                photoDialogView.showCommentImage(file);
            }
        }

    }

    @Override
    public void onClickClose() {
        photoDialogView.navigateToBack();
    }

    @Override
    public void onClickDownload(String filePath, int position) {
        File file = photoDialogInteractor.getFile();
        List<File> files = photoDialogInteractor.getFiles();
        String avatar = photoDialogInteractor.getAvatar();
        String cover = photoDialogInteractor.getCover();

        if (avatar != null) {
            photoDialogInteractor.setAvatarFileDownloadRepository();
            photoDialogInteractor.setFileDownloaded(avatar, filePath);
        }

        if (cover != null) {
            photoDialogInteractor.setCoverFileDownloadRepository();
            photoDialogInteractor.setFileDownloaded(cover, filePath);
        }

        if (files != null) {
            file = files.get(position);
            photoDialogInteractor.setStoryFileDownloadRepository();

            String fileName = file.getName();
            String fileExt = file.getExt();
            String url = fileName + "." + fileExt;
            photoDialogInteractor.setFileDownloaded(url, filePath);
        }

        if (file != null) {
            int postCategoryId = file.getPostCategoryId();
            if (postCategoryId == DefaultFileFlag.STORY_POST_FILE) {
                photoDialogInteractor.setStoryFileDownloadRepository();
            }
            if (postCategoryId == DefaultFileFlag.STORY_COMMENT_POST_FILE) {
                photoDialogInteractor.setCommentFileDownloadRepository();
            }

            String fileName = file.getName();
            String fileExt = file.getExt();
            String url = fileName + "." + fileExt;
            photoDialogInteractor.setFileDownloaded(url, filePath);
        }

    }

    @Override
    public void onPageSelected(int position) {
        List<File> files = photoDialogInteractor.getFiles();
        File file = files.get(position);
        photoDialogInteractor.updateFileByHits(file);

    }

    @Override
    public void onRequestPermissionsResultForWriteExternalStorage(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            photoDialogView.showMessage("권한을 허가해주세요.");
            photoDialogView.navigateToBack();
        }
    }

    @Override
    public void onSuccessSetFileDownloaded(String filePath) {
        photoDialogView.sendBroadcastToScanFile(filePath);
        photoDialogView.showMessage("다운로드가 완료되었습니다.");
    }


    @Override
    public void onSuccessUpdateFileByHits() {

    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            photoDialogView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            photoDialogView.showMessage(httpErrorDto.message());
        }
    }
}
