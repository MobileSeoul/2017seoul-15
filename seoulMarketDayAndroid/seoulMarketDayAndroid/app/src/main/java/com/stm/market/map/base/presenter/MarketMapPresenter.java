package com.stm.market.map.base.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketMapPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Market market);

    void onMarkerClick(Market market);

    void onMapReady();

    void onMapClick();

    void onClickPosition();

    void onClickStreetView();

    void onClickDirections();
}
