package com.stm.login.reset.password.interactor;

import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public interface ResetPasswordInteractor {
    void setUserRepository();

    User getUser();

    void setUser(User user);

    void updateUserByResettingPassword(User user);
}
