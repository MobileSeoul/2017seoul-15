package com.stm.user.detail.normal.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface UserDetailInteractor {
    void setUserRepository();

    void setUserRepository(String accessToken);

    void getUserById(long id);

    User getUser();

    void setUser(User user);

    User getStoryUser();

    void setStoryUser(User storyUser);
}
