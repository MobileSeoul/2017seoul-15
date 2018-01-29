package com.stm.main.fragment.main.search.base.interactor;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchInteractor {
    User getUser();

    void setUser(User user);
}
