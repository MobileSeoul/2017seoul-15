package com.stm.market.map.streetview.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.market.map.base.activity.MarketMapActivity;
import com.stm.market.map.streetview.presenter.MarketStreetViewPresenter;
import com.stm.market.map.streetview.presenter.impl.MarketStreetViewPresenterImpl;
import com.stm.market.map.streetview.view.MarketStreetViewView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketStreetViewActivity extends FragmentActivity implements MarketStreetViewView, OnStreetViewPanoramaReadyCallback {
    private MarketStreetViewPresenter marketStreetViewPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    private StreetViewPanoramaFragment streetViewPanoramaFragment;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_marketstreetview_toolbar)
    View in_marketstreetview_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketstreetview);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        Market market = (Market) getIntent().getSerializableExtra("market");
        this.marketStreetViewPresenter = new MarketStreetViewPresenterImpl(this);
        this.marketStreetViewPresenter.init(user, market);

    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_marketstreetview_toolbar);
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
        }, 400);
    }

    @Override
    public void showToolbarTitle(String message){
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setStreetViewPanoramaFragment() {
        this.streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.svpf_marketstreetview);
        this.streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        marketStreetViewPresenter.onStreetViewPanoramaReady(streetViewPanorama);
    }

    @Override
    public void setStreetViewPanoramaPosition(Market market, StreetViewPanorama streetViewPanorama) {
        double latitude = market.getLatitude();
        double longitude = market.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        streetViewPanorama.setPosition(latLng);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }
}
