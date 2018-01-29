package com.stm.market.fragment.merchant.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev-0 on 2017-07-06.
 */

public interface MarketMerchantPresenter {
    void init(User user, Market market);

    void onCreateView();

    void onScrollChange(int difference);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetUserListByIdAndOffset(List<User> users);

    void onClickMerchant(User user, int position);

    void onActivityResultForUserResultOk(User storyUser, int position);

    void onClickFollow(User merchant, int position);

    void onClickFollowCancel(User merchant, int position);

    void onSuccessSetMerchantFollower(int position);

    void onSuccessDeleteMerchantFollower(int position);
}
