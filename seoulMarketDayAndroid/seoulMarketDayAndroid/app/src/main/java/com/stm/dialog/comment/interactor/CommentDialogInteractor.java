package com.stm.dialog.comment.interactor;

import com.stm.common.dao.Report;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public interface CommentDialogInteractor {
    void setStoryCommentRepository(String accessToken);

    void setReportRepository(String accessToken);

    User getUser();

    void setUser(User user);

    StoryComment getStoryComment();

    void setStoryComment(StoryComment storyComment);

    ArrayList<String> getMessages();

    void setMessages(ArrayList<String> messages);

    void setMessagesAdd(String message);


    void deleteStoryComment(StoryComment storyComment);

    int getPosition();

    void setPosition(int position);

    void setReport(Report report);
}
