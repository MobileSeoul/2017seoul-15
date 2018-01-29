package com.stm.comment.edit.presenter;

import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public interface CommentEditPresenter {
    void onBackPressed();

    void init(User user, StoryComment storyComment, int position);

    void onClickCancel();

    void onClickSubmit(String content);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessUpdateStoryComment();

    void onClickBack();
}
