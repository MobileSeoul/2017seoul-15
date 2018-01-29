package com.stm.user.detail.merchant.fragment.information.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface MerchantDetailInfoPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, User storyUser);

    void onCreateView();

    void onClickPhoneText(String phone);

}
