package com.stm.market.fragment.merchant.view;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-07-06.
 */

public interface MarketMerchantView {
    void setOnScrollChangeListener();

    void showProgressDialog();

    void goneProgressDialog();

    void clearMerchantAdapter();

    void setUserByMarketIdAndOffsetItem(List<User> users, User user);

    void merchantAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void merchantAdapterNotifyItemChanged(int position);

    void showMessage(String message);

    void navigateToMerchantDetailActivity(User user, int position);

    void navigateToLoginActivity();

    void showEmptyView();
}
