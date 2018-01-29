package com.stm.comment.detail.presenter.impl;

import android.text.Editable;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.common.flag.ViewFlag;
import com.stm.comment.detail.interactor.CommentReplyInteractor;
import com.stm.comment.detail.interactor.impl.CommentReplyInteractorImpl;
import com.stm.comment.detail.presenter.CommentReplyPresenter;
import com.stm.comment.detail.view.CommentReplyView;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by Dev-0 on 2017-08-10.
 */

public class CommentReplyPresenterImpl implements CommentReplyPresenter {
    private CommentReplyView commentReplyView;
    private CommentReplyInteractor commentReplyInteractor;

    public CommentReplyPresenterImpl(CommentReplyView commentReplyView) {
        this.commentReplyView = commentReplyView;
        this.commentReplyInteractor = new CommentReplyInteractorImpl(this);
    }

    @Override
    public void init(Story story, StoryComment storyComment, User user, int position) {
        commentReplyView.setToolbarLayout();
        commentReplyView.showToolbarTitle("답글");
        commentReplyView.showExternalStoragePermission();
        commentReplyView.setScrollViewOnScrollChangeListener();
        commentReplyView.showProgressDialog();
        commentReplyInteractor.setUser(user);
        commentReplyInteractor.setStory(story);
        commentReplyInteractor.setStoryComment(storyComment);
        commentReplyInteractor.setPosition(position);

        int offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        long groupId = storyComment.getGroupId();

        if (user != null) {
            long userId = user.getId();
            String accessToken = user.getAccessToken();
            commentReplyInteractor.setStoryCommentRepository(accessToken);
            commentReplyInteractor.setFileRepository(accessToken);
            commentReplyInteractor.getStoryCommentReplyListByGroupIdAndUserIdAndOffset(groupId, userId, offset);

        } else {
            commentReplyInteractor.setStoryCommentRepository();
            commentReplyInteractor.setFileRepository();
            commentReplyInteractor.getStoryCommentReplyListByGroupIdAndOffset(groupId, offset);
        }
    }

    @Override
    public void onClickWrite(String content) {
        User user = commentReplyInteractor.getUser();
        if (user != null) {
            if (content.length() > 0) {
                commentReplyView.showProgressDialog();
                StoryComment storyComment = commentReplyInteractor.getStoryComment();
                long groupId = storyComment.getGroupId();
                User commentUser = storyComment.getUser();

                StoryComment newStoryComment = new StoryComment();
                Story story = commentReplyInteractor.getStory();

                newStoryComment.setStory(story);
                newStoryComment.setUser(user);
                newStoryComment.setGroupId(groupId);
                newStoryComment.setDepth(1);
                newStoryComment.setPosition(0);
                newStoryComment.setContent(content);

                commentReplyView.showCommentEditText("");
                commentReplyInteractor.getStoryCommentReplyByUploadingStoryCommentAndCommentUser(newStoryComment,commentUser);
            }
        } else {
            commentReplyView.navigateToLoginActivity();
        }
    }

    @Override
    public void onClickBack() {
        int commentReplyCountAdded = commentReplyInteractor.getCommentReplyCountAdded();
        if (commentReplyCountAdded == 0) {
            commentReplyView.navigateToBack();
        } else {
            int position = commentReplyInteractor.getPosition();
            List<StoryComment> storyComments = commentReplyInteractor.getStoryComments();
            int storyCommentSize = storyComments.size();

            StoryComment storyComment = null;
            if (storyCommentSize > 0) {
                storyComment = storyComments.get(0);
            }

            commentReplyView.navigateToBackWIthResultOk(storyComment, position, commentReplyCountAdded);
        }
    }

    @Override
    public void onClickAvatar(User user) {
        int level = user.getLevel();
        if(level == UserLevelFlag.MERCHANT){
            commentReplyView.navigateToMerchantDetailActivity(user);
        }

        if(level == UserLevelFlag.NORMAL){
            commentReplyView.navigateToUserDetailActivity(user);
        }

    }

    @Override
    public void onCommentTextChanged(Editable editable) {
        int length = editable.length();
        if (length > 0) {
            commentReplyView.showActivatedWriteButton();
        } else {
            commentReplyView.showDeactivatedWriteButton();
        }
    }

