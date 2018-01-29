package com.stm.user.list.following.merchant.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowingMerchantPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onScrollChange(int difference);

    void onSuccessGetFollowingMerchantListByIdAndOffset(List<User> users);

    void onSuccessDeleteMerchantFollower(int position);

    void onClickFollowingMerchant(User user);

    void onClickBack();

    void onClickFollowCancel(User user, int position);

}
