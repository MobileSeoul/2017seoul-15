package com.stm.login.create.join.view;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public interface JoinView {
    void setToolbarLayout();

    void showMessage(String message);

    void showToolbarTitle(String message);

    void onClickJoin();

    void onClickBack();

    void setUser(User user);

    void navigateToBack();
}
