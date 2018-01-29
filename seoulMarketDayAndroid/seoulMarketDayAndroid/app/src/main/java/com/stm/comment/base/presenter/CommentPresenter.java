package com.stm.comment.base.presenter;

import android.text.Editable;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public interface CommentPresenter {
    void init(User user, Story storyId, int position);

    void onRequestPermissionsResultForReadExternalStorage(int[] grantResults);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onClickWrite(String content);

    void onClickBack();

    void onCommentTextChanged(Editable editable);

    void onBackPressed(int statusOfUploaderView);

    void onClickVideo();

    void onClickPhoto();

    void onClickVR360();

    void onActivityResultForVR360ResultOk(FileDto fileDto);

    void onActivityResultForPhotoResultOk(FileDto fileDto);

    void onActivityResultForVideoResultOk(FileDto fileDto);

    void onUpdateProgress(long bytesRead);

    void onStartProgress(long totalSize);

    void onDestroyProgress();

    void onSuccessGetStoryCommentListById(List<StoryComment> storyComments);

    void onScrollChange(int difference);

    void onSuccessGetStoryCommentByUploadingStoryComment(StoryComment storyComment);

    void onSuccessGetStoryCommentByUploadingStoryCommentAndFileDto(StoryComment storyComment);

    void onClickReply(StoryComment storyComment, int position);

    void onActivityResultForCommentReplyResultOk(StoryComment storyComment, int position, int commentCountAdded);

    void onActivityResultForCommentReplyResultLogin();

    boolean onLongClickCommentLayout(StoryComment storyComment, int position);

    void onActivityResultForCommentResultDelete(int position);

    void onClickPlayerButton(File file);

    void onSuccessUpdateFileByHits(File file);

    void onClickPhotoLayout(File file);

    void onActivityResultForCommentResultEdit(StoryComment newStoryComment, int position);

    void onClickAvatar(User user);
}
