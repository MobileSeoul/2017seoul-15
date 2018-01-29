package com.stm.user.edit.account.base.view;

import com.stm.R;

import butterknife.OnCheckedChanged;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public interface EditAccountView {
    void setIncludedToolbarLayout();

    void showMessage(String message);

    void showToolbarTitle(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setSwitchChecked(boolean checked);

    void onNotificationCheckedChanged(boolean checked);

    void onClickChangePassword();

    void onClickBack();

    void onClickExit();

    void navigateToBack();

    void navigateToChangePasswordActivity();

    void setAllowedForNotification(boolean checked);

    void navigateToExitActivity();
}
