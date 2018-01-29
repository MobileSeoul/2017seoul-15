package com.stm.market.fragment.information.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.market.fragment.information.interactor.MarketInfoInteractor;
import com.stm.market.fragment.information.presenter.MarketInfoPresenter;

/**
 * Created by Dev-0 on 2017-07-04.
 */

public class MarketInfoInteractorImpl implements MarketInfoInteractor {
    private MarketInfoPresenter marketInfoPresenter;
    private User user;
    private Market market;

    public MarketInfoInteractorImpl(MarketInfoPresenter marketInfoPresenter) {
        this.marketInfoPresenter = marketInfoPresenter;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Market getMarket() {
        return this.market;
    }

    @Override
    public void setMarket(Market market) {
        this.market = market;
    }
}
