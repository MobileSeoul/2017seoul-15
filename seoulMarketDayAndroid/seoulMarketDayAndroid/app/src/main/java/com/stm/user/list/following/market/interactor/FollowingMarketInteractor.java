package com.stm.user.list.following.market.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.User;
import com.stm.user.list.following.market.adapter.FollowingMarketAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowingMarketInteractor {
    User getUser();

    void setUser(User user);

    List<Market> getMarkets();

    void setMarkets(List<Market> markets);


    void setUserRepository(String accessToken);

    void setMarketRepository(String accessToken);

    void setMarketAddAll(List<Market> markets);

    void getFollowingMarketListByIdAndOffset(long userId, long offset);

    void deleteMarketFollower(long marketId, MarketFollower marketFollower, int position);
}
