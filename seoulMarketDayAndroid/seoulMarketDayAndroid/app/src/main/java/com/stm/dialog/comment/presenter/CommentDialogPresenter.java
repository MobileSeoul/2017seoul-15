package com.stm.dialog.comment.presenter;

import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public interface CommentDialogPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, StoryComment storyComment, int position);

    void onClickCommentDialogToAuthorLayout(int position);

    void onClickCommentDialogToAuthorWithFileLayout(int position);

    void onClickCommentDialogToOthersLayout(int position);

    void onSuccessDeleteStoryComment();

    void onBackPressed();

    void onActivityResultForCommentEditResultOk(StoryComment storyComment, int position);

    void onSuccessSetReport();
}
