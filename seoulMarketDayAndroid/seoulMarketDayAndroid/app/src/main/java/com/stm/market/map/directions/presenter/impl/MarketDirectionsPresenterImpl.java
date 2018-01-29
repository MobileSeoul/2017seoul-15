package com.stm.market.map.directions.presenter.impl;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.stm.common.dto.Directions;
import com.stm.common.dto.Leg;
import com.stm.common.dao.Market;
import com.stm.common.dto.Route;
import com.stm.common.dto.Step;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.dto.Location;
import com.stm.common.dto.LocationDto;
import com.stm.market.map.directions.interactor.MarketDirectionsInteractor;
import com.stm.market.map.directions.interactor.impl.MarketDirectionsInteractorImpl;
import com.stm.market.map.directions.presenter.MarketDirectionsPresenter;
import com.stm.market.map.directions.view.MarketDirectionsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketDirectionsPresenterImpl implements MarketDirectionsPresenter {
    private MarketDirectionsInteractor marketDirectionsInteractor;
    private MarketDirectionsView marketDirectionsView;

    public MarketDirectionsPresenterImpl(MarketDirectionsView marketDirectionsView) {
        this.marketDirectionsInteractor = new MarketDirectionsInteractorImpl(this);
        this.marketDirectionsView = marketDirectionsView;
    }

    @Override
    public void init(User user, Market market, LocationDto locationDto) {
        marketDirectionsInteractor.setUser(user);
        marketDirectionsInteractor.setMarket(market);
        marketDirectionsInteractor.setLocationDto(locationDto);
        marketDirectionsInteractor.setGoogleRepository();

        marketDirectionsView.setMarkerLayout();
        marketDirectionsView.setGoogleMap();
        marketDirectionsView.setToolbarLayout();

        String marketAddress = market.getLotNumberAddress();
        marketDirectionsView.showToolbarTitle(marketAddress);
    }

    @Override
    public void onMarkerClick(Market market) {
        String marketName = market.getName();
        String marketAddress = market.getLotNumberAddress();
        String marketPhone = market.getPhone();
        String marketAvatar = market.getAvatar();

        marketDirectionsView.showMarketInfo();
        marketDirectionsView.showMarketName(marketName);
        marketDirectionsView.showMarketAddress(marketAddress);
        marketDirectionsView.showMarketAvatar(marketAvatar);

        if (marketPhone.trim().length() == 0 || marketPhone == null) {
            marketDirectionsView.goneMarketPhone();
        } else {
            marketDirectionsView.showMarketPhone(marketPhone);
        }
    }

    @Override
    public void onMapReady(String googleDirectionsApiKey) {
        Market market = marketDirectionsInteractor.getMarket();
        LocationDto locationDto = marketDirectionsInteractor.getLocationDto();
        String mode = "transit";
        marketDirectionsInteractor.getDirections(market, locationDto, mode, googleDirectionsApiKey);
        marketDirectionsView.showMapMarker(market, false);
        marketDirectionsView.showMapCurrentPositionMarker(locationDto);
    }

    @Override
    public void onSuccessGetDirections(Directions directions) {
        Market market = marketDirectionsInteractor.getMarket();
        marketDirectionsView.setGoogleMapPosition(market);

        ArrayList<LatLng> latLngs = new ArrayList<>();
        List<Route> routes = directions.getRoutes();
        int routeSize = routes.size();
        if (routeSize > 0) {
            Route route = routes.get(0);
            List<Leg> legs = route.getLegs();
            int legSize = legs.size();
            if (legSize > 0) {
                Leg leg = legs.get(0);
                List<Step> steps = leg.getSteps();

                Location location;
                int stepSize = steps.size();

                for (int i = 0; i < stepSize; i++) {
                    Step step = steps.get(i);
                    String polyLine = step.getPolyline().getPoints();

                    location = step.getStart_location();
                    latLngs.add(new LatLng(location.getLat(), location.getLng()));

                    ArrayList<LatLng> decodeList = decodePoly(polyLine);
                    latLngs.addAll(decodeList);

                    location = step.getEnd_location();
                    latLngs.add(new LatLng(location.getLat(), location.getLng()));
                }
            }

            int latLngSize = latLngs.size();
            if (latLngSize > 0) {
                PolylineOptions polylineOptions = new PolylineOptions();

                for (int i = 0; i < latLngSize; i++) {
                    LatLng latLng = latLngs.get(i);
                    polylineOptions.add(latLng);
                }

                marketDirectionsView.setPolyLineOptions(polylineOptions);
            }

        }
    }

    @Override
    public void onMapClick() {
        marketDirectionsView.goneMarketInfo();
    }

    @Override
    public void onClickPosition() {
        LocationDto locationDto = marketDirectionsInteractor.getLocationDto();
        if (locationDto != null) {
            marketDirectionsView.onCameraFocusUpdate(locationDto);
            marketDirectionsView.showMapCurrentPositionMarker(locationDto);
        } else {
            marketDirectionsView.showMessage("지도를 닫고, GPS를 확인해주세요");
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketDirectionsView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketDirectionsView.showMessage(httpErrorDto.message());
        }
    }

    public static ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        int length = encoded.length();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < length) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng latLng = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            latLngs.add(latLng);
        }
        return latLngs;
    }


}
