package com.stm.market.base.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public interface MarketPresenter {
    void init(User user, Market market);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onResume(User user);

    void onOffsetChanged(int totalScrollRange, int verticalOffset);

    void onClickFollow();

    void onClickFollowCancel();

    void onClickAddress();

    void onSuccessGetMarketById(Market market);

    void onSuccessSetMarketFollower();

    void onSuccessDeleteMarketFollower();


    void onRefresh(int position);

    void onSuccessGetMarketByIdForRefresh(Market market, int position);

    void onChangeFragment();
}
