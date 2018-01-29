package com.stm.login.create.joincategory.fragment.merchant.search.view;

import com.stm.common.dao.Market;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public interface SearchMarketView {

    void setOnScrollChangeListener();

    void setToolbarLayout();

    void showMessage(String message);

    void onClickBack();

    void onClickClear();

    void showProgressDialog();

    void clearMarketAdapter();

    void goneProgressDialog();

    void notifyItemRangeInserted(int startPosition, int itemCount);

    void setMarketByKeywordAndOffsetItem(List<Market> markets);

    void navigateToBackForResultOk(Market market);

    void navigateToBackForResultCancel();

    void goneEmptyView();


    void showEmptyView();
}
