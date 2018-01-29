package com.stm.user.detail.merchant.base.interactor.impl;

import android.util.Log;

import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.repository.remote.UserRepository;
import com.stm.user.detail.merchant.base.interactor.MerchantDetailInteractor;
import com.stm.user.detail.merchant.base.presenter.MerchantDetailPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class MerchantDetailInteractorImpl implements MerchantDetailInteractor {
    private MerchantDetailPresenter merchantDetailPresenter;
    private User user;
    private User storyUser;
    private int position;

    private boolean isToolbarTitleShown;
    private int totalScrollRange;

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(MerchantDetailInteractorImpl.class);

    public MerchantDetailInteractorImpl(MerchantDetailPresenter merchantDetailPresenter) {
        this.merchantDetailPresenter = merchantDetailPresenter;
        this.isToolbarTitleShown = false;
        this.totalScrollRange = -1;
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
    public void getUserById(long id) {
        Call<User> callFindUserByIdApi = userRepository.findUserById(id);
        callFindUserByIdApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    storyUser = response.body();
                    merchantDetailPresenter.onSuccessGetUserById(storyUser);
                } else {
                    try {
                        Log.e("ㄴㄴㄴㄴ", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    merchantDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                merchantDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getUserById(long id, long userId) {
        Call<User> callFindUserByIdApi = userRepository.findUserByIdAndUserId(id, userId);
        callFindUserByIdApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    storyUser = response.body();
                    merchantDetailPresenter.onSuccessGetUserById(storyUser);
                } else {
                    merchantDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                merchantDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getUserByIdForRefresh(long id, long userId, final int position) {
        Call<User> callFindUserByIdApi = userRepository.findUserByIdAndUserId(id, userId);
        callFindUserByIdApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    storyUser = response.body();
                    merchantDetailPresenter.onSuccessGetUserByIdForRefresh(storyUser, position);
                } else {
                    merchantDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                merchantDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getUserByIdForRefresh(long id, final int position) {
        Call<User> callFindUserByIdApi = userRepository.findUserById(id);
        callFindUserByIdApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    storyUser = response.body();
                    merchantDetailPresenter.onSuccessGetUserByIdForRefresh(storyUser, position);
                } else {
                    merchantDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                merchantDetailPresenter.onNetworkError(null);
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
    public User getStoryUser() {
        return storyUser;
    }

    @Override
    public void setStoryUser(User storyUser) {
        this.storyUser = storyUser;
    }

    @Override
    public boolean isToolbarTitleShown() {
        return isToolbarTitleShown;
    }

    @Override
    public void setToolbarTitleShown(boolean toolbarTitleShown) {
        isToolbarTitleShown = toolbarTitleShown;
    }


    @Override
    public int getTotalScrollRange() {
        return totalScrollRange;
    }

    @Override
    public void setTotalScrollRange(int totalScrollRange) {
        this.totalScrollRange = totalScrollRange;
    }

    @Override
    public void setMerchantFollower(MerchantFollower merchantFollower) {
        User storyUser = merchantFollower.getMerchant();
        long storyUserId = storyUser.getId();

        Call<ResponseBody> CallSetMerchantFollowerApi = userRepository.saveMerchantFollower(storyUserId, merchantFollower);
        CallSetMerchantFollowerApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailPresenter.onSuccessSetMerchantFollower();
                } else {
                    merchantDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteMerchantFollower(MerchantFollower merchantFollower) {
        User storyUser = merchantFollower.getMerchant();
        long storyUserId = storyUser.getId();

        Call<ResponseBody> CallDeleteMerchantFollowerApi = userRepository.deleteMerchantFollower(storyUserId, merchantFollower);
        CallDeleteMerchantFollowerApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailPresenter.onSuccessDeleteMerchantFollower();
                } else {
                    merchantDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailPresenter.onNetworkError(null);
            }
        });

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

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}
