package com.stm.market.fragment.merchant.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-07-06.
 */

public interface MarketMerchantInteractor {

    void setMarketRepository();

    void setMarketRepository(String accessToken);

    void setUserRepository();

    void setUserRepository(String accessToken);

    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);

    List<User> getUsers();

    void setUsers(List<User> users);

    void getUserListByIdAndOffset(long marketId, long offset);

    void getUserListByIdAndUserIdAndOffset(long marketId, long userId, long offset);

    void setUsersAddAll(List<User> users);

    void setMerchantFollower(MerchantFollower merchantFollower, int position);

    void deleteMerchantFollower(MerchantFollower merchantFollower, int position);
}
