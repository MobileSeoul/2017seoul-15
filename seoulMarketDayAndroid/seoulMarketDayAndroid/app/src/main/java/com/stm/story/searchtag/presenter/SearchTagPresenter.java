package com.stm.story.searchtag.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.story.searchtag.adapter.SearchTagStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface SearchTagPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, String tagName);

    void onClickPlayerButton(File file);

    void onClickPhoto(File file);

    void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files);

    void onScrollChange(int difference);

    void onSuccessGetStoryListByTagNameAndOffset(List<Story> newStories);

    void onSuccessGetStoryListByTagNameAndUserIdAndOffset(List<Story> newStories);

    void onSuccessUpdateFileByHits(File file);

    void onClickBack();

    void onClickMoreComment(Story story, int position);

    void onClickAvatar(User user);

    void onLikeChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder searchTagStoryViewHolder, Story position, int story, boolean checked);

    void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onActivityResultForCommentResultOk(int position, int commentCountAdded);

    void onClickMenu(Story story, int position);

    void onActivityResultForStoryResultDelete(int position);

    void onActivityResultForStoryResultEdit(int position, Story story);

    void onBackPressed();

    void onResume(User user);
}
