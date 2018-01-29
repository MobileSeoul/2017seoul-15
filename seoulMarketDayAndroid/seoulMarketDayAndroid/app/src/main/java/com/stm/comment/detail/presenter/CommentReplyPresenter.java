package com.stm.comment.detail.presenter;

import android.text.Editable;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-10.
 */

public interface CommentReplyPresenter {
    void init(Story story, StoryComment storyComment, User user, int position);

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


    void onSuccessGetStoryCommentReplyListByGroupIdAndOffset(List<StoryComment> newStoryComments);

    void onScrollChange(int difference);

    void onSuccessGetStoryCommentReplyByUploadingStoryComment(StoryComment newStoryComment);

    void onSuccessGetStoryCommentReplyByUploadingStoryCommentAndFileDto(StoryComment newStoryComment);

    void onRequestPermissionsResultForReadExternalStorage(int[] grantResults);

    void onNetworkError(HttpErrorDto httpErrorDto);

    boolean onLongClickCommentReplyLayout(StoryComment storyComment, int position);

    void onActivityResultForCommentResultDelete(int position);

    void onClickPlayerButton(File file);

    void onSuccessUpdateFileByHits(File file);

    void onClickPhotoLayout(File file);

    void onActivityResultForCommentResultEdit(StoryComment storyComment, int position);

    void onClickAvatar(User user);
}
