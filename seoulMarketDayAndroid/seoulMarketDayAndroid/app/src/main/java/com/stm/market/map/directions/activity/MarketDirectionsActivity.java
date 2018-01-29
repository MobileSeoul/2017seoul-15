package com.stm.market.map.directions.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.common.util.ToastUtil;
import com.stm.market.map.directions.presenter.MarketDirectionsPresenter;
import com.stm.market.map.directions.presenter.impl.MarketDirectionsPresenterImpl;
import com.stm.market.map.directions.view.MarketDirectionsView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketDirectionsActivity extends FragmentActivity implements MarketDirectionsView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    private MarketDirectionsPresenter marketDirectionsPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private Marker selectedMarker;
    private IncludedMarkerLayout includedMarkerLayout;
    private IncludedToolbarLayout includedToolbarLayout;
    private View item_activitymarketmap_marker;
    private BitmapUtil bitmapUtil;

    @BindDrawable(R.drawable.regioncategorymap_normalmarker)
    Drawable regioncategorymap_normalmarker;

    @BindDrawable(R.drawable.regioncategorymap_normalmarker_selected)
    Drawable regioncategorymap_normalmarker_selected;

    @BindDrawable(R.drawable.regioncategorymap_specialmarker)
    Drawable regioncategorymap_specialmarker;

    @BindDrawable(R.drawable.regioncategorymap_specialmarker_selected)
    Drawable regioncategorymap_specialmarker_selected;

    @BindDrawable(R.drawable.all_arrivalmarker)
    Drawable marketmap_arrivalmarker;

    @BindView(R.id.ll_marketdirections_marketinfo)
    LinearLayout ll_marketdirections_marketinfo;

    @BindView(R.id.iv_marketdirections_position)
    ImageView iv_marketdirections_position;

    @BindView(R.id.iv_marketdirections_marketavatar)
    ImageView iv_marketdirections_marketavatar;

    @BindView(R.id.tv_marketdirections_marketname)
    TextView tv_marketdirections_marketname;

    @BindView(R.id.tv_marketdirections_marketaddress)
    TextView tv_marketdirections_marketaddress;

    @BindView(R.id.tv_marketdirections_marketphone)
    TextView tv_marketdirections_marketphone;

    @BindView(R.id.in_markedirections_toolbar)
    View in_markedirections_toolbar;

    @BindString(R.string.cloud_front_market_avatar)
    String marketAvatarUrl;

    @BindString(R.string.google_directions_api_key)
    String googleDirectionsApiKey;

    @BindColor(R.color.polyline)
    int polyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketdirections);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.bitmapUtil = new BitmapUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        Market market = (Market) getIntent().getSerializableExtra("market");
        LocationDto locationDto = (LocationDto) getIntent().getSerializableExtra("locationDto");
        this.marketDirectionsPresenter = new MarketDirectionsPresenterImpl(this);
        this.marketDirectionsPresenter.init(user, market, locationDto);
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void setMarkerLayout() {
        item_activitymarketmap_marker = LayoutInflater.from(this).inflate(R.layout.item_activitymarketmap_marker, null);
        includedMarkerLayout = new IncludedMarkerLayout();
        ButterKnife.bind(includedMarkerLayout, item_activitymarketmap_marker);
    }

    @Override
    public void setGoogleMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fg_googlemap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void showToolbarTitle(String message){
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setToolbarLayout(){
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_markedirections_toolbar);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        marketDirectionsPresenter.onMapReady(googleDirectionsApiKey);
    }

    @Override
    public void setPolyLineOptions(PolylineOptions polylineOptions) {
        polylineOptions.width(25).color(polyline);
        googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void setMarker(MarkerOptions markerOptions) {
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void setGoogleMapPosition(Market market) {
        double latitude = market.getLatitude();
        double longitude = market.getLongitude();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onCameraFocusUpdate(LocationDto locationDto) {
        double latitude = locationDto.getLatitude();
        double longitude = locationDto.getLongitude();

        CameraUpdate focus = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        googleMap.animateCamera(focus);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Market market = (Market) marker.getTag();

        CameraUpdate focus = CameraUpdateFactory.newLatLng(marker.getPosition());
        googleMap.animateCamera(focus);

        if (market != null) {
            onChangeSelectedMarker(marker);
            marketDirectionsPresenter.onMarkerClick(market);
        }

        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        onChangeSelectedMarker(null);
        marketDirectionsPresenter.onMapClick();
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    @OnClick(R.id.iv_marketdirections_position)
    public void onClickPosition() {
        marketDirectionsPresenter.onClickPosition();
    }

    @Override
    public void showMarketInfo() {
        ll_marketdirections_marketinfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneMarketInfo() {
        ll_marketdirections_marketinfo.setVisibility(View.GONE);
    }

    @Override
    public void goneMarketPhone() {
        tv_marketdirections_marketphone.setVisibility(View.GONE);
    }

    @Override
    public void showMarketName(String message) {
        tv_marketdirections_marketname.setText(message);
    }

    @Override
    public void showMarketAddress(String message) {
        tv_marketdirections_marketaddress.setText(message);
    }

    @Override
    public void showMarketAvatar(String message) {
        String avatar = message;
        if (message.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = marketAvatarUrl + message;
        }
        Glide.with(this).load(avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_marketdirections_marketavatar);
    }

    @Override
    public void showMarketPhone(String message) {
        tv_marketdirections_marketphone.setText(message);
    }

    @Override
    public Marker showMapMarker(Market market, boolean isSelected) {
        double latitude = market.getLatitude();
        double longitude = market.getLongitude();
        MarketCategory marketCategory = market.getMarketCategory();
        long marketCategoryId = marketCategory.getId();

        LatLng latLng = new LatLng(latitude, longitude);

        if (isSelected) {
            if (marketCategoryId == 1) {
                includedMarkerLayout.iv_map_maker.setImageDrawable(regioncategorymap_normalmarker_selected);
            } else {
                includedMarkerLayout.iv_map_maker.setImageDrawable(regioncategorymap_specialmarker_selected);
            }
        } else {
            if (marketCategoryId == 1) {
                includedMarkerLayout.iv_map_maker.setImageDrawable(regioncategorymap_normalmarker);
            } else {
                includedMarkerLayout.iv_map_maker.setImageDrawable(regioncategorymap_specialmarker);
            }
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapUtil.getBitmapByView(item_activitymarketmap_marker)));

        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(market);
        return marker;
    }

    @Override
    public Marker showMapCurrentPositionMarker(LocationDto locationDto) {
        double latitude = locationDto.getLatitude();
        double longitude = locationDto.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        includedMarkerLayout.iv_map_maker.setImageDrawable(marketmap_arrivalmarker);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapUtil.getBitmapByView(item_activitymarketmap_marker)));

        Marker marker = googleMap.addMarker(markerOptions);
        return marker;
    }

    private void onChangeSelectedMarker(Marker marker) {
        if (selectedMarker != null) {
            Market market = (Market) selectedMarker.getTag();
            if (market != null) {
                Marker tempMarker = showMapMarker(market, false);
                selectedMarker = null;
            }
        }

        if (marker != null) {
            Market market = (Market) marker.getTag();
            selectedMarker = showMapMarker(market, true);
            marker.remove();
        }
    }

    static class IncludedMarkerLayout {
        @BindView(R.id.iv_map_maker)
        ImageView iv_map_maker;
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }

}
