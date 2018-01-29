package com.stm.login.create.joincategory.base.view;

import android.support.design.widget.TabLayout;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface JoinCategoryView {
    void setTabLayout();


    void setToolbarLayout();

    void setTabAdapter(User user);

    void showMessage(String message);

    void setTabSelected(TabLayout.Tab tab);

    void setTabUnselected(TabLayout.Tab tab);

    void showToolbarTitle(String message);

    void onClickBack();

    void navigateToBack();
}
