package com.stm.user.detail.merchant.base.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface MerchantDetailPresenter {
    void init(User user, long storyUserId, int position);

    void onClickCover();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetUserById(User storyUser);

    void onResume(User user);

    void onClickFollow();

    void onOffsetChanged(int totalScrollRange, int verticalOffset);

    void onClickWrite();

    void onActivityResultForStoryCreateResultOk(long storyId);

    void onSuccessSetMerchantFollower();

    void onSuccessDeleteMerchantFollower();

    void onClickFollowCancel();

    void onClickBack();

    void onBackPressed();

    void onClickAvatar();

    void onRefresh(int position);

    void onChangeFragment();

    void onSuccessGetUserByIdForRefresh(User storyUser, int position);
}
