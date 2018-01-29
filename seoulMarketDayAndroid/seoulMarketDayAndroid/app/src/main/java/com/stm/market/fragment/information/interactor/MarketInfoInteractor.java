package com.stm.market.fragment.information.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-07-04.
 */

public interface MarketInfoInteractor {

    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);
}
