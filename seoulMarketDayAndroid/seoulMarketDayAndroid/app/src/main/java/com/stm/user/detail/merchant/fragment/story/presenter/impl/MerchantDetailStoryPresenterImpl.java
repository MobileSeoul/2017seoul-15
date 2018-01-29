package com.stm.user.detail.merchant.fragment.story.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.user.detail.merchant.fragment.story.adapter.MerchantDetailStoryAdapter;
import com.stm.user.detail.merchant.fragment.story.interactor.MerchantDetailStoryInteractor;
import com.stm.user.detail.merchant.fragment.story.interactor.impl.MerchantDetailStoryInteractorImpl;
import com.stm.user.detail.merchant.fragment.story.presenter.MerchantDetailStoryPresenter;
import com.stm.user.detail.merchant.fragment.story.view.MerchantDetailStoryView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-28.
 */

public class MerchantDetailStoryPresenterImpl implements MerchantDetailStoryPresenter {
    private MerchantDetailStoryInteractor merchantDetailStoryInteractor;
    private MerchantDetailStoryView merchantDetailStoryView;

    public MerchantDetailStoryPresenterImpl(MerchantDetailStoryView merchantDetailStoryView) {
        this.merchantDetailStoryInteractor = new MerchantDetailStoryInteractorImpl(this);
        this.merchantDetailStoryView = merchantDetailStoryView;
    }

    @Override
    public void init(User user, User storyUser) {
        merchantDetailStoryInteractor.setUser(user);
        merchantDetailStoryInteractor.setStoryUser(storyUser);

        if (user != null) {
            String accessToken = user.getAccessToken();
            merchantDetailStoryInteractor.setUserRepository(accessToken);
            merchantDetailStoryInteractor.setStoryRepository(accessToken);
            merchantDetailStoryInteractor.setFileRepository(accessToken);
        } else {
            merchantDetailStoryInteractor.setUserRepository();
            merchantDetailStoryInteractor.setStoryRepository();
            merchantDetailStoryInteractor.setFileRepository();
        }
    }

    @Override
    public void onCreateView() {
        merchantDetailStoryView.showProgressDialog();
        merchantDetailStoryView.setScrollViewOnScrollChangeListener();

        User user = merchantDetailStoryInteractor.getUser();

        User storyUser = merchantDetailStoryInteractor.getStoryUser();
        long storyUserId = storyUser.getId();

        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        if (user != null) {
            long userId = user.getId();
            merchantDetailStoryInteractor.getStoryListByStoryUserIdAndUserIdAndOffset(storyUserId, userId, offset);
        } else {
            merchantDetailStoryInteractor.getStoryListByStoryUserIdAndOffset(storyUserId, offset);
        }
    }


