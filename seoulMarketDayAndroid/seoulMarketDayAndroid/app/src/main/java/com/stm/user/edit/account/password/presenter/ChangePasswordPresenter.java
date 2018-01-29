package com.stm.user.edit.account.password.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public interface ChangePasswordPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onClickSubmit(String currentPassword, String newPassword, String newPasswordConfirm);

    void onClickBack();

    void onSuccessGetUserByEmailAndPassword(User user, String encryptedCurrentPassword, String newPassword);

    void onSuccessUpdateUser(User user);
}
