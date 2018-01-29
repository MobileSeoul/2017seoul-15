package com.stm.market.map.directions.interactor.impl;

import com.stm.common.dto.Directions;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.market.map.directions.interactor.MarketDirectionsInteractor;
import com.stm.market.map.directions.presenter.MarketDirectionsPresenter;
import com.stm.repository.remote.GoogleRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketDirectionsInteractorImpl implements MarketDirectionsInteractor {
    private MarketDirectionsPresenter marketDirectionsPresenter;
    private User user;
    private Market market;
    private LocationDto locationDto;
    private String googleMapKey;
    private GoogleRepository googleRepository;

    private static final Logger logger = LoggerFactory.getLogger(MarketDirectionsInteractorImpl.class);

    public MarketDirectionsInteractorImpl(MarketDirectionsPresenter marketDirectionsPresenter) {
        this.marketDirectionsPresenter = marketDirectionsPresenter;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Market getMarket() {
        return market;
    }

    @Override
    public void setMarket(Market market) {
        this.market = market;
    }

    @Override
    public LocationDto getLocationDto() {
        return locationDto;
    }

    @Override
    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
    }
    @Override
    public String getGoogleMapKey() {
        return googleMapKey;
    }
    @Override
    public void setGoogleMapKey(String googleMapKey) {
        this.googleMapKey = googleMapKey;
    }

    @Override
    public void setGoogleRepository() {
        this.googleRepository = new NetworkInterceptor().getRetrofitForGoogleRepository().create(GoogleRepository.class);
    }

    @Override
    public void getDirections(Market market, LocationDto locationDto, String mode, String googleApiKey) {
        double fromLatitude = locationDto.getLatitude();
        double fromLongitude = locationDto.getLongitude();
        double marketLatitude = market.getLatitude();
        double marketLongitude = market.getLongitude();
        Call<Directions> callFindDirectionsApi = googleRepository.findDirections((fromLatitude + "," + fromLongitude), (marketLatitude + "," + marketLongitude), mode, googleApiKey);
        callFindDirectionsApi.enqueue(new Callback<Directions>() {
            @Override
            public void onResponse(Call<Directions> call, Response<Directions> response) {
                if (response.isSuccessful()) {
                    Directions directions = response.body();
                    marketDirectionsPresenter.onSuccessGetDirections(directions);
                } else {
                    marketDirectionsPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Directions> call, Throwable t) {
                log(t);
                marketDirectionsPresenter.onNetworkError(null);
            }
        });

    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }
}
