package com.stm.regioncategory.base.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.RegionCategory;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.regioncategory.base.interactor.RegionCategoryInteractor;
import com.stm.regioncategory.base.presenter.RegionCategoryPresenter;
import com.stm.repository.remote.MarketCategoryRepository;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.RegionCategoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public class RegionCategoryInteractorImpl implements RegionCategoryInteractor {
    private RegionCategoryPresenter regionCategoryPresenter;
    private RegionCategory regionCategory;
    private MarketRepository marketRepository;
    private MarketCategoryRepository marketCategoryRepository;
    private RegionCategoryRepository regionCategoryRepository;
    private List<MarketCategory> marketCategories;
    private List<Market> markets;

    private static final Logger logger = LoggerFactory.getLogger(RegionCategoryInteractorImpl.class);

    public RegionCategoryInteractorImpl(RegionCategoryPresenter regionCategoryPresenter) {
        this.regionCategoryPresenter = regionCategoryPresenter;
        this.marketCategories = new ArrayList<>();

        MarketCategory marketCategory = new MarketCategory();
        marketCategory.setName("전체보기");
        this.marketCategories.add(0, marketCategory);
    }

    @Override
    public void setMarketRepository() {
        marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketCategoryRepository() {
        marketCategoryRepository = new NetworkInterceptor().getRetrofitForMarketCategoryRepository().create(MarketCategoryRepository.class);
    }

    @Override
    public void setRegionCategoryRepository() {
        regionCategoryRepository = new NetworkInterceptor().getRetrofitForRegionCategoryRepository().create(RegionCategoryRepository.class);
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
    public List<Market> getMarkets() {
        return markets;
    }

    @Override
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }

    @Override
    public void getMarketListById(long regionCategoryId) {
        Call<List<Market>> callGetMarketListByIdApi = regionCategoryRepository.findMarketListById(regionCategoryId);
        callGetMarketListByIdApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    markets = response.body();
                    regionCategoryPresenter.onSuccessGetMarketListById(markets);
                } else {
                    regionCategoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                regionCategoryPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketListByIdAndMarketCategoryId(long regionCategoryId, final MarketCategory marketCategory) {
        long marketCategoryId = marketCategory.getId();
        Call<List<Market>> callGetMarketListByIAndMarketCategoryIdApi = regionCategoryRepository.findMarketListByIdAndMarketCategoryId(regionCategoryId, marketCategoryId);
        callGetMarketListByIAndMarketCategoryIdApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    markets = response.body();
                    regionCategoryPresenter.onSuccessGetMarketListByIdAndMarketCategoryId(markets, marketCategory);
                } else {
                    regionCategoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                regionCategoryPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketCategoryList() {
        Call<List<MarketCategory>> callGetMarketCategoryListApi = marketCategoryRepository.findMarketCategoryList();
        callGetMarketCategoryListApi.enqueue(new Callback<List<MarketCategory>>() {
            @Override
            public void onResponse(Call<List<MarketCategory>> call, Response<List<MarketCategory>> response) {
                if (response.isSuccessful()) {
                    marketCategories.addAll(response.body());
                    regionCategoryPresenter.onSuccessGetMarketCategoryList(marketCategories);
                } else {
                    regionCategoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<MarketCategory>> call, Throwable t) {
                log(t);
                regionCategoryPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getRegionCategoryById(long regionCategoryId) {
        Call<RegionCategory> callGetRegionCategoryByIdApi = regionCategoryRepository.findRegionCategoryById(regionCategoryId);
        callGetRegionCategoryByIdApi.enqueue(new Callback<RegionCategory>() {
            @Override
            public void onResponse(Call<RegionCategory> call, Response<RegionCategory> response) {
                if (response.isSuccessful()) {
                    regionCategory = response.body();
                    regionCategoryPresenter.onSuccessGetRegionCategoryById(regionCategory);
                } else {
                    regionCategoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<RegionCategory> call, Throwable t) {
                log(t);
                regionCategoryPresenter.onNetworkError(null);
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
