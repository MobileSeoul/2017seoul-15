package com.stm.main.fragment.main.search.fragment.tag.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.main.fragment.main.search.fragment.tag.adapter.SearchTagStoryAdapter;
import com.stm.main.fragment.main.search.fragment.tag.interactor.SearchTagInteractor;
import com.stm.main.fragment.main.search.fragment.tag.interactor.impl.SearchTagInteractorImpl;
import com.stm.main.fragment.main.search.fragment.tag.presenter.SearchTagPresenter;
import com.stm.main.fragment.main.search.fragment.tag.view.SearchTagView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchTagPresenterImpl implements SearchTagPresenter {
    private SearchTagInteractor searchTagInteractor;
    private SearchTagView searchTagView;

    public SearchTagPresenterImpl(SearchTagView searchTagView) {
        this.searchTagInteractor = new SearchTagInteractorImpl(this);
        this.searchTagView = searchTagView;
    }

    @Override
    public void init(User user) {
        searchTagInteractor.setUser(user);

        if (user != null) {
            String accessToken = user.getAccessToken();
            searchTagInteractor.setStoryTagRepository(accessToken);
            searchTagInteractor.setStoryRepository(accessToken);
        } else {
            searchTagInteractor.setStoryTagRepository();
            searchTagInteractor.setStoryRepository();
        }
    }

    @Override
    public void onCreateView() {
        searchTagView.setOnScrollChangeListener();

        User user = searchTagInteractor.getUser();
        String keyword = searchTagInteractor.getKeyword();

        if(keyword != null) {
            if (user != null) {
                long userId = user.getId();
                long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
                searchTagInteractor.getStoryListByTagNameAndOffset(keyword, userId, offset);
            } else {
                long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
                searchTagInteractor.getStoryListByTagNameAndOffset(keyword, offset);
            }
        }
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            searchTagView.showProgressDialog();

            String keyword = searchTagInteractor.getKeyword();
            User user = searchTagInteractor.getUser();

            if (user != null) {
                List<Story> stories = searchTagInteractor.getStories();
                long offset = stories.size();
                long userId = user.getId();

                searchTagInteractor.getStoryListByTagNameAndOffset(keyword, userId, offset);
            } else {
                List<Story> stories = searchTagInteractor.getStories();
                long offset = stories.size();

                searchTagInteractor.getStoryListByTagNameAndOffset(keyword, offset);
            }
        }
    }

    @Override
    public void getStoryListByKeyword(String keyword) {
        searchTagView.showProgressDialog();

        String prevKeyword = searchTagInteractor.getKeyword();
        User user = searchTagInteractor.getUser();
        List<Story> stories = searchTagInteractor.getStories();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;

        if (prevKeyword != null && prevKeyword.equals(keyword)) {
            offset = stories.size();
        }

        if (user != null) {
            long userId = user.getId();
            searchTagInteractor.getStoryListByTagNameAndOffset(keyword, userId, offset);
        } else {
            searchTagInteractor.getStoryListByTagNameAndOffset(keyword, offset);
        }

    }

    @Override
    public void onSuccessGetStoryListByTagNameAndOffset(List<Story> newStories, String keyword) {
        User user = searchTagInteractor.getUser();
        String prevKeyword = searchTagInteractor.getKeyword();
        int newStorySize = newStories.size();

        if (newStorySize > 0) {
            if (prevKeyword == null) {
                searchTagInteractor.setKeyword(keyword);
                searchTagInteractor.setStories(newStories);
                searchTagView.clearSearchTagAdapter();
                searchTagView.setSearchTagAdapterItem(newStories);
                searchTagView.goneEmptyView();

            } else {
                if (prevKeyword.equals(keyword)) {
                    List<Story> stories = searchTagInteractor.getStories();
                    int storySize = stories.size();

                    searchTagInteractor.setStoriesAddAll(newStories);
                    searchTagView.notifyItemRangeInserted(storySize, newStorySize);
                    searchTagView.goneEmptyView();
                } else {
                    searchTagInteractor.setKeyword(keyword);
                    searchTagInteractor.setStories(newStories);
                    searchTagView.clearSearchTagAdapter();
                    searchTagView.setSearchTagAdapterItem(newStories);
                    searchTagView.goneEmptyView();
                }
            }
        } else {
            if (prevKeyword == null) {
                searchTagView.showEmptyView();
            }
        }

        searchTagView.goneProgressDialog();
    }

    @Override
    public void onClickMoreComment(Story story, int position) {
        searchTagView.navigateToCommentActivity(story, position);
    }

    @Override
    public void onClickAvatar(User user) {
        int level = user.getLevel();
        if (level == UserLevelFlag.MERCHANT) {
            searchTagView.navigateToMerchantDetailActivity(user);
        }
        if (level == UserLevelFlag.NORMAL) {
            searchTagView.navigateToUserDetailActivity(user);
        }
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
    public void onClickMenu(Story story, int position) {
        searchTagView.navigateToStoryDialogActivity(story, position);
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
            searchTagView.goneFile(holder);
            searchTagView.goneIndicator(holder);
            searchTagView.gonePosition(holder);
        }
    }

    @Override
    public void onClickPlayerButton(File file) {
        searchTagView.navigateToVideoPlayerActivity(file);
    }

    @Override
    public void onClickPhoto(File file) {
        searchTagView.navigateToPhotoDialogActivity(file);
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
    public void onActivityResultForStoryResultEdit(int position, Story story) {
        searchTagInteractor.replaceStoriesItemAtThePosition(position, story);
        searchTagView.searchTagStoryAdapterNotifyItemChanged(position);
    }


    @Override
    public void onActivityResultForStoryResultDelete(int position) {
        searchTagInteractor.setStoriesRemoveAtThePosition(position);
        searchTagView.searchTagStoryAdapterNotifyItemRemoved(position);
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
