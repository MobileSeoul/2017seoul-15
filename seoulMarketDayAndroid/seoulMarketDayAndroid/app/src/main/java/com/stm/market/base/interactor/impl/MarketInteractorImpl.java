package com.stm.market.base.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.market.base.interactor.MarketInteractor;
import com.stm.market.base.presenter.MarketPresenter;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.repository.remote.MarketRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public class MarketInteractorImpl implements MarketInteractor {
    private MarketPresenter marketPresenter;
    private Market market;
    private User user;
    private boolean isToolbarTitleShown;
    private int totalScrollRange;

    private MarketRepository marketRepository;

    private static final Logger logger = LoggerFactory.getLogger(MarketInteractorImpl.class);

    public MarketInteractorImpl(MarketPresenter marketPresenter) {
        this.marketPresenter = marketPresenter;
        this.isToolbarTitleShown = false;
        this.totalScrollRange = -1;
    }

    @Override
    public void setMarketRepository() {
        marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketRepository(String accessToken) {
        marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
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
    public void getMarketById(long id) {
        Call<Market> callGetMarketByIdApi = marketRepository.findMarketById(id);
        callGetMarketByIdApi.enqueue(new Callback<Market>() {
            @Override
            public void onResponse(Call<Market> call, Response<Market> response) {
                if (response.isSuccessful()) {
                    Market market = response.body();
                    marketPresenter.onSuccessGetMarketById(market);
                } else {
                    marketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Market> call, Throwable t) {
                log(t);
                marketPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketByIdAndUserId(long id, long userId) {
        Call<Market> callGetMarketByIdApi = marketRepository.findMarketByIdAndUserId(id, userId);
        callGetMarketByIdApi.enqueue(new Callback<Market>() {
            @Override
            public void onResponse(Call<Market> call, Response<Market> response) {
                if (response.isSuccessful()) {
                    Market market = response.body();
                    marketPresenter.onSuccessGetMarketById(market);
                } else {
                    marketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Market> call, Throwable t) {
                log(t);
                marketPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketByIdForRefresh(long marketId, long userId, final int position) {
        Call<Market> callGetMarketByIdApi = marketRepository.findMarketByIdAndUserId(marketId, userId);
        callGetMarketByIdApi.enqueue(new Callback<Market>() {
            @Override
            public void onResponse(Call<Market> call, Response<Market> response) {
                if (response.isSuccessful()) {
                    Market market = response.body();
                    marketPresenter.onSuccessGetMarketByIdForRefresh(market, position);
                } else {
                    marketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Market> call, Throwable t) {
                log(t);
                marketPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketByIdForRefresh(long marketId, final int position) {
        Call<Market> callGetMarketByIdApi = marketRepository.findMarketById(marketId);
        callGetMarketByIdApi.enqueue(new Callback<Market>() {
            @Override
            public void onResponse(Call<Market> call, Response<Market> response) {
                if (response.isSuccessful()) {
                    Market market = response.body();
                    marketPresenter.onSuccessGetMarketByIdForRefresh(market, position);
                } else {
                    marketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Market> call, Throwable t) {
                log(t);
                marketPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setMarketFollower(Market market, MarketFollower marketFollower) {
        long id = market.getId();
        Call<ResponseBody> callSaveMarketFollowerApi = marketRepository.saveMarketFollower(id, marketFollower);
        callSaveMarketFollowerApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    marketPresenter.onSuccessSetMarketFollower();
                } else {
                    marketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketPresenter.onNetworkError(null);
            }
        });

    }

    @Override
    public void deleteMarketFollower(Market market, MarketFollower marketFollower) {
        long id = market.getId();
        Call<ResponseBody> callDeleteMarketFollowerApi = marketRepository.deleteMarketFollower(id, marketFollower);
        callDeleteMarketFollowerApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    marketPresenter.onSuccessDeleteMarketFollower();
                } else {
                    marketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketPresenter.onNetworkError(null);
            }
        });

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
    public boolean isToolbarTitleShown() {
        return isToolbarTitleShown;
    }

    @Override
    public void setToolbarTitleShown(boolean toolbarTitleShown) {
        isToolbarTitleShown = toolbarTitleShown;
    }

    @Override
    public int getTotalScrollRange() {
        return totalScrollRange;
    }

    @Override
    public void setTotalScrollRange(int totalScrollRange) {
        this.totalScrollRange = totalScrollRange;
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
