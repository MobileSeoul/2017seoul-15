package com.stm.main.fragment.main.search.base.view;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void onClickBack();

    void onClickClear();

    void navigateToBack();

    void setTabLayout();

    void setSearchTabAdapter();

    void setEditText(String message);

    void setEditTextChangedListener();

    void setUserFragment(String message);

    void setMarketFragment(String message);

    void setTagFragment(String message);


    void setOffscreenPageLimit(int limit);

    void setTagFragmentRefresh();

    void setSearchTabAdapterUser(User user);

    void setSearchTabAdapterNotifyDataSetChanged();

}