    @Override
    public void onBackPressed(int statusOfUploaderView) {
        if (statusOfUploaderView == ViewFlag.VIEW_GONE) {
            int commentReplyCountAdded = commentReplyInteractor.getCommentReplyCountAdded();
            if (commentReplyCountAdded == 0) {
                commentReplyView.navigateToBack();
            } else {
                int position = commentReplyInteractor.getPosition();
                List<StoryComment> storyComments = commentReplyInteractor.getStoryComments();
                int storyCommentSize = storyComments.size();

                StoryComment storyComment = null;
                if (storyCommentSize > 0) {
                    storyComment = storyComments.get(0);
                }

                commentReplyView.navigateToBackWIthResultOk(storyComment, position, commentReplyCountAdded);
            }
        }
    }

    @Override
    public void onClickVideo() {
        commentReplyView.navigateToMultiMediaStoreForVideo();
    }

    @Override
    public void onClickPhoto() {
        commentReplyView.navigateToMultiMediaStoreForPhoto();
    }

    @Override
    public void onClickVR360() {
        commentReplyView.navigateToMultiMediaStoreForVR360();
    }

    @Override
    public void onActivityResultForVR360ResultOk(FileDto fileDto) {
        User user = commentReplyInteractor.getUser();

        if (user != null) {
            commentReplyView.showUploaderScreen();
            commentReplyView.showUploaderMessage("VR360 업로드 중입니다.");

            StoryComment storyComment = commentReplyInteractor.getStoryComment();
            long groupId = storyComment.getGroupId();
            User commentUser = storyComment.getUser();

            StoryComment newStoryComment = new StoryComment();
            Story story = commentReplyInteractor.getStory();

            newStoryComment.setStory(story);
            newStoryComment.setUser(user);
            newStoryComment.setGroupId(groupId);
            newStoryComment.setDepth(1);
            newStoryComment.setPosition(0);
            newStoryComment.setContent("");

            commentReplyInteractor.getStoryCommentReplyByUploadingStoryCommentAndFileDtoAndCommentUser(newStoryComment, fileDto,commentUser);
        } else {
            commentReplyView.navigateToLoginActivity();
        }
    }

    @Override
    public void onActivityResultForPhotoResultOk(FileDto fileDto) {
        User user = commentReplyInteractor.getUser();

        if (user != null) {
            commentReplyView.showUploaderScreen();
            commentReplyView.showUploaderMessage("사진 업로드 중입니다.");

            StoryComment storyComment = commentReplyInteractor.getStoryComment();
            long groupId = storyComment.getGroupId();
            User commentUser = storyComment.getUser();

            StoryComment newStoryComment = new StoryComment();
            Story story = commentReplyInteractor.getStory();

            newStoryComment.setStory(story);
            newStoryComment.setUser(user);
            newStoryComment.setGroupId(groupId);
            newStoryComment.setDepth(1);
            newStoryComment.setPosition(0);
            newStoryComment.setContent("");

            commentReplyInteractor.getStoryCommentReplyByUploadingStoryCommentAndFileDtoAndCommentUser(newStoryComment, fileDto,commentUser);
        } else {
            commentReplyView.navigateToLoginActivity();
        }
    }

    @Override
    public void onActivityResultForVideoResultOk(FileDto fileDto) {
        User user = commentReplyInteractor.getUser();

        if (user != null) {
            long fileLength = fileDto.getFile().length();

            if (fileLength > DefaultFileFlag.MAX_FILE_SIZE) {
                commentReplyView.showMessage("동영상 파일은 최대 20MB 크기까지 등록이 가능합니다.");
            } else {
                commentReplyView.showUploaderScreen();
                commentReplyView.showUploaderMessage("동영상 업로드 중입니다.");

                StoryComment storyComment = commentReplyInteractor.getStoryComment();
                long groupId = storyComment.getGroupId();
                User commentUser = storyComment.getUser();

                StoryComment newStoryComment = new StoryComment();
                Story story = commentReplyInteractor.getStory();

                newStoryComment.setStory(story);
                newStoryComment.setUser(user);
                newStoryComment.setGroupId(groupId);
                newStoryComment.setDepth(1);
                newStoryComment.setPosition(0);
                newStoryComment.setContent("");

                commentReplyInteractor.getStoryCommentReplyByUploadingStoryCommentAndFileDtoAndCommentUser(newStoryComment, fileDto,commentUser);
            }

        } else {
            commentReplyView.navigateToLoginActivity();
        }
    }

    @Override
    public void onUpdateProgress(long bytesRead) {
        commentReplyView.handleUploaderData(bytesRead);
    }

