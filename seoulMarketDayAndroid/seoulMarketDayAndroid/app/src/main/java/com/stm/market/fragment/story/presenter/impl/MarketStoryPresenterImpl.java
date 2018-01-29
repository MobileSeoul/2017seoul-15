package com.stm.market.fragment.story.presenter.impl;

import android.util.Log;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.market.fragment.story.adapter.MarketStoryAdapter;
import com.stm.market.fragment.story.interactor.MarketStoryInteractor;
import com.stm.market.fragment.story.interactor.impl.MarketStoryInteractorImpl;
import com.stm.market.fragment.story.presenter.MarketStoryPresenter;
import com.stm.market.fragment.story.view.MarketStoryView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketStoryPresenterImpl implements MarketStoryPresenter {
    private MarketStoryInteractor marketStoryInteractor;
    private MarketStoryView marketStoryView;

    public MarketStoryPresenterImpl(MarketStoryView marketStoryView) {
        this.marketStoryInteractor = new MarketStoryInteractorImpl(this);
        this.marketStoryView = marketStoryView;
    }


    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketStoryView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketStoryView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void init(User user, Market market) {
        marketStoryInteractor.setUser(user);
        marketStoryInteractor.setMarket(market);

        if (user != null) {
            String accessToken = user.getAccessToken();
            marketStoryInteractor.setFileRepository(accessToken);
            marketStoryInteractor.setMarketRepository(accessToken);
            marketStoryInteractor.setStoryRepository(accessToken);
        } else {
            marketStoryInteractor.setFileRepository();
            marketStoryInteractor.setMarketRepository();
            marketStoryInteractor.setStoryRepository();
        }
    }

    @Override
    public void onCreateView() {
        marketStoryView.showProgressDialog();
        marketStoryView.setScrollViewOnScrollChangeListener();

        Market market = marketStoryInteractor.getMarket();
        long marketId = market.getId();

        User user = marketStoryInteractor.getUser();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        if (user != null) {
            long userId = user.getId();
            marketStoryInteractor.getStoryListByIdAndOffset(marketId, userId, offset);
        } else {
            marketStoryInteractor.getStoryListByIdAndOffset(marketId, offset);
        }

    }

    @Override
    public void setFileAdapterItem(MarketStoryAdapter.MarketStoryViewHolder holder, List<File> files) {
        int fileSize = files.size();

        if (fileSize > 1) {
            marketStoryView.showFile(holder);
            marketStoryView.setMarketStoryFileAdapterItem(holder, files);
            marketStoryView.showPosition(holder);
            marketStoryView.showIndicator(holder);
        } else if (fileSize == 1) {
            marketStoryView.showFile(holder);
            marketStoryView.setMarketStoryFileAdapterItem(holder, files);
            marketStoryView.gonePosition(holder);
            marketStoryView.goneIndicator(holder);

        } else {
            marketStoryView.goneFile(holder);
            marketStoryView.goneIndicator(holder);
        }
    }

    @Override
    public void onClickPhoto(File file) {
        marketStoryView.showProgressDialog();
        marketStoryInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickPlayer(File file) {
        marketStoryView.showProgressDialog();
        marketStoryInteractor.updateFileByHits(file);
    }

    @Override
    public void onSuccessGetStoryListByIdAndOffset(List<Story> newStories) {
        int newStorySize = newStories.size();
        List<Story> stories = marketStoryInteractor.getStories();
        int storySize = stories.size();

        if (newStorySize > 0) {
            if (storySize == 0) {
                marketStoryInteractor.setStories(newStories);
                marketStoryView.clearMarketStoryAdapter();
                marketStoryView.setMarketStoryAdapterItem(newStories);
            } else {
                marketStoryInteractor.setStoriesAddAll(newStories);
                marketStoryView.marketStoryAdapterNotifyItemRangeInserted(storySize, newStorySize);
            }
        } else {
            if(storySize == 0){
                marketStoryView.showEmptyView();
            }
        }

        marketStoryView.goneProgressDialog();
    }

    @Override
    public void onScrollChange(int difference) {
        Log.e("Scroll(MarketStory)", difference + "");
        if (difference <= 0) {
            marketStoryView.showProgressDialog();
            Market market = marketStoryInteractor.getMarket();
            long marketId = market.getId();

            List<Story> stories = marketStoryInteractor.getStories();
            long offset = stories.size();

            User user = marketStoryInteractor.getUser();

            if (user != null) {
                long userId = user.getId();
                marketStoryInteractor.getStoryListByIdAndOffset(marketId, userId, offset);
            } else {
                marketStoryInteractor.getStoryListByIdAndOffset(marketId, offset);
            }
        }
    }

    @Override
    public void onClickAvatar(User storyUser) {
        marketStoryView.navigateToMerchantDetailActivity(storyUser);
    }

    @Override
    public void onClickMenu(Story story, int position) {
        User storyUser = story.getUser();
        Market market = marketStoryInteractor.getMarket();
        storyUser.setMarket(market);
        marketStoryView.navigateToStoryDialogActivity(story, position);
    }

    @Override
    public void onLikeChecked(MarketStoryAdapter.MarketStoryViewHolder holder, int position, Story story, boolean checked) {
        long storyId = story.getId();

        User user = marketStoryInteractor.getUser();
        if (user != null) {
            long userId = user.getId();
            String userName = user.getName();
            User storyUser = story.getUser();
            long storyUserId = storyUser.getId();

            if (checked) {
                marketStoryInteractor.setStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName, position);
            } else {
                marketStoryInteractor.deleteStoryLikeByIdAndStoryUserIdAndUserId(storyId, userId, position);
            }

        } else {
            marketStoryView.navigateToLoginActivity();
            marketStoryView.showMessage("로그인이 필요한 서비스입니다.");
            marketStoryView.setMarketStoryAdapterLikeUnchecked(holder);
        }
    }

    @Override
    public void onClickComment(Story story, int position) {
        marketStoryView.navigateToCommentActivity(story, position);
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        marketStoryView.navigateToSearchTagActivity(hashTag);
    }

    @Override
    public void onActivityResultForStoryEditResultOk(Story story, int position) {
        marketStoryInteractor.replaceStoriesItemAtThePosition(position, story);
        marketStoryView.marketStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onActivityResultForCommentResultOk(int position, int commentCountAdded) {
        List<Story> stories = marketStoryInteractor.getStories();
        Story story = stories.get(position);
        int commentCount = story.getCommentCount();
        story.setCommentCount(commentCount + commentCountAdded);
        story.setFirstLikeChecked(false);
        marketStoryView.marketStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onActivityResultForStoryResultDelete(int position) {
        marketStoryInteractor.setStoriesRemoveAtThePosition(position);
        marketStoryView.marketStoryAdapterNotifyItemRemoved(position);
    }

    @Override
    public void onSuccessSetStoryLikeByIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = marketStoryInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount + 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(true);
        marketStoryView.marketStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = marketStoryInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount - 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(false);
        marketStoryView.marketStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        int type = file.getType();

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            marketStoryView.navigateToPhotoDialogActivity(file);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            marketStoryView.navigateToVideoPlayerActivity(file);
        }

        marketStoryView.goneProgressDialog();
    }
}
