package com.stm.login.create.joincategory.fragment.person.view;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface PersonView {
    void onClickJoin();

    void navigateToJoinActivity();

    void showMessage(String message);

    void navigateToBack();

    void setUser(User user);
}
