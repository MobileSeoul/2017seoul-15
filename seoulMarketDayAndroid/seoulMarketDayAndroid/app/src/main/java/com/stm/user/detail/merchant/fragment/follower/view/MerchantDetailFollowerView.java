package com.stm.user.detail.merchant.fragment.follower.view;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public interface MerchantDetailFollowerView {
    void showProgressDialog();

    void goneProgressDialog();

    void showEmptyView();

    void clearMerchantDetailFollowerAdapter();

    void showMessage(String message);

    void setScrollViewOnScrollChangeListener();

    void setFollowerByIdAndOffsetItem(List<User> newFollowers);

    void followerAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void navigateToUserDetailActivity(User user);
}
