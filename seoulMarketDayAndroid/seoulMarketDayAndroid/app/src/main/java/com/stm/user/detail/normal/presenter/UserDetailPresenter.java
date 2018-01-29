package com.stm.user.detail.normal.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface UserDetailPresenter {
    void init(User user, Long storyUserId);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetUserById(User storyUser);

    void onClickAvatar();

    void onClickCover();
}
