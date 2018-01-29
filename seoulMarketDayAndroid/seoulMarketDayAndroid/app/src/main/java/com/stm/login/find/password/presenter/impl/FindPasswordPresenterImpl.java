package com.stm.login.find.password.presenter.impl;

import android.text.Editable;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.find.password.interactor.impl.FindPasswordInteractorImpl;
import com.stm.login.find.password.interactor.FindPasswordInteractor;
import com.stm.login.find.password.presenter.FindPasswordPresenter;
import com.stm.login.find.password.view.FindPasswordView;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public class FindPasswordPresenterImpl implements FindPasswordPresenter {
    private FindPasswordView findPasswordView;
    private FindPasswordInteractor findPasswordInteractor;


    public FindPasswordPresenterImpl(FindPasswordView findPasswordView) {
        this.findPasswordView = findPasswordView;
        this.findPasswordInteractor = new FindPasswordInteractorImpl(this);
    }

    @Override
    public void init() {
        findPasswordView.setToolbarLayout();
        findPasswordView.showToolbarTitle("계정 찾기");
        findPasswordInteractor.setUserRepository();
    }

    @Override
    public void onTextChanged(Editable editable) {
        String password = editable.toString();

        if (password.length() > 0) {
            findPasswordView.setSubmitButtonClickable();
            findPasswordView.setSubmitButtonColorPointColor();
            findPasswordView.setSubmitButtonTextColorWhite();
        } else {
            findPasswordView.setSubmitButtonUnclickable();
            findPasswordView.setSubmitButtonColorGray();
            findPasswordView.setSubmitButtonTextColorDarkGray();
        }
    }

    @Override
    public void onClickBack() {
        findPasswordView.navigateToBack();
    }

    @Override
    public void onClickSubmitButton(String content) {
        findPasswordView.showProgressDialog();
        findPasswordInteractor.getUserByEmail(content);
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            findPasswordView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            findPasswordView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetUserByEmail(User user) {
        findPasswordView.goneProgressDialog();
        if (user == null) {
            findPasswordView.showMessage("이메일을 확인해주세요");
        } else {
            findPasswordView.navigateToResetPasswordActivity(user);
        }
    }

    @Override
    public void onActivityResultUserEditResultOk() {
        findPasswordView.navigateToBack();
    }

}
