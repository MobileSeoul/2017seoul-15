package com.stm.login.base.interactor.impl;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.base.interactor.LoginInteractor;
import com.stm.login.base.presenter.LoginPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter loginPresenter;
    private User user;

    private CallbackManager callbackManager;
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoginInteractorImpl.class);

    private OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = loginPresenter.onResponse();
                            JSONObject json = new JSONObject(response);
                            String id = json.getJSONObject("response").getString("id");
                            String name = json.getJSONObject("response").getString("name");
                            String email = json.getJSONObject("response").getString("email");

                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(id);

                            loginPresenter.onSuccessGetNaverUser();

                        } catch (JSONException e) {
                            log(e);
                        }
                    }
                }).start();

            } else {
                // error handling
            }
        }
    };

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        this.callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void getUserByEmailAndPassword(String email, String password) {
        Call<User> callFindUserByEmailAndPasswordApi = userRepository.findUserByEmailAndPassword(email, password);
        callFindUserByEmailAndPasswordApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User prevUser = response.body();
                    loginPresenter.onSuccessGetUserByEmailAndPassword(prevUser);
                } else {
                    loginPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                loginPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateUser(User user) {
        long userId = user.getId();
        Call<User> callUpdateUserByIdApi = userRepository.updateUser(userId, user);
        callUpdateUserByIdApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loginPresenter.onSuccessUpdateUser();
                } else {
                    loginPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                loginPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }


    @Override
    public void clearUserRepository() {
        if (userRepository != null) {
            userRepository = null;
        }
    }

    @Override
    public void getFacebookUser() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(id);

                            loginPresenter.onSuccessGetFacebookUser();

                        } catch (JSONException e) {
                            log(e);
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Timber.e("Facebook 로그인 취소");
            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    } else {
                        loginPresenter.onNetworkError(null);
                    }
                }

            }
        });
    }

    @Override
    public OAuthLoginHandler getOAuthLoginHandler() {
        return oAuthLoginHandler;
    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }



}
