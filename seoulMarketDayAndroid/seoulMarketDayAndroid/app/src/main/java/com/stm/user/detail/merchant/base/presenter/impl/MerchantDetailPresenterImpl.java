package com.stm.user.detail.merchant.base.presenter.impl;

import android.util.Log;

import com.stm.common.dao.Market;
import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.MerchantFragmentFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.user.detail.merchant.base.interactor.MerchantDetailInteractor;
import com.stm.user.detail.merchant.base.interactor.impl.MerchantDetailInteractorImpl;
import com.stm.user.detail.merchant.base.presenter.MerchantDetailPresenter;
import com.stm.user.detail.merchant.base.view.MerchantDetailView;
import com.stm.user.detail.merchant.fragment.main.fragment.MerchantDetailMainFragment;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class MerchantDetailPresenterImpl implements MerchantDetailPresenter {
    private MerchantDetailInteractor merchantDetailInteractor;
    private MerchantDetailView merchantDetailView;

    public MerchantDetailPresenterImpl(MerchantDetailView merchantDetailView) {
        this.merchantDetailInteractor = new MerchantDetailInteractorImpl(this);
        this.merchantDetailView = merchantDetailView;
    }

    @Override
    public void init(User user, long storyUserId, int position) {
        merchantDetailView.showProgressDialog();
        merchantDetailInteractor.setUser(user);
        merchantDetailInteractor.setPosition(position);

        if (user != null) {
            String accessToken = user.getAccessToken();
            merchantDetailInteractor.setUserRepository(accessToken);
            long userId = user.getId();
            merchantDetailInteractor.getUserById(storyUserId, userId);
        } else {
            merchantDetailInteractor.setUserRepository();
            merchantDetailInteractor.getUserById(storyUserId);
        }

        merchantDetailView.setTabLayout();
        merchantDetailView.setToolbarLayout();
        merchantDetailView.setAddOnOffsetChangedListener();

    }

    @Override
    public void onSuccessGetUserById(User storyUser) {
        merchantDetailInteractor.setStoryUser(storyUser);
        String name = storyUser.getName();
        String avatar = storyUser.getAvatar();
        String cover = storyUser.getCover();
        String intro = storyUser.getIntro();

        Market market = storyUser.getMarket();
        String marketName = market.getName();

        merchantDetailView.showUserAvatar(avatar);
        merchantDetailView.showUserCover(cover);
        merchantDetailView.showUserNameText(name);
        merchantDetailView.showUserIntroText(intro);
        merchantDetailView.showUserMarketText(marketName);
        merchantDetailView.setTabAdapter(storyUser);

        User user = merchantDetailInteractor.getUser();
        if (user != null) {
            long userId = user.getId();
            int level = user.getLevel();
            long storyUserId = storyUser.getId();
            if (userId != storyUserId) {
                if (level != UserLevelFlag.MERCHANT) {
                    if (storyUser.getFollowed() != null && storyUser.getFollowed()) {
                        merchantDetailView.showFollowCancelButton();
                    } else {
                        merchantDetailView.showFollowButton();
                    }
                } else {
                    merchantDetailView.goneFollowButton();
                    merchantDetailView.goneFollowCancelButton();
                }
            } else {
                merchantDetailView.goneFollowButton();
                merchantDetailView.showWriteButton();
            }
        } else {
            merchantDetailView.showFollowButton();
        }
        merchantDetailView.goneProgressDialog();
    }

    @Override
    public void onResume(User user) {
        if (user != null && merchantDetailInteractor.getUser() == null) {
            merchantDetailView.showProgressDialog();

            merchantDetailInteractor.setUser(user);
            String accessToken = user.getAccessToken();
            merchantDetailInteractor.setUserRepository(accessToken);

            User storyUser = merchantDetailInteractor.getStoryUser();

            long storyUserId = storyUser.getId();
            long userId = user.getId();
            merchantDetailView.clearTabLayout();

            merchantDetailView.setTabLayout();
            merchantDetailView.setAddOnOffsetChangedListener();
            merchantDetailInteractor.getUserById(storyUserId, userId);

            int level = user.getLevel();
            if (level == UserLevelFlag.MERCHANT) {
                merchantDetailView.goneToolbarFollowButton();
                merchantDetailView.goneToolbarFollowCancelButton();
            }
        }
    }

    @Override
    public void onClickFollow() {
        User user = merchantDetailInteractor.getUser();
        if (user == null) {
            merchantDetailView.navigateToLoginActivity();
            merchantDetailView.showMessage("로그인이 필요한 서비스입니다.");
        } else {
            User storyUser = merchantDetailInteractor.getStoryUser();
            MerchantFollower merchantFollower = new MerchantFollower();
            merchantFollower.setMerchant(storyUser);
            merchantFollower.setUser(user);

            merchantDetailInteractor.setMerchantFollower(merchantFollower);

        }
    }

    @Override
    public void onOffsetChanged(int totalScrollRange, int verticalOffset) {
        int scrollRange = merchantDetailInteractor.getTotalScrollRange();
        boolean isShown = merchantDetailInteractor.isToolbarTitleShown();
        User user = merchantDetailInteractor.getUser();

        if (scrollRange == -1) {
            scrollRange = totalScrollRange;
            merchantDetailInteractor.setTotalScrollRange(scrollRange);
        }

        if (scrollRange + verticalOffset == 0) {
            User storyUser = merchantDetailInteractor.getStoryUser();
            long storyUserId = storyUser.getId();
            String storyUserName = storyUser.getName();

            merchantDetailView.showToolbarTitle(storyUserName);
            merchantDetailView.showToolbarBackButtonWithDefaultColor();
            merchantDetailView.setSwipeRefreshLayoutEnabled(false);

            isShown = true;
            merchantDetailInteractor.setToolbarTitleShown(isShown);

            if (user != null) {
                long userId = user.getId();
                int level = user.getLevel();
                if (storyUserId != userId) {
                    if (level != UserLevelFlag.MERCHANT) {
                        if (storyUser.getFollowed() != null && storyUser.getFollowed()) {
                            merchantDetailView.showToolbarFollowCancelButton();
                        } else {
                            merchantDetailView.showToolbarFollowButton();
                        }
                    }
                } else {
                    merchantDetailView.showToolbarWriteButton();
                }
            } else {
                merchantDetailView.showToolbarFollowButton();
            }
        } else if (isShown) {
            User storyUser = merchantDetailInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            merchantDetailView.showToolbarTitle("");
            merchantDetailView.showToolbarBackButtonWithWhiteColor();

            isShown = false;
            merchantDetailInteractor.setToolbarTitleShown(isShown);

            if (user != null) {
                long userId = user.getId();
                long level = user.getLevel();
                if (storyUserId != userId) {
                    if (level != UserLevelFlag.MERCHANT) {
                        if (storyUser.getFollowed() != null && storyUser.getFollowed()) {
                            merchantDetailView.goneToolbarFollowCancelButton();
                        } else {
                            merchantDetailView.goneToolbarFollowButton();
                        }
                    }
                } else {
                    merchantDetailView.goneToolbarWriteButton();
                }
            } else {
                merchantDetailView.goneToolbarFollowButton();

            }
        }

        if (scrollRange + verticalOffset == scrollRange && !isShown) {
            merchantDetailView.setSwipeRefreshLayoutEnabled(true);
            merchantDetailView.setOnRefreshListener();
        } else {
            merchantDetailView.setSwipeRefreshLayoutEnabled(false);
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

    }

    @Override
    public void onClickWrite() {
        User storyUser = merchantDetailInteractor.getStoryUser();
        merchantDetailView.navigateToStoryCreateActivity(storyUser);
    }

    @Override
    public void onActivityResultForStoryCreateResultOk(long storyId) {
        merchantDetailView.showProgressDialog();

        User user = merchantDetailInteractor.getUser();
        User storyUser = merchantDetailInteractor.getStoryUser();

        long storyUserId = storyUser.getId();
        long userId = user.getId();
        merchantDetailView.clearTabLayout();

        merchantDetailView.setTabLayout();
        merchantDetailView.setAddOnOffsetChangedListener();
        merchantDetailView.goneToolbarFollowButton();
        merchantDetailInteractor.getUserById(storyUserId, userId);

    }

    @Override
    public void onClickAvatar() {
        User user = merchantDetailInteractor.getStoryUser();
        String avatar = user.getAvatar();
        merchantDetailView.navigateToPhotoDialogActivityWithAvatar(avatar);
    }

    @Override
    public void onClickCover() {
        User user = merchantDetailInteractor.getStoryUser();
        String cover = user.getCover();
        merchantDetailView.navigateToPhotoDialogActivityWithCover(cover);
    }

    @Override
    public void onSuccessSetMerchantFollower() {
        User storyUser = merchantDetailInteractor.getStoryUser();
        storyUser.setFollowed(true);
        merchantDetailView.goneFollowButton();
        merchantDetailView.goneToolbarFollowButton();
        merchantDetailView.showFollowCancelButton();

    }

    @Override
    public void onSuccessDeleteMerchantFollower() {
        User storyUser = merchantDetailInteractor.getStoryUser();
        storyUser.setFollowed(false);
        merchantDetailView.goneToolbarFollowCancelButton();
        merchantDetailView.goneFollowCancelButton();
        merchantDetailView.showFollowButton();

    }

    @Override
    public void onSuccessGetUserByIdForRefresh(User storyUser, int position) {
        merchantDetailInteractor.setStoryUser(storyUser);
        User user = merchantDetailInteractor.getUser();
        String name = storyUser.getName();
        String avatar = storyUser.getAvatar();
        String cover = storyUser.getCover();
        String intro = storyUser.getIntro();

        Market market = storyUser.getMarket();
        String marketName = market.getName();

        merchantDetailView.showUserAvatar(avatar);
        merchantDetailView.showUserCover(cover);
        merchantDetailView.showUserNameText(name);
        merchantDetailView.showUserIntroText(intro);
        merchantDetailView.showUserMarketText(marketName);

        if (user != null) {
            long userId = user.getId();
            int level = user.getLevel();
            long storyUserId = storyUser.getId();
            if (userId != storyUserId) {
                if (level != UserLevelFlag.MERCHANT) {
                    if (storyUser.getFollowed() != null && storyUser.getFollowed()) {
                        merchantDetailView.showFollowCancelButton();
                    } else {
                        merchantDetailView.showFollowButton();
                    }
                } else {
                    merchantDetailView.goneFollowButton();
                    merchantDetailView.goneFollowCancelButton();
                }
            } else {
                merchantDetailView.goneFollowButton();
                merchantDetailView.showWriteButton();
            }
        } else {
            merchantDetailView.showFollowButton();
        }


        if (position == MerchantFragmentFlag.HOME) {
            merchantDetailView.setMainFragmentRefresh();
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MerchantFragmentFlag.INFO) {
            merchantDetailView.setInfoFragmentRefresh(storyUser);
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MerchantFragmentFlag.STORY) {
            merchantDetailView.setStoryFragmentRefresh();
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MerchantFragmentFlag.PHOTO) {
            merchantDetailView.setPhotoFragmentRefresh();
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MerchantFragmentFlag.VIDEO) {
            merchantDetailView.setVideoFragmentRefresh();
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

        if (position == MerchantFragmentFlag.FOLLOWER) {
            merchantDetailView.setFollowerFragmentRefresh();
            merchantDetailView.setSwipeRefreshLayoutRefreshing(false);
        }

    }

    @Override
    public void onClickFollowCancel() {
        User user = merchantDetailInteractor.getUser();
        if (user == null) {
            merchantDetailView.navigateToLoginActivity();
            merchantDetailView.showMessage("로그인이 필요한 서비스입니다.");
        } else {
            User storyUser = merchantDetailInteractor.getStoryUser();
            MerchantFollower merchantFollower = new MerchantFollower();
            merchantFollower.setMerchant(storyUser);
            merchantFollower.setUser(user);

            merchantDetailInteractor.deleteMerchantFollower(merchantFollower);
        }
    }

    @Override
    public void onClickBack() {
        int position = merchantDetailInteractor.getPosition();
        User storyUser = merchantDetailInteractor.getStoryUser();

        if (storyUser.getFollowed() == null) {
            storyUser.setFollowed(false);
        }

        merchantDetailView.navigateToBackWithStoryUserAndPosition(storyUser, position);


    }

    @Override
    public void onBackPressed() {
        int position = merchantDetailInteractor.getPosition();
        User storyUser = merchantDetailInteractor.getStoryUser();

        if(storyUser != null) {
            if (storyUser.getFollowed() == null) {
                storyUser.setFollowed(false);
            }

            merchantDetailView.navigateToBackWithStoryUserAndPosition(storyUser, position);
        }
    }

    @Override
    public void onRefresh(int position) {
        User prevStoryUser = merchantDetailInteractor.getStoryUser();
        User user = merchantDetailInteractor.getUser();
        long storyUserId = prevStoryUser.getId();

        if (user != null) {
            long userId = user.getId();
            merchantDetailInteractor.getUserByIdForRefresh(storyUserId, userId, position);
        } else {
            merchantDetailInteractor.getUserByIdForRefresh(storyUserId, position);
        }
    }

    @Override
    public void onChangeFragment() {
        merchantDetailView.showProgressDialog();
        User storyUser = merchantDetailInteractor.getStoryUser();
        merchantDetailView.setTabAdapterUser(storyUser);
        merchantDetailView.setTabAdapterNotifyDataSetChanged();
        merchantDetailView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailView.showMessage(httpErrorDto.message());
        }
    }

}
