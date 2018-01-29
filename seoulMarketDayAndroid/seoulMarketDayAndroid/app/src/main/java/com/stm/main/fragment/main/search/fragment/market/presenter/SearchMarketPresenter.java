package com.stm.main.fragment.main.search.fragment.market.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchMarketPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onCreateView();

    void onScrollChange(int difference);

    void onClickMarket(Market market);

    void getMarketListByKeyword(String message);

    void onSuccessGetMarketListByKeywordAndOffset(List<Market> markets, String keyword);
}
