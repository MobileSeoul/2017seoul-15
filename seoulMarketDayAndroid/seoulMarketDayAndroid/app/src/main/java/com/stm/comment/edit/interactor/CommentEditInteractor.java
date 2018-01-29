package com.stm.comment.edit.interactor;

import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public interface CommentEditInteractor {
    void setStoryCommentRepository(String accessToken);

    User getUser();

    void setUser(User user);

    StoryComment getStoryComment();

    void setStoryComment(StoryComment storyComment);

    int getPosition();

    void setPosition(int position);

    void updateStoryComment(StoryComment storyComment);
}
