package com.stm.comment.base.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public interface CommentInteractor {
    User getUser();

    void setUser(User user);

    List<StoryComment> getStoryComments();

    Story getStory();

    void setStory(Story story);

    void setStoryComments(List<StoryComment> storyComments);

    void setStoryCommentRepository();

    void setStoryCommentRepository(String accessToken);

    void setStoryRepository();

    void setStoryRepository(String accessToken);

    void setFileRepository(String accessToken);

    void setFileRepository();

    void getStoryCommentListByIdAndOffset(long id, int offset);

    void getStoryCommentListByIdAndUserIdAndOffset(long id, long userId, int offset);

    void setStoryCommentsAddAll(List<StoryComment> newStoryComments);

    void getStoryCommentByUploadingStoryComment(StoryComment storyComment);

    void setStoryCommentsAddAtThePosition(StoryComment newStoryComment, int position);

    void setStoryCommentsRemoveAtThePosition(int position);

    void getStoryCommentByUploadingStoryCommentAndFileDto(StoryComment storyComment, FileDto fileDto);

    int getPosition();

    void setPosition(int position);

    int getCommentCountAdded();

    void setCommentCountAddedPlusCount(int count);

    void setCommentCountAddedMinusCount(int count);


    void updateFileByHits(File file);
}
