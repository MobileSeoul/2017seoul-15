package com.stm.market.base.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketCategory;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.MarketFragmentFlag;
import com.stm.market.base.interactor.MarketInteractor;
import com.stm.market.base.interactor.impl.MarketInteractorImpl;
import com.stm.market.base.presenter.MarketPresenter;
import com.stm.market.base.view.MarketView;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public class MarketPresenterImpl implements MarketPresenter {
    private MarketView marketView;
    private MarketInteractor marketInteractor;

    public MarketPresenterImpl(MarketView marketView) {
        this.marketView = marketView;
        this.marketInteractor = new MarketInteractorImpl(this);
    }

    @Override
    public void init(User user, Market market) {
        marketView.showProgressDialog();
        marketInteractor.setUser(user);

        long marketId = market.getId();
        if (user == null) {
            marketInteractor.setMarketRepository();
            marketInteractor.getMarketById(marketId);
        } else {
            long userId = user.getId();
            String accessToken = user.getAccessToken();
            marketInteractor.setMarketRepository(accessToken);
            marketInteractor.getMarketByIdAndUserId(marketId, userId);
        }

        marketView.setTabLayout();
        marketView.setToolbarLayout();
        marketView.setAddOnOffsetChangedListener();
        marketView.setOnRefreshListener();
        marketView.goneProgressDialog();
    }


    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetMarketById(Market market) {
        marketInteractor.setMarket(market);
        User user = marketInteractor.getUser();

        MarketCategory marketCategory = market.getMarketCategory();
        String categoryName = marketCategory.getName();
        String avatar = market.getAvatar();
        String name = market.getName();
        String address = market.getLotNumberAddress();
        Boolean isFollowed = market.getFollowed();

        marketView.setTabAdapter(user, market);

        marketView.showMarketAvatar(avatar);
        marketView.showMarketName(name);
        marketView.showMarketCategory(categoryName);
        marketView.showMarketAddress(address);

        if (isFollowed != null && isFollowed) {
            marketView.showFollowCancelButton();
        } else {
            marketView.showFollowButton();
        }
    }

    @Override
    public void onSuccessSetMarketFollower() {
        Market market = marketInteractor.getMarket();
        market.setFollowed(true);
        marketView.showFollowCancelButton();
        marketView.goneFollowButton();
    }

    @Override
    public void onSuccessDeleteMarketFollower() {
        Market market = marketInteractor.getMarket();
        market.setFollowed(false);
        marketView.showFollowButton();
        marketView.goneFollowCancelButton();
    }

    @Override
    public void onSuccessGetMarketByIdForRefresh(Market market, int position) {
        marketInteractor.setMarket(market);

        MarketCategory marketCategory = market.getMarketCategory();
        String categoryName = marketCategory.getName();
        String avatar = market.getAvatar();
        String name = market.getName();
        String address = market.getLotNumberAddress();
        Boolean isFollowed = market.getFollowed();

        marketView.showMarketAvatar(avatar);
        marketView.showMarketName(name);
        marketView.showMarketCategory(categoryName);
        marketView.showMarketAddress(address);

        if (isFollowed != null && isFollowed) {
            marketView.showFollowCancelButton();
        } else {
            marketView.showFollowButton();
        }

        if (position == MarketFragmentFlag.HOME) {
            marketView.setMainFragmentRefresh();
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MarketFragmentFlag.INFO) {
            marketView.setInfoFragmentRefresh();
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MarketFragmentFlag.STORY) {
            marketView.setStoryFragmentRefresh();
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MarketFragmentFlag.PHOTO) {
            marketView.setPhotoFragmentRefresh();
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MarketFragmentFlag.VIDEO) {
            marketView.setVideoFragmentRefresh();
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MarketFragmentFlag.MERCHANT) {
            marketView.setMerchantFragmentRefresh();
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }
    }

    @Override
    public void onChangeFragment() {
        marketView.showProgressDialog();
        Market market = marketInteractor.getMarket();
        marketView.setTabAdapterMarket(market);
        marketView.setTabAdapterNotifyDataSetChanged();
        marketView.goneProgressDialog();
    }

    @Override
    public void onRefresh(int position) {
        Market market = marketInteractor.getMarket();
        long marketId = market.getId();
        User user = marketInteractor.getUser();

        if (user != null) {
            long userId = user.getId();
            marketInteractor.getMarketByIdForRefresh(marketId, userId, position);
        } else {
            marketInteractor.getMarketByIdForRefresh(marketId, position);
        }
    }

    @Override
    public void onOffsetChanged(int totalScrollRange, int verticalOffset) {
        int scrollRange = marketInteractor.getTotalScrollRange();
        boolean isShown = marketInteractor.isToolbarTitleShown();


        if (scrollRange == -1) {
            scrollRange = totalScrollRange;
            marketInteractor.setTotalScrollRange(scrollRange);
        }

        if (scrollRange + verticalOffset == 0) {
            Market market = marketInteractor.getMarket();
            Boolean isFollowed = market.getFollowed();

            String marketName = market.getName();
            marketView.showToolbarTitle(marketName);
            marketView.showToolbarBackButtonWithDefaultColor();
            marketView.setSwipeRefreshLayoutEnabled(false);

            isShown = true;
            marketInteractor.setToolbarTitleShown(isShown);

            if (isFollowed != null && isFollowed) {
                marketView.showToolbarFollowCancelButton();
                marketView.goneToolbarFollowButton();
            } else {
                marketView.showToolbarFollowButton();
                marketView.goneToolbarFollowCancelButton();
            }

        } else if (isShown) {
            Market market = marketInteractor.getMarket();
            Boolean isFollowed = market.getFollowed();

            marketView.showToolbarTitle("");
            marketView.showToolbarBackButtonWithWhiteColor();

            isShown = false;
            marketInteractor.setToolbarTitleShown(isShown);

            if (isFollowed != null && isFollowed) {
                marketView.goneToolbarFollowCancelButton();
            } else {
                marketView.goneToolbarFollowButton();
            }
        }

        if (scrollRange + verticalOffset == scrollRange && !isShown) {
            marketView.setSwipeRefreshLayoutEnabled(true);
            marketView.setOnRefreshListener();
        } else {
            marketView.setSwipeRefreshLayoutEnabled(false);
            marketView.setSwipeRefreshLayoutRefreshing(false);
        }
    }

    @Override
    public void onResume(User user) {
        if (user != null && marketInteractor.getUser() == null) {
            marketView.showProgressDialog();
            marketInteractor.setUser(user);
            String accessToken = user.getAccessToken();
            marketInteractor.setMarketRepository(accessToken);

            Market market = marketInteractor.getMarket();
            boolean isFollowed = market.getFollowed();
            long marketId = market.getId();
            long userId = user.getId();
            marketView.clearTabLayout();

            marketView.setTabLayout();
            marketView.setAddOnOffsetChangedListener();
            marketInteractor.getMarketByIdAndUserId(marketId, userId);

            if (isFollowed) {
                marketView.showFollowCancelButton();
            } else {
                marketView.showFollowButton();
            }

            marketView.goneProgressDialog();
        }
    }

    @Override
    public void onClickFollow() {
        User user = marketInteractor.getUser();

        if (user != null) {
            Market market = marketInteractor.getMarket();
            MarketFollower marketFollower = new MarketFollower();
            marketFollower.setFollower(user);
            marketFollower.setMarket(market);

            marketInteractor.setMarketFollower(market, marketFollower);
        } else {
            marketView.navigateToLoginActivity();
            marketView.showMessage("로그인이 필요한 서비스입니다.");
        }
    }

    @Override
    public void onClickFollowCancel() {
        User user = marketInteractor.getUser();
        Market market = marketInteractor.getMarket();

        MarketFollower marketFollower = new MarketFollower();
        marketFollower.setFollower(user);
        marketFollower.setMarket(market);

        marketInteractor.deleteMarketFollower(market, marketFollower);
    }

    @Override
    public void onClickAddress() {
        Market market = marketInteractor.getMarket();
        marketView.navigateToMarketMapActivity(market);
    }
}
