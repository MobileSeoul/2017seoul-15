package com.stm.market.map.directions.view;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dto.LocationDto;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketDirectionsView {
    void showMessage(String message);

    void setMarkerLayout();

    void setGoogleMap();

    void showToolbarTitle(String message);

    void setToolbarLayout();

    void setPolyLineOptions(PolylineOptions polylineOptions);

    void setMarker(MarkerOptions markerOptions);

    void setGoogleMapPosition(Market market);

    void onCameraFocusUpdate(LocationDto locationDto);

    void onClickBack();

    void onClickPosition();

    void showMarketInfo();

    void goneMarketInfo();

    void goneMarketPhone();

    void showMarketName(String marketName);

    void showMarketAddress(String marketAddress);

    void showMarketAvatar(String marketAvatar);

    void showMarketPhone(String marketPhone);

    Marker showMapMarker(Market market, boolean isSelected);

    Marker showMapCurrentPositionMarker(LocationDto locationDto);
}
