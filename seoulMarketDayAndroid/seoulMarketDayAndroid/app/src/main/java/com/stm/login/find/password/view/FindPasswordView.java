package com.stm.login.find.password.view;

import android.text.Editable;

import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public interface FindPasswordView {
    void showProgressDialog();

    void goneProgressDialog();

    void showToolbarTitle(String message);

    void showMessage(String message);

    void onTextChanged(Editable editable);

    void onClickSubmitButton();

    void navigateToBack();

    void setSubmitButtonClickable();

    void setSubmitButtonUnclickable();

    void setSubmitButtonColorPointColor();

    void setSubmitButtonColorGray();

    void setSubmitButtonTextColorDarkGray();

    void setSubmitButtonTextColorWhite();

    void onClickBack();

    void setToolbarLayout();

    void navigateToResetPasswordActivity(User user);
}
