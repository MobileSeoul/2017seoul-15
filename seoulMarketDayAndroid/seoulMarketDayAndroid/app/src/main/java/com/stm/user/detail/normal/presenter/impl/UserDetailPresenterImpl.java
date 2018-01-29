package com.stm.user.detail.normal.presenter.impl;

import com.stm.common.dao.RangeCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.UserGenderFlag;
import com.stm.common.flag.UserPrivacyRangeFlag;
import com.stm.user.detail.normal.interactor.UserDetailInteractor;
import com.stm.user.detail.normal.interactor.impl.UserDetailInteractorImpl;
import com.stm.user.detail.normal.presenter.UserDetailPresenter;
import com.stm.user.detail.normal.view.UserDetailView;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class UserDetailPresenterImpl implements UserDetailPresenter {
    private UserDetailInteractor userDetailInteractor;
    private UserDetailView userDetailView;

    public UserDetailPresenterImpl(UserDetailView userDetailView) {
        this.userDetailInteractor = new UserDetailInteractorImpl(this);
        this.userDetailView = userDetailView;
    }

    @Override
    public void init(User user, Long storyUserId) {
        userDetailView.showProgressDialog();
        userDetailInteractor.setUser(user);

        if (user != null) {
            String accessToken = user.getAccessToken();
            userDetailInteractor.setUserRepository(accessToken);
        } else {
            userDetailInteractor.setUserRepository();
        }
        userDetailInteractor.getUserById(storyUserId);

        userDetailView.setToolbarLayout();
    }

    @Override
    public void onSuccessGetUserById(User storyUser) {
        String name = storyUser.getName();
        String email = storyUser.getEmail();
        String phone = storyUser.getPhone();
        String avatar = storyUser.getAvatar();
        String cover = storyUser.getCover();
        int gender = storyUser.getGender();
        RangeCategory genderRangeCategory = storyUser.getGenderRangeCategory();
        RangeCategory phoneRangeCategory = storyUser.getPhoneRangeCategory();
        long genderRangeCategoryId = genderRangeCategory.getId();
        long phoneRangeCategoryId = phoneRangeCategory.getId();

        userDetailView.showUserAvatar(avatar);
        userDetailView.showUserCover(cover);
        userDetailView.showUserNameText(name);

        if (email != null) {
            userDetailView.showUserEmailText(email);
        } else {
            userDetailView.goneEmail();
        }

        if (phoneRangeCategoryId == UserPrivacyRangeFlag.PUBLIC) {
            if (phone != null) {
                userDetailView.showUserPhoneText(phone);
            } else {
                userDetailView.gonePhone();
            }
        } else {
            userDetailView.gonePhone();
        }

        if (genderRangeCategoryId == UserPrivacyRangeFlag.PUBLIC) {
            if (gender == UserGenderFlag.FEMALE) {
                userDetailView.showUserGenderText("여성");
            }

            if (gender == UserGenderFlag.MAN) {
                userDetailView.showUserGenderText("남성");
            }
        } else {
            userDetailView.goneGender();
        }

        userDetailView.goneProgressDialog();
    }

    @Override
    public void onClickAvatar() {
        User user = userDetailInteractor.getStoryUser();
        String avatar = user.getAvatar();
        userDetailView.navigateToPhotoDialogActivityWithAvatar(avatar);
    }

    @Override
    public void onClickCover() {
        User user = userDetailInteractor.getStoryUser();
        String cover = user.getCover();
        userDetailView.navigateToPhotoDialogActivityWithCover(cover);
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            userDetailView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            userDetailView.showMessage(httpErrorDto.message());
        }
    }

}
