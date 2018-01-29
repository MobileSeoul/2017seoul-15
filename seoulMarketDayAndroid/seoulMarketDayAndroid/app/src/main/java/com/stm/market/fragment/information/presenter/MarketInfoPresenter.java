package com.stm.market.fragment.information.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-07-04.
 */

public interface MarketInfoPresenter {
    void init(User user, Market market);

    void onCreateView();

    void onClickPhoneText(String phone);

    void onClickHomepageText(String homepage);
}
