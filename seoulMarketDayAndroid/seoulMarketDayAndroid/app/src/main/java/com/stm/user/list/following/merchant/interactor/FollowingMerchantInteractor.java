package com.stm.user.list.following.merchant.interactor;

import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowingMerchantInteractor {


    User getUser();

    void setUser(User user);

    List<User> getUsers();

    void setUsers(List<User> users);

    void setUserRepository();

    void setUserRepository(String accessToken);

    void usersAddAll(List<User> users);

    void getFollowingMerchantListByIdAndOffset(long id, long offset);


    void deleteMerchantFollower(long userId, MerchantFollower merchantFollower, int position);
}
