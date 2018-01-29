package com.stm.regioncategory.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.RegionCategory;
import com.stm.common.util.ToastUtil;
import com.stm.market.base.activity.MarketActivity;
import com.stm.regioncategory.base.adapter.MarketByRegionCategoryAdapter;
import com.stm.regioncategory.base.adapter.MarketCategoryAdapter;
import com.stm.regioncategory.base.presenter.RegionCategoryPresenter;
import com.stm.regioncategory.base.presenter.impl.RegionCategoryPresenterImpl;
import com.stm.regioncategory.base.view.RegionCategoryView;
import com.stm.regioncategory.map.activity.RegionCategoryMapActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 지역구별 카테고리 액티비티
 */

public class RegionCategoryActivity extends Activity implements RegionCategoryView {
    private RegionCategoryPresenter regionCategoryPresenter;

    private IncludedToolbarLayout includedToolbarLayout;
    private IncludedMarketCategoryLayout includedMarketCategoryLayout;
    private MarketCategoryAdapter marketCategoryAdapter;
    private MarketByRegionCategoryAdapter marketByRegionCategoryAdapter;

    private ProgressDialog progressDialog;
    private ToastUtil toastUtil;

    private Animation animationRiseUp;
    private Animation animationDropDown;

    @BindView(R.id.rv_regioncategory)
    RecyclerView rv_regioncategory;

    @BindView(R.id.in_regioncategory_marketcategory)
    View in_regioncategory_marketcategory;

    @BindView(R.id.ll_regioncatgory_empty)
    LinearLayout ll_regioncatgory_empty;

    @BindView(R.id.tv_regioncatgory_empty)
    TextView tv_regioncatgory_empty;

    @BindView(R.id.in_regioncategory_toolbar)
    View in_regioncategory_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regioncategory);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);

        RegionCategory regionCategory = (RegionCategory) getIntent().getSerializableExtra("regionCategory");

        regionCategoryPresenter = new RegionCategoryPresenterImpl(this);
        regionCategoryPresenter.init(regionCategory);
    }

    @Override
    public void init() {
        regionCategoryPresenter.onLoadData();

        animationRiseUp = AnimationUtils.loadAnimation(this, R.anim.rise_up);
        animationDropDown = AnimationUtils.loadAnimation(this, R.anim.drop_down);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @Override
    public void goneProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_regioncategory_toolbar);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setMarketCategoryItem(List<MarketCategory> marketCategories) {
        includedMarketCategoryLayout = new IncludedMarketCategoryLayout();
        ButterKnife.bind(includedMarketCategoryLayout, in_regioncategory_marketcategory);

        in_regioncategory_marketcategory.bringToFront();
        marketCategoryAdapter = new MarketCategoryAdapter(regionCategoryPresenter, marketCategories, this, R.layout.item_regioncategory_marketcategory);
        includedMarketCategoryLayout.rv_regioncategory_marketcategory.setAdapter(marketCategoryAdapter);
        includedMarketCategoryLayout.rv_regioncategory_marketcategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setMarketItem(List<Market> markets) {
        marketByRegionCategoryAdapter = new MarketByRegionCategoryAdapter(regionCategoryPresenter, markets, this, R.layout.item_fragmentmain_market);
        rv_regioncategory.setAdapter(marketByRegionCategoryAdapter);
        rv_regioncategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showEmptyViewText(String message) {
        tv_regioncatgory_empty.setText(message);
    }

    @Override
    public void showEmptyView() {
        ll_regioncatgory_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneEmptyView() {
        ll_regioncatgory_empty.setVisibility(View.GONE);
    }

    @Override
    public void showMarketCategory() {
        in_regioncategory_marketcategory.setVisibility(View.VISIBLE);
        in_regioncategory_marketcategory.startAnimation(animationDropDown);
    }

    @Override
    public void goneMarketCategory() {
        in_regioncategory_marketcategory.startAnimation(animationRiseUp);
        in_regioncategory_marketcategory.setVisibility(View.GONE);
    }

    @Override
    public void clearMarketAdapter() {
        if (marketByRegionCategoryAdapter != null) {
            marketByRegionCategoryAdapter = null;
        }
    }

    @Override
    public void showMarketCategoryName(String message) {
        includedToolbarLayout.tv_regioncategory_marketcategory.setText(message);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void navigateToRegionCategoryMapActivity(RegionCategory regionCategory) {
        Intent intent = new Intent(this, RegionCategoryMapActivity.class);
        intent.putExtra("regionCategory", regionCategory);
        startActivity(intent);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    @OnClick(R.id.ib_toolbar_map)
    public void onClickMap() {
        regionCategoryPresenter.onClickMap();
    }

    @Override
    @OnClick(R.id.ll_regioncategory_marketcategory)
    public void onClickMarketCategory() {
        int isShown = in_regioncategory_marketcategory.getVisibility();
        regionCategoryPresenter.onClickMarketCategory(isShown);
    }

    @Override
    public void navigateToMarketActivity(Market market) {
        long id = market.getId();
        Intent intent = new Intent(this, MarketActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.tv_regioncategory_marketcategory)
        TextView tv_regioncategory_marketcategory;
    }

    static class IncludedMarketCategoryLayout {
        @BindView(R.id.rv_regioncategory_marketcategory)
        RecyclerView rv_regioncategory_marketcategory;
    }

}
