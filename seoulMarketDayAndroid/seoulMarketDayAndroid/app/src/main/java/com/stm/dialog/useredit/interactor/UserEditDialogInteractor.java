package com.stm.dialog.useredit.interactor;

import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-08-23.
 */

public interface UserEditDialogInteractor {
    void setUserRepository(String accessToken);

    User getUser();

    void setUser(User user);

    int getEditFlag();

    void setEditFlag(int editFlag);

    void updateUser(User user);
}
