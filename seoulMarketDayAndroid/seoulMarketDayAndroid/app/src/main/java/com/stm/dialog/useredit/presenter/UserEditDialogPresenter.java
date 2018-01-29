package com.stm.dialog.useredit.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-08-23.
 */

public interface UserEditDialogPresenter {
    void init(User user, int editFlag);

    void onClickEditTextCancel();

    void onTextChanged(int length);

    void afterTextChanged(String content);

    void onClickSubmit(String content);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessUpdateUser(User user);

    void onClickClose();
}
