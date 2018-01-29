package com.stm.story.searchtag.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.story.searchtag.adapter.SearchTagStoryAdapter;
import com.stm.story.searchtag.interactor.SearchTagInteractor;
import com.stm.story.searchtag.interactor.impl.SearchTagInteractorImpl;
import com.stm.story.searchtag.presenter.SearchTagPresenter;
import com.stm.story.searchtag.view.SearchTagView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class SearchTagPresenterImpl implements SearchTagPresenter {
    private SearchTagInteractor searchTagInteractor;
    private SearchTagView searchTagView;

    public SearchTagPresenterImpl(SearchTagView searchTagView) {
        this.searchTagInteractor = new SearchTagInteractorImpl(this);
        this.searchTagView = searchTagView;
    }

    @Override
    public void init(User user, String tagName) {
        searchTagView.showProgressDialog();
        searchTagView.setScrollViewOnScrollChangeListener();
        searchTagInteractor.setUser(user);
        searchTagInteractor.setTagName(tagName);
        searchTagView.setToolbarLayout();
        searchTagView.showToolbarTitle("#" + tagName);


        if (user != null) {
            String accessToken = user.getAccessToken();
            searchTagInteractor.setStoryTagRepository(accessToken);
            searchTagInteractor.setFileRepository(accessToken);
            searchTagInteractor.setStoryRepository(accessToken);
        } else {
            searchTagInteractor.setStoryTagRepository();
            searchTagInteractor.setFileRepository();
            searchTagInteractor.setStoryRepository();
        }

        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        if (user != null) {
            long userId = user.getId();
            searchTagInteractor.getStoryListByTagNameAndUserIdAndOffset(tagName, userId, offset);
        } else {
            searchTagInteractor.getStoryListByTagNameAndOffset(tagName, offset);
        }


    }

    @Override
    public void onClickPlayerButton(File file) {
        searchTagView.showProgressDialog();
        searchTagInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickPhoto(File file) {
        searchTagView.showProgressDialog();
        searchTagInteractor.updateFileByHits(file);
    }

    @Override
    public void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files) {
        int fileSize = files.size();

        if (fileSize > 1) {
            searchTagView.setSearchTagFileAdapterItem(holder, files);
        } else if (fileSize == 1) {
            searchTagView.setSearchTagFileAdapterItem(holder, files);
            searchTagView.gonePosition(holder);
            searchTagView.goneIndicator(holder);
        } else {
            searchTagView.goneMedia(holder);
            searchTagView.goneIndicator(holder);
        }
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            searchTagView.showProgressDialog();

            String tagName = searchTagInteractor.getTagName();
            User user = searchTagInteractor.getUser();
            List<Story> stories = searchTagInteractor.getStories();
            long offset = stories.size();

            if (user != null) {
                long userId = user.getId();
                searchTagInteractor.getStoryListByTagNameAndUserIdAndOffset(tagName, userId, offset);
            } else {
                searchTagInteractor.getStoryListByTagNameAndOffset(tagName, offset);
            }
        }
    }

    @Override
    public void onSuccessGetStoryListByTagNameAndOffset(List<Story> newStories) {
        int newStorySize = newStories.size();
        List<Story> stories = searchTagInteractor.getStories();
        int storySize = stories.size();

        if (storySize == 0) {
            if (newStorySize == 0) {
                searchTagView.showEmptyView();
            } else {
                searchTagInteractor.setStories(newStories);
                searchTagView.clearSearchTagStoryAdapter();
                searchTagView.setSearchTagStoryAdapterItem(newStories);
            }
        } else {
            searchTagInteractor.setStoriesAddAll(newStories);
            searchTagView.searchTagStoryAdapterNotifyItemRangeInserted(storySize, newStorySize);
        }

        searchTagView.goneProgressDialog();
    }

    @Override
    public void onSuccessGetStoryListByTagNameAndUserIdAndOffset(List<Story> newStories) {
        int newStorySize = newStories.size();
        List<Story> stories = searchTagInteractor.getStories();
        int storySize = stories.size();

        if (storySize == 0) {
            if (newStorySize == 0) {
                searchTagView.showEmptyView();
            } else {
                searchTagInteractor.setStories(newStories);
                searchTagView.clearSearchTagStoryAdapter();
                searchTagView.setSearchTagStoryAdapterItem(newStories);
            }
        } else {
            searchTagInteractor.setStoriesAddAll(newStories);
            searchTagView.searchTagStoryAdapterNotifyItemRangeInserted(storySize, newStorySize);
        }

        searchTagView.goneProgressDialog();
    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        searchTagView.goneProgressDialog();

        int type = file.getType();
        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            searchTagView.navigateToVideoPlayerActivity(file);
        }
        if (type == DefaultFileFlag.PHOTO_TYPE) {
            searchTagView.navigateToPhotoDialogActivity(file);
        }
    }

    @Override
    public void onClickBack() {
        searchTagView.navigateToBack();
    }

    @Override
    public void onClickMoreComment(Story story, int position) {
        searchTagView.navigateToCommentActivity(story, position);
    }

    @Override
    public void onClickAvatar(User user) {
        searchTagView.navigateToMerchantDetailActivity(user);
    }

    @Override
    public void onLikeChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, Story story, int position, boolean checked) {
        User user = searchTagInteractor.getUser();

        if (user != null) {
            User storyUser = story.getUser();
            long storyUserId = storyUser.getId();
            long userId = user.getId();
            String userName = user.getName();
            long storyId = story.getId();

            if (checked) {
                searchTagInteractor.setStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName, position);
            } else {
                searchTagInteractor.deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, userId, position);
            }
        } else {
            searchTagView.showMessage("로그인이 필요한 서비스입니다.");
            searchTagView.navigateToLoginActivity();
            searchTagView.setSearchTagAdapterLikeUnChecked(holder);
        }

    }

    @Override
    public void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = searchTagInteractor.getStories();
        Story story = stories.get(position);
        int likeCount = story.getLikeCount();
        int commentCount = story.getCommentCount();

        story.setFirstLikeChecked(false);
        story.setLikeChecked(true);
        story.setLikeCount(likeCount + 1);
        story.setCommentCount(commentCount);

        searchTagView.searchTagStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = searchTagInteractor.getStories();
        Story story = stories.get(position);
        int likeCount = story.getLikeCount();
        int commentCount = story.getCommentCount();

        story.setFirstLikeChecked(false);
        story.setLikeChecked(false);
        story.setLikeCount(likeCount - 1);
        story.setCommentCount(commentCount);
        searchTagView.searchTagStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onActivityResultForCommentResultOk(int position, int commentCountAdded) {
        List<Story> stories = searchTagInteractor.getStories();
        Story story = stories.get(position);
        int commentCount = story.getCommentCount();
        story.setCommentCount(commentCount + commentCountAdded);
        story.setFirstLikeChecked(false);
        searchTagView.searchTagStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onClickMenu(Story story, int position) {
        searchTagView.navigateToStoryDialogActivity(story, position);
    }

    @Override
    public void onActivityResultForStoryResultDelete(int position) {
        searchTagInteractor.setStoriesRemoveAtThePosition(position);
        searchTagView.searchTagStoryAdapterNotifyItemRemoved(position);
    }

    @Override
    public void onActivityResultForStoryResultEdit(int position, Story story) {
        searchTagInteractor.replaceStoriesItemAtThePosition(position, story);
        searchTagView.searchTagStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onBackPressed() {
        searchTagView.navigateToBack();
    }

    @Override
    public void onResume(User user) {
        if (user != null && searchTagInteractor.getUser() == null) {
            searchTagInteractor.setUser(user);

            String accessToken = user.getAccessToken();
            searchTagInteractor.setStoryTagRepository(accessToken);
            searchTagInteractor.setStoryRepository(accessToken);
            searchTagInteractor.setFileRepository(accessToken);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            searchTagView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            searchTagView.showMessage(httpErrorDto.message());
        }
    }
}


