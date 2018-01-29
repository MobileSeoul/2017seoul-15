package com.stm.user.list.follower.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowerPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onSuccessGetFollowerListByStoryUserIdAndOffset(List<User> users);

    void onClickBack();

    void onScrollChange(int difference);

    void onClickFollower(User user);
}
