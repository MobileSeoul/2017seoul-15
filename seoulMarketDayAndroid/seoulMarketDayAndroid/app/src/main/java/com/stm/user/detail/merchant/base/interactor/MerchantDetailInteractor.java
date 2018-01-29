package com.stm.user.detail.merchant.base.interactor;

import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface MerchantDetailInteractor {
    void setUserRepository();
    void setUserRepository(String accessToken);

    void getUserById(long id, long userId);

    void getUserByIdForRefresh(long storyUserId, int position);

    User getUser();

    void setUser(User user);

    void getUserById(long id);

    User getStoryUser();

    void setStoryUser(User storyUser);

    boolean isToolbarTitleShown();

    void setToolbarTitleShown(boolean toolbarTitleShown);

    int getTotalScrollRange();

    void setTotalScrollRange(int totalScrollRange);

    void setMerchantFollower(MerchantFollower merchantFollower);

    void deleteMerchantFollower(MerchantFollower merchantFollower);

    int getPosition();

    void setPosition(int position);

    void getUserByIdForRefresh(long storyUserId, long userId, int position);
}
