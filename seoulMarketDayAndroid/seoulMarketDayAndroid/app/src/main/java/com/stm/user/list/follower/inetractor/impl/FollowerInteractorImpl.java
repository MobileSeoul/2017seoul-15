package com.stm.user.list.follower.inetractor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.list.follower.inetractor.FollowerInteractor;
import com.stm.user.list.follower.presenter.FollowerPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowerInteractorImpl implements FollowerInteractor {
    private FollowerPresenter followerPresenter;
    private User user;
    private List<User> users;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(FollowerInteractorImpl.class);

    public FollowerInteractorImpl(FollowerPresenter followerPresenter) {
        this.followerPresenter = followerPresenter;
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
    public void setUserRepository(){
        this.userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository(String accessToken){
        this.userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void usersAddAll(List<User> users){
        this.users.addAll(users);
    }

    @Override
    public void getFollowerListByStoryUserIdAndOffset(long id, long offset){
        Call<List<User>> callFindFollowerListByStoryUserIdAndOffsetApi = userRepository.findFollowerListByStoryUserIdAndOffset(id, offset);
        callFindFollowerListByStoryUserIdAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    List<User> users = response.body();
                    followerPresenter.onSuccessGetFollowerListByStoryUserIdAndOffset(users);
                }else{
                    followerPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                followerPresenter.onNetworkError(null);
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
