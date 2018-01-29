package com.stm.main.fragment.user.create.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface UserCreatePresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void onCreateView();

    void init();

    void onResume(User user);
}
