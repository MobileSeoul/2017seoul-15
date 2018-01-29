package com.stm.main.fragment.main.search.fragment.market.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.main.fragment.main.search.fragment.market.interactor.SearchMarketInteractor;
import com.stm.main.fragment.main.search.fragment.market.presenter.SearchMarketPresenter;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchMarketInteractorImpl implements SearchMarketInteractor {
    private SearchMarketPresenter searchMarketPresenter;
    private User user;
    private String keyword;
    private List<Market> markets;
    private MarketRepository marketRepository;
    private static final Logger logger = LoggerFactory.getLogger(SearchMarketInteractorImpl.class);

    public SearchMarketInteractorImpl(SearchMarketPresenter searchMarketPresenter) {
        this.searchMarketPresenter = searchMarketPresenter;
        this.markets = new ArrayList<>();
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
    public List<Market> getMarkets() {
        return markets;
    }

    @Override
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void setMarketRepository() {
        this.marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketRepository(String accessToken) {
        this.marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketsAddAll(List<Market> markets) {
        this.markets.addAll(markets);
    }

    @Override
    public void getMarketListByKeywordAndOffset(final String keyword, long userId, long offset) {
        Call<List<Market>> callFindMarketListByKeywordAndOffsetApi = marketRepository.findMarketListByKeywordAndOffset(keyword, userId, offset);
        callFindMarketListByKeywordAndOffsetApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    List<Market> markets = response.body();
                    searchMarketPresenter.onSuccessGetMarketListByKeywordAndOffset(markets, keyword);
                } else {
                    searchMarketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                searchMarketPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketListByKeywordAndOffset(final String keyword, long offset) {
        Call<List<Market>> callFindMarketListByKeywordAndOffsetApi = marketRepository.findMarketListByKeywordAndOffset(keyword, offset);
        callFindMarketListByKeywordAndOffsetApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    List<Market> markets = response.body();
                    searchMarketPresenter.onSuccessGetMarketListByKeywordAndOffset(markets, keyword);
                } else {
                    searchMarketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                searchMarketPresenter.onNetworkError(null);
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
