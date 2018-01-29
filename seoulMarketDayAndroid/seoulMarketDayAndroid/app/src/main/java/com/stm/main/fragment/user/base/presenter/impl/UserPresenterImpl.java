package com.stm.main.fragment.user.base.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.UserLevelFlag;
import com.stm.main.fragment.user.base.interactor.UserInteractor;
import com.stm.main.fragment.user.base.interactor.impl.UserInteractorImpl;
import com.stm.main.fragment.user.base.presenter.UserPresenter;
import com.stm.main.fragment.user.base.view.UserView;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class UserPresenterImpl implements UserPresenter {
    private UserInteractor userInteractor;
    private UserView userView;

    public UserPresenterImpl(UserView userView) {
        this.userInteractor = new UserInteractorImpl(this);
        this.userView = userView;
    }

    @Override
    public void init(User user) {
        userView.showProgressDialog();
        userInteractor.setUser(user);

        String accessToken = user.getAccessToken();
        userInteractor.setUserRepository(accessToken);
    }

    @Override
    public void onCreateView() {
        User user = userInteractor.getUser();
        String name = user.getName();
        String avatar = user.getAvatar();
        int level = user.getLevel();

        userView.showUserNameText(name);
        userView.showUserAvatar(avatar);

        if (level == UserLevelFlag.NORMAL) {
            userView.goneFollowCustomer();
        } else {
            userView.goneFollowMerchant();
        }

        userView.goneProgressDialog();
    }


    @Override
    public void onResume(User user) {
        if (user != null && userInteractor.getUser() != null && !user.equals(userInteractor.getUser())) {
            userInteractor.setUser(user);
            userView.onDataChangeForUserFragment();
        }

        if(user == null && userInteractor.getUser() != null){
            userInteractor.setUser(null);
            userView.onChangeForUserCreateFragment();
        }
    }

    @Override
    public void onClickUserInfo() {
        User user = userInteractor.getUser();
        int level = user.getLevel();

        if (level == UserLevelFlag.MERCHANT) {
            userView.navigateToMerchantDetailActivity(user);
        } else {
            userView.navigateToUserDetailActivity(user);
        }
    }


    @Override
    public void onClickSettingsLogout() {
        userView.showProgressDialog();

        User prevUser = userInteractor.getUser();
        long userId = prevUser.getId();

        User user = new User();
        user.setId(userId);
        user.setFcmToken("");
        user.setDeviceId("");
        userInteractor.updateUser(user);
    }

    @Override
    public void onClickUserEdit() {
        userView.navigateToUserEditActivity();
    }

    @Override
    public void onClickFollower() {
        userView.navigateToFollowerActivity();
    }

    @Override
    public void onClickFollowingMerchant() {
        userView.navigateToFollowingMerchantActivity();
    }

    @Override
    public void onClickFollowingMarket() {
        userView.navigateToFollowingMarketActivity();
    }


    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            userView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            userView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessUpdateUser() {
        userView.removeUser();
        userInteractor.setUser(null);

        userView.goneProgressDialog();
        userView.showMessage("로그아웃 되었습니다.");
        userView.onChangeForUserCreateFragment();
    }

    @Override
    public void onClickEditAccount() {
        userView.navigateToEditAccountActivity();
    }

    @Override
    public void onClickSettingsExclamation() {
        userView.navigateToOpinionActivity();
    }

    @Override
    public void onClickSettingsInfo() {
        userView.navigateToAppInfoActivity();
    }

}
