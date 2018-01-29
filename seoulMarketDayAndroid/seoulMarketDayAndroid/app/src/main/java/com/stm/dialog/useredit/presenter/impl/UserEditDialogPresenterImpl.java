package com.stm.dialog.useredit.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.UserEditFlag;
import com.stm.dialog.useredit.interactor.UserEditDialogInteractor;
import com.stm.dialog.useredit.interactor.impl.UserEditDialogInteractorImpl;
import com.stm.dialog.useredit.presenter.UserEditDialogPresenter;
import com.stm.dialog.useredit.view.UserEditDialogView;

import java.util.regex.Pattern;

/**
 * Created by Dev-0 on 2017-08-23.
 */

public class UserEditDialogPresenterImpl implements UserEditDialogPresenter {
    private UserEditDialogView userEditDialogView;
    private UserEditDialogInteractor userEditDialogInteractor;

    public UserEditDialogPresenterImpl(UserEditDialogView userEditDialogView) {
        this.userEditDialogView = userEditDialogView;
        this.userEditDialogInteractor = new UserEditDialogInteractorImpl(this);
    }

    @Override
    public void init(User user, int editFlag) {
        String name = user.getName();
        String intro = user.getIntro();
        String phone = user.getPhone();


        if (editFlag == UserEditFlag.NAME_EDIT) {
            userEditDialogView.showToolbarTitle("이름 작성");
            userEditDialogView.showDialogMessage("이름을 입력해주세요");
            userEditDialogView.showEditText(name);

            userEditDialogView.setEditTextMaxLength(10);
            userEditDialogView.showTextMaxSize(10);

            int textSize = name.length();
            userEditDialogView.showTextSize(textSize);


        } else if (editFlag == UserEditFlag.INTRO_EDIT) {
            userEditDialogView.showToolbarTitle("소개 작성");
            userEditDialogView.showDialogMessage("소개를 입력해주세요");

            int textSize = 0;
            if (intro != null && intro.length() > 0) {
                userEditDialogView.showEditText(intro);
                textSize = intro.length();
            }

            userEditDialogView.setEditTextMaxLength(60);
            userEditDialogView.showTextMaxSize(60);

            userEditDialogView.showTextSize(textSize);
        } else {
            userEditDialogView.showToolbarTitle("전화번호 작성");
            userEditDialogView.showDialogMessage("전화번호를 '-'없이 입력해주세요");
            int textSize = 0;

            if (phone != null && phone.length() > 0) {
                userEditDialogView.showEditText(phone);
                textSize = phone.length();
            }

            userEditDialogView.setEditTextMaxLength(11);
            userEditDialogView.showTextMaxSize(11);
            userEditDialogView.showTextSize(textSize);
            userEditDialogView.setEditTextPhoneInputType();

        }

        userEditDialogInteractor.setEditFlag(editFlag);
        userEditDialogInteractor.setUser(user);
        String accessToken = user.getAccessToken();
        userEditDialogInteractor.setUserRepository(accessToken);

        userEditDialogView.setEditTextAddTextChangedListener();
    }

    @Override
    public void onClickEditTextCancel() {
        userEditDialogView.showEditText("");
    }

    @Override
    public void onTextChanged(int length) {
        userEditDialogView.showTextSizeCount(length);
    }

    @Override
    public void afterTextChanged(String content) {
        int editFlag = userEditDialogInteractor.getEditFlag();
        User user = userEditDialogInteractor.getUser();
        int contentSize = content.length();

        if (editFlag == UserEditFlag.NAME_EDIT) {
            String name = user.getName();

            if (contentSize > 0 && !name.equals(content)) {
                userEditDialogView.setSubmitButtonClickable();
                userEditDialogView.setSubmitButtonColorPointColor();
            } else {
                userEditDialogView.setSubmitButtonUnclickable();
                userEditDialogView.setSubmitButtonColorDarkGray();
            }
        } else if (editFlag == UserEditFlag.INTRO_EDIT) {
            String intro = user.getIntro();
            if (intro != null && intro.length() > 0) {
                if (contentSize > 0 && !intro.equals(content)) {
                    userEditDialogView.setSubmitButtonClickable();
                    userEditDialogView.setSubmitButtonColorPointColor();
                } else {
                    userEditDialogView.setSubmitButtonUnclickable();
                    userEditDialogView.setSubmitButtonColorDarkGray();
                }
            } else {
                if (contentSize > 0) {
                    userEditDialogView.setSubmitButtonClickable();
                    userEditDialogView.setSubmitButtonColorPointColor();
                } else {
                    userEditDialogView.setSubmitButtonUnclickable();
                    userEditDialogView.setSubmitButtonColorDarkGray();
                }
            }

        } else {
            String phone = user.getPhone();
            if (phone != null && phone.length() > 0) {
                if (contentSize > 0 && !phone.equals(content)) {
                    userEditDialogView.setSubmitButtonClickable();
                    userEditDialogView.setSubmitButtonColorPointColor();
                } else {
                    userEditDialogView.setSubmitButtonUnclickable();
                    userEditDialogView.setSubmitButtonColorDarkGray();
                }
            } else {
                if (contentSize > 0) {
                    userEditDialogView.setSubmitButtonClickable();
                    userEditDialogView.setSubmitButtonColorPointColor();
                } else {
                    userEditDialogView.setSubmitButtonUnclickable();
                    userEditDialogView.setSubmitButtonColorDarkGray();
                }
            }
        }
    }

    @Override
    public void onClickSubmit(String content) {
        int editFlag = userEditDialogInteractor.getEditFlag();
        User prevUser = userEditDialogInteractor.getUser();
        long prevUserId = prevUser.getId();

        if (editFlag == UserEditFlag.NAME_EDIT) {
            userEditDialogView.showProgressDialog();

            User user = new User();
            user.setId(prevUserId);
            user.setName(content);

            userEditDialogInteractor.updateUser(user);
        } else if (editFlag == UserEditFlag.INTRO_EDIT) {
            userEditDialogView.showProgressDialog();
            User user = new User();
            user.setId(prevUserId);
            user.setIntro(content);

            userEditDialogInteractor.updateUser(user);
        } else {
            if (Pattern.matches("^[0-9]*$", content)) {
                userEditDialogView.showProgressDialog();
                User user = new User();
                user.setId(prevUserId);
                user.setPhone(content);

                userEditDialogInteractor.updateUser(user);
            } else {
                userEditDialogView.showMessage("전화번호 형식을 확인해주세요.('-'없이 숫자만 입력)");
            }
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            userEditDialogView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            userEditDialogView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessUpdateUser(User user) {
        User prevUser = userEditDialogInteractor.getUser();
        String accessToken = prevUser.getAccessToken();

        user.setAccessToken(accessToken);
        userEditDialogInteractor.setUser(user);
        userEditDialogView.setUser(user);
        userEditDialogView.goneProgressDialog();

        int editFlag = userEditDialogInteractor.getEditFlag();

        userEditDialogView.navigateToBackWithResultEdit(user, editFlag);
    }

    @Override
    public void onClickClose() {
        userEditDialogView.navigateToBack();
    }
}
