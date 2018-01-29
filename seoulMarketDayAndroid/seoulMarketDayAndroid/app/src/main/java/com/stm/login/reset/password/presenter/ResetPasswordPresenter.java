package com.stm.login.reset.password.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public interface ResetPasswordPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void onClickBack();

    void init(User user);

    void onEmailChecked(boolean checked);

    void onClickSubmitButton();

    void onSuccessUpdateUserByResettingPassword();
}
