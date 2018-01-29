package com.stm.user.detail.normal.view;

import com.stm.R;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface UserDetailView {
    void setToolbarLayout();

    void showMessage(String message);

    void showUserNameText(String message);

    void showUserEmailText(String message);

    void showUserPhoneText(String message);

    void showUserAvatar(String message);

    void showUserCover(String message);

    void goneGender();

    void goneEmail();

    void gonePhone();

    void showProgressDialog();

    void goneProgressDialog();

    void onClickBack();

    void onClickAvatar();

    void onClickCover();

    void showUserGenderText(String message);

    void navigateToPhotoDialogActivityWithAvatar(String avatar);

    void navigateToPhotoDialogActivityWithCover(String message);
}
