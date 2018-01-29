package com.stm.main.fragment.main.search.fragment.user.view;

import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchUserView {
    void showMessage(String message);

    void showEmptyView();

    void showProgressDialog();

    void goneProgressDialog();

    void setOnScrollChangeListener();

    void setSearchUserAdapterItem(List<User> users);

    void getUserListByKeyword(String keyword);

    void clearSearchUserAdapter();

    void notifyItemRangeInserted(int startPosition, int itemCount);

    void goneEmptyView();

    void navigateToUserDetailActivity(User user);

    void navigateToMerchantDetailActivity(User user);
}
