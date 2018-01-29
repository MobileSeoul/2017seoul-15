package com.stm.market.map.base.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.dto.LocationDto;
import com.stm.market.map.base.interactor.MarketMapInteractor;
import com.stm.market.map.base.interactor.impl.MarketMapInteractorImpl;
import com.stm.market.map.base.presenter.MarketMapPresenter;
import com.stm.market.map.base.view.MarketMapView;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketMapPresenterImpl implements MarketMapPresenter {
    private MarketMapInteractor marketMapInteractor;
    private MarketMapView marketMapView;

    public MarketMapPresenterImpl(MarketMapView marketMapView) {
        this.marketMapInteractor = new MarketMapInteractorImpl(this);
        this.marketMapView = marketMapView;
    }


    @Override
    public void init(User user, Market market) {
        marketMapView.showProgressDialog();

        marketMapInteractor.setUser(user);
        marketMapInteractor.setMarket(market);

        marketMapView.setLocationManager();
        marketMapView.setLocationListener();
        marketMapView.setMarkerLayout();
        marketMapView.setGoogleMap();
        marketMapView.setToolbarLayout();

        String marketAddress = market.getLotNumberAddress();
        marketMapView.showToolbarTitle(marketAddress);

        LocationDto locationDto = marketMapView.getCurrentLocation();
        marketMapInteractor.setLocationDto(locationDto);
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketMapView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketMapView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onMarkerClick(Market market) {
        String marketName = market.getName();
        String marketAddress = market.getLotNumberAddress();
        String marketPhone = market.getPhone();
        String marketAvatar = market.getAvatar();

        marketMapView.showMarketInfo();
        marketMapView.showMarketName(marketName);
        marketMapView.showMarketAddress(marketAddress);
        marketMapView.showMarketAvatar(marketAvatar);

        if (marketPhone.trim().length() == 0 || marketPhone == null) {
            marketMapView.goneMarketPhone();
        } else {
            marketMapView.showMarketPhone(marketPhone);
        }
    }

    @Override
    public void onMapReady() {
        Market market = marketMapInteractor.getMarket();

        marketMapView.setMarkerLayout();
        marketMapView.setGoogleMapPosition(market);
        marketMapView.showMapMarker(market, false);
        marketMapView.goneProgressDialog();
    }


    @Override
    public void onMapClick() {
        marketMapView.goneMarketInfo();
    }

    @Override
    public void onClickPosition() {
        LocationDto locationDto = marketMapInteractor.getLocationDto();
        if (locationDto != null) {
            marketMapView.onCameraFocusUpdate(locationDto);
            marketMapView.showMapCurrentPositionMarker(locationDto);
        } else {
            marketMapView.showMessage("지도를 닫고, GPS를 확인해주세요");
        }
    }

    @Override
    public void onClickStreetView() {
        Market market = marketMapInteractor.getMarket();
        marketMapView.navigateToMarketStreetViewActivity(market);
    }

    @Override
    public void onClickDirections() {
        Market market = marketMapInteractor.getMarket();
        LocationDto locationDto = marketMapInteractor.getLocationDto();

        if(locationDto != null){
            marketMapView.navigateToMarketDirectionsActivity(market, locationDto);
        } else {
            marketMapView.showMessage("지도를 닫고, GPS를 확인해주세요");
        }
    }
}
