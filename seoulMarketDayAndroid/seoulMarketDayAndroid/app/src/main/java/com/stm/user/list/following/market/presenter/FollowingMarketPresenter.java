package com.stm.user.list.following.market.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.list.following.market.adapter.FollowingMarketAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowingMarketPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetFollowingMarketListByIdAndOffset(List<Market> markets);

    void init(User user);

    void onClickBack();

    void onScrollChange(int difference);

    void onClickFollowingMarket(Market market);

    void onClickFollowCancel(Market market, int position);

    void onSuccessDeleteMarketFollower(int position);


}
