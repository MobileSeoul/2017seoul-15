package com.stm.user.detail.merchant.fragment.follower.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.fragment.follower.adapter.MerchantDetailFollowerAdapter;
import com.stm.user.detail.merchant.fragment.follower.presenter.MerchantDetailFollowerPresenter;
import com.stm.user.detail.merchant.fragment.follower.presenter.impl.MerchantDetailFollowerPresenterImpl;
import com.stm.user.detail.merchant.fragment.follower.view.MerchantDetailFollowerView;
import com.stm.user.detail.normal.activity.UserDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public class MerchantDetailFollowerFragment extends Fragment implements MerchantDetailFollowerView, NestedScrollView.OnScrollChangeListener {
    private MerchantDetailFollowerPresenter merchantDetailFollowerPresenter;
    private Context context;
    private SharedPrefersManager sharedPrefersManager;
    private ToastUtil toastUtil;
    private Handler progressDialogHandler;
    private ProgressDialog progressDialog;

    private MerchantDetailFollowerAdapter merchantDetailFollowerAdapter;

    @BindView(R.id.nsv_merchantdetailfollower)
    NestedScrollView nsv_merchantdetailfollower;

    @BindView(R.id.rv_merchantdetailfollower)
    RecyclerView rv_merchantdetailfollower;

    @BindView(R.id.ll_merchantdetailfollower_empty)
    LinearLayout ll_merchantdetailfollower_empty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.toastUtil = new ToastUtil(context);
        this.progressDialogHandler = new Handler();

        User storyUser = (User) getArguments().getSerializable("storyUser");
        User user = sharedPrefersManager.getUser();
        this.merchantDetailFollowerPresenter = new MerchantDetailFollowerPresenterImpl(this);
        this.merchantDetailFollowerPresenter.init(user, storyUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantdetailfollower, container, false);
        ButterKnife.bind(this, view);

        merchantDetailFollowerPresenter.onCreateView();
        return view;
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
    public void showEmptyView(){
        ll_merchantdetailfollower_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearMerchantDetailFollowerAdapter() {
        if (merchantDetailFollowerAdapter != null) {
            merchantDetailFollowerAdapter = null;
        }
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_merchantdetailfollower.setOnScrollChangeListener(this);
    }

    @Override
    public void setFollowerByIdAndOffsetItem(List<User> newFollowers) {
        merchantDetailFollowerAdapter = new MerchantDetailFollowerAdapter(merchantDetailFollowerPresenter, newFollowers, context, R.layout.item_merchantdetailfollower);
        rv_merchantdetailfollower.setAdapter(merchantDetailFollowerAdapter);
        rv_merchantdetailfollower.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void followerAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (merchantDetailFollowerAdapter != null) {
            merchantDetailFollowerAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void navigateToUserDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        merchantDetailFollowerPresenter.onScrollChange(difference);
    }
}
