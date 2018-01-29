package com.stm.market.map.streetview.view;

import com.google.android.gms.maps.StreetViewPanorama;
import com.stm.R;
import com.stm.common.dao.Market;

import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketStreetViewView {
    void setToolbarLayout();

    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void showToolbarTitle(String message);

    void setStreetViewPanoramaFragment();

    void setStreetViewPanoramaPosition(Market market, StreetViewPanorama streetViewPanorama);

    void onClickBack();
}
