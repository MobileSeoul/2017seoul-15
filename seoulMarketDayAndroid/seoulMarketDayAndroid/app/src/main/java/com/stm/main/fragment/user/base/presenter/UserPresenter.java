package com.stm.main.fragment.user.base.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface UserPresenter {
    void init(User user);

    void onCreateView();

    void onClickUserInfo();

    void onResume(User user);

    void onClickSettingsLogout();

    void onClickUserEdit();

    void onClickFollower();

    void onClickFollowingMerchant();

    void onClickFollowingMarket();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessUpdateUser();


    void onClickEditAccount();

    void onClickSettingsExclamation();

    void onClickSettingsInfo();
}
