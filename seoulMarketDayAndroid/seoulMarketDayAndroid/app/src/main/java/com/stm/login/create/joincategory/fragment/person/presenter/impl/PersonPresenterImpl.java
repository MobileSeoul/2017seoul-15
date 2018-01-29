package com.stm.login.create.joincategory.fragment.person.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.UserLevelFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.create.joincategory.fragment.person.interactor.PersonInteractor;
import com.stm.login.create.joincategory.fragment.person.interactor.impl.PersonInteractorImpl;
import com.stm.login.create.joincategory.fragment.person.presenter.PersonPresenter;
import com.stm.login.create.joincategory.fragment.person.view.PersonView;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class PersonPresenterImpl implements PersonPresenter {
    private PersonView personView;
    private PersonInteractor personInteractor;

    public PersonPresenterImpl(PersonView personView) {
        this.personView = personView;
        this.personInteractor = new PersonInteractorImpl(this);
    }

    @Override
    public void init(User user) {
        personInteractor.setUser(user);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void onClickJoin() {
        User user = personInteractor.getUser();
        if (user != null) {
            user.setLevel(UserLevelFlag.NORMAL);
            personInteractor.setUser(user);
            personInteractor.setUserRepository();
            personInteractor.setUser();
        } else {
            personView.navigateToJoinActivity();
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            personView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            personView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessSetUser() {
        User user = personInteractor.getUser();
        personView.setUser(user);
        personView.navigateToBack();
    }

}
