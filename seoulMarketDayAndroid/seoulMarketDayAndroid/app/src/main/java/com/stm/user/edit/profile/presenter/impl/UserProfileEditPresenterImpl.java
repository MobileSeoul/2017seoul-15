package com.stm.user.edit.profile.presenter.impl;

import com.stm.common.dao.RangeCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.flag.UserEditFlag;
import com.stm.common.flag.UserGenderFlag;
import com.stm.common.flag.UserPrivacyRangeFlag;
import com.stm.user.edit.profile.interactor.UserProfileEditInteractor;
import com.stm.user.edit.profile.interactor.impl.UserProfileEditInteractorImpl;
import com.stm.user.edit.profile.presenter.UserProfileEditPresenter;
import com.stm.user.edit.profile.view.UserProfileEditView;


/**
 * Created by Dev-0 on 2017-08-22.
 */

public class UserProfileEditPresenterImpl implements UserProfileEditPresenter {
    private UserProfileEditView userProfileEditView;
    private UserProfileEditInteractor userEditInteractor;

    public UserProfileEditPresenterImpl(UserProfileEditView userProfileEditView) {
        this.userProfileEditView = userProfileEditView;
        this.userEditInteractor = new UserProfileEditInteractorImpl(this);
    }

    @Override
    public void init(User user) {
        userProfileEditView.setToolbarLayout();
        userProfileEditView.showToolbarTitle("프로필 수정");
        userProfileEditView.setAvatarBringToFront();

        String name = user.getName();
        String email = user.getEmail();
        String avatar = user.getAvatar();
        String cover = user.getCover();
        String intro = user.getIntro();
        String phone = user.getPhone();
        int gender = user.getGender();

        RangeCategory genderRangeCategory = user.getGenderRangeCategory();
        long genderRangeCategoryId = genderRangeCategory.getId();
        RangeCategory phoneRangeCategory = user.getPhoneRangeCategory();
        long phoneRangeCategoryId = phoneRangeCategory.getId();

        String accessToken = user.getAccessToken();
        userEditInteractor.setUser(user);
        userEditInteractor.setUserRepository(accessToken);

        userProfileEditView.showUserNameText(name);
        userProfileEditView.showUserEmailText(email);
        userProfileEditView.showUserAvatar(avatar);
        userProfileEditView.showUserCover(cover);

        if (intro == null || intro.length() == 0) {
            userProfileEditView.showUserIntroText("소개를 입력하세요");
        } else {
            userProfileEditView.showUserIntroText(intro);
        }

        if (phone == null || phone.length() == 0) {
            userProfileEditView.showUserPhoneText("전화번호를 입력하세요");
        } else {
            userProfileEditView.showUserPhoneText(phone);
        }

        if (gender == UserGenderFlag.MAN) {
            userProfileEditView.setGenderForManChecked(true);
        } else {
            userProfileEditView.setGenderForWomanChecked(true);
        }

        if (genderRangeCategoryId == UserPrivacyRangeFlag.PUBLIC) {
            userProfileEditView.setUserGenderRangeChecked(true);
        } else {
            userProfileEditView.setUserGenderRangeChecked(false);
        }

        if (phoneRangeCategoryId == UserPrivacyRangeFlag.PUBLIC) {
            userProfileEditView.setUserPhoneRangeChecked(true);
        } else {
            userProfileEditView.setUserPhoneRangeChecked(false);
        }

        userEditInteractor.setFirstChecked(true);
        userProfileEditView.showExternalStoragePermission();
    }

    @Override
    public void onClickBack() {
        userProfileEditView.navigateToBack();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            userProfileEditView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            userProfileEditView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onRequestPermissionsResultForWriteExternalStorage(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            userProfileEditView.showMessage("권한을 허가해주세요.");
            userProfileEditView.navigateToBack();
        }
    }

    @Override
    public void onActivityResultForCoverPhotoResultOk(FileDto fileDto) {
        User prevUser = userEditInteractor.getUser();
        long prevUserId = prevUser.getId();
        String prevCover = prevUser.getCover();

        User user = new User();
        user.setId(prevUserId);
        user.setCover(prevCover);
        userProfileEditView.showProgressDialog();
        userEditInteractor.updateUser(user, fileDto);
    }

    @Override
    public void onActivityResultForAvatarPhotoResultOk(FileDto fileDto) {
        User prevUser = userEditInteractor.getUser();
        long prevUserId = prevUser.getId();
        String prevAvatar = prevUser.getAvatar();

        User user = new User();
        user.setId(prevUserId);
        user.setAvatar(prevAvatar);
        userProfileEditView.showProgressDialog();
        userEditInteractor.updateUser(user, fileDto);
    }

    @Override
    public void onClickEditCover() {
        userProfileEditView.navigateToMultiMediaStoreForCover();
    }

