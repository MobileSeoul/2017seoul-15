package com.stm.login.create.joincategory.fragment.person.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface PersonInteractor {

    void setUserRepository();

    User getUser();

    void setUser(User user);

    void setUser();
}
