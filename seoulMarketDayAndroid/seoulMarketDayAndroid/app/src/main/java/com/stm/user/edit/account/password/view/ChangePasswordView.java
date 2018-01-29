package com.stm.user.edit.account.password.view;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public interface ChangePasswordView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void showToolbarTitle(String message);

    void setIncludedToolbarLayout();

    void onClickBack();

    void setEditTextAddTextChangedListener();

    void onClickSubmit();

    void navigateToBack();
}
