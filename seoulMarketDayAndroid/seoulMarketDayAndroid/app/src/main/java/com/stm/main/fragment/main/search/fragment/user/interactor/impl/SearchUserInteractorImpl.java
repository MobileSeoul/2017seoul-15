package com.stm.main.fragment.main.search.fragment.user.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.main.fragment.main.search.fragment.user.interactor.SearchUserInteractor;
import com.stm.main.fragment.main.search.fragment.user.presenter.SearchUserPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchUserInteractorImpl implements SearchUserInteractor {
    private SearchUserPresenter searchUserPresenter;
    private User user;
    private List<User> users;
    private UserRepository userRepository;
    private String keyword;
    private static final Logger logger = LoggerFactory.getLogger(SearchUserInteractorImpl.class);

    public SearchUserInteractorImpl(SearchUserPresenter searchUserPresenter) {
        this.searchUserPresenter = searchUserPresenter;
        this.users = new ArrayList<>();
    }

    @Override
    public User getUser() {
        return this.user;
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
    public String getKeyword() {
        return keyword;
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void setUsersAddAll(List<User> users) {
        this.users.addAll(users);
    }

    @Override
    public void getUserListByKeywordAndOffset(final String keyword, long offset) {
        Call<List<User>> callFindUserListByKeywordAndOffsetApi = userRepository.findUserListByKeywordAndOffset(keyword, offset);
        callFindUserListByKeywordAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    searchUserPresenter.onSuccessGetUserListByKeywordAndOffset(users, keyword);
                } else {
                    searchUserPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                searchUserPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getUserListByKeywordAndOffset(final String keyword, long userId, long offset) {
        Call<List<User>> callFindUserListByKeywordAndOffsetApi = userRepository.findUserListByKeywordAndOffset(keyword, userId, offset);
        callFindUserListByKeywordAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    searchUserPresenter.onSuccessGetUserListByKeywordAndOffset(users, keyword);
                } else {
                    searchUserPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                searchUserPresenter.onNetworkError(null);
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
