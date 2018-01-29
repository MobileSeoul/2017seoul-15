package com.stm.regioncategory.map.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.HttpErrorDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev-0 on 2017-06-19.
 */

public interface RegionCategoryMapPresenter {
    void init(RegionCategory regionCategory);

    void onLoadData();

    void onSuccessMapReady();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetMarketListById(List<Market> markets);

    void onSuccessGetMarketListByIdAndLocation(List<Market> markets);

    void onMarkerClick(Market market);

    void onMapClick();

    void onClickPosition();

    void onSnippetClick(Market market);
}
