package com.stm.user.edit.profile.view;

import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-08-22.
 */

public interface UserProfileEditView {
    void showProgressDialog();

    void goneProgressDialog();

    void onClickEditName();

    void onClickEditIntro();

    void onClickEditPhone();

    void onClickEditAvatar();

    void onClickEditCover();

    void setToolbarLayout();

    void setAvatarBringToFront();

    void showToolbarTitle(String message);

    void onClickGenderForMan();

    void onClickGenderForWoman();

    void setGenderForManChecked(boolean isChecked);

    void setGenderForWomanChecked(boolean isChecked);

    void onClickBack();

    void navigateToBack();

    void showMessage(String message);

    void showUserNameText(String message);

    void showUserEmailText(String message);

    void showUserPhoneText(String message);

    void showUserIntroText(String message);

    void setUserGenderRangeChecked(boolean isChecked);

    void setUserPhoneRangeChecked(boolean isChecked);

    void showExternalStoragePermission();

    void navigateToMultiMediaStoreForAvatar();

    void navigateToMultiMediaStoreForCover();

    void showUserAvatar(String message);


    void showUserCover(String message);

    void setUser(User user);

    void navigateToUserEditDialogActivity(int editFlag);
}
