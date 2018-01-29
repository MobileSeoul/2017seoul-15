package com.stm.user.list.follower.inetractor;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowerInteractor {
    User getUser();

    void setUser(User user);

    List<User> getUsers();

    void setUsers(List<User> users);

    void setUserRepository();

    void setUserRepository(String accessToken);

    void usersAddAll(List<User> users);

    void getFollowerListByStoryUserIdAndOffset(long id, long offset);
}
