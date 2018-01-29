package com.stm.user.detail.merchant.fragment.follower.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.user.detail.merchant.fragment.follower.interactor.MerchantDetailFollowerInteractor;
import com.stm.user.detail.merchant.fragment.follower.interactor.impl.MerchantDetailFollowerInteractorImpl;
import com.stm.user.detail.merchant.fragment.follower.presenter.MerchantDetailFollowerPresenter;
import com.stm.user.detail.merchant.fragment.follower.view.MerchantDetailFollowerView;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public class MerchantDetailFollowerPresenterImpl implements MerchantDetailFollowerPresenter {
    private MerchantDetailFollowerView merchantDetailFollowerView;
    private MerchantDetailFollowerInteractor merchantDetailFollowerInteractor;

    public MerchantDetailFollowerPresenterImpl(MerchantDetailFollowerView merchantDetailFollowerView) {
        this.merchantDetailFollowerView = merchantDetailFollowerView;
        this.merchantDetailFollowerInteractor = new MerchantDetailFollowerInteractorImpl(this);
    }

    @Override
    public void init(User user, User storyUser) {
        merchantDetailFollowerInteractor.setUser(user);
        merchantDetailFollowerInteractor.setStoryUser(storyUser);

        if (user != null) {
            String accessToken = user.getAccessToken();
            merchantDetailFollowerInteractor.setUserRepository(accessToken);
        } else {
            merchantDetailFollowerInteractor.setUserRepository();
        }
    }

    @Override
    public void onCreateView() {
        merchantDetailFollowerView.showProgressDialog();
        merchantDetailFollowerView.setScrollViewOnScrollChangeListener();

        User storyUser = merchantDetailFollowerInteractor.getStoryUser();
        long storyUserId = storyUser.getId();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        merchantDetailFollowerInteractor.getFollowerListByStoryUserIdAndOffset(storyUserId, offset);
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            merchantDetailFollowerView.showProgressDialog();

            User storyUser = merchantDetailFollowerInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            List<User> followers = merchantDetailFollowerInteractor.getFollowers();
            long offset = followers.size();

            merchantDetailFollowerInteractor.getFollowerListByStoryUserIdAndOffset(storyUserId, offset);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailFollowerView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailFollowerView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetFollowerListByStoryUserIdAndOffset(List<User> newFollowers) {
        int newFollowerSize = newFollowers.size();
        List<User> followers = merchantDetailFollowerInteractor.getFollowers();
        int followerSize = followers.size();

        if (followerSize == 0) {
            if (newFollowerSize == 0) {
                merchantDetailFollowerView.showEmptyView();
            } else {
                merchantDetailFollowerInteractor.setFollowers(newFollowers);
                merchantDetailFollowerView.clearMerchantDetailFollowerAdapter();
                merchantDetailFollowerView.setFollowerByIdAndOffsetItem(newFollowers);
            }
        } else {
            merchantDetailFollowerInteractor.setFollowersAddAll(newFollowers);
            merchantDetailFollowerView.followerAdapterNotifyItemRangeInserted(followerSize, newFollowerSize);
        }

        merchantDetailFollowerView.goneProgressDialog();
    }

    @Override
    public void onClickFollower(User user) {
        int level = user.getLevel();
        if(level == UserLevelFlag.NORMAL){
            merchantDetailFollowerView.navigateToUserDetailActivity(user);
        }

    }
}
