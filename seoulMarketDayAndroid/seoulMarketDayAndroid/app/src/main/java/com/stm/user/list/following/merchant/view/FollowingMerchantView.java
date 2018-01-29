package com.stm.user.list.following.merchant.view;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowingMerchantView {
    void showMessage(String message);

    void showToolbarTitle(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setScrollViewOnScrollChangeListener();

    void showEmptyView();

    void clearFollowingMerchantAdapter();

    void setFollowingMerchantAdapterItem(List<User> users);

    void setIncludedToolbarLayout();

    void followingMerchantAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void navigateToMerchantDetailActivity(User user);

    void navigateToBack();

    void onClickBack();

    void followingMerchantAdapterNotifyItemRemoved(int position);

    void removeFollowingMerchant(int position);
}
