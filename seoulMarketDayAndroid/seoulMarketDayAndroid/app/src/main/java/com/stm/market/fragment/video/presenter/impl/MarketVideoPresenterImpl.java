package com.stm.market.fragment.video.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.market.fragment.video.interactor.MarketVideoInteractor;
import com.stm.market.fragment.video.interactor.impl.MarketVideoInteractorImpl;
import com.stm.market.fragment.video.presenter.MarketVideoPresenter;
import com.stm.market.fragment.video.view.MarketVideoView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketVideoPresenterImpl implements MarketVideoPresenter {
    private MarketVideoInteractor marketVideoInteractor;
    private MarketVideoView marketVideoView;

    public MarketVideoPresenterImpl(MarketVideoView marketVideoView) {
        this.marketVideoInteractor = new MarketVideoInteractorImpl(this);
        this.marketVideoView = marketVideoView;
    }

    @Override
    public void init(User user, Market market) {
        marketVideoView.showProgressDialog();

        marketVideoInteractor.setUser(user);
        marketVideoInteractor.setMarket(market);

        if (user != null) {
            String accessToken = user.getAccessToken();
            marketVideoInteractor.setMarketRepository(accessToken);
            marketVideoInteractor.setFileRepository(accessToken);
        } else {
            marketVideoInteractor.setMarketRepository();
            marketVideoInteractor.setFileRepository();
        }
    }

    @Override
    public void onCreateView() {
        marketVideoView.setScrollViewOnScrollChangeListener();

        Market market = marketVideoInteractor.getMarket();
        long marketId = market.getId();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        marketVideoView.setScrollViewOnScrollChangeListener();
        marketVideoInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            marketVideoView.showProgressDialog();
            Market market = marketVideoInteractor.getMarket();
            long marketId = market.getId();

            List<File> files = marketVideoInteractor.getFiles();
            long offset = files.size();
            marketVideoInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketVideoView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketVideoView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onClickVideo(File file, int position) {
        marketVideoView.showProgressDialog();
        marketVideoInteractor.updateFileByHits(file, position);
    }

    @Override
    public void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newFiles) {
        List<File> files = marketVideoInteractor.getFiles();
        int fileSize = files.size();
        int newFileSize = newFiles.size();

        if (newFileSize > 0) {
            if(fileSize == 0){
                marketVideoInteractor.setFiles(newFiles);
                marketVideoView.clearMarketVideoAdapter();
                marketVideoView.setMarketVideoAdapterItem(newFiles);
            } else {
                marketVideoInteractor.setFilesAddAll(newFiles);
                marketVideoView.marketVideoAdapterNotifyItemRangeInserted(fileSize, newFileSize);
            }
        } else {
            marketVideoView.showEmptyView();
        }

        marketVideoView.goneProgressDialog();
    }

    @Override
    public void onSuccessUpdateFileByHits(int position) {
        List<File> files = marketVideoInteractor.getFiles();
        File file = files.get(position);
        int prevHit = file.getHits();
        file.setHits(prevHit + 1);

        marketVideoView.marketVideoAdapterNotifyItemChanged(position);
        marketVideoView.goneProgressDialog();
        marketVideoView.navigateToVideoPlayerActivity(file);
    }

}
