package com.stm.main.base.view;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.stm.R;
import com.stm.common.dao.User;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public interface MainView {
    void showMessage(String message);

    void showLocationPermission();

    void onChangeForUserCreateFragmentAndNotificationFragment(Fragment fragment);

    void showProgressDialog();

    void goneProgressDialog();

    void setTabAdapterNotifyDataSetChanged();

    void setTabAdapterUser(User user);

    void setTabAdapter(User user);

    void setTabLayout();

    ViewPager getViewPager();

    void setToolbar();

    void onChangeForUserFragmentAndNotificationFragment(Fragment fragment);

    void onClickSearch();

    void navigateToSearchActivity();
}
