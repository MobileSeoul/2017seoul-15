package com.stm.user.list.follower.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.normal.activity.UserDetailActivity;
import com.stm.user.list.follower.adapter.FollowerAdapter;
import com.stm.user.list.follower.presenter.FollowerPresenter;
import com.stm.user.list.follower.presenter.impl.FollowerPresenterImpl;
import com.stm.user.list.follower.view.FollowerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowerActivity extends Activity implements FollowerView, NestedScrollView.OnScrollChangeListener {
    private FollowerPresenter followerPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private FollowerAdapter followerAdapter;
    private IncludedToolbarLayout includedToolbarLayout;


    @BindView(R.id.rv_follower)
    RecyclerView rv_follower;

    @BindView(R.id.ll_follower_empty)
    LinearLayout ll_follower_empty;

    @BindView(R.id.nsv_follower)
    NestedScrollView nsv_follower;

    @BindView(R.id.in_follower_toolbar)
    View in_follower_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.followerPresenter = new FollowerPresenterImpl(this);
        this.followerPresenter.init(user);
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
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showEmptyView() {
        ll_follower_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setIncludedToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_follower_toolbar);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_follower.setOnScrollChangeListener(this);
    }

    @Override
    public void setFollowerAdapterItem(List<User> users) {
        this.followerAdapter = new FollowerAdapter(followerPresenter, users, this, R.layout.item_follower);
        rv_follower.setAdapter(followerAdapter);
        rv_follower.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void clearFollowerAdapter() {
        if (followerAdapter != null) {
            followerAdapter = null;
        }
    }

    @Override
    public void followerAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (followerAdapter != null) {
            followerAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        followerPresenter.onScrollChange(difference);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long userId = user.getId();

        Intent intent = new Intent(this, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    public void navigateToUserDetail(User user) {
        long userId = user.getId();

        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }


    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack(){
        followerPresenter.onClickBack();
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }
}
