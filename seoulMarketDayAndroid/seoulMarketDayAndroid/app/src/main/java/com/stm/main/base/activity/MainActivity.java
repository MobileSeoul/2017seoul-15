package com.stm.main.base.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.MainFragmentFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.util.ToastUtil;
import com.stm.main.base.adapter.MainTabAdapter;
import com.stm.main.base.presenter.MainPresenter;
import com.stm.main.base.presenter.impl.MainPresenterImpl;
import com.stm.main.base.view.MainView;
import com.stm.main.fragment.main.base.fragment.MainFragment;
import com.stm.main.fragment.main.search.base.activity.SearchActivity;
import com.stm.main.fragment.notification.fragment.NotificationFragment;
import com.stm.main.fragment.user.base.fragment.UserFragment;
import com.stm.main.fragment.user.create.fragment.UserCreateFragment;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements MainView, TabLayout.OnTabSelectedListener {
    private MainPresenter mainPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;

    private IncludedToolbarLayout includedToolbarLayout;
    private InflatedTabHomeLayout inflatedTabHomeLayout;
    private InflatedTabNotificationLayout inflatedTabNotificationLayout;
    private InflatedTabMyPageLayout inflatedTabMyPageLayout;

    private MainTabAdapter mainTabAdapter;

    @BindView(R.id.tl_main)
    TabLayout tl_main;

    @BindView(R.id.vp_main)
    ViewPager vp_main;

    @BindView(R.id.in_mainactivity_toolbar)
    View in_mainactivity_toolbar;

    @BindDrawable(R.drawable.tab_selector_home)
    Drawable tab_selector_home;

    @BindDrawable(R.drawable.tab_selector_notification)
    Drawable tab_selector_notification;

    @BindDrawable(R.drawable.tab_selector_user)
    Drawable tab_selector_user;

    @BindColor(R.color.transparent)
    int transparent;

    private Handler mainTabAdapterHandler;
    private Handler progressDialogHandler;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);

        this.mainTabAdapterHandler = new Handler();
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.mainPresenter = new MainPresenterImpl(this);
        this.mainPresenter.init(user);
    }

    @Override
    public void showLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionFlag.ACCESS_FINE_LOCATION);
    }

    @Override
    public void setTabLayout() {
        View tabHome = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_img, null, false);
        View tabNotification = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_img, null, false);
        View tabUser = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_img, null, false);

        inflatedTabHomeLayout = new InflatedTabHomeLayout();
        inflatedTabNotificationLayout = new InflatedTabNotificationLayout();
        inflatedTabMyPageLayout = new InflatedTabMyPageLayout();

        ButterKnife.bind(inflatedTabHomeLayout, tabHome);
        ButterKnife.bind(inflatedTabNotificationLayout, tabNotification);
        ButterKnife.bind(inflatedTabMyPageLayout, tabUser);

        inflatedTabHomeLayout.iv_tabHome.setBackgroundResource(R.drawable.tab_selector_home);
        inflatedTabNotificationLayout.iv_tabNotification.setBackgroundResource(R.drawable.tab_selector_notification);
        inflatedTabMyPageLayout.iv_tabMyPage.setBackgroundResource(R.drawable.tab_selector_user);

        tl_main.addTab(tl_main.newTab());
        tl_main.addTab(tl_main.newTab());
        tl_main.addTab(tl_main.newTab());

        tl_main.getTabAt(0).setCustomView(tabHome);
        tl_main.getTabAt(1).setCustomView(tabNotification);
        tl_main.getTabAt(2).setCustomView(tabUser);

        vp_main.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_main));
        tl_main.addOnTabSelectedListener(this);
    }

    @Override
    public ViewPager getViewPager() {
        return vp_main;
    }

    @Override
    public void setToolbar() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_mainactivity_toolbar);
    }

    @Override
    public void onChangeForUserFragmentAndNotificationFragment(Fragment fragment) {
        User user = sharedPrefersManager.getUser();

        Fragment notificationFragment = new NotificationFragment();
        mainTabAdapter.setFragmentAtThePosition(MainFragmentFlag.NOTIFICATION, notificationFragment);

        mainTabAdapter.removeFragment(fragment);
        Fragment userFragment = new UserFragment();
        mainTabAdapter.addFragment(userFragment);
        mainPresenter.onChangeForLoginAndLogout(user);
    }

    @Override
    public void onChangeForUserCreateFragmentAndNotificationFragment(Fragment fragment) {
        User user = sharedPrefersManager.getUser();
        Fragment notificationFragment = new NotificationFragment();
        mainTabAdapter.setFragmentAtThePosition(MainFragmentFlag.NOTIFICATION, notificationFragment);

        mainTabAdapter.removeFragment(fragment);
        Fragment userCreateFragment = new UserCreateFragment();
        mainTabAdapter.addFragment(userCreateFragment);
        mainPresenter.onChangeForLoginAndLogout(user);
    }


    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(transparent));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @Override
    public void goneProgressDialog() {
        progressDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 400);
    }

    @Override
    public void setTabAdapterNotifyDataSetChanged() {
        mainTabAdapterHandler.post(new Runnable() {
            @Override
            public void run() {
                mainTabAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void setTabAdapterUser(User user) {
        mainTabAdapter.setUser(user);
    }

    @Override
    public void setTabAdapter(User user) {
        mainTabAdapter = new MainTabAdapter(getSupportFragmentManager(), user, this);
        mainTabAdapter.addFragment(new MainFragment());
        mainTabAdapter.addFragment(new NotificationFragment());

        if (user != null) {
            mainTabAdapter.addFragment(new UserFragment());
        } else {
            mainTabAdapter.addFragment(new UserCreateFragment());
        }

        vp_main.setAdapter(mainTabAdapter);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_main.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    @OnClick(R.id.ib_toolbarmain_search)
    public void onClickSearch(){
        mainPresenter.onClickSearch();
    }

    @Override
    public void navigateToSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionFlag.ACCESS_FINE_LOCATION:
                mainPresenter.onRequestPermissionsResultForAccessFineLocation(grantResults);
                break;
        }
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbarmain_search)
        ImageView ib_toolbarmain_search;
    }

    static class InflatedTabHomeLayout {
        @BindView(R.id.iv_all_tab)
        ImageView iv_tabHome;
    }

    static class InflatedTabNotificationLayout {
        @BindView(R.id.iv_all_tab)
        ImageView iv_tabNotification;
    }

    static class InflatedTabMyPageLayout {
        @BindView(R.id.iv_all_tab)
        ImageView iv_tabMyPage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
