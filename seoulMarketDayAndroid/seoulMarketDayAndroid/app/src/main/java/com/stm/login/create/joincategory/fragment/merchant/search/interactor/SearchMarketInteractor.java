package com.stm.login.create.joincategory.fragment.merchant.search.interactor;

import com.stm.common.dao.Market;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public interface SearchMarketInteractor {

    void setMarketRepositoryCreation();

    String getKeyword();

    void setKeyword(String keyword);

    void getMarketListByKeyword(String keyword, long offset);

    List<Market> getMarkets();

    void setMarketsAddAll(List<Market> markets);

    void setMarkets(List<Market> markets);
}
