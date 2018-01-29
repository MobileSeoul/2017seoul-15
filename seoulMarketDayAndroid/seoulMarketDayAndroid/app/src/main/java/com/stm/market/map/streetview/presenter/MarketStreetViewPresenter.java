package com.stm.market.map.streetview.presenter;

import com.google.android.gms.maps.StreetViewPanorama;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface MarketStreetViewPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Market market);

    void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama);
}
