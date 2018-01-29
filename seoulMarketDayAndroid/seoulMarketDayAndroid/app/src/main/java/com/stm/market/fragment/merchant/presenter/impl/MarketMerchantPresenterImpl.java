package com.stm.market.fragment.merchant.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.market.fragment.merchant.interactor.MarketMerchantInteractor;
import com.stm.market.fragment.merchant.interactor.impl.MarketMerchantInteractorImpl;
import com.stm.market.fragment.merchant.presenter.MarketMerchantPresenter;
import com.stm.market.fragment.merchant.view.MarketMerchantView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev-0 on 2017-07-06.
 */

public class MarketMerchantPresenterImpl implements MarketMerchantPresenter {
    private MarketMerchantView marketMerchantView;
    private MarketMerchantInteractor marketMerchantInteractor;

    public MarketMerchantPresenterImpl(MarketMerchantView marketMerchantView) {
        this.marketMerchantView = marketMerchantView;
        this.marketMerchantInteractor = new MarketMerchantInteractorImpl(this);

    }

    @Override
    public void init(User user, Market market) {
        marketMerchantInteractor.setUser(user);
        marketMerchantInteractor.setMarket(market);
        if (user != null) {
            String accessToken = user.getAccessToken();
            marketMerchantInteractor.setMarketRepository(accessToken);
            marketMerchantInteractor.setUserRepository(accessToken);
        } else {
            marketMerchantInteractor.setMarketRepository();
            marketMerchantInteractor.setUserRepository();
        }

    }

    @Override
    public void onCreateView() {
        marketMerchantView.setOnScrollChangeListener();
        Market market = marketMerchantInteractor.getMarket();
        long marketId = market.getId();

        marketMerchantView.showProgressDialog();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        User user = marketMerchantInteractor.getUser();
        if (user != null) {
            long userId = user.getId();
            marketMerchantInteractor.getUserListByIdAndUserIdAndOffset(marketId, userId, offset);
        } else {
            marketMerchantInteractor.getUserListByIdAndOffset(marketId, offset);
        }
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            marketMerchantView.showProgressDialog();
            Market market = marketMerchantInteractor.getMarket();
            long marketId = market.getId();
            List<User> users = marketMerchantInteractor.getUsers();
            int userSize = users.size();

            long offset = userSize;
            marketMerchantInteractor.getUserListByIdAndOffset(marketId, offset);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketMerchantView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketMerchantView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetUserListByIdAndOffset(List<User> newUsers) {
        int newUserSize = newUsers.size();

        List<User> users = marketMerchantInteractor.getUsers();
        int userSize = users.size();
        if (userSize == 0) {
            if(newUserSize == 0){
                marketMerchantView.showEmptyView();
            } else {
                User user = marketMerchantInteractor.getUser();
                marketMerchantInteractor.setUsers(newUsers);
                marketMerchantView.clearMerchantAdapter();
                marketMerchantView.setUserByMarketIdAndOffsetItem(newUsers, user);
            }
        } else {
            marketMerchantInteractor.setUsersAddAll(newUsers);
            marketMerchantView.merchantAdapterNotifyItemRangeInserted(userSize, newUserSize);
        }

        marketMerchantView.goneProgressDialog();
    }

    @Override
    public void onClickMerchant(User user, int position) {
        marketMerchantView.navigateToMerchantDetailActivity(user, position);
    }

    @Override
    public void onActivityResultForUserResultOk(User storyUser, int position) {
        User merchant = marketMerchantInteractor.getUsers().get(position);
        Boolean isFollowed = storyUser.getFollowed();
        merchant.setFollowed(isFollowed);
        marketMerchantView.merchantAdapterNotifyItemChanged(position);
    }

    @Override
    public void onClickFollow(User merchant, int position) {
        User user = marketMerchantInteractor.getUser();
        if (user == null) {
            marketMerchantView.navigateToLoginActivity();
            marketMerchantView.showMessage("로그인이 필요한 서비스입니다.");
        } else {
            MerchantFollower merchantFollower = new MerchantFollower();
            merchantFollower.setMerchant(merchant);
            merchantFollower.setUser(user);

            marketMerchantInteractor.setMerchantFollower(merchantFollower,position);

        }
    }

    @Override
    public void onClickFollowCancel(User merchant, int position) {
        User user = marketMerchantInteractor.getUser();
        if (user == null) {
            marketMerchantView.navigateToLoginActivity();
            marketMerchantView.showMessage("로그인이 필요한 서비스입니다.");
        } else {
            MerchantFollower merchantFollower = new MerchantFollower();
            merchantFollower.setMerchant(merchant);
            merchantFollower.setUser(user);

            marketMerchantInteractor.deleteMerchantFollower(merchantFollower, position);
        }
    }

    @Override
    public void onSuccessSetMerchantFollower(int position) {
        User merchant = marketMerchantInteractor.getUsers().get(position);
        merchant.setFollowed(true);
        marketMerchantView.merchantAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessDeleteMerchantFollower(int position) {
        User merchant = marketMerchantInteractor.getUsers().get(position);
        merchant.setFollowed(false);
        marketMerchantView.merchantAdapterNotifyItemChanged(position);
    }
}
