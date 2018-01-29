package com.stm.user.edit.account.exit.presenter.impl;

import com.stm.common.dao.Exit;
import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.edit.account.exit.interactor.ExitInteractor;
import com.stm.user.edit.account.exit.interactor.impl.ExitInteractorImpl;
import com.stm.user.edit.account.exit.presenter.ExitPresenter;
import com.stm.user.edit.account.exit.view.ExitView;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitPresenterImpl implements ExitPresenter {
    private ExitInteractor exitInteractor;
    private ExitView exitView;

    public ExitPresenterImpl(ExitView exitView) {
        this.exitInteractor = new ExitInteractorImpl(this);
        this.exitView = exitView;
    }

    @Override
    public void init(User user) {
        exitView.showProgressDialog();
        exitInteractor.setUser(user);

        String name = user.getName();
        String accessToken = user.getAccessToken();

        exitView.showUserName(name);
        exitInteractor.setExitRepository(accessToken);
        exitView.setIncludedToolbarLayout();
        exitView.showToolbarTitle("회원 탈퇴");
        exitView.setEditTextChangedListener();

        exitView.goneProgressDialog();
    }

    @Override
    public void onClickBack() {
        exitView.navigateToBack();
    }

    @Override
    public void onClickAccountExitCategory() {
        exitView.navigateToExitCategoryDialogActivity();
    }

    @Override
    public void onActivityResultForExitCategoryDialogResultOk(ExitCategory exitCategory) {
        exitInteractor.setExitCategory(exitCategory);
        String name = exitCategory.getName();
        exitView.showExitCategoryName(name);
    }

    @Override
    public void onClickExitSubmit(String message) {
        int messageLength = message.length();
        ExitCategory exitCategory = exitInteractor.getExitCategory();

        if (exitCategory != null) {
            if (messageLength > 0) {
                exitView.showProgressDialog();
                User user = exitInteractor.getUser();

                Exit exit = new Exit();
                exit.setExitCategory(exitCategory);
                exit.setUser(user);
                exit.setContent(message);

                exitInteractor.setExit(exit);
            } else {
                exitView.showMessage("탈퇴 사유의 상세 설명을 입력해주세요.");
            }
        } else {
            exitView.showMessage("탈퇴 사유를 선택해주세요");
        }
    }

    @Override
    public void onSuccessSetExit(Boolean isExist) {
        if(isExist) {
            exitView.goneProgressDialog();
            exitView.showMessage("탈퇴 승인을 기다리는 회원입니다.");
        } else {
            exitView.goneProgressDialog();
            exitView.showMessage("탈퇴 신청이 완료되었습니다.");
            exitView.navigateToBack();
        }
    }

    @Override
    public void afterTextChanged(String message) {
        int messageLength = message.length();
        if (messageLength > 0) {
            exitView.setSubmitButtonActivated();
        } else {
            exitView.setSubmitButtonInactivated();
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            exitView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            exitView.showMessage(httpErrorDto.message());
        }
    }

}
