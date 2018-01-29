package com.stm.user.detail.merchant.fragment.follower.interactor;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public interface MerchantDetailFollowerInteractor {
    User getUser();

    void setUser(User user);

    User getStoryUser();

    void setStoryUser(User storyUser);

    void setUserRepository(String accessToken);

    void setUserRepository();

    void getFollowerListByStoryUserIdAndOffset(long storyUserId, long offset);

    List<User> getFollowers();

    void setFollowers(List<User> followers);

    void setFollowersAddAll(List<User> followers);
}
