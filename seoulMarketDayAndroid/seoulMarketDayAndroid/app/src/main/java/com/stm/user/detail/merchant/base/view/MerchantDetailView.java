package com.stm.user.detail.merchant.base.view;

import android.support.v4.view.ViewPager;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public interface MerchantDetailView {
    void showProgressDialog();

    void goneProgressDialog();

    void setAddOnOffsetChangedListener();

    void setOnRefreshListener();

    void setToolbarLayout();


    void setSwipeRefreshLayoutRefreshing(boolean refreshing);

    void setMainFragmentRefresh();

    void setTabAdapterNotifyDataSetChanged();

    void setInfoFragmentRefresh(User storyUser);

    void setStoryFragmentRefresh();

    void setPhotoFragmentRefresh();

    void setVideoFragmentRefresh();

    void setFollowerFragmentRefresh();

    void showMessage(String message);

    void showUserNameText(String message);


    void showUserIntroText(String message);

    void showUserMarketText(String message);

    void showWriteButton();

    void showUserAvatar(String message);

    void showUserCover(String message);


    void showFollowButton();

    void goneFollowButton();

    void goneToolbarWriteButton();

    void showToolbarWriteButton();

    void showToolbarTitle(String message);

    void showToolbarFollowButton();

    void goneToolbarFollowButton();

    void showToolbarFollowCancelButton();

    void goneToolbarFollowCancelButton();

    void showToolbarBackButtonWithDefaultColor();

    void showToolbarBackButtonWithWhiteColor();

    void setTabLayout();

    void setTabAdapter(User user);


    void clearTabLayout();

    void onClickFollow();

    void onClickFollowCancel();

    void onClickWrite();

    void onClickBack();

    void navigateToBack();

    void navigateToBackWithStoryUserAndPosition(User storyUser, int position);

    void navigateToLoginActivity();


    void showFollowCancelButton();

    void goneFollowCancelButton();

    void goneWriteButton();

    void navigateToStoryCreateActivity(User storyUser);

    ViewPager getViewPager();

    void navigateToPhotoDialogActivityWithAvatar(String message);

    void navigateToPhotoDialogActivityWithCover(String message);

    void setSwipeRefreshLayoutEnabled(boolean enabled);


    void setTabAdapterUser(User user);
}
