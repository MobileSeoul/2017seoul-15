package com.stm.login.find.password.interactor;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public interface FindPasswordInteractor {
    void setUserRepository();

    void getUserByEmail(String email);
}
