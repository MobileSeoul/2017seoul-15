package com.stm.user.edit.account.base.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.LoginFlag;
import com.stm.user.edit.account.base.interactor.EditAccountInteractor;
import com.stm.user.edit.account.base.interactor.impl.EditAccountInteractorImpl;
import com.stm.user.edit.account.base.presenter.EditAccountPresenter;
import com.stm.user.edit.account.base.view.EditAccountView;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public class EditAccountPresenterImpl implements EditAccountPresenter {
    private EditAccountInteractor editAccountInteractor;
    private EditAccountView editAccountView;

    public EditAccountPresenterImpl(EditAccountView editAccountView) {
        this.editAccountInteractor = new EditAccountInteractorImpl(this);
        this.editAccountView = editAccountView;
    }

    @Override
    public void init(User user, boolean isAllowedForNotification) {
        editAccountView.showProgressDialog();
        editAccountInteractor.setUser(user);
        editAccountView.setIncludedToolbarLayout();
        editAccountView.showToolbarTitle("계정 설정");
        editAccountView.setSwitchChecked(isAllowedForNotification);
        editAccountView.goneProgressDialog();
    }

    @Override
    public void onClickChangePassword() {
        User user = editAccountInteractor.getUser();
        int loginCategoryId = user.getLoginCategoryId();
        if(loginCategoryId == LoginFlag.BASIC) {
            editAccountView.navigateToChangePasswordActivity();
        } else {
            editAccountView.showMessage("SNS 계정 회원은 비밀번호를 변경할 수 없습니다.");
        }
    }

    @Override
    public void onClickExit() {
        editAccountView.navigateToExitActivity();
    }

    @Override
    public void onClickBack() {
        editAccountView.navigateToBack();
    }

    @Override
    public void onNotificationCheckedChanged(boolean checked) {
        if (checked) {
            editAccountView.setAllowedForNotification(true);
        } else {
            editAccountView.setAllowedForNotification(false);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            editAccountView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            editAccountView.showMessage(httpErrorDto.message());
        }
    }


}
