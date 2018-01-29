package com.stm.comment.base.presenter.impl;

import android.text.Editable;

import com.stm.comment.base.interactor.CommentInteractor;
import com.stm.comment.base.interactor.impl.CommentInteractorImpl;
import com.stm.comment.base.presenter.CommentPresenter;
import com.stm.comment.base.view.CommentView;
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

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public class CommentPresenterImpl implements CommentPresenter {
    private CommentInteractor commentInteractor;
    private CommentView commentView;

    public CommentPresenterImpl(CommentView commentView) {
        this.commentInteractor = new CommentInteractorImpl(this);
        this.commentView = commentView;
    }

    @Override
    public void init(User user, Story story, int position) {
        commentView.setToolbarLayout();
        commentView.showToolbarTitle("댓글");
        commentView.showExternalStoragePermission();
        commentView.setScrollViewOnScrollChangeListener();
        commentView.showProgressDialog();
        commentInteractor.setUser(user);
        commentInteractor.setStory(story);
        commentInteractor.setPosition(position);

        int offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        long storyId = story.getId();

        if (user != null) {
            long userId = user.getId();
            String accessToken = user.getAccessToken();
            commentInteractor.setStoryRepository(accessToken);
            commentInteractor.setStoryCommentRepository(accessToken);
            commentInteractor.setFileRepository(accessToken);
            commentInteractor.getStoryCommentListByIdAndUserIdAndOffset(storyId, userId, offset);

        } else {
            commentInteractor.setStoryRepository();
            commentInteractor.setStoryCommentRepository();
            commentInteractor.setFileRepository();
            commentInteractor.getStoryCommentListByIdAndOffset(storyId, offset);

        }
    }

    @Override
    public void onClickWrite(String content) {
        User user = commentInteractor.getUser();
        if (user != null) {
            if (content.length() > 0) {
                commentView.showProgressDialog();

                StoryComment storyComment = new StoryComment();
                Story story = commentInteractor.getStory();

                storyComment.setStory(story);
                storyComment.setUser(user);
                storyComment.setGroupId(0);
                storyComment.setDepth(0);
                storyComment.setPosition(0);
                storyComment.setContent(content);

                commentView.showCommentEditText("");
                commentInteractor.getStoryCommentByUploadingStoryComment(storyComment);
            }
        } else {
            commentView.showMessage("로그인이 필요한 서비스입니다.");
            commentView.navigateToLoginActivity();
        }
    }

    @Override
    public void onClickBack() {
        int commentCountAdded = commentInteractor.getCommentCountAdded();
        if (commentCountAdded == 0) {
            commentView.navigateToBack();
        } else {
            int position = commentInteractor.getPosition();
            commentView.navigateToBackWIthResultOk(position, commentCountAdded);
        }
    }


    @Override
    public void onCommentTextChanged(Editable editable) {
        int length = editable.length();
        if (length > 0) {
            commentView.showActivatedWriteButton();
        } else {
            commentView.showDeactivatedWriteButton();
        }
    }

    @Override
    public void onBackPressed(int statusOfUploaderView) {
        if (statusOfUploaderView == ViewFlag.VIEW_GONE) {
            int commentCountAdded = commentInteractor.getCommentCountAdded();
            if (commentCountAdded == 0) {
                commentView.navigateToBack();
            } else {
                int position = commentInteractor.getPosition();
                commentView.navigateToBackWIthResultOk(position, commentCountAdded);
            }
        }
    }

    @Override
    public void onClickVideo() {
        commentView.navigateToMultiMediaStoreForVideo();
    }

    @Override
    public void onClickPhoto() {
        commentView.navigateToMultiMediaStoreForPhoto();
    }

    @Override
    public void onClickVR360() {
        commentView.navigateToMultiMediaStoreForVR360();
    }

    @Override
    public void onActivityResultForVR360ResultOk(FileDto fileDto) {
        User user = commentInteractor.getUser();

        if (user != null) {
            commentView.showUploaderScreen();
            commentView.showUploaderMessage("VR360 업로드 중입니다.");

            StoryComment storyComment = new StoryComment();
            Story story = commentInteractor.getStory();

            storyComment.setStory(story);
            storyComment.setUser(user);
            storyComment.setGroupId(0);
            storyComment.setDepth(0);
            storyComment.setPosition(0);
            storyComment.setContent("");

            commentInteractor.getStoryCommentByUploadingStoryCommentAndFileDto(storyComment, fileDto);
        } else {
            commentView.showMessage("로그인이 필요한 서비스입니다.");
            commentView.navigateToLoginActivity();
        }
    }

    @Override
    public void onActivityResultForPhotoResultOk(FileDto fileDto) {
        User user = commentInteractor.getUser();

        if (user != null) {
            commentView.showUploaderScreen();
            commentView.showUploaderMessage("사진 업로드 중입니다.");

            StoryComment storyComment = new StoryComment();
            Story story = commentInteractor.getStory();

            storyComment.setStory(story);
            storyComment.setUser(user);
            storyComment.setGroupId(0);
            storyComment.setDepth(0);
            storyComment.setPosition(0);
            storyComment.setContent("");

            commentInteractor.getStoryCommentByUploadingStoryCommentAndFileDto(storyComment, fileDto);
        } else {
            commentView.showMessage("로그인이 필요한 서비스입니다.");
            commentView.navigateToLoginActivity();
        }
    }

    @Override
    public void onActivityResultForVideoResultOk(FileDto fileDto) {
        User user = commentInteractor.getUser();

        if (user != null) {
            long fileLength = fileDto.getFile().length();

            if (fileLength > DefaultFileFlag.MAX_FILE_SIZE) {
                commentView.showMessage("동영상 파일은 최대 20MB 크기까지 등록이 가능합니다.");
            } else {
                commentView.showUploaderScreen();
                commentView.showUploaderMessage("동영상 업로드 중입니다.");
                StoryComment storyComment = new StoryComment();
                Story story = commentInteractor.getStory();

                storyComment.setStory(story);
                storyComment.setUser(user);
                storyComment.setGroupId(0);
                storyComment.setDepth(0);
                storyComment.setPosition(0);
                storyComment.setContent("");

                commentInteractor.getStoryCommentByUploadingStoryCommentAndFileDto(storyComment, fileDto);
            }

        } else {
            commentView.showMessage("로그인이 필요한 서비스입니다.");
            commentView.navigateToLoginActivity();
        }
    }

    @Override
    public void onUpdateProgress(long bytesRead) {
        commentView.handleUploaderData(bytesRead);
    }

    @Override
    public void onStartProgress(long totalSize) {
        commentView.setUploaderMax(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        commentView.setUploaderMax(0);
    }

    @Override
    public void onSuccessGetStoryCommentListById(List<StoryComment> newStoryComments) {
        int newStoryCommentSize = newStoryComments.size();

        List<StoryComment> storyComments = commentInteractor.getStoryComments();
        int storyCommentSize = storyComments.size();
        if (storyCommentSize == 0) {
            commentInteractor.setStoryComments(newStoryComments);
            commentView.clearCommentAdapter();
            commentView.setCommentAdapterItem(newStoryComments);
        } else {
            commentInteractor.setStoryCommentsAddAll(newStoryComments);
            commentView.commentAdapterNotifyItemRangeInserted(storyCommentSize, newStoryCommentSize);
        }

        commentView.goneProgressDialog();
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            commentView.showProgressDialog();
            Story story = commentInteractor.getStory();
            long storyId = story.getId();

            List<StoryComment> storyComments = commentInteractor.getStoryComments();
            int offset = storyComments.size();

            User user = commentInteractor.getUser();
            if (user != null) {
                long userId = user.getId();
                commentInteractor.getStoryCommentListByIdAndUserIdAndOffset(storyId, userId, offset);

            } else {
                commentInteractor.getStoryCommentListByIdAndOffset(storyId, offset);
            }
        }
    }

    @Override
    public void onSuccessGetStoryCommentByUploadingStoryComment(StoryComment newStoryComment) {
        commentInteractor.setStoryCommentsAddAtThePosition(newStoryComment, 0);
        commentView.commentAdapterNotifyItemInserted(0);
        commentView.setNestedScrollUpPosition();
        commentInteractor.setCommentCountAddedPlusCount(1);
        commentView.goneProgressDialog();
    }

    @Override
    public void onSuccessGetStoryCommentByUploadingStoryCommentAndFileDto(StoryComment newStoryComment) {
        commentView.showProgressDialog();
        commentView.goneUploaderScreen();
        commentInteractor.setStoryCommentsAddAtThePosition(newStoryComment, 0);
        commentView.commentAdapterNotifyItemInserted(0);
        commentView.setNestedScrollUpPosition();
        commentInteractor.setCommentCountAddedPlusCount(1);
        commentView.goneProgressDialog();
    }

    @Override
    public void onClickReply(StoryComment storyComment, int position) {
        Story story = commentInteractor.getStory();
        commentView.navigateToCommentReplyActivity(story, storyComment, position);
    }

    @Override
    public void onActivityResultForCommentReplyResultOk(StoryComment newStoryComment, int position, int commentCountAdded) {
        List<StoryComment> storyComments = commentInteractor.getStoryComments();
        StoryComment storyComment = storyComments.get(position);

        int replyCommentCount = storyComment.getReplyCommentCount();
        int newReplyCommentCount = replyCommentCount + commentCountAdded;

        int replyCommentPosition = position + 1;

        storyComment.setReplyCommentCount(newReplyCommentCount);
        commentView.commentAdapterNotifyItemChanged(position);

        commentInteractor.setCommentCountAddedPlusCount(commentCountAdded);

        if (replyCommentCount > 0) {
            if (newReplyCommentCount > 0) {
                StoryComment storyReplyComment = storyComments.get(replyCommentPosition);

                storyReplyComment.setReplyCommentCount(newReplyCommentCount);
                commentView.commentAdapterNotifyItemChanged(replyCommentPosition);

            } else {
                commentInteractor.setStoryCommentsRemoveAtThePosition(replyCommentPosition);
                commentView.commentAdapterNotifyItemRemoved(replyCommentPosition);
            }

        } else {
            if (newReplyCommentCount > 0) {
                if (newStoryComment != null) {
                    newStoryComment.setReplyCommentCount(newReplyCommentCount);
                    commentInteractor.setStoryCommentsAddAtThePosition(newStoryComment, replyCommentPosition);
                    commentView.commentAdapterNotifyItemInserted(replyCommentPosition);
                }
            }
        }
    }

    @Override
    public void onActivityResultForCommentReplyResultLogin() {
        commentView.showMessage("로그인이 필요한 서비스입니다.");
        commentView.navigateToLoginActivity();
    }

    @Override
    public boolean onLongClickCommentLayout(StoryComment storyComment, int position) {
        User user = commentInteractor.getUser();
        if(user != null) {
            commentView.navigateToCommentDialogActivity(storyComment, position);
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResultForCommentResultDelete(int position) {
        commentInteractor.setStoryCommentsRemoveAtThePosition(position);
        commentView.commentAdapterNotifyItemRemoved(position);
        commentInteractor.setCommentCountAddedMinusCount(1);
    }

    @Override
    public void onClickPlayerButton(File file) {
        commentView.showProgressDialog();
        commentInteractor.updateFileByHits(file);
    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        commentView.goneProgressDialog();

        int type = file.getType();
        if(type == DefaultFileFlag.PHOTO_TYPE){
            commentView.navigateToPhotoDialogActivity(file);
        }
        if(type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE){
            commentView.navigateToVideoPlayerActivity(file);
        }

    }

    @Override
    public void onClickPhotoLayout(File file) {
        commentView.showProgressDialog();
        commentInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickAvatar(User user) {
        int level = user.getLevel();
        if(level == UserLevelFlag.MERCHANT){
            commentView.navigateToMerchantDetailActivity(user);
        }
        if(level == UserLevelFlag.NORMAL){
            commentView.navigateToUserDetailActivity(user);
        }

    }

    @Override
    public void onActivityResultForCommentResultEdit(StoryComment newStoryComment, int position) {
        StoryComment storyComment = commentInteractor.getStoryComments().get(position);
        String newContent = newStoryComment.getContent();
        storyComment.setContent(newContent);
        commentView.commentAdapterNotifyItemChanged(position);
    }

    @Override
    public void onRequestPermissionsResultForReadExternalStorage(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            commentView.showMessage("권한을 허가해주세요.");
            commentView.navigateToBack();
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            commentView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            commentView.showMessage(httpErrorDto.message());
        }
    }


}
