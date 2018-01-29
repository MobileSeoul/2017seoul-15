package com.stm.market.map.base.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.common.util.GeocoderUtil;
import com.stm.common.util.ToastUtil;
import com.stm.market.map.base.presenter.MarketMapPresenter;
import com.stm.market.map.base.presenter.impl.MarketMapPresenterImpl;
import com.stm.market.map.base.view.MarketMapView;
import com.stm.market.map.directions.activity.MarketDirectionsActivity;
import com.stm.market.map.streetview.activity.MarketStreetViewActivity;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketMapActivity extends FragmentActivity implements MarketMapView, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, OnMapReadyCallback {
    private MarketMapPresenter marketMapPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private BitmapUtil bitmapUtil;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private GeocoderUtil geocoderUtil;
    private Marker selectedMarker;

    private View item_activitymarketmap_marker;
    private IncludedMarkerLayout includedMarkerLayout;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindDrawable(R.drawable.regioncategorymap_normalmarker)
    Drawable regioncategorymap_normalmarker;

    @BindDrawable(R.drawable.regioncategorymap_normalmarker_selected)
    Drawable regioncategorymap_normalmarker_selected;

    @BindDrawable(R.drawable.regioncategorymap_specialmarker)
    Drawable regioncategorymap_specialmarker;

    @BindDrawable(R.drawable.regioncategorymap_specialmarker_selected)
    Drawable regioncategorymap_specialmarker_selected;

//    @BindDrawable(R.drawable.regioncategorymap_manmarker)
//    Drawable regioncategorymap_manmarker;

    @BindDrawable(R.drawable.all_arrivalmarker)
    Drawable marketmap_arrivalmarker;

    @BindView(R.id.ll_marketmap_marketinfo)
    LinearLayout ll_marketmap_marketinfo;

    @BindView(R.id.iv_marketmap_position)
    ImageView iv_marketmap_position;

    @BindView(R.id.iv_marketmap_marketavatar)
    ImageView iv_marketmap_marketavatar;

    @BindView(R.id.tv_marketmap_marketname)
    TextView tv_marketmap_marketname;

    @BindView(R.id.tv_marketmap_marketaddress)
    TextView tv_marketmap_marketaddress;

    @BindView(R.id.tv_marketmap_marketphone)
    TextView tv_marketmap_marketphone;

    @BindView(R.id.in_marketmap_toolbar)
    View in_marketmap_toolbar;

    @BindString(R.string.cloud_front_market_avatar)
    String marketAvatarUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketmap);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.geocoderUtil = new GeocoderUtil(this);
        this.bitmapUtil = new BitmapUtil(this);

        Market market = (Market) getIntent().getSerializableExtra("market");
        User user = sharedPrefersManager.getUser();
        this.marketMapPresenter = new MarketMapPresenterImpl(this);
        this.marketMapPresenter.init(user, market);
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
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_marketmap_toolbar);
    }

    @Override
    public void setLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    public void setLocationListener() {
        if (locationListener == null) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        }
    }

    @Override
    public void setGoogleMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fg_googlemap);
        mapFragment.getMapAsync(this);
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
    public void setMarkerLayout() {
        item_activitymarketmap_marker = LayoutInflater.from(this).inflate(R.layout.item_activitymarketmap_marker, null);
        includedMarkerLayout = new IncludedMarkerLayout();
        ButterKnife.bind(includedMarkerLayout, item_activitymarketmap_marker);
    }

    @Override
    public LocationDto getCurrentLocation() {
        LocationDto locationDto = null;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationListener == null) {
                setLocationListener();
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            android.location.Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (currentLocation != null) {
                double latitude = currentLocation.getLatitude();
                double longitude = currentLocation.getLongitude();
                locationDto = new LocationDto(latitude, longitude);
                String currentAddress = geocoderUtil.getCurrentAddress(latitude, longitude);
                locationDto.setCurrentAddress(currentAddress);
                locationListener = null;
            }

        }
        return locationDto;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Market market = (Market) marker.getTag();

        CameraUpdate focus = CameraUpdateFactory.newLatLng(marker.getPosition());
        googleMap.animateCamera(focus);

        if (market != null) {
            onChangeSelectedMarker(marker);
            marketMapPresenter.onMarkerClick(market);
        }
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        onChangeSelectedMarker(null);
        marketMapPresenter.onMapClick();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        marketMapPresenter.onMapReady();
    }


    @Override
    public void onCameraFocusUpdate(LocationDto locationDto) {
        double latitude = locationDto.getLatitude();
        double longitude = locationDto.getLongitude();

        CameraUpdate focus = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        googleMap.animateCamera(focus);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    @OnClick(R.id.iv_marketmap_position)
    public void onClickPosition() {
        marketMapPresenter.onClickPosition();
    }

    @Override
    @OnClick(R.id.iv_marketmap_streetview)
    public void onClickStreetView() {
        marketMapPresenter.onClickStreetView();
    }

    @Override
    @OnClick(R.id.iv_marketmap_directions)
    public void onClickDirections() {
        marketMapPresenter.onClickDirections();
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
    public void showMarketName(String message) {
        tv_marketmap_marketname.setText(message);
    }

    @Override
    public void showMarketAddress(String message) {
        tv_marketmap_marketaddress.setText(message);
    }

    @Override
    public void showMarketPhone(String message) {
        tv_marketmap_marketphone.setText(message);
    }

    @Override
    public void showMarketAvatar(String message) {
        String avatar = message;
        if (message.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = marketAvatarUrl + message;
        }
        Glide.with(this).load(avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_marketmap_marketavatar);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showMarketInfo() {
        ll_marketmap_marketinfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneMarketInfo() {
        ll_marketmap_marketinfo.setVisibility(View.GONE);
    }

    @Override
    public void goneMarketPhone() {
        tv_marketmap_marketphone.setVisibility(View.GONE);
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

    @Override
    public void navigateToMarketStreetViewActivity(Market market) {
        Intent intent = new Intent(this, MarketStreetViewActivity.class);
        intent.putExtra("market", market);
        startActivity(intent);
    }

    @Override
    public void navigateToMarketDirectionsActivity(Market market, LocationDto locationDto) {
        Intent intent = new Intent(this, MarketDirectionsActivity.class);
        intent.putExtra("market", market);
        intent.putExtra("locationDto", locationDto);
        startActivity(intent);
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
