package com.stm.user.list.following.market.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.user.list.following.market.interactor.FollowingMarketInteractor;
import com.stm.user.list.following.market.interactor.impl.FollowingMarketInteractorImpl;
import com.stm.user.list.following.market.presenter.FollowingMarketPresenter;
import com.stm.user.list.following.market.view.FollowingMarketView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMarketPresenterImpl implements FollowingMarketPresenter {
    private FollowingMarketInteractor followingMarketInteractor;
    private FollowingMarketView followingMarketView;

    public FollowingMarketPresenterImpl(FollowingMarketView followingMarketView) {
        this.followingMarketInteractor = new FollowingMarketInteractorImpl(this);
        this.followingMarketView = followingMarketView;
    }

    @Override
    public void init(User user) {
        followingMarketView.showProgressDialog();
        followingMarketInteractor.setUser(user);
        followingMarketView.setScrollViewOnScrollChangeListener();
        followingMarketView.setIncludedToolbarLayout();
        followingMarketView.showToolbarTitle("팔로우한 시장");

        String accessToken = user.getAccessToken();
        long userId = user.getId();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        followingMarketInteractor.setUserRepository(accessToken);
        followingMarketInteractor.setMarketRepository(accessToken);
        followingMarketInteractor.getFollowingMarketListByIdAndOffset(userId, offset);
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            followingMarketView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            followingMarketView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetFollowingMarketListByIdAndOffset(List<Market> newMarkets) {
        int newMarketSize = newMarkets.size();

        List<Market> markets = followingMarketInteractor.getMarkets();
        int marketSize = markets.size();

        if (marketSize == 0) {
            if (newMarketSize == 0) {
                followingMarketView.showEmptyView();
            } else {
                followingMarketInteractor.setMarkets(newMarkets);
                followingMarketView.clearFollowingMarketAdapter();
                followingMarketView.setFollowingMarketAdapterItem(newMarkets);
            }
        } else {
            followingMarketInteractor.setMarketAddAll(newMarkets);
            followingMarketView.followingMarketAdapterNotifyItemRangeInserted(marketSize, newMarketSize);
        }

        followingMarketView.goneProgressDialog();
    }

    @Override
    public void onClickBack() {
        followingMarketView.navigateToBack();
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            followingMarketView.showProgressDialog();
            List<Market> markets = followingMarketInteractor.getMarkets();
            long offset = markets.size();

            User user = followingMarketInteractor.getUser();
            long userId = user.getId();

            followingMarketInteractor.getFollowingMarketListByIdAndOffset(userId, offset);
        }
    }

    @Override
    public void onClickFollowingMarket(Market market) {
        followingMarketView.navigateToMarketActivity(market);
    }


    @Override
    public void onClickFollowCancel(Market market, int position) {
        User user = followingMarketInteractor.getUser();
        long marketId = market.getId();

        MarketFollower marketFollower = new MarketFollower();
        marketFollower.setFollower(user);
        marketFollower.setMarket(market);

        followingMarketInteractor.deleteMarketFollower(marketId, marketFollower, position);
    }

    @Override
    public void onSuccessDeleteMarketFollower(int position) {
        followingMarketView.removeFollowingMarket(position);
        followingMarketView.followingMarketAdapterNotifyItemRemoved(position);
    }
}
