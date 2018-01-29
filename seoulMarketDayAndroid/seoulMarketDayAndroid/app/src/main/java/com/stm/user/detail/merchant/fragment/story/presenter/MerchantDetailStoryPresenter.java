package com.stm.user.detail.merchant.fragment.story.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.detail.merchant.fragment.story.adapter.MerchantDetailStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-28.
 */

public interface MerchantDetailStoryPresenter{

    void onCreateView();

    void onLikeChecked(int position, Story story, boolean checked);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, User storyUser);

    void onClickComment(Story story, int position);

    void onScrollChange(int difference);

    void onSuccessGetStoryListByStoryUserIdAndOffset(List<Story> stories);

    void setMerchantDetailStoryFileAdapterItem(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files);

    void onClickPlayerButton(File file);

    void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onActivityResultForCommentResultOk(int position, int commentCountAdded);

    void onSuccessUpdateFileByHits(File file);

    void onClickMenu(Story story, int position);

    void onClickPhoto(File file);

    void onActivityResultForStoryResultDelete(int position);

    void onHashTagClicked(String tagName);

    void onActivityResultForStoryEditResultOk(Story story, int position);

    void setStoryRefresh();
}
