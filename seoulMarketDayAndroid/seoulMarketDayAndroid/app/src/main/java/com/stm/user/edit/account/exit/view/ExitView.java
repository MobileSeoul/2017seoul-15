package com.stm.user.edit.account.exit.view;

import com.stm.R;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitView {
    void setIncludedToolbarLayout();

    void showMessage(String message);

    void showToolbarTitle(String message);

    void showUserName(String message);

    void showExitCategoryName(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setEditTextChangedListener();

    void setSubmitButtonActivated();

    void setSubmitButtonInactivated();

    void onClickBack();

    void onClickExitCategory();

    void onClickExitSubmit();

    void navigateToBack();

    void navigateToExitCategoryDialogActivity();
}
