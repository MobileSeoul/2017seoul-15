package com.stm.login.base.view;

import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface LoginView {
    void init();

    void initFacebookSdk();

    void initNaverOauthLogin();

    void setNaverOAuthLoginHandler(OAuthLoginHandler oAuthLoginHandler);

    void setToolbarLayout();

    String getNaverOAuthResponse();

    void onClickBack();

    void onClickLogin();

    void onClickFacebookLogin();

    void onClickNaverLogin();


    void onClickFindPwd();

    void onClickJoin();

    void showToolbarTitle(String message);

    void showMessage(String message);

    void navigateToJoinCategoryActivity();

    void navigateToJoinCategoryActivity(User user);

    void setUser(User user);

    User getUser();

    void navigateToBack();

    void navigateToOauthLoginActivity(OAuthLoginHandler oAuthLoginHandler);

    void navigateToFindPasswordActivity();
}