    @Override
    public void onStartProgress(long totalSize) {
        commentReplyView.setUploaderMax(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        commentReplyView.setUploaderMax(0);
    }

    @Override
    public void onSuccessGetStoryCommentReplyListByGroupIdAndOffset(List<StoryComment> newStoryComments) {
        int newStoryCommentSize = newStoryComments.size();

        List<StoryComment> storyComments = commentReplyInteractor.getStoryComments();
        int storyCommentSize = storyComments.size();
        if (storyCommentSize == 0) {
            commentReplyInteractor.setStoryComments(newStoryComments);
            commentReplyView.clearCommentReplyAdapter();
            commentReplyView.setCommentReplyAdapterItem(newStoryComments);
        } else {
            commentReplyInteractor.setStoryCommentsAddAll(newStoryComments);
            commentReplyView.commentReplyAdapterNotifyItemRangeInserted(storyCommentSize, newStoryCommentSize);
        }

        commentReplyView.goneProgressDialog();
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            commentReplyView.showProgressDialog();
            StoryComment storyComment = commentReplyInteractor.getStoryComment();
            long groupId = storyComment.getGroupId();

            List<StoryComment> storyComments = commentReplyInteractor.getStoryComments();
            int offset = storyComments.size();

            User user = commentReplyInteractor.getUser();
            if (user != null) {
                long userId = user.getId();
                commentReplyInteractor.getStoryCommentReplyListByGroupIdAndUserIdAndOffset(groupId, userId, offset);

            } else {
                commentReplyInteractor.getStoryCommentReplyListByGroupIdAndOffset(groupId, offset);
            }
        }
    }

    @Override
    public void onSuccessGetStoryCommentReplyByUploadingStoryComment(StoryComment newStoryComment) {
        commentReplyInteractor.setStoryCommentsAddAtThePosition(newStoryComment, 0);
        commentReplyView.commentReplyAdapterNotifyItemInserted(0);
        commentReplyView.setNestedScrollUpPosition();
        commentReplyInteractor.setCommentReplyCountAddedPlus(1);
        commentReplyView.goneProgressDialog();
    }

    @Override
    public void onSuccessGetStoryCommentReplyByUploadingStoryCommentAndFileDto(StoryComment newStoryComment) {
        commentReplyView.showProgressDialog();
        commentReplyView.goneUploaderScreen();
        commentReplyInteractor.setStoryCommentsAddAtThePosition(newStoryComment, 0);
        commentReplyView.commentReplyAdapterNotifyItemInserted(0);
        commentReplyView.setNestedScrollUpPosition();
        commentReplyInteractor.setCommentReplyCountAddedPlus(1);
        commentReplyView.goneProgressDialog();
    }

    @Override
    public void onRequestPermissionsResultForReadExternalStorage(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            commentReplyView.showMessage("권한을 허가해주세요.");
            commentReplyView.navigateToBack();
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            commentReplyView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            commentReplyView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public boolean onLongClickCommentReplyLayout(StoryComment storyComment, int position) {
        User user = commentReplyInteractor.getUser();
        if(user != null){
            commentReplyView.navigateToCommentDialogActivity(storyComment, position);
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResultForCommentResultDelete(int position) {
        commentReplyInteractor.setStoryCommentsRemoveAtThePosition(position);
        commentReplyView.commentReplyAdapterNotifyItemRemoved(position);
        commentReplyInteractor.setCommentReplyCountAddedMinus(1);

    }

    @Override
    public void onClickPlayerButton(File file) {
        commentReplyView.showProgressDialog();
        commentReplyInteractor.updateFileByHits(file);
    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        commentReplyView.goneProgressDialog();

        int type = file.getType();
        if(type == DefaultFileFlag.PHOTO_TYPE){
            commentReplyView.navigateToPhotoDialogActivity(file);
        }

        if(type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE){
            commentReplyView.navigateToVideoPlayerActivity(file);
        }
    }

    @Override
    public void onClickPhotoLayout(File file) {
        commentReplyView.showProgressDialog();
        commentReplyInteractor.updateFileByHits(file);
    }

    @Override
    public void onActivityResultForCommentResultEdit(StoryComment newStoryComment, int position) {
        StoryComment storyComment = commentReplyInteractor.getStoryComments().get(position);
        String newContent = newStoryComment.getContent();
        storyComment.setContent(newContent);
        commentReplyView.commentReplyAdapterNotifyItemChanged(position);
    }

}
