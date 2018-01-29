package com.stm.market.fragment.photo.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.market.fragment.photo.interactor.MarketPhotoInteractor;
import com.stm.market.fragment.photo.interactor.impl.MarketPhotoInteractorImpl;
import com.stm.market.fragment.photo.presenter.MarketPhotoPresenter;
import com.stm.market.fragment.photo.view.MarketPhotoView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketPhotoPresenterImpl implements MarketPhotoPresenter {
    private MarketPhotoInteractor marketPhotoInteractor;
    private MarketPhotoView marketPhotoView;

    public MarketPhotoPresenterImpl(MarketPhotoView marketPhotoView) {
        this.marketPhotoInteractor = new MarketPhotoInteractorImpl(this);
        this.marketPhotoView = marketPhotoView;
    }

    @Override
    public void init(User user, Market market) {
        marketPhotoView.showProgressDialog();

        marketPhotoInteractor.setUser(user);
        marketPhotoInteractor.setMarket(market);

        if (user != null) {
            String accessToken = user.getAccessToken();
            marketPhotoInteractor.setMarketRepository(accessToken);
            marketPhotoInteractor.setFileRepository(accessToken);
        } else {
            marketPhotoInteractor.setMarketRepository();
            marketPhotoInteractor.setFileRepository();
        }
    }

    @Override
    public void onCreateView() {
        Market market = marketPhotoInteractor.getMarket();
        long marketId = market.getId();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        marketPhotoView.setScrollViewOnScrollChangeListener();
        marketPhotoInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.PHOTO_TYPE, offset);
    }


    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketPhotoView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketPhotoView.showMessage(httpErrorDto.message());
        }
    }


    @Override
    public void onClickPhoto(File file, int position) {
        marketPhotoView.showProgressDialog();
        List<File> files = marketPhotoInteractor.getFiles();
        marketPhotoView.navigateToPhotoDialogActivity(files, position);
        marketPhotoView.goneProgressDialog();
    }

    @Override
    public void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newFiles) {
        List<File> files = marketPhotoInteractor.getFiles();
        int fileSize = files.size();
        int newFileSize = newFiles.size();

        if (newFileSize > 0) {
            if (fileSize == 0) {
                marketPhotoInteractor.setFiles(newFiles);
                marketPhotoView.clearMarketPhotoAdapter();
                marketPhotoView.setMarketPhotoAdapterItem(newFiles);
            } else {
                marketPhotoInteractor.setFilesAddAll(newFiles);
                marketPhotoView.notifyItemRangeInserted(fileSize, newFileSize);
            }
        } else {
            if (fileSize == 0) {
                marketPhotoView.showEmptyView();
            }
        }

        marketPhotoView.goneProgressDialog();
    }

    @Override
    public void onSuccessUpdateFileByHits(int position) {
        List<File> files = marketPhotoInteractor.getFiles();
        marketPhotoView.navigateToPhotoDialogActivity(files, position);
        marketPhotoView.goneProgressDialog();
    }


    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            marketPhotoView.showProgressDialog();
            Market market = marketPhotoInteractor.getMarket();
            long marketId = market.getId();

            List<File> files = marketPhotoInteractor.getFiles();
            long offset = files.size();
            marketPhotoInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.PHOTO_TYPE, offset);
        }
    }
}
