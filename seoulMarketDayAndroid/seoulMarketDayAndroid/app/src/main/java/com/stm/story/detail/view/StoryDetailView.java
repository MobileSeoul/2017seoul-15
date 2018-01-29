package com.stm.story.detail.view;

import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public interface StoryDetailView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void showUserName(String message);

    void showUserAvatar(String message);

    void showContent(String message);

    void showDate(String message);

    void showLikeCount(String message);

    void showCommentCount(String message);

    void showFileTotal(String message);

    void gonePosition();

    void goneFile();

    void goneIndicator();

    void showIndicator();

    void showPosition();

    void showFile();

    void setLikeChecked(boolean isLikeChecked);

    void setStoryDetailFileAdapterItem(List<File> files);

    void clearStoryDetailFileAdapter();

    void onClickAvatar();

    void onClickComment();

    void onClickClose();

    void onClickMenu();

    void navigateToBack();

    void onPageSelected(int position);

    void navigateToCommentActivity(Story story);

    void navigateToStoryDialogActivity(Story story);

    void navigateToPhotoDialogActivity(File file);

    void navigateToVideoPlayerActivity(File file);

    void navigateToMerchantDetailActivity(User storyUser);

    void navigateToLoginActivity();

    void navigateToSearchTagActivity(String tagName);

}
