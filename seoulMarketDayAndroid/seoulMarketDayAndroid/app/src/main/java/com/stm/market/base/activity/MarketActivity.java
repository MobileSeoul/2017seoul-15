package com.stm.market.base.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.MarketFragmentFlag;
import com.stm.common.util.ToastUtil;
import com.stm.login.base.activity.LoginActivity;
import com.stm.market.base.adapter.MarketTabAdapter;
import com.stm.market.base.presenter.MarketPresenter;
import com.stm.market.base.presenter.impl.MarketPresenterImpl;
import com.stm.market.base.view.MarketView;
import com.stm.market.fragment.information.fragment.MarketInfoFragment;
import com.stm.market.fragment.main.fragment.MarketMainFragment;
import com.stm.market.fragment.merchant.fragment.MarketMerchantFragment;
import com.stm.market.fragment.photo.fragment.MarketPhotoFragment;
import com.stm.market.fragment.story.fragment.MarketStoryFragment;
import com.stm.market.fragment.video.fragment.MarketVideoFragment;
import com.stm.market.map.base.activity.MarketMapActivity;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public class MarketActivity extends FragmentActivity implements MarketView, TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener {
    private MarketPresenter marketPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private MarketTabAdapter marketTabAdapter;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private Handler marketTabAdapterHandler;

    private IncludedToolbarLayout includedToolbarLayout;
    private InflatedTabHomeLayout inflatedTabHomeLayout;
    private InflatedTabInfoLayout inflatedTabInfoLayout;
    private InflatedTabStoryLayout inflatedTabStoryLayout;

    private InflatedTabPhotoLayout inflatedTabPhotoLayout;
    private InflatedTabVideoLayout inflatedTabVideoLayout;
    private InflatedTabMerchantLayout inflatedTabMerchantLayout;

    @BindView(R.id.in_market_toolbar)
    View in_market_toolbar;

    @BindView(R.id.iv_market_avatar)
    ImageView iv_market_avatar;

    @BindView(R.id.tv_market_name)
    TextView tv_market_name;

    @BindView(R.id.tv_market_category)
    TextView tv_market_category;

    @BindView(R.id.tv_market_address)
    TextView tv_market_address;

    @BindView(R.id.tl_market)
    TabLayout tl_market;

    @BindView(R.id.vp_market)
    ViewPager vp_market;

    @BindView(R.id.ct_market)
    CollapsingToolbarLayout ct_market;

    @BindView(R.id.abl_market)
    AppBarLayout abl_market;

    @BindView(R.id.btn_market_follow)
    Button btn_market_follow;

    @BindView(R.id.btn_market_follow_cancel)
    Button btn_market_follow_cancel;

    @BindView(R.id.srl_market)
    SwipeRefreshLayout srl_market;


    @BindString(R.string.cloud_front_market_avatar)
    String marketAvatarUrl;

    @BindColor(R.color.defaultTextColor)
    int defaultTextColor;

    @BindColor(R.color.white)
    int white;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.marketTabAdapterHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        long id = getIntent().getLongExtra("id", 0);

        Market market = new Market();
        market.setId(id);

        marketPresenter = new MarketPresenterImpl(this);
        marketPresenter.init(user, market);
    }


    @Override
    public void setAddOnOffsetChangedListener() {
        abl_market.addOnOffsetChangedListener(this);
    }

    @Override
    public void setOnRefreshListener() {
        srl_market.setOnRefreshListener(this);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.bringToFront();
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showToolbarBackButtonWithDefaultColor() {
        includedToolbarLayout.ib_toolbar_back.setColorFilter(defaultTextColor);
    }


    @Override
    public void showToolbarBackButtonWithWhiteColor() {
        includedToolbarLayout.ib_toolbar_back.setColorFilter(white);
    }

    @Override
    public void showMarketCategory(String message) {
        tv_market_category.setText(message);
    }

    @Override
    public void showMarketAvatar(String message) {
        if (message.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            message = marketAvatarUrl + message;
        }
        if (!this.isFinishing()) {
            Glide.with(this).load(message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_market_avatar);
        }
    }

    @Override
    public void showMarketName(String message) {
        tv_market_name.setText(message);
    }

    @Override
    public void showMarketAddress(String message) {
        tv_market_address.setText(message);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_market_toolbar);
    }


    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void setTabLayout() {
        View tabHome = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabInfo = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabStory = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabPhoto = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabVideo = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabMerchant = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);

        inflatedTabHomeLayout = new InflatedTabHomeLayout();
        inflatedTabInfoLayout = new InflatedTabInfoLayout();
        inflatedTabStoryLayout = new InflatedTabStoryLayout();
        inflatedTabPhotoLayout = new InflatedTabPhotoLayout();
        inflatedTabVideoLayout = new InflatedTabVideoLayout();
        inflatedTabMerchantLayout = new InflatedTabMerchantLayout();

        ButterKnife.bind(inflatedTabHomeLayout, tabHome);
        ButterKnife.bind(inflatedTabInfoLayout, tabInfo);
        ButterKnife.bind(inflatedTabStoryLayout, tabStory);
        ButterKnife.bind(inflatedTabPhotoLayout, tabPhoto);
        ButterKnife.bind(inflatedTabVideoLayout, tabVideo);
        ButterKnife.bind(inflatedTabMerchantLayout, tabMerchant);

        inflatedTabInfoLayout.tv_all_tab.setText("정보");
        inflatedTabHomeLayout.tv_all_tab.setText("홈");
        inflatedTabStoryLayout.tv_all_tab.setText("게시글");
        inflatedTabPhotoLayout.tv_all_tab.setText("사진");
        inflatedTabVideoLayout.tv_all_tab.setText("동영상");
        inflatedTabMerchantLayout.tv_all_tab.setText("상인");


        tl_market.addTab(tl_market.newTab());
        tl_market.addTab(tl_market.newTab());
        tl_market.addTab(tl_market.newTab());
        tl_market.addTab(tl_market.newTab());
        tl_market.addTab(tl_market.newTab());
        tl_market.addTab(tl_market.newTab());

        tl_market.getTabAt(0).setCustomView(tabHome);
        tl_market.getTabAt(1).setCustomView(tabInfo);
        tl_market.getTabAt(2).setCustomView(tabStory);
        tl_market.getTabAt(3).setCustomView(tabPhoto);
        tl_market.getTabAt(4).setCustomView(tabVideo);
        tl_market.getTabAt(5).setCustomView(tabMerchant);

        vp_market.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_market));
        tl_market.addOnTabSelectedListener(this);
    }

    @Override
    public void setTabAdapter(User user, Market market) {
        marketTabAdapter = new MarketTabAdapter(getSupportFragmentManager(), user, market);
        marketTabAdapter.addFragment(new MarketMainFragment());
        marketTabAdapter.addFragment(new MarketInfoFragment());
        marketTabAdapter.addFragment(new MarketStoryFragment());
        marketTabAdapter.addFragment(new MarketPhotoFragment());
        marketTabAdapter.addFragment(new MarketVideoFragment());
        marketTabAdapter.addFragment(new MarketMerchantFragment());

        vp_market.setAdapter(marketTabAdapter);
    }


    @Override
    public void clearTabLayout() {
        tl_market.removeAllTabs();
    }


    @Override
    public ViewPager getViewPager() {
        return vp_market;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_market.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh() {
        int position = tl_market.getSelectedTabPosition();
        marketPresenter.onRefresh(position);
    }

    @Override
    public void setSwipeRefreshLayoutRefreshing(boolean refreshing) {
        srl_market.setRefreshing(refreshing);
    }

    @Override
    public void setSwipeRefreshLayoutEnabled(boolean enable) {
        srl_market.setEnabled(enable);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        marketPresenter.onOffsetChanged(totalScrollRange, verticalOffset);
    }


    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        }, 10);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        marketPresenter.onResume(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showFollowButton() {
        btn_market_follow.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneFollowButton() {
        btn_market_follow.setVisibility(View.GONE);
    }

    @Override
    public void showFollowCancelButton() {
        btn_market_follow_cancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneFollowCancelButton() {
        btn_market_follow_cancel.setVisibility(View.GONE);
    }


    @Override
    public void showToolbarFollowCancelButton() {
        includedToolbarLayout.ib_toolbar_follow_cancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneToolbarFollowCancelButton() {
        includedToolbarLayout.ib_toolbar_follow_cancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToolbarFollowButton() {
        includedToolbarLayout.ib_toolbar_follow.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneToolbarFollowButton() {
        includedToolbarLayout.ib_toolbar_follow.setVisibility(View.INVISIBLE);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    @OnClick({R.id.ib_toolbar_follow, R.id.btn_market_follow})
    public void onClickFollow() {
        marketPresenter.onClickFollow();
    }

    @Override
    @OnClick({R.id.ib_toolbar_follow_cancel, R.id.btn_market_follow_cancel})
    public void onClickFollowCancel() {
        marketPresenter.onClickFollowCancel();
    }


    @Override
    @OnClick(R.id.ll_market_address)
    public void onClickAddress() {
        marketPresenter.onClickAddress();
    }


    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToMarketMapActivity(Market market) {
        Intent intent = new Intent(this, MarketMapActivity.class);
        intent.putExtra("market", market);
        startActivity(intent);
    }

    @Override
    public void setMainFragmentRefresh() {
        Fragment fragment = new MarketMainFragment();
        marketTabAdapter.setFragment(MarketFragmentFlag.HOME, fragment);
        marketPresenter.onChangeFragment();
    }

    @Override
    public void setInfoFragmentRefresh() {
        Fragment fragment = new MarketInfoFragment();
        marketTabAdapter.setFragment(MarketFragmentFlag.INFO, fragment);
        marketPresenter.onChangeFragment();
    }

    @Override
    public void setStoryFragmentRefresh() {
        Fragment fragment = new MarketStoryFragment();
        marketTabAdapter.setFragment(MarketFragmentFlag.STORY, fragment);
        marketPresenter.onChangeFragment();
    }

    @Override
    public void setPhotoFragmentRefresh() {
        Fragment fragment = new MarketPhotoFragment();
        marketTabAdapter.setFragment(MarketFragmentFlag.PHOTO, fragment);
        marketPresenter.onChangeFragment();
    }

    @Override
    public void setVideoFragmentRefresh() {
        Fragment fragment = new MarketVideoFragment();
        marketTabAdapter.setFragment(MarketFragmentFlag.VIDEO, fragment);
        marketPresenter.onChangeFragment();
    }

    @Override
    public void setMerchantFragmentRefresh() {
        Fragment fragment = new MarketMerchantFragment();
        marketTabAdapter.setFragment(MarketFragmentFlag.MERCHANT, fragment);
        marketPresenter.onChangeFragment();
    }

    @Override
    public void setTabAdapterMarket(Market market) {
        marketTabAdapter.setMarket(market);
    }

    @Override
    public void setTabAdapterNotifyDataSetChanged() {
        marketTabAdapterHandler.post(new Runnable() {
            @Override
            public void run() {
                marketTabAdapter.notifyDataSetChanged();
            }
        });
    }



    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.ib_toolbar_follow)
        ImageButton ib_toolbar_follow;

        @BindView(R.id.ib_toolbar_follow_cancel)
        ImageButton ib_toolbar_follow_cancel;
    }

    static class InflatedTabHomeLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    static class InflatedTabInfoLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    static class InflatedTabStoryLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    static class InflatedTabPhotoLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    static class InflatedTabVideoLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    static class InflatedTabMerchantLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }
}
