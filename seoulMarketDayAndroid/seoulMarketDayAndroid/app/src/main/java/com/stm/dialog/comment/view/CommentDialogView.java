package com.stm.dialog.comment.view;

import com.stm.common.dao.StoryComment;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public interface CommentDialogView {
    void showMessage(String message);

    void setCommentDialogToAuthorAdapterItem(ArrayList<String> messages);

    void setCommentDialogToAuthorWithFileAdapterItem(ArrayList<String> messages);

    void setCommentDialogToOthersAdapterItem(ArrayList<String> messages);

    void navigateToBack();

    void setContentCopied(String content);

    void showProgressDialog();

    void goneProgressDialog();

    void navigateToBackWithResultDelete(int position);

    void navigateToCommentEditActivity(StoryComment storyComment, int commentPosition);

    void navigateToBackWithResultEdit(StoryComment storyComment, int position);
}
