package com.stm.dialog.useredit.view;

import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-08-23.
 */

public interface UserEditDialogView {
    void showProgressDialog();

    void goneProgressDialog();

    void onClickSubmit();

    void onClickClose();

    void setSubmitButtonClickable();

    void setSubmitButtonUnclickable();

    void setSubmitButtonColorPointColor();

    void setSubmitButtonColorDarkGray();

    void showEditText(String message);

    void showTextMaxSize(int size);

    void showTextSize(int size);

    void setEditTextMaxLength(int length);

    void setEditTextAddTextChangedListener();

    void setEditTextPhoneInputType();

    void showMessage(String message);

    void onClickEditTextCancel();

    void showToolbarTitle(String message);

    void showDialogMessage(String message);

    void navigateToBack();

    void navigateToBackWithResultEdit(User user, int editFlag);

    void showTextSizeCount(int size);

    void setUser(User user);
}
