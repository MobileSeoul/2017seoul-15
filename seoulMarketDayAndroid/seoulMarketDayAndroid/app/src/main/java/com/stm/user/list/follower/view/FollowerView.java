package com.stm.user.list.follower.view;

import com.stm.R;
import com.stm.common.dao.User;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowerView {
    void showProgressDialog();

    void goneProgressDialog();

    void showMessage(String message);

    void showEmptyView();


    void showToolbarTitle(String message);

    void setIncludedToolbarLayout();

    void setScrollViewOnScrollChangeListener();

    void setFollowerAdapterItem(List<User> newUsers);

    void clearFollowerAdapter();

    void followerAdapterNotifyItemRangeInserted(int userSize, int newUserSize);

    void navigateToBack();

    void navigateToMerchantDetailActivity(User user);

    void navigateToUserDetail(User user);

    void onClickBack();
}
