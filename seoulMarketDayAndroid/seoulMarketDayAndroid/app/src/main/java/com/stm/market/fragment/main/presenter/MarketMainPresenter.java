package com.stm.market.fragment.main.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.market.fragment.main.adapter.MarketMainStoryAdapter;

import java.util.List;

/**
 * Created by Dev-0 on 2017-07-03.
 */

public interface MarketMainPresenter {
    void init(User user, Market market);

    void onCreateView();


    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetMarketById(Market market);

    void onSuccessGetFileListByIdAndTypeAndOffset(List<File> files, int type);

    void onSuccessGetStoryListByIdAndOffset(List<Story> stories);

    void onSuccessUpdateFileByHits(File file);

    void onSuccessUpdateFileByHits(int position);

    void onSuccessSetStoryLikeByIdAndStoryUserIdAndUserId(int position);

    void onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId(int position);

    void onScrolled(int scrollOffset, int scrollRange, int scrollExtent);

    void onScrollChange(int difference);

    void onClickVideo(File file);

    void onPageSelected(int position);

    void onFilePageSelected(File file);

    void onClickPhotoLayout(File file, int position);

    void onClickPhoneText(String phone);

    void onClickHomepageText(String homepage);

    void onClickPhoto(File file);

    void onClickPlayer(File file);

    void onClickComment(Story story, int position);

    void onLikeChecked(MarketMainStoryAdapter.MarketMainStoryViewHolder marketMainStoryViewHolder, int position, Story story, boolean checked);

    void onClickMenu(Story story, int position);

    void onClickAvatar(User storyUser);

    void onHashTagClicked(String tagName);

    void setFileAdapterItem(MarketMainStoryAdapter.MarketMainStoryViewHolder holder, List<File> files);

    void onActivityResultForCommentResultOk(int position, int commentCountAdded);

    void onActivityResultForStoryResultDelete(int position);

    void onActivityResultForStoryEditResultOk(Story story, int position);

}
