package com.stm.user.detail.merchant.fragment.information.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface MerchantDetailInfoInteractor {
    User getStoryUser();

    void setStoryUser(User user);

    User getUser();

    void setUser(User user);

}
