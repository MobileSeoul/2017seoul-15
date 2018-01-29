package com.stm.comment.detail.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-10.
 */

public interface CommentReplyInteractor {
    int getCommentReplyCountAdded();

    void setCommentReplyCountAddedPlus(int count);

    void setCommentReplyCountAddedMinus(int count);

    Story getStory();

    void setStory(Story story);

    StoryComment getStoryComment();

    void setStoryComment(StoryComment storyComment);

    User getUser();

    void setUser(User user);

    int getPosition();

    void setPosition(int position);

    void setStoryCommentRepository();

    void setStoryCommentRepository(String accessToken);

    void setFileRepository(String accessToken);

    void setFileRepository();

    void setStoryCommentsAddAll(List<StoryComment> newStoryComments);

    void setStoryCommentsAddAtThePosition(StoryComment newStoryComment, int position);

    void setStoryCommentsRemoveAtThePosition(int position);

    List<StoryComment> getStoryComments();

    void setStoryComments(List<StoryComment> storyComments);

    void getStoryCommentReplyListByGroupIdAndUserIdAndOffset(long groupId, long userId, int offset);

    void getStoryCommentReplyListByGroupIdAndOffset(long groupId, int offset);


    void getStoryCommentReplyByUploadingStoryCommentAndFileDtoAndCommentUser(StoryComment storyComment, FileDto fileDto, User commentUser);

    void getStoryCommentReplyByUploadingStoryCommentAndCommentUser(StoryComment storyComment, User commentUser);

    void updateFileByHits(File file);
}
