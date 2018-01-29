package com.stm.login.create.join.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public interface JoinInteractor {
    void setUserRepository();

    void getCheckByEmail(String email);

    void setUser();

    Market getMarket();

    void setMarket(Market market);

    User getUser();

    void setUser(User user);
}
