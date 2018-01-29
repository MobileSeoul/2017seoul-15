package com.stm.user.list.following.merchant.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.list.following.market.activity.FollowingMarketActivity;
import com.stm.user.list.following.merchant.adapter.FollowingMerchantAdapter;
import com.stm.user.list.following.merchant.presenter.FollowingMerchantPresenter;
import com.stm.user.list.following.merchant.presenter.impl.FollowingMerchantPresenterImpl;
import com.stm.user.list.following.merchant.view.FollowingMerchantView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMerchantActivity extends Activity implements FollowingMerchantView, NestedScrollView.OnScrollChangeListener {
    private FollowingMerchantPresenter followingMerchantPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private FollowingMerchantAdapter followingMerchantAdapter;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.nsv_followingmerchant)
    NestedScrollView nsv_followingmerchant;

    @BindView(R.id.rv_followingmerchant)
    RecyclerView rv_followingmerchant;

    @BindView(R.id.ll_followingmerchant_empty)
    LinearLayout ll_followingmerchant_empty;

    @BindView(R.id.in_followingmerchant_toolbar)
    View in_followingmerchant_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followingmerchant);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.followingMerchantPresenter = new FollowingMerchantPresenterImpl(this);
        this.followingMerchantPresenter.init(user);
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
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
    public void setScrollViewOnScrollChangeListener() {
        nsv_followingmerchant.setOnScrollChangeListener(this);
    }

    @Override
    public void showEmptyView() {
        ll_followingmerchant_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearFollowingMerchantAdapter() {
        if (followingMerchantAdapter != null) {
            followingMerchantAdapter = null;
        }
    }

    @Override
    public void setFollowingMerchantAdapterItem(List<User> users) {
        followingMerchantAdapter = new FollowingMerchantAdapter(followingMerchantPresenter, users, this, R.layout.item_followingmerchant);
        rv_followingmerchant.setAdapter(followingMerchantAdapter);
        rv_followingmerchant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setIncludedToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_followingmerchant_toolbar);
    }

    @Override
    public void followingMerchantAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (followingMerchantAdapter != null) {
            followingMerchantAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        followingMerchantPresenter.onScrollChange(difference);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(this, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        followingMerchantPresenter.onClickBack();
    }

    @Override
    public void followingMerchantAdapterNotifyItemRemoved(int position) {
        if (followingMerchantAdapter != null) {
            followingMerchantAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void removeFollowingMerchant(int position) {
        followingMerchantAdapter.removeFollowingMerchant(position);
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }
}
