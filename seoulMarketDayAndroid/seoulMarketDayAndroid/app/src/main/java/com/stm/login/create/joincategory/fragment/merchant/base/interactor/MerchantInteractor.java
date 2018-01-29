package com.stm.login.create.joincategory.fragment.merchant.base.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface MerchantInteractor {
    void setUserRepository();

    Market getMarket();

    void setMarket(Market market);

    User getUser();

    void setUser(User user);

    void setUser();
}
