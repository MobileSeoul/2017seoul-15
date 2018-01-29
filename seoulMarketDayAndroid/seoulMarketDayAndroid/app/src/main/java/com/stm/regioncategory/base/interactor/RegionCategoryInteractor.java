package com.stm.regioncategory.base.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.RegionCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public interface RegionCategoryInteractor {
    void setMarketRepository();

    void setMarketCategoryRepository();

    void setRegionCategoryRepository();

    RegionCategory getRegionCategory();

    void setRegionCategory(RegionCategory regionCategory);

    List<Market> getMarkets();

    void getMarketCategoryList();

    void getRegionCategoryById(long regionCategoryId);

    void setMarkets(List<Market> markets);

    void getMarketListById(long regionCategoryId);

    void getMarketListByIdAndMarketCategoryId(long regionCategoryId, MarketCategory marketCategory);
}
