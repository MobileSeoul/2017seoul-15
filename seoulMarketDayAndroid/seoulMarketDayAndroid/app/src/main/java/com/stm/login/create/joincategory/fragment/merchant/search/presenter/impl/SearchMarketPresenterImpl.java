package com.stm.login.create.joincategory.fragment.merchant.search.presenter.impl;

import android.text.Editable;

import com.stm.common.dao.Market;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.create.joincategory.fragment.merchant.search.interactor.SearchMarketInteractor;
import com.stm.login.create.joincategory.fragment.merchant.search.interactor.impl.SearchMarketInteractorImpl;
import com.stm.login.create.joincategory.fragment.merchant.search.presenter.SearchMarketPresenter;
import com.stm.login.create.joincategory.fragment.merchant.search.view.SearchMarketView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class SearchMarketPresenterImpl implements SearchMarketPresenter {
    private SearchMarketView searchMarketView;
    private SearchMarketInteractor searchMarketInteractor;

    public SearchMarketPresenterImpl(SearchMarketView searchMarketView) {
        this.searchMarketView = searchMarketView;
        this.searchMarketInteractor = new SearchMarketInteractorImpl(this);
    }

    @Override
    public void init() {
        searchMarketInteractor.setMarketRepositoryCreation();
        searchMarketView.setToolbarLayout();
        searchMarketView.setOnScrollChangeListener();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            searchMarketView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            searchMarketView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetMarketListByKeyword(List<Market> newMarkets, String keyword) {
//        int newMarketsSize = newMarkets.size();
//
//        List<Market> markets = searchMarketInteractor.getMarkets();
//        int marketSize = markets.size();
//        if (marketSize == 0) {
//            searchMarketInteractor.setMarkets(newMarkets);
//            searchMarketView.clearMarketAdapter();
//            searchMarketView.setMarketByKeywordAndOffsetItem(newMarkets);
//        } else {
//            searchMarketInteractor.setMarketsAddAll(newMarkets);
//            searchMarketView.notifyItemRangeInserted(marketSize, newMarketsSize);
//        }
//
//        searchMarketView.goneProgressDialog();

        String prevKeyword = searchMarketInteractor.getKeyword();
        int newMarketSize = newMarkets.size();

        if (newMarketSize > 0) {
            if (prevKeyword == null) {
                searchMarketInteractor.setKeyword(keyword);
                searchMarketInteractor.setMarkets(newMarkets);
                searchMarketView.clearMarketAdapter();
                searchMarketView.setMarketByKeywordAndOffsetItem(newMarkets);
                searchMarketView.goneEmptyView();

            } else {
                if (prevKeyword.equals(keyword)) {
                    List<Market> markets = searchMarketInteractor.getMarkets();
                    int marketSize = markets.size();

                    searchMarketInteractor.setMarketsAddAll(newMarkets);
                    searchMarketView.notifyItemRangeInserted(marketSize, newMarketSize);
                    searchMarketView.goneEmptyView();
                } else {
                    searchMarketInteractor.setKeyword(keyword);
                    searchMarketInteractor.setMarkets(newMarkets);
                    searchMarketView.clearMarketAdapter();
                    searchMarketView.setMarketByKeywordAndOffsetItem(newMarkets);
                    searchMarketView.goneEmptyView();
                }
            }
        } else {
            if (prevKeyword == null || (prevKeyword != null && !keyword.equals(prevKeyword))) {
                searchMarketView.showEmptyView();
            }

        }

        searchMarketView.goneProgressDialog();
    }

    @Override
    public void onTextChanged(Editable editable) {
        String keyword = editable.toString();
        int keywordLength = keyword.length();

        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        if (keywordLength != 0) {
            searchMarketView.showProgressDialog();
            searchMarketInteractor.getMarketListByKeyword(keyword, offset);
        }
    }


    @Override
    public void onScrollChange(int difference, String keyword) {
        int keywordLength = keyword.length();
        if (keywordLength != 0) {
            if (difference <= 0) {
                searchMarketView.showProgressDialog();

                List<Market> markets = searchMarketInteractor.getMarkets();
                int marketSize = markets.size();

                long offset = marketSize;
                searchMarketInteractor.getMarketListByKeyword(keyword, offset);
            }
        }
    }

    @Override
    public void onClickItem(Market market) {
        searchMarketView.navigateToBackForResultOk(market);
    }

    @Override
    public void onClickBack() {
        searchMarketView.navigateToBackForResultCancel();
    }


}
