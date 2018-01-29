package com.stm.login.base.presenter.impl;

import com.facebook.CallbackManager;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.stm.common.dao.User;
import com.stm.common.flag.LoginFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.base.interactor.LoginInteractor;
import com.stm.login.base.interactor.impl.LoginInteractorImpl;
import com.stm.login.base.presenter.LoginPresenter;
import com.stm.login.base.view.LoginView;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl(this);
    }

    @Override
    public void beforeInit() {
        loginView.initFacebookSdk();
    }

    @Override
    public void init() {
        loginInteractor.setUserRepository();
        loginView.init();
        loginView.setToolbarLayout();
        loginView.showToolbarTitle("로그인");

        loginView.initNaverOauthLogin();
        OAuthLoginHandler oAuthLoginHandler = loginInteractor.getOAuthLoginHandler();
        loginView.setNaverOAuthLoginHandler(oAuthLoginHandler);
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            loginView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            loginView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onClickJoin() {
        loginView.navigateToJoinCategoryActivity();
    }

    @Override
    public void onClickLogin(User user) {
        loginInteractor.setUser(user);

        String email = user.getEmail();
        String password = user.getPassword();
        loginInteractor.getUserByEmailAndPassword(email, password);
    }


    @Override
    public void onClickFacebookLogin(User user) {
        loginInteractor.setUser(user);
        loginInteractor.getFacebookUser();
    }

    @Override
    public void onClickNaverLogin(User user) {
        loginInteractor.setUser(user);
        OAuthLoginHandler oAuthLoginHandler = loginInteractor.getOAuthLoginHandler();
        loginView.navigateToOauthLoginActivity(oAuthLoginHandler);
    }

    @Override
    public String onResponse() {
        String response = loginView.getNaverOAuthResponse();
        return response;
    }

    @Override
    public void onBackPressed() {
        loginView.navigateToBack();
    }

    @Override
    public void onClickFindPwd() {
        loginView.navigateToFindPasswordActivity();
    }

    @Override
    public void onSuccessGetFacebookUser() {
        User user = loginInteractor.getUser();

        String email = user.getEmail();
        String password = user.getPassword();
        loginInteractor.getUserByEmailAndPassword(email, password);
    }

    @Override
    public void onSuccessGetNaverUser() {
        User user = loginInteractor.getUser();

        String email = user.getEmail();
        String password = user.getPassword();
        loginInteractor.getUserByEmailAndPassword(email, password);
    }

    @Override
    public CallbackManager getCallbackManager() {
        CallbackManager callbackManager = loginInteractor.getCallbackManager();
        return callbackManager;
    }

    @Override
    public void onResume(User user) {
        if (user != null) {
            loginView.navigateToBack();
        }
    }


    @Override
    public void onSuccessGetUserByEmailAndPassword(User prevUser) {
        User user = loginInteractor.getUser();

        if (prevUser == null) {
            int loginCategoryId = user.getLoginCategoryId();

            if (loginCategoryId == LoginFlag.FACEBOOK || loginCategoryId == LoginFlag.NAVER) {
                loginView.navigateToJoinCategoryActivity(user);
            } else {
                loginView.showMessage("이메일과  비밀번호를 확인하세요");
            }
        } else {
            long userId = prevUser.getId();
            String accessToken = prevUser.getAccessToken();

            if (accessToken != null) {
                loginInteractor.clearUserRepository();
                loginInteractor.setUserRepository(accessToken);


                String deviceId = user.getDeviceId();
                String fcmToken = user.getFcmToken();

                String prevDeviceId = prevUser.getDeviceId();
                String prevFcmToken = prevUser.getFcmToken();

                if (deviceId.trim().equals(prevDeviceId) && fcmToken.trim().equals(prevFcmToken)) {
                    loginInteractor.setUser(prevUser);
                    loginView.setUser(prevUser);
                    loginView.navigateToBack();
                } else {
                    user.setId(userId);
                    user.setEmail(null);
                    user.setPassword(null);
                    user.setName(null);
                    user.setLoginCategoryId(0);

                    loginInteractor.updateUser(user);

                    prevUser.setDeviceId(deviceId);
                    prevUser.setFcmToken(fcmToken);
                    loginInteractor.setUser(prevUser);
                }
            } else {
                loginView.showMessage("일시적인 장애입니다. 다시 시도해주세요.");

            }
        }


    }

    @Override
    public void onSuccessUpdateUser() {
        User user = loginInteractor.getUser();
        loginView.setUser(user);
        loginView.navigateToBack();
    }

}
