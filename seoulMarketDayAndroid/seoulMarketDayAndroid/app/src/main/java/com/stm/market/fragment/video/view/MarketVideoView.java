package com.stm.market.fragment.video.view;

import com.stm.common.dao.File;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketVideoView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setScrollViewOnScrollChangeListener();

    void showEmptyView();

    void setMarketVideoAdapterItem(List<File> files);

    void clearMarketVideoAdapter();

    void marketVideoAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void marketVideoAdapterNotifyItemChanged(int position);

    void navigateToVideoPlayerActivity(File file);



}
