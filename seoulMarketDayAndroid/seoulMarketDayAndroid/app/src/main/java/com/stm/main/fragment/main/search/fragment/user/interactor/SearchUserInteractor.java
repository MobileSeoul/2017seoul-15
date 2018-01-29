package com.stm.main.fragment.main.search.fragment.user.interactor;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchUserInteractor {
    User getUser();

    void setUser(User user);

    List<User> getUsers();

    void setUsers(List<User> users);

    void setUserRepository();

    void setUserRepository(String accessToken);

    String getKeyword();

    void setKeyword(String keyword);

    void setUsersAddAll(List<User> users);

    void getUserListByKeywordAndOffset(String keyword, long offset);

    void getUserListByKeywordAndOffset(String keyword, long userId, long offset);
}
