package com.stm.login.create.joincategory.fragment.merchant.base.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface MerchantPresenter {
    void onCreateView();
    void init(User user);

    void onActivityResultForSearchMarketResultOk(Market market);

    void onClickJoin();

    void onClickSearchMarket();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessSetUser();
}
