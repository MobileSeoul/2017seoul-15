package com.stm.main.fragment.main.search.fragment.market.view;

import android.support.v4.widget.NestedScrollView;

import com.stm.common.dao.Market;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchMarketView {
    void showMessage(String message);

    void showEmptyView();

    void goneEmptyView();

    void showProgressDialog();

    void goneProgressDialog();

    void setOnScrollChangeListener();

    void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);

    void setSearchMarketAdapterItem(List<Market> markets);

    void clearSearchMarketAdapter();

    void notifyItemRangeInserted(int startPosition, int itemCount);

    void getMarketListByKeyword(String message);

    void navigateToMarketDetailActivity(Market market);
}
