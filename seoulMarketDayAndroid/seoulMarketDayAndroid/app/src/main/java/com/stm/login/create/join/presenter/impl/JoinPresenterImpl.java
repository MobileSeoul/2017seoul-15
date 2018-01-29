package com.stm.login.create.join.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.LoginFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.create.join.interactor.JoinInteractor;
import com.stm.login.create.join.interactor.impl.JoinInteractorImpl;
import com.stm.login.create.join.presenter.JoinPresenter;
import com.stm.login.create.join.view.JoinView;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class JoinPresenterImpl implements JoinPresenter {
    private JoinView joinView;
    private JoinInteractor joinInteractor;


    public JoinPresenterImpl(JoinView joinView) {
        this.joinView = joinView;
        this.joinInteractor = new JoinInteractorImpl(this);
    }

    @Override
    public void init(Market market) {
        joinInteractor.setMarket(market);
        joinView.setToolbarLayout();
        joinView.showToolbarTitle("회원가입");

        joinInteractor.setUserRepository();
    }

    @Override
    public void onClickJoin(User user, String password, String passwordConfirm, String encryptedPassword, boolean isValidEmail) {
        String email = user.getEmail();
        String name = user.getName();
        int passwordLength = password.length();
        int passwordConfirmLength = passwordConfirm.length();
        Market market = joinInteractor.getMarket();

        if (!isValidEmail) {
            joinView.showMessage("이메일을 확인해주세요.");
        } else if ((password.isEmpty() || passwordConfirm.isEmpty()) || (passwordLength < 8 || passwordConfirmLength < 8) || !password.equals(passwordConfirm)) {
            joinView.showMessage("비밀번호를 확인해주세요.");
        } else if (name.isEmpty()) {
            joinView.showMessage("이름을 확인해주세요.");
        } else {
            user.setPassword(encryptedPassword);
            user.setMarket(market);

            joinInteractor.setUser(user);
            joinInteractor.getCheckByEmail(email);
        }

    }


    @Override
    public void onSuccessSetUser() {
        User user = joinInteractor.getUser();
        joinView.setUser(user);
        joinView.navigateToBack();
    }


    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            joinView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            joinView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetCheckByEmail(Boolean isExist) {
        if (isExist) {
            joinView.showMessage("중복된 이메일입니다.");
        } else {
            Market market = joinInteractor.getMarket();
            User user = joinInteractor.getUser();
            if (market != null) {
                user.setLevel(UserLevelFlag.MERCHANT);
            } else {
                user.setLevel(UserLevelFlag.NORMAL);
            }

            user.setLoginCategoryId(LoginFlag.BASIC);
            joinInteractor.setUser();
        }
    }


}
