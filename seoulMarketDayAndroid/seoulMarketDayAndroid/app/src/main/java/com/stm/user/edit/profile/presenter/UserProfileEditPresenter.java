package com.stm.user.edit.profile.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-08-22.
 */

public interface UserProfileEditPresenter {
    void onClickBack();

    void onBackPressed();

    void init(User user);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onRequestPermissionsResultForWriteExternalStorage(int[] grantResults);

    void onActivityResultForCoverPhotoResultOk(FileDto fileDto);

    void onActivityResultForAvatarPhotoResultOk(FileDto fileDto);

    void onClickEditCover();

    void onClickEditAvatar();

    void onSuccessUpdateUserByFileDto(User user);

    void onClickEditIntro();

    void onClickEditPhone();

    void onGenderRangeCheckedChanged(boolean isChecked);

    void onPhoneRangeCheckedChanged(boolean isChecked);


    void onSuccessUpdateUser(User user);

    void onClickEditName();

    void onActivityResultForUserEditResultEdit(User user, int editFlag);

    void onClickGenderForMan();

    void onClickGenderForWoman();
}
