package com.stm.story.detail.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.util.CalculateDateUtil;
import com.stm.story.detail.interactor.StoryDetailInteractor;
import com.stm.story.detail.interactor.impl.StoryDetailInteractorImpl;
import com.stm.story.detail.presenter.StoryDetailPresenter;
import com.stm.story.detail.view.StoryDetailView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public class StoryDetailPresenterImpl implements StoryDetailPresenter {
    private StoryDetailInteractor storyDetailInteractor;
    private StoryDetailView storyDetailView;

    public StoryDetailPresenterImpl(StoryDetailView storyDetailView) {
        this.storyDetailInteractor = new StoryDetailInteractorImpl(this);
        this.storyDetailView = storyDetailView;
    }

    @Override
    public void init(User user, long id) {
        storyDetailView.showProgressDialog();
        storyDetailInteractor.setUser(user);

        if (user != null) {
            String accessToken = user.getAccessToken();
            storyDetailInteractor.setStoryRepository(accessToken);
            storyDetailInteractor.getStoryById(id, user);
        } else {
            storyDetailInteractor.setStoryRepository();
            storyDetailInteractor.getStoryById(id);
        }

    }

    @Override
    public void onResume(User user) {
        if (user != null && storyDetailInteractor.getUser() == null) {
            storyDetailInteractor.setUser(user);
        }
    }

    @Override
    public void onSuccessGetStoryById(Story story) {
        storyDetailInteractor.setStory(story);

        User storyUser = story.getUser();
        String name = storyUser.getName();
        String avatar = storyUser.getAvatar();
        String content = story.getContent();
        String createdAt = story.getCreatedAt();
        int likeCount = story.getLikeCount();
        int commentCount = story.getCommentCount();
        boolean isLikeChecked = story.getLikeChecked();
        List<File> files = story.getFiles();
        int fileSize = files.size();

        storyDetailView.showUserName(name);
        storyDetailView.showUserAvatar(avatar);
        storyDetailView.showContent(content);
        storyDetailView.showDate(CalculateDateUtil.getCalculateDateByDateTime(createdAt));
        storyDetailView.showLikeCount(String.valueOf(likeCount));
        storyDetailView.showCommentCount(String.valueOf(commentCount));
        storyDetailView.setLikeChecked(isLikeChecked);
        story.setFirstLikeChecked(true);


        if (fileSize > 1) {
            storyDetailView.setStoryDetailFileAdapterItem(files);
            storyDetailView.showFileTotal(String.valueOf(fileSize));
        } else if (fileSize == 1) {
            storyDetailView.setStoryDetailFileAdapterItem(files);
            storyDetailView.goneIndicator();
            storyDetailView.gonePosition();
        } else {
            storyDetailView.goneFile();
            storyDetailView.gonePosition();
        }

        storyDetailView.goneProgressDialog();
    }

    @Override
    public void onClickComment() {
        Story story = storyDetailInteractor.getStory();
        storyDetailView.navigateToCommentActivity(story);
    }

    @Override
    public void onClickClose() {
        storyDetailView.navigateToBack();
    }

    @Override
    public void onClickMenu() {
        Story story = storyDetailInteractor.getStory();
        storyDetailView.navigateToStoryDialogActivity(story);
    }

    @Override
    public void onClickPhoto(File file) {
        storyDetailView.navigateToPhotoDialogActivity(file);
    }

    @Override
    public void onClickPlayerButton(File file) {
        storyDetailView.navigateToVideoPlayerActivity(file);
    }

    @Override
    public void onClickAvatar() {
        Story story = storyDetailInteractor.getStory();
        User storyUser = story.getUser();
        storyDetailView.navigateToMerchantDetailActivity(storyUser);
    }

    @Override
    public void onLikeChecked(boolean checked) {
        User user = storyDetailInteractor.getUser();
        Story story = storyDetailInteractor.getStory();
        boolean isFirstLikeChecked = story.getFirstLikeChecked();

        if (isFirstLikeChecked) {
            if (user != null) {
                long userId = user.getId();
                String userName = user.getName();
                User storyUser = story.getUser();
                long storyUserId = storyUser.getId();
                long storyId = story.getId();

                if (checked) {
                    storyDetailInteractor.setStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName);
                } else {
                    storyDetailInteractor.deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, userId);
                }
            } else {
                storyDetailView.navigateToLoginActivity();
                storyDetailView.showMessage("로그인이 필요한 서비스입니다.");
            }
        }
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        storyDetailView.navigateToSearchTagActivity(hashTag);
    }

    @Override
    public void onActivityResultForCommentResultOk(int commentCountAdded) {
        Story story = storyDetailInteractor.getStory();
        int commentCount = story.getCommentCount();
        story.setCommentCount(commentCount + commentCountAdded);
        storyDetailView.showCommentCount(String.valueOf(story.getCommentCount()));
    }

    @Override
    public void onActivityResultForStoryEditResultOk(Story story) {
        storyDetailInteractor.setStory(story);
        story.setFirstLikeChecked(true);
        String content = story.getContent();
        List<File> files = story.getFiles();
        int fileSize = files.size();

        if (fileSize > 1) {
            storyDetailView.clearStoryDetailFileAdapter();
            storyDetailView.setStoryDetailFileAdapterItem(files);
            storyDetailView.showFileTotal(String.valueOf(fileSize));
            storyDetailView.showFile();
            storyDetailView.showIndicator();
            storyDetailView.showPosition();
        } else if (fileSize == 1) {
            storyDetailView.clearStoryDetailFileAdapter();
            storyDetailView.setStoryDetailFileAdapterItem(files);
            storyDetailView.showFile();
            storyDetailView.goneIndicator();
            storyDetailView.gonePosition();
        } else {
            storyDetailView.goneFile();
            storyDetailView.gonePosition();
            storyDetailView.goneIndicator();
        }

        storyDetailView.showContent(content);
    }

    @Override
    public void onActivityResultForStoryResultDelete() {
        storyDetailView.navigateToBack();
    }

    @Override
    public void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId() {
        Story story = storyDetailInteractor.getStory();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount + 1);
        story.setLikeChecked(true);

        storyDetailView.setLikeChecked(true);
        storyDetailView.showLikeCount(String.valueOf(story.getLikeCount()));
    }

    @Override
    public void onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId() {
        Story story = storyDetailInteractor.getStory();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount - 1);
        story.setLikeChecked(false);

        storyDetailView.setLikeChecked(false);
        storyDetailView.showLikeCount(String.valueOf(story.getLikeCount()));
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            storyDetailView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            storyDetailView.showMessage(httpErrorDto.message());
        }
    }


}
