package com.stm.market.base.view;

import android.support.v4.view.ViewPager;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public interface MarketView {

    void setAddOnOffsetChangedListener();


    void setOnRefreshListener();

    void showToolbarTitle(String message);

    void showToolbarBackButtonWithDefaultColor();

    void showToolbarBackButtonWithWhiteColor();


    void showMarketCategory(String message);

    void showMarketAvatar(String message);

    void showMarketName(String message);

    void showMarketAddress(String message);

    void setToolbarLayout();

    void onClickBack();

    void showMessage(String message);

    void setTabLayout();

    void setTabAdapter(User user, Market market);

    void clearTabLayout();

    ViewPager getViewPager();

    void setSwipeRefreshLayoutRefreshing(boolean refreshing);

    void setSwipeRefreshLayoutEnabled(boolean enable);

    void showProgressDialog();

    void goneProgressDialog();

    void goneFollowButton();

    void showToolbarFollowCancelButton();

    void goneFollowCancelButton();

    void showFollowButton();

    void showFollowCancelButton();

    void goneToolbarFollowCancelButton();

    void showToolbarFollowButton();

    void goneToolbarFollowButton();

    void onClickFollow();

    void onClickFollowCancel();

    void onClickAddress();

    void navigateToLoginActivity();

    void navigateToMarketMapActivity(Market market);

    void setMainFragmentRefresh();

    void setInfoFragmentRefresh();

    void setTabAdapterMarket(Market market);

    void setTabAdapterNotifyDataSetChanged();

    void setStoryFragmentRefresh();

    void setPhotoFragmentRefresh();

    void setVideoFragmentRefresh();

    void setMerchantFragmentRefresh();
}
