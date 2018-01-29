package com.stm.main.fragment.main.base.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.dto.LocationDto;
import com.stm.main.fragment.main.base.interactor.MainFragmentInteractor;
import com.stm.main.fragment.main.base.interactor.impl.MainFragmentInteractorImpl;
import com.stm.main.fragment.main.base.presenter.MainFragmentPresenter;
import com.stm.main.fragment.main.base.view.MainFragmentView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class MainFragmentPresenterImpl implements MainFragmentPresenter {
    private MainFragmentView mainFragmentView;
    private MainFragmentInteractor mainFragmentInteractor;

    public MainFragmentPresenterImpl(MainFragmentView mainFragmentView) {
        this.mainFragmentView = mainFragmentView;
        this.mainFragmentInteractor = new MainFragmentInteractorImpl(this);
    }

    @Override
    public void init() {
        mainFragmentView.setLocationManager();
        mainFragmentView.setLocationListener();
//        LocationDto locationDto = mainFragmentView.getCurrentLocation();

//        mainFragmentInteractor.setLocationDto(locationDto);
        mainFragmentInteractor.setMarketRepository();
        mainFragmentInteractor.setUserRepository();
        mainFragmentInteractor.setStoryRepository();
    }

    @Override
    public void onCreateView() {
        mainFragmentView.showProgressDialog();

        LocationDto locationDto = mainFragmentInteractor.getLocationDto();
        if (locationDto != null) {
            mainFragmentInteractor.getMarketListByLocation(locationDto);
        } else {
            mainFragmentInteractor.getMarketListByFollower();
        }

        mainFragmentInteractor.getBest5UserListPerMonth();
        mainFragmentInteractor.getBest5StoryListPerMonth();

        mainFragmentView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            mainFragmentView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            mainFragmentView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetMarketListByLocation(List<Market> markets) {
        int marketSize = markets.size();
        if (marketSize > 0) {
            mainFragmentView.setMarketByLocationItem(markets);

            LocationDto locationDto = mainFragmentInteractor.getLocationDto();
            String currentAddress = locationDto.getCurrentAddress();

            mainFragmentView.showRecommendTitle(currentAddress);
            mainFragmentView.showRecommendLocationIcon();
        } else {
            mainFragmentInteractor.getMarketListByFollower();
        }
    }

    @Override
    public void onSuccessGetMarketListByFollower(List<Market> markets) {
        mainFragmentView.setMarketByFollowerItem(markets);
        mainFragmentView.showRecommendTitle("베스트 시장");
        mainFragmentView.showRecommendBestIcon();
    }

    @Override
    public void onClickMarketRefresh() {
        mainFragmentView.clearMarketAdapter();
        mainFragmentView.showProgressDialog();

        LocationDto locationDto = mainFragmentView.getCurrentLocation();
        mainFragmentInteractor.setLocationDto(locationDto);
        if (locationDto != null) {
            mainFragmentInteractor.getMarketListByLocation(locationDto);
        } else {
            mainFragmentInteractor.getMarketListByFollower();
        }
        mainFragmentView.goneProgressDialog();
    }

    @Override
    public void onClickRegion(RegionCategory regionCategory) {
        mainFragmentView.navigateToRegionCategoryActivity(regionCategory);
    }

    @Override
    public void onClickMarket(Market market) {
        mainFragmentView.navigateToMarketActivity(market);
    }

    @Override
    public void onSuccessGetBest5UserListPerMonth(List<User> best5Users) {
        mainFragmentView.setBest5UserItem(best5Users);
    }

    @Override
    public void onClickBestUser(User user, int position) {
        mainFragmentView.navigateToMerchantDetailActivity(user, position);
    }

    @Override
    public void onClickBestStory(Story story, int position) {
        mainFragmentView.navigateToStoryDetailActivity(story, position);
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        mainFragmentView.navigateToSearchTagActivity(hashTag);
    }

    @Override
    public void onSuccessGetBest5StoryListPerMonth(List<Story> best5Stories) {
        mainFragmentView.setBest5StoryItem(best5Stories);
    }

}
