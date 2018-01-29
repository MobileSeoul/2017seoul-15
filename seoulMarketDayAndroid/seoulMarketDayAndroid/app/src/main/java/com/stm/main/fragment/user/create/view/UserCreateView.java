package com.stm.main.fragment.user.create.view;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface UserCreateView {
    void navigateToLoginActivity();

    void navigateToJoinCategoryActivity();

    void onClickLogin();

    void onClickJoin();

    void showMessage(String message);

    void onChangeForUserFragment();
}
