package com.stm.comment.base.view;

import android.text.Editable;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public interface CommentView {
    void showExternalStoragePermission();

    void showMessage(String message);

    void onClickWrite();

    void onClickBack();

    void showCommentEditText(String message);

    void showUploaderMessage(String message);

    void setCommentAdapterItem(List<StoryComment> storyComments);

    void navigateToBack();

    void navigateToBackWIthResultOk(int position, int commentCountAdded);

    void onCommentTextChanged(Editable editable);

    void onClickPhoto();

    void onClickVideo();

    void onClickVR360();

    void showToolbarTitle(String message);

    void setToolbarLayout();

    void navigateToMultiMediaStoreForPhoto();

    void navigateToMultiMediaStoreForVideo();

    void navigateToMultiMediaStoreForVR360();

    void navigateToMerchantDetailActivity(User user);

    void commentAdapterNotifyItemChanged(int position);

    void commentAdapterNotifyItemRemoved(int position);

    void showActivatedWriteButton();

    void showDeactivatedWriteButton();

    void navigateToLoginActivity();

    void showProgressDialog();

    void goneProgressDialog();

    void showUploaderScreen();

    void goneUploaderScreen();

    void clearCommentAdapter();

    void commentAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void commentAdapterNotifyItemInserted(int position);

    void setScrollViewOnScrollChangeListener();

    void setNestedScrollUpPosition();

    void showUploaderData(int bytesRead);

    void setUploaderMax(long maxSize);

    void handleUploaderData(long bytesRead);

    void navigateToCommentReplyActivity(Story story, StoryComment storyComment, int position);

    void navigateToCommentDialogActivity(StoryComment storyComment, int position);

    void navigateToVideoPlayerActivity(com.stm.common.dao.File file);

    void navigateToPhotoDialogActivity(File file);

    void navigateToUserDetailActivity(User user);
}
