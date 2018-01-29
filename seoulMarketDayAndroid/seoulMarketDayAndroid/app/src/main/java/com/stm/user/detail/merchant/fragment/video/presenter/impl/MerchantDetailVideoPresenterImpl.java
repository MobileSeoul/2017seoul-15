package com.stm.user.detail.merchant.fragment.video.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.user.detail.merchant.fragment.video.interactor.MerchantDetailVideoInteractor;
import com.stm.user.detail.merchant.fragment.video.interactor.impl.MerchantDetailVideoInteractorImpl;
import com.stm.user.detail.merchant.fragment.video.presenter.MerchantDetailVideoPresenter;
import com.stm.user.detail.merchant.fragment.video.view.MerchantDetailVideoView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public class MerchantDetailVideoPresenterImpl implements MerchantDetailVideoPresenter {
    private MerchantDetailVideoInteractor merchantDetailVideoInteractor;
    private MerchantDetailVideoView merchantDetailVideoView;

    public MerchantDetailVideoPresenterImpl(MerchantDetailVideoView merchantDetailVideoView) {
        this.merchantDetailVideoInteractor = new MerchantDetailVideoInteractorImpl(this);
        this.merchantDetailVideoView = merchantDetailVideoView;
    }

    @Override
    public void init(User user, User storyUser) {
        merchantDetailVideoInteractor.setUser(user);
        merchantDetailVideoInteractor.setStoryUser(storyUser);

        if (user != null) {
            String accessToken = user.getAccessToken();
            merchantDetailVideoInteractor.setUserRepository(accessToken);
            merchantDetailVideoInteractor.setFileRepository(accessToken);
        } else {
            merchantDetailVideoInteractor.setUserRepository();
            merchantDetailVideoInteractor.setFileRepository();
        }
    }

    @Override
    public void onCreateView() {
        merchantDetailVideoView.showProgressDialog();
        merchantDetailVideoView.setScrollViewScrollChangedListener();

        User storyUser = merchantDetailVideoInteractor.getStoryUser();
        long storyUserId = storyUser.getId();
        int offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        merchantDetailVideoInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
    }

    @Override
    public void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newFiles) {
        int newFileSize = newFiles.size();
        List<File> files = merchantDetailVideoInteractor.getFiles();
        int fileSize = files.size();

        if (newFileSize > 0) {
            if (fileSize == 0) {
                merchantDetailVideoInteractor.setFiles(newFiles);
                merchantDetailVideoView.clearMerchantDetailVideoAdapter();
                merchantDetailVideoView.setMerchantDetailVideoAdapterItem(newFiles);
            } else {
                merchantDetailVideoInteractor.setFilesAddAll(newFiles);
                merchantDetailVideoView.videoAdapterNotifyItemRangeInserted(fileSize, newFileSize);
            }
        } else {
            if (fileSize == 0) {
                merchantDetailVideoView.showEmptyView();
            }
        }

        merchantDetailVideoView.goneProgressDialog();
    }

    @Override
    public void onClickVideo(File file, int position) {
        merchantDetailVideoView.showProgressDialog();
        merchantDetailVideoInteractor.updateFileByHits(file, position);
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            merchantDetailVideoView.showProgressDialog();
            User storyUser = merchantDetailVideoInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            List<File> files = merchantDetailVideoInteractor.getFiles();
            long offset = files.size();
            merchantDetailVideoInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
        }
    }

    @Override
    public void onSuccessUpdateFileByHits(int position) {
        File file = merchantDetailVideoInteractor.getFiles().get(position);
        int prevHit = file.getHits();
        file.setHits(prevHit + 1);

        merchantDetailVideoView.videoAdapterNotifyItemChanged(position);
        merchantDetailVideoView.goneProgressDialog();
        merchantDetailVideoView.navigateToVideoPlayerActivity(file);

    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailVideoView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailVideoView.showMessage(httpErrorDto.message());
        }
    }

}
