package com.stm.user.detail.merchant.fragment.main.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface MerchantDetailMainPresenter {
    void onScrollChange(int difference);

    void onPageSelected(int position);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, User storyUser);

    void onCreateView();

    void onHashTagClicked(String hashTag);

    void onClickPlayerButton(File file);

    void onSuccessGetStoryListByStoryUserIdAndOffset(List<Story> stories);

    void onClickComment(Story story, int position);

    void setFileAdapterItem(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files);

    void onLikeChecked(int position, Story story, boolean checked);

    void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position);

    void onSuccessGetFileListByIdAndTypeAndOffset(List<File> files, int type);

    void onScrolled(int scrollOffset, int scrollRange, int scrollExtent);

    void onClickVideo(File file);

    void onActivityResultForCommentResultOk(int position, int commentCountAdded);

    void onClickPhoto(File file);

    void onSuccessUpdateFileByHits(File file);

    void onSuccessUpdateFileByHits(int position);

    void onClickMenu(Story story, int position);

    void onClickPhotoLayout(File file, int position);

    void onActivityResultForStoryResultDelete(int position);

    void onActivityResultForStoryEditResultOk(Story story, int position);


}
