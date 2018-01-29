package com.stm.market.map.streetview.presenter.impl;

import com.google.android.gms.maps.StreetViewPanorama;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.market.map.streetview.interactor.MarketStreetViewInteractor;
import com.stm.market.map.streetview.interactor.impl.MarketStreetViewInteractorImpl;
import com.stm.market.map.streetview.presenter.MarketStreetViewPresenter;
import com.stm.market.map.streetview.view.MarketStreetViewView;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public class MarketStreetViewPresenterImpl implements MarketStreetViewPresenter {
    private MarketStreetViewInteractor marketStreetViewInteractor;
    private MarketStreetViewView marketStreetViewView;

    public MarketStreetViewPresenterImpl(MarketStreetViewView marketStreetViewView) {
        this.marketStreetViewInteractor = new MarketStreetViewInteractorImpl(this);
        this.marketStreetViewView = marketStreetViewView;
    }

    @Override
    public void init(User user, Market market) {
        marketStreetViewView.showProgressDialog();
        marketStreetViewInteractor.setUser(user);
        marketStreetViewInteractor.setMarket(market);


        marketStreetViewView.setStreetViewPanoramaFragment();
        marketStreetViewView.setToolbarLayout();

        String marketAddress = market.getLotNumberAddress();
        marketStreetViewView.showToolbarTitle(marketAddress);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Market market = marketStreetViewInteractor.getMarket();
        marketStreetViewView.setStreetViewPanoramaPosition(market, streetViewPanorama);

        marketStreetViewView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketStreetViewView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketStreetViewView.showMessage(httpErrorDto.message());
        }
    }
}
