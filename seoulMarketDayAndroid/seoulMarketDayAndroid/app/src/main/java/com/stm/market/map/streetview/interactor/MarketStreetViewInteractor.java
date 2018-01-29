package com.stm.market.map.streetview.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketStreetViewInteractor {
    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);
}
