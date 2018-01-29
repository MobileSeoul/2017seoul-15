package com.stm.user.edit.account.base.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public interface EditAccountPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, boolean isAllowedForNotification);

    void onClickChangePassword();

    void onClickExit();

    void onClickBack();

    void onNotificationCheckedChanged(boolean checked);
}
