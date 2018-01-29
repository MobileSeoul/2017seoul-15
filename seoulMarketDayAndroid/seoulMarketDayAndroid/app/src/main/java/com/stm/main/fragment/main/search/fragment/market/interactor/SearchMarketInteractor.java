package com.stm.main.fragment.main.search.fragment.market.interactor;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchMarketInteractor {
    User getUser();

    void setUser(User user);

    List<Market> getMarkets();

    void setMarkets(List<Market> markets);

    String getKeyword();

    void setKeyword(String keyword);

    void setMarketRepository();

    void setMarketRepository(String accessToken);

    void setMarketsAddAll(List<Market> markets);

    void getMarketListByKeywordAndOffset(String keyword, long userId, long offset);

    void getMarketListByKeywordAndOffset(String keyword, long offset);
}