    @Override
    public void onClickComment(Story story, int position) {
        User storyUser = merchantDetailStoryInteractor.getStoryUser();
        story.setUser(storyUser);
        merchantDetailStoryView.navigateToCommentActivity(story, position);
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            merchantDetailStoryView.showProgressDialog();

            User storyUser = merchantDetailStoryInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            List<Story> stories = merchantDetailStoryInteractor.getStories();
            long offset = stories.size();

            User user = merchantDetailStoryInteractor.getUser();

            if (user != null) {
                long userId = user.getId();
                merchantDetailStoryInteractor.getStoryListByStoryUserIdAndUserIdAndOffset(storyUserId, userId, offset);
            } else {
                merchantDetailStoryInteractor.getStoryListByStoryUserIdAndOffset(storyUserId, offset);
            }
        }
    }

    @Override
    public void onSuccessGetStoryListByStoryUserIdAndOffset(List<Story> newStories) {
        int newStorySize = newStories.size();
        List<Story> stories = merchantDetailStoryInteractor.getStories();
        int storySize = stories.size();

        if (storySize == 0) {
            if (newStorySize == 0) {
                merchantDetailStoryView.showEmptyView();
            } else {
                merchantDetailStoryInteractor.setStories(newStories);
                merchantDetailStoryView.clearMerchantDetailStoryAdapter();
                merchantDetailStoryView.setStoryByIdAndOffsetItem(newStories);
            }
        } else {
            merchantDetailStoryInteractor.setStoriesAddAll(newStories);
            merchantDetailStoryView.storyAdapterNotifyItemRangeInserted(storySize, newStorySize);
        }

        merchantDetailStoryView.goneProgressDialog();
    }

    @Override
    public void setMerchantDetailStoryFileAdapterItem(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files) {
        int fileSize = files.size();

        if (fileSize > 1) {
            merchantDetailStoryView.showFile(holder);
            merchantDetailStoryView.setMerchantDetailStoryFileAdapterItem(holder, files);
        } else if (fileSize == 1) {
            merchantDetailStoryView.showFile(holder);
            merchantDetailStoryView.setMerchantDetailStoryFileAdapterItem(holder, files);
            merchantDetailStoryView.gonePosition(holder);
            merchantDetailStoryView.goneIndicator(holder);
        } else {
            merchantDetailStoryView.goneFile(holder);
            merchantDetailStoryView.goneIndicator(holder);
        }
    }

    @Override
    public void onClickPlayerButton(File file) {
        merchantDetailStoryView.showProgressDialog();
        merchantDetailStoryInteractor.updateFileByHits(file);
    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        merchantDetailStoryView.goneProgressDialog();

        int type = file.getType();
        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            merchantDetailStoryView.navigateToVideoPlayerActivity(file);
        }
        if (type == DefaultFileFlag.PHOTO_TYPE) {
            merchantDetailStoryView.navigateToPhotoDialogActivity(file);
        }
    }

    @Override
    public void onClickMenu(Story story, int position) {
        User storyUser = merchantDetailStoryInteractor.getStoryUser();
        story.setUser(storyUser);

        merchantDetailStoryView.navigateToStoryDialogActivity(story, position);
    }

    @Override
    public void onClickPhoto(File file) {
        merchantDetailStoryView.showProgressDialog();
        merchantDetailStoryInteractor.updateFileByHits(file);
    }

    @Override
    public void onActivityResultForStoryResultDelete(int position) {
        merchantDetailStoryInteractor.setStoriesRemoveAtThePosition(position);
        merchantDetailStoryView.storyAdapterNotifyItemRemoved(position);
    }

    @Override
    public void onHashTagClicked(String tagName) {
        merchantDetailStoryView.navigateToSearchTagActivity(tagName);
    }

    @Override
    public void onActivityResultForStoryEditResultOk(Story story, int position) {
        merchantDetailStoryInteractor.replaceStoriesItemAtThePosition(position, story);
        merchantDetailStoryView.storyAdapterNotifyItemChanged(position);
    }

    @Override
    public void setStoryRefresh() {
        merchantDetailStoryInteractor.setStoriesRemoveAll();
    }


    @Override
    public void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = merchantDetailStoryInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount + 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(true);
        merchantDetailStoryView.storyAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = merchantDetailStoryInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount - 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(false);
        merchantDetailStoryView.storyAdapterNotifyItemChanged(position);
    }

    @Override
    public void onActivityResultForCommentResultOk(int position, int commentCountAdded) {
        List<Story> stories = merchantDetailStoryInteractor.getStories();
        Story story = stories.get(position);
        int commentCount = story.getCommentCount();

        story.setCommentCount(commentCount + commentCountAdded);
        story.setFirstLikeChecked(false);

        merchantDetailStoryView.storyAdapterNotifyItemChanged(position);
    }

    @Override
    public void onLikeChecked(int position, Story story, boolean checked) {
        long storyId = story.getId();

        User user = merchantDetailStoryInteractor.getUser();
        if (user != null) {
            long userId = user.getId();
            String userName = user.getName();
            User storyUser = merchantDetailStoryInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            if (checked) {
                merchantDetailStoryInteractor.setStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName, position);
            } else {
                merchantDetailStoryInteractor.deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, userId, position);
            }

        } else {
            merchantDetailStoryView.navigateToLoginActivity();
            merchantDetailStoryView.showMessage("로그인이 필요한 서비스입니다.");
        }

    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailStoryView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailStoryView.showMessage(httpErrorDto.message());
        }
    }
}
