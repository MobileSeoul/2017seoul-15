package com.stm.comment.edit.view;

import com.stm.R;
import com.stm.common.dao.StoryComment;

import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public interface CommentEditView {

    void showMessage(String message);

    void showUserAvatar(String avatar);

    void setToolbarLayout();

    void showToolbarTitle(String message);

    void showContent(String content);

    void onClickCancel();

    void onClickSubmit();

    void navigateToBack();

    void onClickBack();

    void showProgressDialog();

    void goneProgressDialog();

    void navigateToBackWithResultOk(StoryComment storyComment, int position);
}
