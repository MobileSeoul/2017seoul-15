package com.stm.login.reset.password.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.reset.password.interactor.ResetPasswordInteractor;
import com.stm.login.reset.password.interactor.impl.ResetPasswordInteractorImpl;
import com.stm.login.reset.password.presenter.ResetPasswordPresenter;
import com.stm.login.reset.password.view.ResetPasswordView;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter {
    private ResetPasswordView resetPasswordView;
    private ResetPasswordInteractor resetPasswordInteractor;

    public ResetPasswordPresenterImpl(ResetPasswordView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
        this.resetPasswordInteractor = new ResetPasswordInteractorImpl(this);
    }

    @Override
    public void init(User user) {
        resetPasswordInteractor.setUser(user);
        resetPasswordInteractor.setUserRepository();
        resetPasswordView.setToolbarLayout();
        resetPasswordView.showToolbarTitle("임시 비밀번호 발급");

        String avatar = user.getAvatar();
        String name = user.getName();
        String email = user.getEmail();

        resetPasswordView.showUserAvatar(avatar);
        resetPasswordView.showUserEmail(email);
        resetPasswordView.showUserName(name);
    }

    @Override
    public void onEmailChecked(boolean checked) {
        if (checked) {
            resetPasswordView.setSubmitButtonClickable();
            resetPasswordView.setSubmitButtonColorPointColor();
            resetPasswordView.setSubmitButtonTextColorWhite();
        } else {
            resetPasswordView.setSubmitButtonUnclickable();
            resetPasswordView.setSubmitButtonColorGray();
            resetPasswordView.setSubmitButtonTextColorDarkGray();

        }
    }

    @Override
    public void onClickSubmitButton() {
        resetPasswordView.showProgressDialog();
        User user = resetPasswordInteractor.getUser();
        resetPasswordInteractor.updateUserByResettingPassword(user);
    }

    @Override
    public void onSuccessUpdateUserByResettingPassword() {
        resetPasswordView.goneProgressDialog();
        resetPasswordView.showMessage("이메일로 임시비밀번호가 발급되었습니다.");
        resetPasswordView.navigateToBackWithResultOk();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            resetPasswordView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            resetPasswordView.showMessage(httpErrorDto.message());
        }
    }
    @Override
    public void onClickBack() {
        resetPasswordView.navigateToBack();
    }


}
