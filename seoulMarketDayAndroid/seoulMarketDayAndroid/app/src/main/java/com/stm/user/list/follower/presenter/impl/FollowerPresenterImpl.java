package com.stm.user.list.follower.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.user.list.follower.inetractor.FollowerInteractor;
import com.stm.user.list.follower.inetractor.impl.FollowerInteractorImpl;
import com.stm.user.list.follower.presenter.FollowerPresenter;
import com.stm.user.list.follower.view.FollowerView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowerPresenterImpl implements FollowerPresenter {
    private FollowerInteractor followerInteractor;
    private FollowerView followerView;

    public FollowerPresenterImpl(FollowerView followerView) {
        this.followerInteractor = new FollowerInteractorImpl(this);
        this.followerView = followerView;
    }

    @Override
    public void init(User user) {
        followerView.showProgressDialog();

        followerInteractor.setUser(user);
        followerView.setScrollViewOnScrollChangeListener();
        followerView.setIncludedToolbarLayout();
        followerView.showToolbarTitle("나를 팔로우한 고객");
        long userId = user.getId();
        String accessToken = user.getAccessToken();
        followerInteractor.setUserRepository(accessToken);

        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        followerInteractor.getFollowerListByStoryUserIdAndOffset(userId, offset);
    }

    @Override
    public void onSuccessGetFollowerListByStoryUserIdAndOffset(List<User> newUsers) {
        int newUserSize = newUsers.size();

        List<User> users = followerInteractor.getUsers();
        int userSize = users.size();

        if (userSize == 0) {
            if (newUserSize == 0) {
                followerView.showEmptyView();
            } else {
                followerInteractor.setUsers(newUsers);
                followerView.clearFollowerAdapter();
                followerView.setFollowerAdapterItem(newUsers);
            }
        } else {
            followerInteractor.usersAddAll(newUsers);
            followerView.followerAdapterNotifyItemRangeInserted(userSize, newUserSize);
        }

        followerView.goneProgressDialog();
    }

    @Override
    public void onClickBack() {
        followerView.navigateToBack();
    }

    @Override
    public void onScrollChange(int difference) {
        if(difference <= 0){
            followerView.showProgressDialog();

            User user = followerInteractor.getUser();
            long userId = user.getId();

            List<User> users = followerInteractor.getUsers();
            long offset = users.size();

            followerInteractor.getFollowerListByStoryUserIdAndOffset(userId, offset);
        }
    }

    @Override
    public void onClickFollower(User user) {
        int level = user.getLevel();
        if(level == UserLevelFlag.MERCHANT){
            followerView.navigateToMerchantDetailActivity(user);
        }

        if(level == UserLevelFlag.NORMAL){
            followerView.navigateToUserDetail(user);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            followerView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            followerView.showMessage(httpErrorDto.message());
        }
    }


}
