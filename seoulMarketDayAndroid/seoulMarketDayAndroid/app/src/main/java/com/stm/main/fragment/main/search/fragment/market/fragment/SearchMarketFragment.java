package com.stm.main.fragment.main.search.fragment.market.fragment;

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
import com.stm.common.util.ToastUtil;
import com.stm.main.fragment.main.search.fragment.market.adapter.SearchMarketAdapter;
import com.stm.main.fragment.main.search.fragment.market.presenter.SearchMarketPresenter;
import com.stm.main.fragment.main.search.fragment.market.presenter.impl.SearchMarketPresenterImpl;
import com.stm.main.fragment.main.search.fragment.market.view.SearchMarketView;
import com.stm.market.base.activity.MarketActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchMarketFragment extends Fragment implements SearchMarketView, NestedScrollView.OnScrollChangeListener{
    private SearchMarketPresenter searchMarketPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SearchMarketAdapter searchMarketAdapter;

    @BindView(R.id.ll_searchmarket_empty)
    LinearLayout ll_searchmarket_empty;

    @BindView(R.id.rv_searchmarket)
    RecyclerView rv_searchmarket;

    @BindView(R.id.nsv_searchmarket)
    NestedScrollView nsv_searchmarket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.searchMarketPresenter = new SearchMarketPresenterImpl(this);
        this.searchMarketPresenter.init(user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchmarket, container, false);
        ButterKnife.bind(this, view);
        searchMarketPresenter.onCreateView();
        return view;
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showEmptyView(){
        ll_searchmarket_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneEmptyView() {
        ll_searchmarket_empty.setVisibility(View.GONE);
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
    public void setOnScrollChangeListener() {
        nsv_searchmarket.setOnScrollChangeListener(this);
    }

    @Override
    public void setSearchMarketAdapterItem(List<Market> markets){
        searchMarketAdapter = new SearchMarketAdapter(searchMarketPresenter, markets, context, R.layout.item_searchmarket);
        rv_searchmarket.setAdapter(searchMarketAdapter);
        rv_searchmarket.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void getMarketListByKeyword(String keyword){
        searchMarketPresenter.getMarketListByKeyword(keyword);
    }

    @Override
    public void clearSearchMarketAdapter(){
        if(searchMarketAdapter != null){
            searchMarketAdapter = null;
        }
    }

    @Override
    public void notifyItemRangeInserted(int startPosition, int itemCount) {
        if(searchMarketAdapter != null){
            searchMarketAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void navigateToMarketDetailActivity(Market market) {
        long id = market.getId();
        Intent intent = new Intent(context, MarketActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        searchMarketPresenter.onScrollChange(difference);
    }

}
