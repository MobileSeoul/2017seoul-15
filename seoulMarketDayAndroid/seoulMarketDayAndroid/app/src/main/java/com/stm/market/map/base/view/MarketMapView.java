package com.stm.market.map.base.view;

import com.google.android.gms.maps.model.Marker;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dto.LocationDto;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketMapView {
    void showProgressDialog();

    void goneProgressDialog();

    void showMessage(String message);

    void setToolbarLayout();

    void setLocationManager();

    void setLocationListener();

    void setGoogleMap();

    void setGoogleMapPosition(Market market);

    void setMarkerLayout();

    LocationDto getCurrentLocation();

    void onClickStreetView();

    void onClickDirections();

    Marker showMapMarker(Market market, boolean isSelected);

    void showToolbarTitle(String message);

    void showMarketInfo();

    void showMarketName(String marketName);

    void showMarketAddress(String marketAddress);

    void goneMarketInfo();

    void goneMarketPhone();

    void showMarketPhone(String marketPhone);

    void showMarketAvatar(String marketAvatar);

    Marker showMapCurrentPositionMarker(LocationDto locationDto);

    void onCameraFocusUpdate(LocationDto locationDto);

    void onClickBack();

    void onClickPosition();

    void navigateToMarketStreetViewActivity(Market market);

    void navigateToMarketDirectionsActivity(Market market, LocationDto locationDto);
}