    @Override
    public void onClickEditAvatar() {
        userProfileEditView.navigateToMultiMediaStoreForAvatar();
    }

    @Override
    public void onSuccessUpdateUserByFileDto(User user) {
        User prevUser = userEditInteractor.getUser();
        String accessToken = prevUser.getAccessToken();
        String prevAvatar = prevUser.getAvatar();
        String prevCover = prevUser.getCover();

        String avatar = user.getAvatar();
        String cover = user.getCover();

        if (!avatar.equals(prevAvatar)) {
            userProfileEditView.showUserAvatar(avatar);
        }
        if (!cover.equals(prevCover)) {
            userProfileEditView.showUserCover(cover);
        }
        user.setAccessToken(accessToken);
        userEditInteractor.setUser(user);
        userProfileEditView.setUser(user);
        userProfileEditView.goneProgressDialog();
    }

    @Override
    public void onSuccessUpdateUser(User user) {
        User prevUser = userEditInteractor.getUser();
        String accessToken = prevUser.getAccessToken();

        user.setAccessToken(accessToken);
        userEditInteractor.setUser(user);
        userProfileEditView.setUser(user);
        userProfileEditView.goneProgressDialog();
    }

    @Override
    public void onClickEditName() {
        userProfileEditView.navigateToUserEditDialogActivity(UserEditFlag.NAME_EDIT);
    }

    @Override
    public void onActivityResultForUserEditResultEdit(User user, int editFlag) {
        userEditInteractor.setUser(user);
        userProfileEditView.setUser(user);
        if (editFlag == UserEditFlag.NAME_EDIT) {
            String name = user.getName();
            userProfileEditView.showUserNameText(name);
        } else if (editFlag == UserEditFlag.INTRO_EDIT) {
            String intro = user.getIntro();
            userProfileEditView.showUserIntroText(intro);
        } else {
            String phone = user.getPhone();
            userProfileEditView.showUserPhoneText(phone);
        }
    }

    @Override
    public void onClickGenderForMan() {
        User prevUser = userEditInteractor.getUser();
        long prevUserId = prevUser.getId();

        User user = new User();
        user.setId(prevUserId);
        user.setGender(UserGenderFlag.MAN);
        userProfileEditView.showProgressDialog();
        userEditInteractor.updateUser(user);
    }

    @Override
    public void onClickGenderForWoman() {
        User prevUser = userEditInteractor.getUser();
        long prevUserId = prevUser.getId();

        User user = new User();
        user.setId(prevUserId);
        user.setGender(UserGenderFlag.FEMALE);
        userProfileEditView.showProgressDialog();
        userEditInteractor.updateUser(user);
    }

    @Override
    public void onClickEditIntro() {
        userProfileEditView.navigateToUserEditDialogActivity(UserEditFlag.INTRO_EDIT);

    }

    @Override
    public void onClickEditPhone() {
        userProfileEditView.navigateToUserEditDialogActivity(UserEditFlag.PHONE_EDIT);

    }


    @Override
    public void onGenderRangeCheckedChanged(boolean isChecked) {
        boolean isFirstChecked = userEditInteractor.isFirstChecked();
        if (isFirstChecked) {
            User prevUser = userEditInteractor.getUser();
            long prevUserId = prevUser.getId();

            User user = new User();
            user.setId(prevUserId);
            userProfileEditView.showProgressDialog();
            if (isChecked) {
                RangeCategory rangeCategory = new RangeCategory();
                rangeCategory.setId(UserPrivacyRangeFlag.PUBLIC);
                user.setGenderRangeCategory(rangeCategory);
                userEditInteractor.updateUser(user);
            } else {
                RangeCategory rangeCategory = new RangeCategory();
                rangeCategory.setId(UserPrivacyRangeFlag.PRIVATE);
                user.setGenderRangeCategory(rangeCategory);
                userEditInteractor.updateUser(user);
            }
        }
    }

    @Override
    public void onPhoneRangeCheckedChanged(boolean isChecked) {
        boolean isFirstChecked = userEditInteractor.isFirstChecked();
        if (isFirstChecked) {
            User prevUser = userEditInteractor.getUser();
            long prevUserId = prevUser.getId();

            User user = new User();
            user.setId(prevUserId);
            userProfileEditView.showProgressDialog();
            if (isChecked) {
                RangeCategory rangeCategory = new RangeCategory();
                rangeCategory.setId(UserPrivacyRangeFlag.PUBLIC);
                user.setPhoneRangeCategory(rangeCategory);
                userEditInteractor.updateUser(user);
            } else {
                RangeCategory rangeCategory = new RangeCategory();
                rangeCategory.setId(UserPrivacyRangeFlag.PRIVATE);
                user.setPhoneRangeCategory(rangeCategory);
                userEditInteractor.updateUser(user);
            }
        }
    }

}
