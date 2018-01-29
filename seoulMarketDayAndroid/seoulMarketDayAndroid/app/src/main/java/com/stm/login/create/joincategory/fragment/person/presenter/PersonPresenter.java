package com.stm.login.create.joincategory.fragment.person.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface PersonPresenter {
    void init(User user);
    void onCreateView();

    void onClickJoin();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessSetUser();
}
