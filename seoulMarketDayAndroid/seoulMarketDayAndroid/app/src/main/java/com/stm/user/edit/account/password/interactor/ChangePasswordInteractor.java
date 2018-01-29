package com.stm.user.edit.account.password.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public interface ChangePasswordInteractor {
    User getUser();

    void setUser(User user);

    void setUserRepository(String accessToken);

    void getUserByEmailAndPassword(String email, String password, String newPassword);

    void updateUser(long userId, User user);
}
