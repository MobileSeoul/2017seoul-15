package com.stm.market.map.streetview.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.market.map.streetview.interactor.MarketStreetViewInteractor;
import com.stm.market.map.streetview.presenter.MarketStreetViewPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketStreetViewInteractorImpl implements MarketStreetViewInteractor {
    private MarketStreetViewPresenter marketStreetViewPresenter;
    private User user;
    private Market market;
    private static final Logger logger = LoggerFactory.getLogger(MarketStreetViewInteractorImpl.class);

    public MarketStreetViewInteractorImpl(MarketStreetViewPresenter marketStreetViewPresenter) {
        this.marketStreetViewPresenter = marketStreetViewPresenter;
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
