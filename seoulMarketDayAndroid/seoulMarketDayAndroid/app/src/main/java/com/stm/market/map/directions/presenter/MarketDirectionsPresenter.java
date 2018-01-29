package com.stm.market.map.directions.presenter;

import com.stm.common.dto.Directions;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.dto.LocationDto;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketDirectionsPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Market market, LocationDto locationDto);

    void onSuccessGetDirections(Directions directions);

    void onMapReady(String googleDirectionsApiKey);

    void onMarkerClick(Market market);

    void onMapClick();

    void onClickPosition();
}
