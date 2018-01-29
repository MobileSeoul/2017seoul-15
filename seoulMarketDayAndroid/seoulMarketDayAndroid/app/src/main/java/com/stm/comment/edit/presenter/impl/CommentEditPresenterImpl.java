package com.stm.comment.edit.presenter.impl;

import com.stm.comment.edit.interactor.CommentEditInteractor;
import com.stm.comment.edit.interactor.impl.CommentEditInteractorImpl;
import com.stm.comment.edit.presenter.CommentEditPresenter;
import com.stm.comment.edit.view.CommentEditView;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public class CommentEditPresenterImpl implements CommentEditPresenter {
    private CommentEditView commentEditView;
    private CommentEditInteractor commentEditInteractor;

    public CommentEditPresenterImpl(CommentEditView commentEditView) {
        this.commentEditView = commentEditView;
        this.commentEditInteractor = new CommentEditInteractorImpl(this);
    }

    @Override
    public void init(User user, StoryComment storyComment, int position) {
        commentEditInteractor.setUser(user);
        commentEditInteractor.setStoryComment(storyComment);
        commentEditInteractor.setPosition(position);
        commentEditView.setToolbarLayout();

        String accessToken = user.getAccessToken();
        commentEditInteractor.setStoryCommentRepository(accessToken);

        String avatar = user.getAvatar();
        String content = storyComment.getContent();

        commentEditView.showToolbarTitle("댓글 수정");
        commentEditView.showUserAvatar(avatar);
        commentEditView.showContent(content);
    }

    @Override
    public void onClickCancel() {
        commentEditView.navigateToBack();
    }

    @Override
    public void onClickSubmit(String content) {
        StoryComment storyComment = commentEditInteractor.getStoryComment();
        String preContent = storyComment.getContent();

        if(content.length() != 0 && !content.equals(preContent)){
            storyComment.setContent(content);
            commentEditInteractor.updateStoryComment(storyComment);
        }else{
            commentEditView.showMessage("변경된 사항이 없습니다");
        }
    }

    @Override
    public void onBackPressed() {
        commentEditView.navigateToBack();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            commentEditView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            commentEditView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessUpdateStoryComment() {
        StoryComment storyComment = commentEditInteractor.getStoryComment();
        int position = commentEditInteractor.getPosition();
        commentEditView.navigateToBackWithResultOk(storyComment, position);
    }

    @Override
    public void onClickBack() {
        commentEditView.navigateToBack();
    }

}
