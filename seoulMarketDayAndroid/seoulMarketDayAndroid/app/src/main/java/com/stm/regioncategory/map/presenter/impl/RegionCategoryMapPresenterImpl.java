package com.stm.regioncategory.map.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.LocationDto;
import com.stm.common.dto.HttpErrorDto;
import com.stm.regioncategory.map.interactor.RegionCategoryMapInteractor;
import com.stm.regioncategory.map.interactor.impl.RegionCategoryMapInteractorImpl;
import com.stm.regioncategory.map.presenter.RegionCategoryMapPresenter;
import com.stm.regioncategory.map.view.RegionCategoryMapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev-0 on 2017-06-19.
 */

public class RegionCategoryMapPresenterImpl implements RegionCategoryMapPresenter {
    private RegionCategoryMapView regionCategoryMapView;
    private RegionCategoryMapInteractor regionCategoryMapInteractor;

    public RegionCategoryMapPresenterImpl(RegionCategoryMapView regionCategoryMapView) {
        this.regionCategoryMapView = regionCategoryMapView;
        this.regionCategoryMapInteractor = new RegionCategoryMapInteractorImpl(this);
    }

    @Override
    public void init(RegionCategory regionCategory) {
        regionCategoryMapInteractor.setRegionCategory(regionCategory);
        regionCategoryMapView.init();
        regionCategoryMapView.setToolbarLayout();

        String regionCategoryName = regionCategory.getName();
        regionCategoryMapView.showToolbarTitle(regionCategoryName + " 지도");
    }

    @Override
    public void onLoadData() {
        regionCategoryMapView.showProgressDialog();
        regionCategoryMapView.setLocationManager();
        regionCategoryMapView.setLocationListener();
        LocationDto locationDto = regionCategoryMapView.getCurrentLocation();
        regionCategoryMapInteractor.setLocationDto(locationDto);

        regionCategoryMapView.setGoogleMap();
        regionCategoryMapInteractor.setRegionCategoryRepository();
        regionCategoryMapView.goneProgressDialog();
    }

    @Override
    public void onSuccessMapReady() {
        LocationDto locationDto = regionCategoryMapInteractor.getLocationDto();
        RegionCategory regionCategory = regionCategoryMapInteractor.getRegionCategory();
        long regionCategoryId = regionCategory.getId();

        if (locationDto != null) {
            regionCategoryMapInteractor.getMarketListByIdAndLocation(regionCategoryId, locationDto);
        } else {
            regionCategoryMapInteractor.getMarketListById(regionCategoryId);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            regionCategoryMapView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            regionCategoryMapView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetMarketListById(List<Market> markets) {
        regionCategoryMapView.setMarkerLayout();

        int marketSize = markets.size();

        for (int i = 0; i < marketSize; i++) {
            if (i == 0) {
                regionCategoryMapView.setGoogleMapPosition(markets.get(i));
            }
            regionCategoryMapView.showMapMarker(markets.get(i), false);
        }
    }

    @Override
    public void onSuccessGetMarketListByIdAndLocation(List<Market> markets) {
        regionCategoryMapView.setMarkerLayout();

        int marketSize = markets.size();

        for (int i = 0; i < marketSize; i++) {
            if (i == 0) {
                regionCategoryMapView.setGoogleMapPosition(markets.get(i));
            }
            regionCategoryMapView.showMapMarker(markets.get(i), false);
        }
    }

    @Override
    public void onMarkerClick(Market market) {
        LocationDto locationDto = regionCategoryMapInteractor.getLocationDto();
        String marketName = market.getName();
        String marketAddress = market.getLotNumberAddress();
        String marketPhone = market.getPhone();
        String marketAvatar = market.getAvatar();

        regionCategoryMapView.showMarketInfo();
        regionCategoryMapView.showMarketName(marketName);
        regionCategoryMapView.showMarketAddress(marketAddress);

        if (marketPhone.trim().length() == 0 || marketPhone == null) {
            regionCategoryMapView.goneMarketPhone();
        } else {
            regionCategoryMapView.showMarketPhone(marketPhone);
        }

        regionCategoryMapView.showMarketAvatar(marketAvatar);
        if (locationDto != null) {
            double distance = market.getDistance();
            String distanceUnit = LocationDto.KILO_METER_UNIT;

            if (distance < 1) {
                distance *= 1000;
                distanceUnit = LocationDto.METER_UNIT;
            }

            String message = "떨어진 거리: " + distance + distanceUnit;
            regionCategoryMapView.showMarketDistance(message);
        } else {
            regionCategoryMapView.goneMarketDistance();
        }

    }

    @Override
    public void onMapClick() {
        regionCategoryMapView.goneMarketInfo();
    }

    @Override
    public void onClickPosition() {
        LocationDto locationDto = regionCategoryMapInteractor.getLocationDto();
        if (locationDto != null) {
            regionCategoryMapView.onCameraFocusUpdate(locationDto);
            regionCategoryMapView.showMapCurrentPositionMarker(locationDto);
        } else {
            regionCategoryMapView.showMessage("지도를 닫고, GPS를 확인해주세요");
        }
    }

    @Override
    public void onSnippetClick(Market market) {
        regionCategoryMapView.navigateToMarketActivity(market);
    }
}
