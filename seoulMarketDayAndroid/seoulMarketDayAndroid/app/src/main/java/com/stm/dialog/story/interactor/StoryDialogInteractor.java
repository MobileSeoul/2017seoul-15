package com.stm.dialog.story.interactor;

import com.stm.common.dao.Report;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface StoryDialogInteractor {
    int getPosition();

    void setPosition(int position);

    User getUser();

    void setUser(User user);

    Story getStory();

    void setStory(Story story);

    ArrayList<String> getMessages();

    void setMessages(ArrayList<String> messages);

    void setMessagesAdd(String message);

    void setStoryRepository(String accessToken);

    void setReportRepository(String accessToken);

    void setReport(Report report);

    void deleteStory(Story story);
}
