package com.stm.market.base.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.MarketUser;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public interface MarketInteractor {
    void setMarketRepository(String accessToken);

    User getUser();

    void setUser(User user);

    void setMarketRepository();

    void getMarketById(long id);

    void getMarketByIdAndUserId(long id, long userId);

    void getMarketByIdForRefresh(long marketId, int position);

    void setMarketFollower(Market market, MarketFollower marketFollower);

    void deleteMarketFollower(Market market, MarketFollower marketFollower);

    Market getMarket();

    void setMarket(Market market);

    boolean isToolbarTitleShown();

    void setToolbarTitleShown(boolean toolbarTitleShown);

    int getTotalScrollRange();

    void setTotalScrollRange(int totalScrollRange);


    void getMarketByIdForRefresh(long marketId, long userId, int position);
}
