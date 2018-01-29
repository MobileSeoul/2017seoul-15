package com.stm.main.fragment.main.search.fragment.market.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.main.fragment.main.search.fragment.market.interactor.SearchMarketInteractor;
import com.stm.main.fragment.main.search.fragment.market.interactor.impl.SearchMarketInteractorImpl;
import com.stm.main.fragment.main.search.fragment.market.presenter.SearchMarketPresenter;
import com.stm.main.fragment.main.search.fragment.market.view.SearchMarketView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchMarketPresenterImpl implements SearchMarketPresenter {
    private SearchMarketInteractor searchMarketInteractor;
    private SearchMarketView searchMarketView;

    public SearchMarketPresenterImpl(SearchMarketView searchMarketView) {
        this.searchMarketInteractor = new SearchMarketInteractorImpl(this);
        this.searchMarketView = searchMarketView;
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
    public void init(User user) {
        searchMarketInteractor.setUser(user);

        if (user != null) {
            String accessToken = user.getAccessToken();
            searchMarketInteractor.setMarketRepository(accessToken);
        } else {
            searchMarketInteractor.setMarketRepository();
        }
    }

    @Override
    public void onCreateView() {
        searchMarketView.setOnScrollChangeListener();
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            searchMarketView.showProgressDialog();

            String keyword = searchMarketInteractor.getKeyword();
            User user = searchMarketInteractor.getUser();
            List<Market> markets = searchMarketInteractor.getMarkets();
            long offset = markets.size();

            if (user != null) {
                long userId = user.getId();
                searchMarketInteractor.getMarketListByKeywordAndOffset(keyword, userId, offset);
            } else {
                searchMarketInteractor.getMarketListByKeywordAndOffset(keyword, offset);
            }
        }
    }

    @Override
    public void onClickMarket(Market market) {
        searchMarketView.navigateToMarketDetailActivity(market);
    }

    @Override
    public void getMarketListByKeyword(String keyword) {
        searchMarketView.showProgressDialog();

        String prevKeyword = searchMarketInteractor.getKeyword();
        User user = searchMarketInteractor.getUser();
        List<Market> markets = searchMarketInteractor.getMarkets();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        if (prevKeyword != null && prevKeyword.equals(keyword)) {
            offset = markets.size();
        }

        if (user != null) {
            long userId = user.getId();
            searchMarketInteractor.getMarketListByKeywordAndOffset(keyword, userId, offset);
        } else {
            searchMarketInteractor.getMarketListByKeywordAndOffset(keyword, offset);
        }
    }

    @Override
    public void onSuccessGetMarketListByKeywordAndOffset(List<Market> newMarkets, String keyword) {
        String prevKeyword = searchMarketInteractor.getKeyword();
        int newMarketSize = newMarkets.size();

        if (newMarketSize > 0) {
            if (prevKeyword == null) {
                searchMarketInteractor.setKeyword(keyword);
                searchMarketInteractor.setMarkets(newMarkets);
                searchMarketView.clearSearchMarketAdapter();
                searchMarketView.setSearchMarketAdapterItem(newMarkets);
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
                    searchMarketView.clearSearchMarketAdapter();
                    searchMarketView.setSearchMarketAdapterItem(newMarkets);
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
}
