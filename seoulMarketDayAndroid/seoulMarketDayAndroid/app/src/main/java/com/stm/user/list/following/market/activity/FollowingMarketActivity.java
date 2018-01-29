package com.stm.user.list.following.market.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.market.base.activity.MarketActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.list.follower.activity.FollowerActivity;
import com.stm.user.list.following.market.adapter.FollowingMarketAdapter;
import com.stm.user.list.following.market.presenter.FollowingMarketPresenter;
import com.stm.user.list.following.market.presenter.impl.FollowingMarketPresenterImpl;
import com.stm.user.list.following.market.view.FollowingMarketView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMarketActivity extends Activity implements FollowingMarketView, NestedScrollView.OnScrollChangeListener {
    private FollowingMarketPresenter followingMarketPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private FollowingMarketAdapter followingMarketAdapter;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.nsv_followingmarket)
    NestedScrollView nsv_followingmarket;

    @BindView(R.id.ll_followingmarket_empty)
    LinearLayout ll_followingmarket_empty;

    @BindView(R.id.rv_followingmarket)
    RecyclerView rv_followingmarket;

    @BindView(R.id.in_followingmarket_toolbar)
    View in_followingmarket_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followingmarket);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.followingMarketPresenter = new FollowingMarketPresenterImpl(this);
        this.followingMarketPresenter.init(user);
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
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
        nsv_followingmarket.setOnScrollChangeListener(this);
    }

    @Override
    public void showEmptyView() {
        ll_followingmarket_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearFollowingMarketAdapter() {
        if (followingMarketAdapter != null) {
            followingMarketAdapter = null;
        }
    }

    @Override
    public void setFollowingMarketAdapterItem(List<Market> markets) {
        followingMarketAdapter = new FollowingMarketAdapter(followingMarketPresenter, markets, this, R.layout.item_followingmarket);
        rv_followingmarket.setAdapter(followingMarketAdapter);
        rv_followingmarket.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setIncludedToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_followingmarket_toolbar);
    }

    @Override
    public void followingMarketAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (followingMarketAdapter != null) {
            followingMarketAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void navigateToMarketActivity(Market market) {
        long marketId = market.getId();
        Intent intent = new Intent(this, MarketActivity.class);
        intent.putExtra("id", marketId);
        startActivity(intent);
    }

    @Override
    public void showFollowCancel(FollowingMarketAdapter.FollowingMarketViewHolder holder) {
        followingMarketAdapter.showFollow(holder);
    }

    @Override
    public void goneFollow(FollowingMarketAdapter.FollowingMarketViewHolder holder) {
        followingMarketAdapter.goneFollow(holder);
    }

    @Override
    public void showFollow(FollowingMarketAdapter.FollowingMarketViewHolder holder) {
        followingMarketAdapter.showFollow(holder);
    }

    @Override
    public void goneFollowCancel(FollowingMarketAdapter.FollowingMarketViewHolder holder) {
        followingMarketAdapter.goneFollowCancel(holder);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }


    @Override
    public void removeFollowingMarket(int position) {
        followingMarketAdapter.removeFollowingMarket(position);
    }

    @Override
    public void followingMarketAdapterNotifyItemRemoved(int position) {
        if (followingMarketAdapter != null) {
            followingMarketAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack(){
        followingMarketPresenter.onClickBack();
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        followingMarketPresenter.onScrollChange(difference);
    }


    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }

}
