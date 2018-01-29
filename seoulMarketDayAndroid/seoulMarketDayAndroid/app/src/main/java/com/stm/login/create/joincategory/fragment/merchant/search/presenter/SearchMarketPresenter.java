package com.stm.login.create.joincategory.fragment.merchant.search.presenter;

import android.text.Editable;

import com.stm.common.dao.Market;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public interface SearchMarketPresenter {
    void init();
    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetMarketListByKeyword(List<Market> newMarkets, String keyword);

    void onTextChanged(Editable editable);

    void onScrollChange(int difference, String keyword);

    void onClickItem(Market market);

    void onClickBack();
}
