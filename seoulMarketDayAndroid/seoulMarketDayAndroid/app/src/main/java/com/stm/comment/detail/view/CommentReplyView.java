package com.stm.comment.detail.view;

import android.text.Editable;

import com.stm.common.dao.File;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-10.
 */

public interface CommentReplyView {
    void showExternalStoragePermission();

    void showMessage(String message);

    void onClickWrite();

    void showCommentEditText(String message);

    void showUploaderMessage(String message);

    void setCommentReplyAdapterItem(List<StoryComment> storyComments);

    void navigateToBack();

    void navigateToBackWIthResultOk(StoryComment storyComment, int position, int commentReplyCountAdded);

    void onClickBack();

    void onCommentTextChanged(Editable editable);

    void onClickPhoto();

    void onClickVideo();

    void onClickVR360();

    void showToolbarTitle(String message);

    void setToolbarLayout();

    void navigateToMultiMediaStoreForPhoto();

    void navigateToMultiMediaStoreForVideo();

    void navigateToMultiMediaStoreForVR360();

    void showActivatedWriteButton();

    void showDeactivatedWriteButton();

    void navigateToLoginActivity();

    void showProgressDialog();

    void goneProgressDialog();

    void showUploaderScreen();

    void goneUploaderScreen();

    void clearCommentReplyAdapter();

    void commentReplyAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void commentReplyAdapterNotifyItemInserted(int position);

    void setScrollViewOnScrollChangeListener();

    void setNestedScrollUpPosition();

    void showUploaderData(int bytesRead);

    void setUploaderMax(long maxSize);

    void handleUploaderData(long bytesRead);

    void navigateToCommentDialogActivity(StoryComment storyComment, int position);

    void navigateToVideoPlayerActivity(com.stm.common.dao.File file);

    void commentReplyAdapterNotifyItemRemoved(int position);

    void navigateToPhotoDialogActivity(File file);

    void commentReplyAdapterNotifyItemChanged(int position);

    void navigateToMerchantDetailActivity(User user);

    void navigateToUserDetailActivity(User user);
}
