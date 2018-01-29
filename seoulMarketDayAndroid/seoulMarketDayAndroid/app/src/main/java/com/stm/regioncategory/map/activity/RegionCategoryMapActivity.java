package com.stm.regioncategory.map.activity;

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
import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.common.util.GeocoderUtil;
import com.stm.common.util.ToastUtil;
import com.stm.market.base.activity.MarketActivity;
import com.stm.regioncategory.map.presenter.RegionCategoryMapPresenter;
import com.stm.regioncategory.map.presenter.impl.RegionCategoryMapPresenterImpl;
import com.stm.regioncategory.map.view.RegionCategoryMapView;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-06-19.
 */

public class RegionCategoryMapActivity extends FragmentActivity implements RegionCategoryMapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    private RegionCategoryMapPresenter regionCategoryMapPresenter;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    private ProgressDialog progressDialog;
    private GeocoderUtil geocoderUtil;
    private BitmapUtil bitmapUtil;
    private ToastUtil toastUtil;

    @BindView(R.id.ll_regioncategorymap_marketinfo)
    LinearLayout ll_regioncategorymap_marketinfo;

    @BindView(R.id.iv_regioncategorymap_marketphoto)
    ImageView iv_regioncategorymap_marketphoto;

    @BindView(R.id.tv_regioncategorymap_marketname)
    TextView tv_regioncategorymap_marketname;

    @BindView(R.id.tv_regioncategorymap_marketaddress)
    TextView tv_regioncategorymap_marketaddress;

    @BindView(R.id.tv_regioncategorymap_marketphone)
    TextView tv_regioncategorymap_marketphone;

    @BindView(R.id.tv_regioncategorymap_marketdistance)
    TextView tv_regioncategorymap_marketdistance;

    @BindView(R.id.iv_regioncategorymap_position)
    ImageView iv_regioncategorymap_position;

    @BindView(R.id.in_regioncategorymap_toolbar)
    View in_regioncategorymap_toolbar;

    View item_activitymarketmap_marker;

    IncludedMarkerLayout includedMarkerLayout;

    @BindDrawable(R.drawable.regioncategorymap_normalmarker)
    Drawable regioncategorymap_normalmarker;

    @BindDrawable(R.drawable.regioncategorymap_normalmarker_selected)
    Drawable regioncategorymap_normalmarker_selected;

    @BindDrawable(R.drawable.regioncategorymap_specialmarker)
    Drawable regioncategorymap_specialmarker;

    @BindDrawable(R.drawable.regioncategorymap_specialmarker_selected)
    Drawable regioncategorymap_specialmarker_selected;

    @BindDrawable(R.drawable.all_arrivalmarker)
    Drawable all_arrivalmarker;

    @BindString(R.string.cloud_front_market_avatar)
    String marketAvatarUrl;

    private IncludedToolbarLayout includedToolbarLayout;

    private Marker selectedMarker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regioncategorymap);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.geocoderUtil = new GeocoderUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.toastUtil = new ToastUtil(this);
        this.bitmapUtil = new BitmapUtil(this);

        RegionCategory regionCategory = (RegionCategory) getIntent().getSerializableExtra("regionCategory");
        this.regionCategoryMapPresenter = new RegionCategoryMapPresenterImpl(this);
        this.regionCategoryMapPresenter.init(regionCategory);
    }

    @Override
    public void init() {
        regionCategoryMapPresenter.onLoadData();
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_regioncategorymap_toolbar);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
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
    public void setGoogleMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fg_googlemap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        regionCategoryMapPresenter.onSuccessMapReady();
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
    public void showMarketInfo() {
        ll_regioncategorymap_marketinfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMarketAvatar(String message) {
        String avatar = message;
        if (message.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = marketAvatarUrl + message;
        }
        Glide.with(this).load(avatar).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_regioncategorymap_marketphoto);
    }

    @Override
    public void showMarketName(String message) {
        tv_regioncategorymap_marketname.setText(message);
    }

    @Override
    public void showMarketAddress(String message) {
        tv_regioncategorymap_marketaddress.setText(message);
    }

    @Override
    public void showMarketPhone(String message) {
        tv_regioncategorymap_marketphone.setText(message);
    }

    @Override
    public void showMarketDistance(String message) {
        tv_regioncategorymap_marketdistance.setText(message);
    }

    @Override
    public void goneMarketInfo() {
        ll_regioncategorymap_marketinfo.setVisibility(View.GONE);
    }

    @Override
    public void goneMarketDistance() {
        tv_regioncategorymap_marketdistance.setVisibility(View.GONE);
    }

    @Override
    public void goneMarketPhone() {
        tv_regioncategorymap_marketphone.setVisibility(View.GONE);
    }


    @Override
    @OnClick(R.id.iv_regioncategorymap_position)
    public void onClickPosition() {
        regionCategoryMapPresenter.onClickPosition();
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
    public void showMessage(String message) {
        toastUtil.showMessage(message);
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

        includedMarkerLayout.iv_map_maker.setImageDrawable(all_arrivalmarker);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapUtil.getBitmapByView(item_activitymarketmap_marker)));

        Marker marker = googleMap.addMarker(markerOptions);
        return marker;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Market market = (Market) marker.getTag();

        CameraUpdate focus = CameraUpdateFactory.newLatLng(marker.getPosition());
        googleMap.animateCamera(focus);

        if (market != null) {
            onChangeSelectedMarker(marker);
            regionCategoryMapPresenter.onMarkerClick(market);
        }
        return true;
    }


    @Override
    @OnClick(R.id.ll_regioncategorymap_marketinfo)
    public void onSnippetClick() {
        Market market = (Market) selectedMarker.getTag();
        regionCategoryMapPresenter.onSnippetClick(market);
    }


    @Override
    public void navigateToMarketActivity(Market market) {
        long id = market.getId();
        Intent intent = new Intent(this, MarketActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        onChangeSelectedMarker(null);
        regionCategoryMapPresenter.onMapClick();
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


    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }

    static class IncludedMarkerLayout {
        @BindView(R.id.iv_map_maker)
        ImageView iv_map_maker;
    }

}
