package com.stm.main.fragment.main.search.fragment.tag.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.main.fragment.main.search.fragment.tag.adapter.SearchTagStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchTagPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void onCreateView();

    void init(User user);

    void onScrollChange(int difference);

    void getStoryListByKeyword(String keyword);

    void onSuccessGetStoryListByTagNameAndOffset(List<Story> stories, String keyword);

    void onClickMoreComment(Story story, int position);

    void onClickAvatar(User user);

    void onLikeChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder searchTagStoryViewHolder, Story story, int position, boolean checked);

    void onClickMenu(Story story, int position);

    void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files);

    void onClickPlayerButton(File file);

    void onClickPhoto(File file);

    void onActivityResultForCommentResultOk(int position, int commentCountAdded);

    void onActivityResultForStoryResultDelete(int position);

    void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onSuccessUpdateFileByHits(File file);

    void onActivityResultForStoryResultEdit(int position, Story story);

}
