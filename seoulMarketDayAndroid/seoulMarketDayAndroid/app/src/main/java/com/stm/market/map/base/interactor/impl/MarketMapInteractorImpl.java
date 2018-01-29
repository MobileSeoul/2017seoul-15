package com.stm.market.map.base.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.LogFlag;
import com.stm.market.map.base.interactor.MarketMapInteractor;
import com.stm.market.map.base.presenter.MarketMapPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketMapInteractorImpl implements MarketMapInteractor {
    private MarketMapPresenter marketMapPresenter;
    private User user;
    private Market market;
    private LocationDto locationDto;
    private static final Logger logger = LoggerFactory.getLogger(MarketMapInteractorImpl.class);

    public MarketMapInteractorImpl(MarketMapPresenter marketMapPresenter) {
        this.marketMapPresenter = marketMapPresenter;
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
