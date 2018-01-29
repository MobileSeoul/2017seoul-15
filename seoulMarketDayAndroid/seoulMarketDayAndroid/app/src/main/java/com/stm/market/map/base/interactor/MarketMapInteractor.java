package com.stm.market.map.base.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketMapInteractor {
    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);


    LocationDto getLocationDto();

    void setLocationDto(LocationDto locationDto);
}
