package com.stm.regioncategory.map.view;

import com.google.android.gms.maps.model.Marker;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dto.LocationDto;

import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-06-19.
 */

public interface RegionCategoryMapView {
    void init();

    void setToolbarLayout();

    void showToolbarTitle(String message);

    void setGoogleMap();

    void setGoogleMapPosition(Market market);

    void setMarkerLayout();

    LocationDto getCurrentLocation();

    void setLocationManager();

    void showProgressDialog();

    void goneProgressDialog();

    void showMarketInfo();

    void showMarketAvatar(String message);

    void showMarketName(String message);

    void showMarketAddress(String message);

    void showMarketPhone(String message);

    void showMarketDistance(String message);

    void goneMarketInfo();

    void goneMarketDistance();

    void goneMarketPhone();

    void onClickPosition();

    void onCameraFocusUpdate(LocationDto locationDto);

    void setLocationListener();

    void onClickBack();

    void showMessage(String message);

    Marker showMapMarker(Market market, boolean isSelected);

    Marker showMapCurrentPositionMarker(LocationDto locationDto);

    void onSnippetClick();

    void navigateToMarketActivity(Market market);
}
