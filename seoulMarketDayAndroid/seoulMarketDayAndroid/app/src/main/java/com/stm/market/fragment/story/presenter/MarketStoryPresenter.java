package com.stm.market.fragment.story.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.market.fragment.story.adapter.MarketStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketStoryPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Market market);

    void onCreateView();

    void setFileAdapterItem(MarketStoryAdapter.MarketStoryViewHolder holder, List<File> files);

    void onClickPhoto(File file);

    void onClickPlayer(File file);

    void onSuccessGetStoryListByIdAndOffset(List<Story> stories);

    void onScrollChange(int difference);

    void onClickAvatar(User storyUser);

    void onClickMenu(Story story, int position);

    void onLikeChecked(MarketStoryAdapter.MarketStoryViewHolder marketStoryViewHolder, int position, Story story, boolean checked);

    void onClickComment(Story story, int position);

    void onHashTagClicked(String hashTag);

    void onActivityResultForStoryEditResultOk(Story story, int position);

    void onActivityResultForCommentResultOk(int position, int commentCountAdded);

    void onActivityResultForStoryResultDelete(int position);

    void onSuccessSetStoryLikeByIdAndStoryUserIdAndUserId(int position);

    void onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId(int position);

    void onSuccessUpdateFileByHits(File file);
}
