package com.stm.user.detail.merchant.fragment.photo.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.user.detail.merchant.fragment.photo.interactor.MerchantDetailPhotoInteractor;
import com.stm.user.detail.merchant.fragment.photo.interactor.impl.MerchantDetailPhotoInteractorImpl;
import com.stm.user.detail.merchant.fragment.photo.presenter.MerchantDetailPhotoPresenter;
import com.stm.user.detail.merchant.fragment.photo.view.MerchantDetailPhotoView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public class MerchantDetailPhotoPresenterImpl implements MerchantDetailPhotoPresenter {
    private MerchantDetailPhotoInteractor merchantDetailPhotoInteractor;
    private MerchantDetailPhotoView merchantDetailPhotoView;

    public MerchantDetailPhotoPresenterImpl(MerchantDetailPhotoView merchantDetailPhotoView) {
        this.merchantDetailPhotoInteractor = new MerchantDetailPhotoInteractorImpl(this);
        this.merchantDetailPhotoView = merchantDetailPhotoView;
    }

    @Override
    public void init(User user, User storyUser) {
        merchantDetailPhotoInteractor.setUser(user);
        merchantDetailPhotoInteractor.setStoryUser(storyUser);

        if (user != null) {
            String accessToken = user.getAccessToken();
            merchantDetailPhotoInteractor.setUserRepository(accessToken);
            merchantDetailPhotoInteractor.setFileRepository(accessToken);
        } else {
            merchantDetailPhotoInteractor.setUserRepository();
            merchantDetailPhotoInteractor.setFileRepository();
        }
    }

    @Override
    public void onCreateView() {
        merchantDetailPhotoView.showProgressDialog();
        merchantDetailPhotoView.setScrollViewOnScrollChangeListener();

        User storyUser = merchantDetailPhotoInteractor.getStoryUser();
        long storyUserId = storyUser.getId();
        int offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        merchantDetailPhotoInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.PHOTO_TYPE, offset);
    }

    @Override
    public void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newPhotos) {
        int newPhotoSize = newPhotos.size();
        List<File> photos = merchantDetailPhotoInteractor.getFiles();
        int photoSize = photos.size();

        if (newPhotoSize > 0) {
            if (photoSize == 0) {
                merchantDetailPhotoInteractor.setFiles(newPhotos);
                merchantDetailPhotoView.clearMerchantDetailPhotoAdapter();
                merchantDetailPhotoView.setMerchantDetailPhotoAdapterItem(newPhotos);
            } else {
                merchantDetailPhotoInteractor.setFilesAddAll(newPhotos);
                merchantDetailPhotoView.notifyItemRangeInserted(photoSize, newPhotoSize);
            }
        } else {
            if (photoSize == 0) {
                merchantDetailPhotoView.showEmptyView();
            }
        }

        merchantDetailPhotoView.goneProgressDialog();
    }

    @Override
    public void onClickPhoto(File file, int position) {
        merchantDetailPhotoView.showProgressDialog();
        List<File> files = merchantDetailPhotoInteractor.getFiles();
        merchantDetailPhotoView.navigateToPhotoDialogActivity(files, position);
        merchantDetailPhotoView.goneProgressDialog();
    }

    @Override
    public void onSuccessUpdateFileByHits(int position) {
        merchantDetailPhotoView.goneProgressDialog();

        List<File> files = merchantDetailPhotoInteractor.getFiles();
        merchantDetailPhotoView.navigateToPhotoDialogActivity(files, position);
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            merchantDetailPhotoView.showProgressDialog();
            User storyUser = merchantDetailPhotoInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            List<File> files = merchantDetailPhotoInteractor.getFiles();
            long offset = files.size();
            merchantDetailPhotoInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.PHOTO_TYPE, offset);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailPhotoView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailPhotoView.showMessage(httpErrorDto.message());
        }
    }

}