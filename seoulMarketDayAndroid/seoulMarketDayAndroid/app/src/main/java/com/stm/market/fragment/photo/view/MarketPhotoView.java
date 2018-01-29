package com.stm.market.fragment.photo.view;

import com.stm.common.dao.File;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketPhotoView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void showEmptyView();

    void navigateToPhotoDialogActivity(List<File> files, int position);

    void clearMarketPhotoAdapter();

    void setMarketPhotoAdapterItem(List<File> files);

    void setScrollViewOnScrollChangeListener();

    void notifyItemRangeInserted(int startPosition, int itemSize);
}
