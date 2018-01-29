package com.stm.login.base.presenter;

import com.facebook.CallbackManager;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface LoginPresenter {

    void beforeInit();

    void init();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onClickJoin();

    void onClickLogin(User user);

    void onSuccessGetUserByEmailAndPassword(User prevUser);

    void onSuccessUpdateUser();

    void onClickFacebookLogin(User user);

    void onSuccessGetFacebookUser();

    void onSuccessGetNaverUser();

    CallbackManager getCallbackManager();

    void onResume(User user);

    void onClickNaverLogin(User user);

    String onResponse();

    void onBackPressed();

    void onClickFindPwd();
}
