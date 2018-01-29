package com.stm.user.edit.account.exit.interactor;

import com.stm.common.dao.Exit;
import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitInteractor {
    User getUser();

    void setUser(User user);

    ExitCategory getExitCategory();

    void setExitRepository(String accessToken);

    void setExitCategory(ExitCategory exitCategory);

    Boolean getExist();

    void setExist(Boolean exist);

    void setExit(Exit exit);
}
