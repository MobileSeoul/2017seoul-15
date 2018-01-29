package com.stm.regioncategory.map.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.regioncategory.map.interactor.RegionCategoryMapInteractor;
import com.stm.regioncategory.map.presenter.RegionCategoryMapPresenter;
import com.stm.repository.remote.RegionCategoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-06-19.
 */

public class RegionCategoryMapInteractorImpl implements RegionCategoryMapInteractor {
    private RegionCategoryMapPresenter regionCategoryMapPresenter;
    private RegionCategory regionCategory;
    private LocationDto locationDto;
    private List<Market> markets;

    private RegionCategoryRepository regionCategoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(RegionCategoryMapInteractorImpl.class);


    public RegionCategoryMapInteractorImpl(RegionCategoryMapPresenter regionCategoryMapPresenter) {
        this.regionCategoryMapPresenter = regionCategoryMapPresenter;
    }

    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    @Override
    public RegionCategory getRegionCategory() {
        return regionCategory;
    }

    @Override
    public void setRegionCategory(RegionCategory regionCategory) {
        this.regionCategory = regionCategory;
    }

    @Override
    public void setRegionCategoryRepository() {
        regionCategoryRepository = new NetworkInterceptor().getRetrofitForRegionCategoryRepository().create(RegionCategoryRepository.class);
    }

    @Override
    public void getMarketListById(long regionCategoryId) {
        Call<List<Market>> callGetMarketListByIdApi = regionCategoryRepository.findMarketListById(regionCategoryId);
        callGetMarketListByIdApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    markets = response.body();
                    regionCategoryMapPresenter.onSuccessGetMarketListById(markets);
                } else {
                    regionCategoryMapPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                regionCategoryMapPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketListByIdAndLocation(long regionCategoryId, LocationDto locationDto) {
        double latitude = locationDto.getLatitude();
        double longitude = locationDto.getLongitude();

        HashMap<String, Double> map = new HashMap<>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        Call<List<Market>> callGetMarketListByIdAndLocationApi = regionCategoryRepository.findMarketListByIdAndLocation(regionCategoryId,map);
        callGetMarketListByIdAndLocationApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    markets = response.body();
                    regionCategoryMapPresenter.onSuccessGetMarketListByIdAndLocation(markets);
                } else {
                    regionCategoryMapPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                regionCategoryMapPresenter.onNetworkError(null);
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
