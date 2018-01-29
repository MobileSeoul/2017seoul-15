package com.stm.main.fragment.user.base.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface UserInteractor {
    User getUser();

    void setUser(User user);

    void setUserRepository(String accessToken);

    void updateUser(User user);
}
