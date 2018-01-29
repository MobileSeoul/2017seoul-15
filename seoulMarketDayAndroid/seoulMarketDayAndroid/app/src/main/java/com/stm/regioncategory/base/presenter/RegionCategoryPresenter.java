package com.stm.regioncategory.base.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.HttpErrorDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public interface RegionCategoryPresenter {
    void init(RegionCategory regionCategory);

    void onLoadData();

    void onClickMarketCategory(int isShown);

    void onClickMarketCategoryItem(MarketCategory marketCategory);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetMarketCategoryList(List<MarketCategory> marketCategories);

    void onSuccessGetRegionCategoryById(RegionCategory regionCategory);

    void onSuccessGetMarketListById(List<Market> markets);

    void onClickMap();

    void onSuccessGetMarketListByIdAndMarketCategoryId(List<Market> markets, MarketCategory marketCategory);

    void onClickMarket(Market market);
}
