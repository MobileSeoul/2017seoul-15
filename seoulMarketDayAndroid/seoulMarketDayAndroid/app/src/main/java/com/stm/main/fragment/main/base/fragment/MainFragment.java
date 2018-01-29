package com.stm.main.fragment.main.base.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;
import com.stm.common.util.GeocoderUtil;
import com.stm.common.util.ToastUtil;
import com.stm.main.fragment.main.base.adapter.Best5StoryAdapter;
import com.stm.main.fragment.main.base.adapter.Best5UserAdapter;
import com.stm.main.fragment.main.base.adapter.MarketByFollowerAdapter;
import com.stm.main.fragment.main.base.adapter.MarketByLocationAdapter;
import com.stm.main.fragment.main.base.presenter.MainFragmentPresenter;
import com.stm.main.fragment.main.base.presenter.impl.MainFragmentPresenterImpl;
import com.stm.main.fragment.main.base.view.MainFragmentView;
import com.stm.market.base.activity.MarketActivity;
import com.stm.regioncategory.base.activity.RegionCategoryActivity;
import com.stm.story.detail.activity.StoryDetailActivity;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class MainFragment extends Fragment implements MainFragmentView {
    private MainFragmentPresenter mainFragmentPresenter;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private MarketByLocationAdapter marketByLocationAdapter;
    private MarketByFollowerAdapter marketByFollowerAdapter;
    private Best5UserAdapter best5UserAdapter;
    private Best5StoryAdapter best5StoryAdapter;

    private ProgressDialog progressDialog;
    private GeocoderUtil geocoderUtil;
    private Context context;
    private ToastUtil toastUtil;
    private Handler progressDialogHandler;


    @BindView(R.id.iv_mainfragment_recommend)
    ImageView iv_mainfragment_recommend;

    @BindView(R.id.iv_mainfragment_refresh)
    ImageView iv_mainfragment_refresh;

    @BindView(R.id.tv_mainfragment_recommend)
    TextView tv_mainfragment_recommend;

    @BindView(R.id.rv_mainfragment_recommend)
    RecyclerView rv_mainfragment_recommend;

    @BindView(R.id.rv_mainfragment_userranking)
    RecyclerView rv_mainfragment_userranking;

    @BindView(R.id.rv_mainfragment_writingranking)
    RecyclerView rv_mainfragment_writingranking;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.geocoderUtil = new GeocoderUtil(context);
        this.progressDialog = new ProgressDialog(context);
        this.toastUtil = new ToastUtil(context);
        this.progressDialogHandler = new Handler();

        this.mainFragmentPresenter = new MainFragmentPresenterImpl(this);
        this.mainFragmentPresenter.init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mainFragmentPresenter.onCreateView();

        return view;
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showRecommendTitle(String message) {
        tv_mainfragment_recommend.setText(message);
    }

    @Override
    public void showRecommendLocationIcon() {
        iv_mainfragment_recommend.setImageResource(R.drawable.main_location);
    }

    @Override
    public void showRecommendBestIcon() {
        iv_mainfragment_recommend.setImageResource(R.drawable.main_market);
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
    public void setMarketByLocationItem(List<Market> markets) {
        marketByLocationAdapter = new MarketByLocationAdapter(mainFragmentPresenter, markets, context, R.layout.item_fragmentmain_marketwithdistance);
        rv_mainfragment_recommend.setAdapter(marketByLocationAdapter);
        rv_mainfragment_recommend.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setMarketByFollowerItem(List<Market> markets) {
        marketByFollowerAdapter = new MarketByFollowerAdapter(mainFragmentPresenter, markets, context, R.layout.item_fragmentmain_market);
        rv_mainfragment_recommend.setAdapter(marketByFollowerAdapter);
        rv_mainfragment_recommend.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setBest5UserItem(List<User> best5Users) {
        best5UserAdapter = new Best5UserAdapter(mainFragmentPresenter, best5Users, context, R.layout.item_fragmentmain_ranking);
        rv_mainfragment_userranking.setAdapter(best5UserAdapter);
        rv_mainfragment_userranking.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void setBest5StoryItem(List<Story> best5Stories) {
        best5StoryAdapter = new Best5StoryAdapter(mainFragmentPresenter, best5Stories, context, R.layout.item_fragmentmain_storyranking);
        rv_mainfragment_writingranking.setAdapter(best5StoryAdapter);
        rv_mainfragment_writingranking.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void navigateToMerchantDetailActivity(User user, int position) {
        long storyUserId = user.getId();
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }

    @Override
    public void navigateToStoryDetailActivity(Story story, int position) {
        long storyId = story.getId();
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra("storyId", storyId);
        startActivity(intent);
    }

    @Override
    public LocationDto getCurrentLocation() {
        LocationDto locationDto = null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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
    public void clearMarketAdapter() {
        if (marketByFollowerAdapter != null) {
            marketByFollowerAdapter = null;
        }

        if (marketByLocationAdapter != null) {
            marketByLocationAdapter = null;
        }
    }

    @Override
    @OnClick(R.id.iv_mainfragment_refresh)
    public void onClickMarketRefresh() {
        mainFragmentPresenter.onClickMarketRefresh();
    }

    @Override
    @OnClick({R.id.fl_mainfragment_gwangjin, R.id.fl_mainfragment_mapo, R.id.fl_mainfragment_seongdong, R.id.fl_mainfragment_gangbuk, R.id.fl_mainfragment_seodaemun, R.id.fl_mainfragment_jongro, R.id.fl_mainfragment_junggu,
            R.id.fl_mainfragment_yongsan, R.id.fl_mainfragment_dobong, R.id.fl_mainfragment_dongdaemun, R.id.fl_mainfragment_jungnang, R.id.fl_mainfragment_seongbuk, R.id.fl_mainfragment_nowon, R.id.fl_mainfragment_eunpyung,
            R.id.fl_mainfragment_gangdong, R.id.fl_mainfragment_songpa, R.id.fl_mainfragment_gangnam, R.id.fl_mainfragment_seocho, R.id.fl_mainfragment_geumcheon, R.id.fl_mainfragment_gwanak, R.id.fl_mainfragment_guro,
            R.id.fl_mainfragment_gangseo, R.id.fl_mainfragment_yangcheon, R.id.fl_mainfragment_yeongdeungpo, R.id.fl_mainfragment_dongjak,})
    public void onClickRegion(View view) {
        long regionCategoryId = Long.parseLong(view.getTag().toString());
        RegionCategory regionCategory = new RegionCategory();
        regionCategory.setId(regionCategoryId);

        mainFragmentPresenter.onClickRegion(regionCategory);
    }

    @Override
    public void navigateToRegionCategoryActivity(RegionCategory regionCategory) {
        Intent intent = new Intent(context, RegionCategoryActivity.class);
        intent.putExtra("regionCategory", regionCategory);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void navigateToMarketActivity(Market market) {
        long id = market.getId();
        Intent intent = new Intent(context, MarketActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void navigateToSearchTagActivity(String tagName) {
        Intent intent = new Intent(context, SearchTagActivity.class);
        intent.putExtra("tagName", tagName);
        startActivity(intent);
    }

}