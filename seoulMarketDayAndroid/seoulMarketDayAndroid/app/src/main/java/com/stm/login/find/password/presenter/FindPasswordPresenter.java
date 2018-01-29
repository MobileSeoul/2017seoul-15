package com.stm.login.find.password.presenter;

import android.text.Editable;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public interface FindPasswordPresenter {
    void init();

    void onTextChanged(Editable editable);

    void onClickBack();

    void onClickSubmitButton(String content);

    void onNetworkError(HttpErrorDto httpErrorDto);


    void onSuccessGetUserByEmail(User user);

    void onActivityResultUserEditResultOk();
}
