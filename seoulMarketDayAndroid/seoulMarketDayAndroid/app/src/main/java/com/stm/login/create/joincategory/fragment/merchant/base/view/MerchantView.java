package com.stm.login.create.joincategory.fragment.merchant.base.view;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface MerchantView {
    void onClickSearchMarket();

    void navigateToSearchMarketActivity();

    void showMarketName(String name);

//    void setJoinActivated();
//
//    void setJoinDeactivated();

    void showJoinButton();

    void onClickJoin();

    void navigateToJoinActivity(Market market);

    void showMessage(String message);

    void navigateToBack();

    void setUser(User user);
}
