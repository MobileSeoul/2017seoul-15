package com.stm.login.reset.password.view;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public interface ResetPasswordView {
    void showToolbarTitle(String message);

    void showMessage(String message);

    void onClickBack();

    void showUserAvatar(String avatar);

    void showUserName(String name);

    void showUserEmail(String email);

    void navigateToBack();

    void setSubmitButtonClickable();

    void setSubmitButtonUnclickable();

    void setSubmitButtonColorPointColor();


    void setSubmitButtonColorGray();

    void setSubmitButtonTextColorDarkGray();

    void setSubmitButtonTextColorWhite();

    void onClickSubmitButton();

    void showProgressDialog();

    void goneProgressDialog();

    void setToolbarLayout();

    void navigateToBackWithResultOk();
}
