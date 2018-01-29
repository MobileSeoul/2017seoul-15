package com.stm.user.list.following.merchant.presenter.impl;

import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.user.list.following.merchant.interactor.FollowingMerchantInteractor;
import com.stm.user.list.following.merchant.interactor.impl.FollowingMerchantInteractorImpl;
import com.stm.user.list.following.merchant.presenter.FollowingMerchantPresenter;
import com.stm.user.list.following.merchant.view.FollowingMerchantView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMerchantPresenterImpl implements FollowingMerchantPresenter {
    private FollowingMerchantInteractor followingMerchantInteractor;
    private FollowingMerchantView followingMerchantView;

    public FollowingMerchantPresenterImpl(FollowingMerchantView followingMerchantView) {
        this.followingMerchantInteractor = new FollowingMerchantInteractorImpl(this);
        this.followingMerchantView = followingMerchantView;
    }

    @Override
    public void init(User user) {
        followingMerchantView.showProgressDialog();
        followingMerchantInteractor.setUser(user);
        followingMerchantView.setScrollViewOnScrollChangeListener();
        followingMerchantView.setIncludedToolbarLayout();
        followingMerchantView.showToolbarTitle("팔로우한 상인");

        String accessToken = user.getAccessToken();
        followingMerchantInteractor.setUserRepository(accessToken);

        long userId = user.getId();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        followingMerchantInteractor.getFollowingMerchantListByIdAndOffset(userId, offset);
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            List<User> users = followingMerchantInteractor.getUsers();
            long offset = users.size();

            User user = followingMerchantInteractor.getUser();
            long userId = user.getId();

            followingMerchantInteractor.getFollowingMerchantListByIdAndOffset(userId, offset);
        }
    }

    @Override
    public void onSuccessGetFollowingMerchantListByIdAndOffset(List<User> newUsers) {
        int newUserSize = newUsers.size();

        List<User> users = followingMerchantInteractor.getUsers();
        int userSize = users.size();

        if (userSize == 0) {
            if (newUserSize == 0) {
                followingMerchantView.showEmptyView();
            } else {
                followingMerchantInteractor.setUsers(newUsers);
                followingMerchantView.clearFollowingMerchantAdapter();
                followingMerchantView.setFollowingMerchantAdapterItem(newUsers);
            }
        } else {
            followingMerchantInteractor.usersAddAll(newUsers);
            followingMerchantView.followingMerchantAdapterNotifyItemRangeInserted(userSize, newUserSize);
        }

        followingMerchantView.goneProgressDialog();
    }

    @Override
    public void onSuccessDeleteMerchantFollower(int position) {
        followingMerchantView.removeFollowingMerchant(position);
        followingMerchantView.followingMerchantAdapterNotifyItemRemoved(position);
    }

    @Override
    public void onClickFollowingMerchant(User user) {
        followingMerchantView.navigateToMerchantDetailActivity(user);
    }

    @Override
    public void onClickBack() {
        followingMerchantView.navigateToBack();
    }

    @Override
    public void onClickFollowCancel(User merchant, int position) {
        User user = followingMerchantInteractor.getUser();
        long userId = user.getId();
        MerchantFollower merchantFollower = new MerchantFollower();
        merchantFollower.setUser(user);
        merchantFollower.setMerchant(merchant);
        followingMerchantInteractor.deleteMerchantFollower(userId, merchantFollower, position);
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            followingMerchantView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            followingMerchantView.showMessage(httpErrorDto.message());
        }
    }
}
