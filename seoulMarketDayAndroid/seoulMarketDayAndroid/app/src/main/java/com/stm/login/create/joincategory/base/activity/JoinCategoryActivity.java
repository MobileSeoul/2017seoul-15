package com.stm.login.create.joincategory.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.login.create.joincategory.base.adapter.JoinCategoryAdapter;
import com.stm.login.create.joincategory.base.presenter.JoinCategoryPresenter;
import com.stm.login.create.joincategory.base.presenter.impl.JoinCategoryPresenterImpl;
import com.stm.login.create.joincategory.base.view.JoinCategoryView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class JoinCategoryActivity extends FragmentActivity implements JoinCategoryView, TabLayout.OnTabSelectedListener {
    private JoinCategoryPresenter joinCategoryPresenter;
    private ToastUtil toastUtil;

    private InflatedTabPersonLayout inflatedTabPersonLayout;
    private InflatedTabMerchantLayout inflatedTabMerchantLayout;
    private IncludedToolbarLayout includedToolbarLayout;
    private SelectedTabLayout selectedTabLayout;

    private JoinCategoryAdapter joinCategoryAdapter;


    @BindDrawable(R.drawable.joincategory_roundbox_top_pointcolor_r10)
    Drawable joincategory_roundbox_top_lightpink_r10;

    @BindDrawable(R.drawable.joincategory_roundbox_top_white_r10)
    Drawable joincategory_roundbox_top_white_r10;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.darkGray)
    int darkGray;

    @BindView(R.id.tl_joincategory)
    TabLayout tl_joincategory;

    @BindView(R.id.vp_joincategory)
    ViewPager vp_joincategory;

    @BindView(R.id.in_joincategory_toolbar)
    View in_joincategory_toolbar;

    private SharedPrefersManager sharedPrefersManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joincategory);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        User user = (User) getIntent().getSerializableExtra("user");

        this.joinCategoryPresenter = new JoinCategoryPresenterImpl(this);
        this.joinCategoryPresenter.init(user);
    }

    @Override
    public void setTabLayout() {
        View tabPerson = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabMerchant = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);

        selectedTabLayout = new SelectedTabLayout();
        inflatedTabPersonLayout = new InflatedTabPersonLayout();
        inflatedTabMerchantLayout = new InflatedTabMerchantLayout();

        ButterKnife.bind(inflatedTabPersonLayout, tabPerson);
        ButterKnife.bind(inflatedTabMerchantLayout, tabMerchant);

        inflatedTabPersonLayout.tv_all_tab.setText("일반 회원");
        inflatedTabMerchantLayout.tv_all_tab.setText("상인 회원");

        tl_joincategory.addTab(tl_joincategory.newTab());
        tl_joincategory.addTab(tl_joincategory.newTab());

        tl_joincategory.getTabAt(0).setCustomView(tabPerson);
        tl_joincategory.getTabAt(1).setCustomView(tabMerchant);

        vp_joincategory.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_joincategory));
        tl_joincategory.addOnTabSelectedListener(this);

        joinCategoryPresenter.onTabSelected(tl_joincategory.getTabAt(0));
        joinCategoryPresenter.onTabUnselected(tl_joincategory.getTabAt(1));
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_joincategory_toolbar);
    }

    @Override
    public void setTabAdapter(User user) {
        int tabCount = tl_joincategory.getTabCount();
        joinCategoryAdapter = new JoinCategoryAdapter(getSupportFragmentManager(), tabCount, user);
        vp_joincategory.setAdapter(joinCategoryAdapter);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_joincategory.setCurrentItem(tab.getPosition());
        joinCategoryPresenter.onTabSelected(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        joinCategoryPresenter.onTabUnselected(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void setTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ButterKnife.bind(selectedTabLayout, view);
        selectedTabLayout.tv_all_tab.setBackgroundDrawable(joincategory_roundbox_top_lightpink_r10);
        selectedTabLayout.tv_all_tab.setTextColor(white);
    }

    @Override
    public void setTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ButterKnife.bind(selectedTabLayout, view);
        selectedTabLayout.tv_all_tab.setBackgroundDrawable(joincategory_roundbox_top_white_r10);
        selectedTabLayout.tv_all_tab.setTextColor(darkGray);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        joinCategoryPresenter.onResume(user);
    }

    public class InflatedTabPersonLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    public class InflatedTabMerchantLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    public class SelectedTabLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }


}
