package com.stm.dialog.comment.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Report;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.ReportFlag;
import com.stm.dialog.comment.interactor.CommentDialogInteractor;
import com.stm.dialog.comment.interactor.impl.CommentDialogInteractorImpl;
import com.stm.dialog.comment.presenter.CommentDialogPresenter;
import com.stm.dialog.comment.view.CommentDialogView;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public class CommentDialogPresenterImpl implements CommentDialogPresenter {
    private CommentDialogInteractor commentDialogInteractor;
    private CommentDialogView commentDialogView;

    public CommentDialogPresenterImpl(CommentDialogView commentDialogView) {
        this.commentDialogInteractor = new CommentDialogInteractorImpl(this);
        this.commentDialogView = commentDialogView;
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            commentDialogView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            commentDialogView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void init(User user, StoryComment storyComment, int position) {
        commentDialogInteractor.setUser(user);
        commentDialogInteractor.setStoryComment(storyComment);
        commentDialogInteractor.setPosition(position);

        String accessToken = user.getAccessToken();
        commentDialogInteractor.setStoryCommentRepository(accessToken);
        commentDialogInteractor.setReportRepository(accessToken);

        User storyCommentUser = storyComment.getUser();
        long storyCommentUserId = storyCommentUser.getId();

        long userId = user.getId();
        if (storyCommentUserId != userId) {
            commentDialogInteractor.setMessagesAdd("복사");
            commentDialogInteractor.setMessagesAdd("신고");
            commentDialogInteractor.setMessagesAdd("취소");
            ArrayList<String> messages = commentDialogInteractor.getMessages();
            commentDialogView.setCommentDialogToOthersAdapterItem(messages);
        } else {
            File file = storyComment.getFile();
            if (file != null) {
                commentDialogInteractor.setMessagesAdd("삭제");
                commentDialogInteractor.setMessagesAdd("취소");
                ArrayList<String> messages = commentDialogInteractor.getMessages();
                commentDialogView.setCommentDialogToAuthorWithFileAdapterItem(messages);
            } else {
                commentDialogInteractor.setMessagesAdd("복사");
                commentDialogInteractor.setMessagesAdd("삭제");
                commentDialogInteractor.setMessagesAdd("수정");
                commentDialogInteractor.setMessagesAdd("취소");
                ArrayList<String> messages = commentDialogInteractor.getMessages();
                commentDialogView.setCommentDialogToAuthorAdapterItem(messages);
            }
        }

    }


    @Override
    public void onClickCommentDialogToAuthorLayout(int position) {
        StoryComment storyComment = commentDialogInteractor.getStoryComment();

        if (position == 0) {
            String content = storyComment.getContent();
            commentDialogView.setContentCopied(content);
            commentDialogView.showMessage("클립보드에 복사되었습니다.");
            commentDialogView.navigateToBack();
        }

        if (position == 1) {
            commentDialogView.showProgressDialog();
            commentDialogInteractor.deleteStoryComment(storyComment);
        }

        if (position == 2) {
            int commentPosition = commentDialogInteractor.getPosition();
            commentDialogView.navigateToCommentEditActivity(storyComment, commentPosition);
        }

        if (position == 3) {
            commentDialogView.navigateToBack();
        }

    }

    @Override
    public void onClickCommentDialogToAuthorWithFileLayout(int position) {
        StoryComment storyComment = commentDialogInteractor.getStoryComment();

        if (position == 0) {
            commentDialogView.showProgressDialog();
            commentDialogInteractor.deleteStoryComment(storyComment);
        }

        if (position == 1) {
            commentDialogView.navigateToBack();
        }
    }

    @Override
    public void onClickCommentDialogToOthersLayout(int position) {
        StoryComment storyComment = commentDialogInteractor.getStoryComment();

        if (position == 0) {
            String content = storyComment.getContent();
            commentDialogView.setContentCopied(content);
            commentDialogView.showMessage("클립보드에 복사되었습니다.");
            commentDialogView.navigateToBack();
        }

        if (position == 1) {
            User user = commentDialogInteractor.getUser();
            long storyCommentId = storyComment.getId();
            Report report = new Report();
            report.setContentCategoryId(ReportFlag.STORY_COMMENT_CONTENT);
            report.setUser(user);
            report.setContentId(storyCommentId);

            commentDialogInteractor.setReport(report);
        }

        if (position == 2) {
            commentDialogView.navigateToBack();
        }
    }

    @Override
    public void onSuccessDeleteStoryComment() {
        int position = commentDialogInteractor.getPosition();
        commentDialogView.navigateToBackWithResultDelete(position);
    }

    @Override
    public void onBackPressed() {
        commentDialogView.navigateToBack();
    }

    @Override
    public void onActivityResultForCommentEditResultOk(StoryComment storyComment, int position) {
        commentDialogView.navigateToBackWithResultEdit(storyComment, position);
    }

    @Override
    public void onSuccessSetReport() {
        commentDialogView.showMessage("신고가 접수되었습니다.");
        commentDialogView.navigateToBack();
    }

}
