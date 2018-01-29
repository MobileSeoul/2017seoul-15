package com.stm.main.fragment.user.base.view;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface UserView {
    void showUserNameText(String message);

    void showUserAvatar(String message);

    void goneFollowCustomer();

    void onClickFollowingMerchant();

    void onClickFollower();

    void goneFollowMerchant();

    void onClickUserInfo();

    void onClickFollowingMarket();

    void onClickEditAccount();

//    void onClickSettingsTerms();

    void onClickSettingsExclamation();

    void onClickSettingsInfo();

    void onClickUserEdit();

    void onClickSettingsLogout();

    void navigateToUserDetailActivity(User user);

    void navigateToMerchantDetailActivity(User user);

    void showMessage(String message);

    void removeUser();

    void onChangeForUserCreateFragment();

    void onDataChangeForUserFragment();

    void navigateToUserEditActivity();

    void navigateToFollowerActivity();

    void navigateToFollowingMerchantActivity();

    void navigateToFollowingMarketActivity();

    void navigateToEditAccountActivity();

    void showProgressDialog();

    void goneProgressDialog();

    void navigateToOpinionActivity();

    void navigateToAppInfoActivity();
}
