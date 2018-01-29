package com.stm.user.list.following.merchant.interactor.impl;

import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.list.following.merchant.interactor.FollowingMerchantInteractor;
import com.stm.user.list.following.merchant.presenter.FollowingMerchantPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMerchantInteractorImpl implements FollowingMerchantInteractor {
    private FollowingMerchantPresenter followingMerchantPresenter;
    private User user;
    private List<User> users;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(FollowingMerchantInteractorImpl.class);

    public FollowingMerchantInteractorImpl(FollowingMerchantPresenter followingMerchantPresenter) {
        this.followingMerchantPresenter = followingMerchantPresenter;
        this.users = new ArrayList<>();
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
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void setUserRepository() {
        this.userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository(String accessToken) {
        this.userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void usersAddAll(List<User> users) {
        this.users.addAll(users);
    }

    @Override
    public void getFollowingMerchantListByIdAndOffset(long id, long offset) {
        Call<List<User>> callFindFollowerListByStoryUserIdAndOffsetApi = userRepository.findFollowingMerchantListByIdAndOffset(id, offset);
        callFindFollowerListByStoryUserIdAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    followingMerchantPresenter.onSuccessGetFollowingMerchantListByIdAndOffset(users);
                } else {
                    followingMerchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                followingMerchantPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteMerchantFollower(long userId, MerchantFollower merchantFollower, final int position) {
        Call<ResponseBody> callDeleteMerchantFollower = userRepository.deleteMerchantFollower(userId, merchantFollower);
        callDeleteMerchantFollower.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    followingMerchantPresenter.onSuccessDeleteMerchantFollower(position);
                } else {
                    followingMerchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                followingMerchantPresenter.onNetworkError(null);
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
}
