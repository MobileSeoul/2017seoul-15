package com.stm.regioncategory.base.presenter.impl;

import android.view.View;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.HttpErrorDto;
import com.stm.regioncategory.base.interactor.RegionCategoryInteractor;
import com.stm.regioncategory.base.interactor.impl.RegionCategoryInteractorImpl;
import com.stm.regioncategory.base.presenter.RegionCategoryPresenter;
import com.stm.regioncategory.base.view.RegionCategoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public class RegionCategoryPresenterImpl implements RegionCategoryPresenter {
    private RegionCategoryView regionCategoryView;
    private RegionCategoryInteractor regionCategoryInteractor;

    public RegionCategoryPresenterImpl(RegionCategoryView regionCategoryView) {
        this.regionCategoryView = regionCategoryView;
        this.regionCategoryInteractor = new RegionCategoryInteractorImpl(this);
    }

    @Override
    public void init(RegionCategory regionCategory) {
        regionCategoryInteractor.setRegionCategory(regionCategory);

        regionCategoryView.init();
        regionCategoryView.setToolbarLayout();
    }

    @Override
    public void onLoadData() {
        regionCategoryView.showProgressDialog();
        regionCategoryInteractor.setMarketRepository();
        regionCategoryInteractor.setMarketCategoryRepository();
        regionCategoryInteractor.setRegionCategoryRepository();

        RegionCategory regionCategory = regionCategoryInteractor.getRegionCategory();
        long regionCategoryId = regionCategory.getId();

        regionCategoryInteractor.getMarketCategoryList();
        regionCategoryInteractor.getRegionCategoryById(regionCategoryId);
        regionCategoryInteractor.getMarketListById(regionCategoryId);

        regionCategoryView.goneProgressDialog();
    }

    @Override
    public void onClickMarketCategory(int isShown) {
        if (isShown == View.VISIBLE) {
            regionCategoryView.goneMarketCategory();
        } else {
            regionCategoryView.showMarketCategory();
        }
    }

    @Override
    public void onClickMarketCategoryItem(MarketCategory marketCategory) {
        List<Market> markets = regionCategoryInteractor.getMarkets();
        int marketSize = markets.size();

        RegionCategory regionCategory = regionCategoryInteractor.getRegionCategory();
        long regionCategoryId = regionCategory.getId();

        String name = marketCategory.getName();

        regionCategoryView.showMarketCategoryName(name);
        regionCategoryView.goneMarketCategory();

        regionCategoryView.showProgressDialog();

        if (marketSize == 0) {
            regionCategoryView.goneEmptyView();
        }
        regionCategoryInteractor.getMarketListByIdAndMarketCategoryId(regionCategoryId, marketCategory);
        regionCategoryView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            regionCategoryView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            regionCategoryView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetMarketCategoryList(List<MarketCategory> marketCategories) {
        regionCategoryView.setMarketCategoryItem(marketCategories);
    }

    @Override
    public void onSuccessGetRegionCategoryById(RegionCategory regionCategory) {
        regionCategoryView.showToolbarTitle(regionCategory.getName());
    }

    @Override
    public void onSuccessGetMarketListById(List<Market> markets) {
        regionCategoryView.setMarketItem(markets);
    }

    @Override
    public void onClickMap() {
        RegionCategory regionCategory = regionCategoryInteractor.getRegionCategory();
        regionCategoryView.navigateToRegionCategoryMapActivity(regionCategory);
    }

    @Override
    public void onSuccessGetMarketListByIdAndMarketCategoryId(List<Market> markets, MarketCategory marketCategory) {
        RegionCategory regionCategory = regionCategoryInteractor.getRegionCategory();
        String regionCategoryName = regionCategory.getName();
        String marketCategoryName = marketCategory.getName();
        int marketSize = markets.size();

        regionCategoryView.clearMarketAdapter();
        regionCategoryView.setMarketItem(markets);

        if (marketSize == 0) {
            regionCategoryView.showEmptyView();
            String message = regionCategoryName+"에는 "+ marketCategoryName+ "이 없습니다. \n다른 시장유형을 찾아보세요.";
            regionCategoryView.showEmptyViewText(message);
        }
    }

    @Override
    public void onClickMarket(Market market) {
        regionCategoryView.navigateToMarketActivity(market);
    }

}
