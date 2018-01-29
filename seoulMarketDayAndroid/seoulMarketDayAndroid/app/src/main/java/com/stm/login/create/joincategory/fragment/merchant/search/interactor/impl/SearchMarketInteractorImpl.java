package com.stm.login.create.joincategory.fragment.merchant.search.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.create.joincategory.fragment.merchant.search.interactor.SearchMarketInteractor;
import com.stm.login.create.joincategory.fragment.merchant.search.presenter.SearchMarketPresenter;
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
 * Created by ㅇㅇ on 2017-06-21.
 */

public class SearchMarketInteractorImpl implements SearchMarketInteractor {
    private SearchMarketPresenter searchMarketPresenter;
    private static final Logger logger = LoggerFactory.getLogger(SearchMarketInteractorImpl.class);

    private String keyword;
    private List<Market> markets;
    private MarketRepository marketRepository;

    public SearchMarketInteractorImpl(SearchMarketPresenter searchMarketPresenter) {
        this.searchMarketPresenter = searchMarketPresenter;
        this.markets = new ArrayList<>();
    }

    @Override
    public void setMarketRepositoryCreation() {
        marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
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
    public void getMarketListByKeyword(final String keyword, long offset) {
        Call<List<Market>> callGetMarketListByKeywordApi = marketRepository.findMarketListByKeywordAndOffset(keyword, offset);
        callGetMarketListByKeywordApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    List<Market> markets = response.body();
                    searchMarketPresenter.onSuccessGetMarketListByKeyword(markets, keyword);
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
    public List<Market> getMarkets() {
        return markets;
    }

    @Override
    public void setMarketsAddAll(List<Market> markets) {
        this.markets.addAll(markets);
    }

    @Override
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
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
