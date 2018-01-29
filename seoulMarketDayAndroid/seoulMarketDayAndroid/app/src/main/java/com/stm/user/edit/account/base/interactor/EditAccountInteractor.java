package com.stm.user.edit.account.base.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public interface EditAccountInteractor {
    User getUser();

    void setUser(User user);
}
