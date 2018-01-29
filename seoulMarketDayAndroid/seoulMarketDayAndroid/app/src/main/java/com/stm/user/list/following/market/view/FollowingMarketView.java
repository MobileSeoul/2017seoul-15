package com.stm.user.list.following.market.view;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.user.list.following.market.adapter.FollowingMarketAdapter;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public interface FollowingMarketView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setScrollViewOnScrollChangeListener();

    void onClickBack();

    void followingMarketAdapterNotifyItemRemoved(int position);

    void navigateToBack();

    void showEmptyView();

    void clearFollowingMarketAdapter();

    void setFollowingMarketAdapterItem(List<Market> markets);

    void setIncludedToolbarLayout();

    void followingMarketAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void navigateToMarketActivity(Market market);

    void showFollowCancel(FollowingMarketAdapter.FollowingMarketViewHolder holder);

    void goneFollow(FollowingMarketAdapter.FollowingMarketViewHolder holder);

    void showFollow(FollowingMarketAdapter.FollowingMarketViewHolder holder);

    void goneFollowCancel(FollowingMarketAdapter.FollowingMarketViewHolder holder);

    void showToolbarTitle(String message);

    void removeFollowingMarket(int position);
}
