package com.stm.user.edit.account.password.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.util.LoginUtil;
import com.stm.user.edit.account.password.interactor.ChangePasswordInteractor;
import com.stm.user.edit.account.password.interactor.impl.ChangePasswordInteractorImpl;
import com.stm.user.edit.account.password.presenter.ChangePasswordPresenter;
import com.stm.user.edit.account.password.view.ChangePasswordView;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordInteractor changePasswordInteractor;
    private ChangePasswordView changePasswordView;

    public ChangePasswordPresenterImpl(ChangePasswordView changePasswordView) {
        this.changePasswordInteractor = new ChangePasswordInteractorImpl(this);
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void init(User user) {
        changePasswordView.showProgressDialog();

        changePasswordInteractor.setUser(user);
        changePasswordView.setIncludedToolbarLayout();
        changePasswordView.showToolbarTitle("비밀번호 변경");
        changePasswordView.setEditTextAddTextChangedListener();

        String accessToken = user.getAccessToken();
        changePasswordInteractor.setUserRepository(accessToken);

        changePasswordView.goneProgressDialog();
    }

    @Override
    public void onClickSubmit(String currentPassword, String newPassword, String newPasswordConfirm) {
        if (newPassword.equals(newPasswordConfirm)) {
            int newPasswordLength = newPassword.length();
            if (newPasswordLength > 7) {
                User user = changePasswordInteractor.getUser();
                String email = user.getEmail();
                String encryptedCurrentPassword = LoginUtil.encryptWithSHA256(currentPassword);

                changePasswordInteractor.getUserByEmailAndPassword(email, encryptedCurrentPassword, newPassword);
            } else {
                changePasswordView.showMessage("8자 이상의 비밀번호를 입력해주세요.");
            }
        } else {
            changePasswordView.showMessage("새로운 비밀번호가 일치하지 않습니다.");
        }

    }

    @Override
    public void onClickBack() {
        changePasswordView.navigateToBack();
    }

    @Override
    public void onSuccessGetUserByEmailAndPassword(User user, String encryptedCurrentPassword, String newPassword) {
        if (user != null) {
            if (encryptedCurrentPassword.equals(newPassword)) {
                changePasswordView.showMessage("기존 비밀번호와 다른 비밀번호를 입력해주세요.");
            } else {
                long userId = user.getId();
                String encryptedPassword = LoginUtil.encryptWithSHA256(newPassword);
                User newUser = new User();
                newUser.setId(userId);
                newUser.setPassword(encryptedPassword);

                changePasswordInteractor.updateUser(userId, newUser);
            }
        } else {
            changePasswordView.showMessage("올바른 비밀번호를 입력해주세요.");
        }
    }

    @Override
    public void onSuccessUpdateUser(User user) {
        changePasswordInteractor.setUser(user);
        changePasswordView.showMessage("비밀번호가 변경되었습니다.");
        changePasswordView.navigateToBack();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            changePasswordView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            changePasswordView.showMessage(httpErrorDto.message());
        }
    }


}
