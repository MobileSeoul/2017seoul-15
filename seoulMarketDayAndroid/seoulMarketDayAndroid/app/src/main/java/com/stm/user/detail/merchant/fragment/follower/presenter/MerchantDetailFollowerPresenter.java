package com.stm.user.detail.merchant.fragment.follower.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public interface MerchantDetailFollowerPresenter {
    void init(User user, User storyUser);

    void onCreateView();

    void onScrollChange(int difference);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetFollowerListByStoryUserIdAndOffset(List<User> newFollowers);

    void onClickFollower(User user);
}
