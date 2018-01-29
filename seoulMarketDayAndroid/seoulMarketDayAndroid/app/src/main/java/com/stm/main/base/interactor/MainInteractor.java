package com.stm.main.base.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public interface MainInteractor {
    User getUser();

    void setUser(User user);
}
