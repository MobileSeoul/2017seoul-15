package com.stm.user.detail.merchant.base.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.MerchantFragmentFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.login.base.activity.LoginActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.create.activity.StoryCreateActivity;
import com.stm.user.detail.merchant.base.adapter.MerchantDetailTabAdapter;
import com.stm.user.detail.merchant.base.presenter.MerchantDetailPresenter;
import com.stm.user.detail.merchant.base.presenter.impl.MerchantDetailPresenterImpl;
import com.stm.user.detail.merchant.base.view.MerchantDetailView;
import com.stm.user.detail.merchant.fragment.follower.fragment.MerchantDetailFollowerFragment;
import com.stm.user.detail.merchant.fragment.information.fragment.MerchantDetailInfoFragment;
import com.stm.user.detail.merchant.fragment.main.fragment.MerchantDetailMainFragment;
import com.stm.user.detail.merchant.fragment.photo.fragment.MerchantDetailPhotoFragment;
import com.stm.user.detail.merchant.fragment.story.fragment.MerchantDetailStoryFragment;
import com.stm.user.detail.merchant.fragment.video.fragment.MerchantDetailVideoFragment;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class MerchantDetailActivity extends FragmentActivity implements MerchantDetailView, TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener {
    private MerchantDetailPresenter merchantDetailPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private FragmentManager fragmentManager;
    private MerchantDetailTabAdapter merchantDetailTabAdapter;
    private Handler merchantDetailTabAdapterHandler;

    private IncludedToolbarLayout includedToolbarLayout;
    private InflatedTabHomeLayout inflatedTabHomeLayout;
    private InflatedTabInfoLayout inflatedTabInfoLayout;
    private InflatedTabStoryLayout inflatedTabStoryLayout;
    private InflatedTabPhotoLayout inflatedTabPhotoLayout;
    private InflatedTabVideoLayout inflatedTabVideoLayout;
    private InflatedTabFollowerLayout inflatedTabFollowerLayout;

    @BindView(R.id.iv_merchantdetail_cover)
    ImageView iv_merchantdetail_cover;

    @BindView(R.id.iv_merchantdetail_avatar)
    ImageView iv_merchantdetail_avatar;

    @BindView(R.id.tv_merchantdetail_name)
    TextView tv_merchantdetail_name;

    @BindView(R.id.tv_merchantdetail_email)
    TextView tv_merchantdetail_email;

    @BindView(R.id.tv_merchantdetail_market)
    TextView tv_merchantdetail_market;

    @BindString(R.string.cloud_front_user_avatar)
    String userAvatarUrl;

    @BindString(R.string.cloud_front_user_cover)
    String userCoverUrl;

    @BindView(R.id.tl_merchantdetail)
    TabLayout tl_merchantdetail;

    @BindView(R.id.vp_merchantdetail)
    ViewPager vp_merchantdetail;

    @BindView(R.id.tv_merchantdetail_intro)
    TextView tv_merchantdetail_intro;

    @BindView(R.id.in_merchantdetail_toolbar)
    View in_merchantdetail_toolbar;

    @BindView(R.id.btn_merchantdetail_follow)
    Button btn_merchantdetail_follow;

    @BindView(R.id.btn_merchantdetail_follow_cancel)
    Button btn_merchantdetail_follow_cancel;

    @BindView(R.id.abl_merchantdetail)
    AppBarLayout abl_merchantdetail;

    @BindView(R.id.btn_merchantdetail_write)
    Button btn_merchantdetail_write;

    @BindView(R.id.srl_merchantdetail)
    SwipeRefreshLayout srl_merchantdetail;

    @BindColor(R.color.defaultTextColor)
    int defaultTextColor;

    @BindColor(R.color.white)
    int white;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchantdetail);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);
        this.merchantDetailTabAdapterHandler = new Handler();

        long storyUserId = getIntent().getLongExtra("storyUserId", 0);
        int position = getIntent().getIntExtra("position", -1);

        User user = sharedPrefersManager.getUser();

        this.merchantDetailPresenter = new MerchantDetailPresenterImpl(this);
        this.merchantDetailPresenter.init(user, storyUserId, position);

    }


    @Override
    public void showProgressDialog() {
        progressDialog.show();
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
    public void setAddOnOffsetChangedListener() {
        this.abl_merchantdetail.addOnOffsetChangedListener(this);
    }

    @Override
    public void setOnRefreshListener() {
        srl_merchantdetail.setOnRefreshListener(this);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_merchantdetail_toolbar);
    }

    @Override
    public void setSwipeRefreshLayoutRefreshing(boolean refreshing) {
        srl_merchantdetail.setRefreshing(refreshing);
    }


    @Override
    public void setSwipeRefreshLayoutEnabled(boolean enabled) {
        srl_merchantdetail.setEnabled(enabled);
    }

    @Override
    public void setTabAdapterUser(User user) {
        merchantDetailTabAdapter.setUser(user);
    }

    @Override
    public void setTabAdapterNotifyDataSetChanged() {
        merchantDetailTabAdapterHandler.post(new Runnable() {
            @Override
            public void run() {
                merchantDetailTabAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setMainFragmentRefresh() {
        Fragment fragment = new MerchantDetailMainFragment();
        merchantDetailTabAdapter.setFragment(MerchantFragmentFlag.HOME, fragment);
        merchantDetailPresenter.onChangeFragment();
    }

    @Override
    public void setInfoFragmentRefresh(User storyUser) {
        Fragment fragment = new MerchantDetailInfoFragment();
        merchantDetailTabAdapter.setFragment(MerchantFragmentFlag.INFO, fragment);
        merchantDetailPresenter.onChangeFragment();
    }

    @Override
    public void setStoryFragmentRefresh() {
        Fragment fragment = new MerchantDetailStoryFragment();
        merchantDetailTabAdapter.setFragment(MerchantFragmentFlag.STORY, fragment);
        merchantDetailPresenter.onChangeFragment();
    }

    @Override
    public void setPhotoFragmentRefresh() {
        Fragment fragment = new MerchantDetailPhotoFragment();
        merchantDetailTabAdapter.setFragment(MerchantFragmentFlag.PHOTO, fragment);
        merchantDetailPresenter.onChangeFragment();
    }

    @Override
    public void setVideoFragmentRefresh() {
        Fragment fragment = new MerchantDetailVideoFragment();
        merchantDetailTabAdapter.setFragment(MerchantFragmentFlag.VIDEO, fragment);
        merchantDetailPresenter.onChangeFragment();
    }

    @Override
    public void setFollowerFragmentRefresh() {
        Fragment fragment = new MerchantDetailFollowerFragment();
        merchantDetailTabAdapter.setFragment(MerchantFragmentFlag.FOLLOWER, fragment);
        merchantDetailPresenter.onChangeFragment();
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showUserNameText(String message) {
        tv_merchantdetail_name.setText(message);
    }

    @Override
    public void showUserIntroText(String message) {
        tv_merchantdetail_intro.setText(message);
    }

    @Override
    public void showUserMarketText(String message) {
        tv_merchantdetail_market.setText(message);
    }

    @Override
    public void showWriteButton() {
        btn_merchantdetail_write.setVisibility(View.VISIBLE);
    }


    @Override
    public void showUserAvatar(String message) {
        Glide.with(this).load(userAvatarUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_merchantdetail_avatar);
    }

    @Override
    public void showUserCover(String message) {
        Glide.with(this).load(userCoverUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_merchantdetail_cover);
    }

    @Override
    public void showFollowButton() {
        btn_merchantdetail_follow.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneFollowButton() {
        btn_merchantdetail_follow.setVisibility(View.GONE);
    }


    @Override
    public void showFollowCancelButton() {
        btn_merchantdetail_follow_cancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneFollowCancelButton() {
        btn_merchantdetail_follow_cancel.setVisibility(View.GONE);
    }

    @Override
    public void goneWriteButton() {
        btn_merchantdetail_write.setVisibility(View.GONE);
    }

    @Override
    public void goneToolbarWriteButton() {
        includedToolbarLayout.ib_toolbar_write.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToolbarWriteButton() {
        includedToolbarLayout.ib_toolbar_write.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showToolbarFollowButton() {
        includedToolbarLayout.ib_toolbar_follow.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneToolbarFollowButton() {
        includedToolbarLayout.ib_toolbar_follow.setVisibility(View.GONE);
    }

    @Override
    public void showToolbarFollowCancelButton() {
        includedToolbarLayout.ib_toolbar_follow_cancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneToolbarFollowCancelButton() {
        includedToolbarLayout.ib_toolbar_follow_cancel.setVisibility(View.GONE);
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
    public void setTabLayout() {
        View tabHome = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabInfo = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabStory = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabPhoto = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabVideo = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);
        View tabFollower = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_all_tab_text, null, false);

        inflatedTabHomeLayout = new InflatedTabHomeLayout();
        inflatedTabInfoLayout = new InflatedTabInfoLayout();
        inflatedTabStoryLayout = new InflatedTabStoryLayout();
        inflatedTabPhotoLayout = new InflatedTabPhotoLayout();
        inflatedTabVideoLayout = new InflatedTabVideoLayout();
        inflatedTabFollowerLayout = new InflatedTabFollowerLayout();

        ButterKnife.bind(inflatedTabHomeLayout, tabHome);
        ButterKnife.bind(inflatedTabInfoLayout, tabInfo);
        ButterKnife.bind(inflatedTabStoryLayout, tabStory);
        ButterKnife.bind(inflatedTabPhotoLayout, tabPhoto);
        ButterKnife.bind(inflatedTabVideoLayout, tabVideo);
        ButterKnife.bind(inflatedTabFollowerLayout, tabFollower);

        inflatedTabInfoLayout.tv_all_tab.setText("정보");
        inflatedTabHomeLayout.tv_all_tab.setText("홈");
        inflatedTabStoryLayout.tv_all_tab.setText("게시글");
        inflatedTabPhotoLayout.tv_all_tab.setText("사진");
        inflatedTabVideoLayout.tv_all_tab.setText("동영상");
        inflatedTabFollowerLayout.tv_all_tab.setText("팔로워");

        tl_merchantdetail.addTab(tl_merchantdetail.newTab());
        tl_merchantdetail.addTab(tl_merchantdetail.newTab());
        tl_merchantdetail.addTab(tl_merchantdetail.newTab());
        tl_merchantdetail.addTab(tl_merchantdetail.newTab());
        tl_merchantdetail.addTab(tl_merchantdetail.newTab());
        tl_merchantdetail.addTab(tl_merchantdetail.newTab());

        tl_merchantdetail.getTabAt(MerchantFragmentFlag.HOME).setCustomView(tabHome);
        tl_merchantdetail.getTabAt(MerchantFragmentFlag.INFO).setCustomView(tabInfo);
        tl_merchantdetail.getTabAt(MerchantFragmentFlag.STORY).setCustomView(tabStory);
        tl_merchantdetail.getTabAt(MerchantFragmentFlag.PHOTO).setCustomView(tabPhoto);
        tl_merchantdetail.getTabAt(MerchantFragmentFlag.VIDEO).setCustomView(tabVideo);
        tl_merchantdetail.getTabAt(MerchantFragmentFlag.FOLLOWER).setCustomView(tabFollower);

        vp_merchantdetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_merchantdetail));
        tl_merchantdetail.addOnTabSelectedListener(this);

    }

    @Override
    public void setTabAdapter(User user) {
        merchantDetailTabAdapter = new MerchantDetailTabAdapter(getSupportFragmentManager(), user, this);
        merchantDetailTabAdapter.addFragment(new MerchantDetailMainFragment());
        merchantDetailTabAdapter.addFragment(new MerchantDetailInfoFragment());
        merchantDetailTabAdapter.addFragment(new MerchantDetailStoryFragment());
        merchantDetailTabAdapter.addFragment(new MerchantDetailPhotoFragment());
        merchantDetailTabAdapter.addFragment(new MerchantDetailVideoFragment());
        merchantDetailTabAdapter.addFragment(new MerchantDetailFollowerFragment());
        vp_merchantdetail.setAdapter(merchantDetailTabAdapter);
    }

    @Override
    public void clearTabLayout() {
        tl_merchantdetail.removeAllTabs();
    }


    @Override
    @OnClick({R.id.btn_merchantdetail_follow, R.id.ib_toolbar_follow})
    public void onClickFollow() {
        merchantDetailPresenter.onClickFollow();
    }

    @Override
    @OnClick({R.id.btn_merchantdetail_follow_cancel, R.id.ib_toolbar_follow_cancel})
    public void onClickFollowCancel() {
        merchantDetailPresenter.onClickFollowCancel();
    }

    @Override
    @OnClick({R.id.btn_merchantdetail_write, R.id.ib_toolbar_write})
    public void onClickWrite() {
        merchantDetailPresenter.onClickWrite();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        merchantDetailPresenter.onResume(user);

//        if (merchantDetailTabAdapter != null) {
//            merchantDetailTabAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        merchantDetailPresenter.onClickBack();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        merchantDetailPresenter.onBackPressed();
    }

    @Override
    public void navigateToBackWithStoryUserAndPosition(User storyUser, int position) {
        Intent intent = getIntent();
        intent.putExtra("storyUser", storyUser);
        intent.putExtra("position", position);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToStoryCreateActivity(User storyUser) {
        Intent intent = new Intent(this, StoryCreateActivity.class);
        intent.putExtra("storyUser", storyUser);
        startActivityForResult(intent, ActivityResultFlag.STORY_CREATE_REQEUST);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        merchantDetailPresenter.onOffsetChanged(totalScrollRange, verticalOffset);
    }

    @Override
    public ViewPager getViewPager() {
        return vp_merchantdetail;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_merchantdetail.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh() {
        int position = tl_merchantdetail.getSelectedTabPosition();
        merchantDetailPresenter.onRefresh(position);
    }

    @OnClick(R.id.iv_merchantdetail_avatar)
    public void onClickAvatar() {
        merchantDetailPresenter.onClickAvatar();
    }

    @OnClick(R.id.iv_merchantdetail_cover)
    public void onClickCover() {
        merchantDetailPresenter.onClickCover();
    }

    @Override
    public void navigateToPhotoDialogActivityWithAvatar(String message) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("avatar", message);
        startActivity(intent);
    }

    @Override
    public void navigateToPhotoDialogActivityWithCover(String message) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("cover", message);
        startActivity(intent);
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.ib_toolbar_write)
        ImageButton ib_toolbar_write;

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

    static class InflatedTabFollowerLayout {
        @BindView(R.id.tv_all_tab)
        TextView tv_all_tab;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ActivityResultFlag.STORY_CREATE_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        long storyId = data.getLongExtra("storyId", 0);
                        if (storyId > 0) {
                            merchantDetailPresenter.onActivityResultForStoryCreateResultOk(storyId);
                        }
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;
        }

    }
}
