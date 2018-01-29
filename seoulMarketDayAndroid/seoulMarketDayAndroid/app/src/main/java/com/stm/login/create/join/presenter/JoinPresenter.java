package com.stm.login.create.join.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public interface JoinPresenter {
    void init(Market market);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetCheckByEmail(Boolean check);

    void onClickJoin(User user, String password, String passwordConfirm, String encryptedPassword, boolean isValidEmail);

    void onSuccessSetUser();
}
