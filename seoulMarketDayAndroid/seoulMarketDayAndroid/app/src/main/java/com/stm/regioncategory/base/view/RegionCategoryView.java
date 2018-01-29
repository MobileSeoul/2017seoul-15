package com.stm.regioncategory.base.view;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.RegionCategory;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public interface RegionCategoryView {
    void init();

    void showProgressDialog();

    void goneProgressDialog();

    void setToolbarLayout();

    void showToolbarTitle(String message);

    void setMarketCategoryItem(List<MarketCategory> marketCategories);

    void setMarketItem(List<Market> markets);

    void showEmptyViewText(String message);

    void showEmptyView();

    void goneEmptyView();

    void showMarketCategory();

    void goneMarketCategory();

    void clearMarketAdapter();

    void showMarketCategoryName(String message);

    void showMessage(String message);

    void navigateToRegionCategoryMapActivity(RegionCategory regionCategory);

    void onClickBack();

    void onClickMap();

    void onClickMarketCategory();

    void navigateToMarketActivity(Market market);
}
