package com.stm.market.map.directions.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketDirectionsInteractor {
    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);

    LocationDto getLocationDto();

    void setLocationDto(LocationDto locationDto);


    String getGoogleMapKey();

    void setGoogleMapKey(String googleMapKey);

    void setGoogleRepository();

    void getDirections(Market market, LocationDto locationDto, String mode, String googleApiKey);
}
