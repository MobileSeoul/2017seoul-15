package com.stm.main.fragment.user.create.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.main.fragment.user.create.interactor.UserCreateInteractor;
import com.stm.main.fragment.user.create.interactor.impl.UserCreateInteractorImpl;
import com.stm.main.fragment.user.create.presenter.UserCreatePresenter;
import com.stm.main.fragment.user.create.view.UserCreateView;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class UserCreatePresenterImpl implements UserCreatePresenter {
    private UserCreateInteractor userCreateInteractor;
    private UserCreateView userCreateView;

    public UserCreatePresenterImpl(UserCreateView userCreateView) {
        this.userCreateInteractor = new UserCreateInteractorImpl(this);
        this.userCreateView = userCreateView;
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void init() {

    }

    @Override
    public void onResume(User user) {
        if (user != null) {
            userCreateView.onChangeForUserFragment();
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            userCreateView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            userCreateView.showMessage(httpErrorDto.message());
        }
    }


}
