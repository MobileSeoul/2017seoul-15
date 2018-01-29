package com.stm.login.base.interactor;

import com.facebook.CallbackManager;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface LoginInteractor {
    void setUserRepository();

    void setUserRepository(String accessToken);

    void getUserByEmailAndPassword(String email, String password);

    void updateUser(User user);

    User getUser();

    void setUser(User user);

    CallbackManager getCallbackManager();

    void clearUserRepository();

    void getFacebookUser();

    OAuthLoginHandler getOAuthLoginHandler();

}
