package com.stm.market.fragment.merchant.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.login.base.activity.LoginActivity;
import com.stm.market.fragment.merchant.adapter.MarketMerchantAdapter;
import com.stm.market.fragment.merchant.presenter.MarketMerchantPresenter;
import com.stm.market.fragment.merchant.presenter.impl.MarketMerchantPresenterImpl;
import com.stm.market.fragment.merchant.view.MarketMerchantView;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketMerchantFragment extends Fragment implements MarketMerchantView, NestedScrollView.OnScrollChangeListener {
    private MarketMerchantPresenter marketMerchantPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private MarketMerchantAdapter marketMerchantAdapter;


    @BindView(R.id.nsv_marketmerchant)
    NestedScrollView nsv_marketmerchant;

    @BindView(R.id.rv_marketmerchant)
    RecyclerView rv_marketmerchant;

    @BindView(R.id.ll_marketmerchant_empty)
    LinearLayout ll_marketmerchant_empty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialogHandler = new Handler();

        User user = (User) getArguments().getSerializable("user");
        Market market = (Market) getArguments().getSerializable("market");

        this.marketMerchantPresenter = new MarketMerchantPresenterImpl(this);
        this.marketMerchantPresenter.init(user, market);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_marketmerchant, container, false);
        ButterKnife.bind(this, view);

        this.marketMerchantPresenter.onCreateView();
        return view;
    }

    @Override
    public void setOnScrollChangeListener() {
        this.nsv_marketmerchant.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        this.marketMerchantPresenter.onScrollChange(difference);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
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
        }, 400);
    }


    @Override
    public void clearMerchantAdapter() {
        if (marketMerchantAdapter != null) {
            marketMerchantAdapter = null;
        }
    }

    @Override
    public void setUserByMarketIdAndOffsetItem(List<User> users, User user) {
        marketMerchantAdapter = new MarketMerchantAdapter(marketMerchantPresenter, users, context, R.layout.item_marketfragmentmerchat_merchant, user);
        rv_marketmerchant.setAdapter(marketMerchantAdapter);
        rv_marketmerchant.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void merchantAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (marketMerchantAdapter != null) {
            marketMerchantAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void merchantAdapterNotifyItemChanged(int position) {
        if (marketMerchantAdapter != null) {
            marketMerchantAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showEmptyView() {
        ll_marketmerchant_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user, int position) {
        long userId = user.getId();
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.USER_REQUEST_FROM_MERCHANT_LIST);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.USER_REQUEST_FROM_MERCHANT_LIST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        User storyUser = (User) data.getSerializableExtra("storyUser");
                        marketMerchantPresenter.onActivityResultForUserResultOk(storyUser, position);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;
        }
    }
}
