package com.stm.user.create.opinion.view;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionVIew {
    void showProgressDialog();

    void showOpinionCategoryName(String message);

    void onClickOpinionCategory();

    void onClickOpinionSubmit();

    void goneProgressDialog();

    void showToolbarTitle(String message);

    void showUserName(String message);

    void setToolbarLayout();

    void setEditTextChangedListener();

    void setSubmitButtonActivated();

    void setSubmitButtonInactivated();

    void onClickBack();

    void navigateToBack();

    void showMessage(String message);


    void navigateToOpinionCategoryDialogActivity();
}
