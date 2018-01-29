package com.stm.dialog.story.presenter.impl;

import com.stm.common.dao.Report;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.ReportFlag;
import com.stm.dialog.story.interactor.StoryDialogInteractor;
import com.stm.dialog.story.interactor.impl.StoryDialogInteractorImpl;
import com.stm.dialog.story.presenter.StoryDialogPresenter;
import com.stm.dialog.story.view.StoryDialogView;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class StoryDialogPresenterImpl implements StoryDialogPresenter {
    private StoryDialogInteractor storyDialogInteractor;
    private StoryDialogView storyDialogView;

    public StoryDialogPresenterImpl(StoryDialogView storyDialogView) {
        this.storyDialogInteractor = new StoryDialogInteractorImpl(this);
        this.storyDialogView = storyDialogView;
    }

    @Override
    public void init(User user, Story story, int position) {
        storyDialogInteractor.setPosition(position);
        storyDialogInteractor.setUser(user);
        storyDialogInteractor.setStory(story);

        User storyUser = story.getUser();
        long storyUserId = storyUser.getId();

        if (user == null) {
            storyDialogInteractor.setMessagesAdd("복사");
            storyDialogInteractor.setMessagesAdd("신고");
            storyDialogInteractor.setMessagesAdd("취소");
            ArrayList<String> messages = storyDialogInteractor.getMessages();
            storyDialogView.setStoryDialogToOthersAdapterItem(messages);
        } else {
            long userId = user.getId();
            String accessToken = user.getAccessToken();
            storyDialogInteractor.setStoryRepository(accessToken);
            storyDialogInteractor.setReportRepository(accessToken);

            if (storyUserId != userId) {
                storyDialogInteractor.setMessagesAdd("복사");
                storyDialogInteractor.setMessagesAdd("신고");
                storyDialogInteractor.setMessagesAdd("취소");
                ArrayList<String> messages = storyDialogInteractor.getMessages();
                storyDialogView.setStoryDialogToOthersAdapterItem(messages);
            } else {
                storyDialogInteractor.setMessagesAdd("복사");
                storyDialogInteractor.setMessagesAdd("삭제");
                storyDialogInteractor.setMessagesAdd("수정");
                storyDialogInteractor.setMessagesAdd("취소");
                ArrayList<String> messages = storyDialogInteractor.getMessages();
                storyDialogView.setStoryDialogToAuthorAdapterItem(messages);
            }
        }


    }

    @Override
    public void onClickStoryDialogToAuthorLayout(int position) {
        Story story = storyDialogInteractor.getStory();

        if (position == 0) {
            String content = story.getContent();
            storyDialogView.setContentCopied(content);
            storyDialogView.showMessage("클립보드에 복사되었습니다.");
            storyDialogView.navigateToBack();
        }
        if (position == 1) {
            storyDialogView.showProgressDialog();
            storyDialogInteractor.deleteStory(story);
        }
        if (position == 2) {
            storyDialogView.navigateToStoryEditActivity(story);
        }

        if (position == 3) {
            storyDialogView.navigateToBack();
        }

    }
    @Override
    public void onSuccessDeleteStory() {
        storyDialogView.goneProgressDialog();
        int position  = storyDialogInteractor.getPosition();
        storyDialogView.navigateToBackWithResultDelete(position);
    }

    @Override
    public void onActivityResultForStoryEditResultOk(Story story) {
        int position = storyDialogInteractor.getPosition();
        storyDialogView.navigateToBackWithResultEdit(story, position);
    }

    @Override
    public void onSuccessSetReport() {
        storyDialogView.showMessage("신고가 접수되었습니다.");
        storyDialogView.navigateToBack();
    }

    @Override
    public void onClickStoryDialogToOthersLayout(int position) {
        Story story = storyDialogInteractor.getStory();
        User user = storyDialogInteractor.getUser();

        if (position == 0) {
            String content = story.getContent();
            storyDialogView.setContentCopied(content);
            storyDialogView.showMessage("클립보드에 복사되었습니다.");
            storyDialogView.navigateToBack();
        }

        if (position == 1) {
            if (user == null) {
                storyDialogView.showMessage("로그인이 필요한 서비스입니다.");
                storyDialogView.navigateToLoginActivity();
            } else {
                long storyId = story.getId();
                Report report = new Report();
                report.setContentCategoryId(ReportFlag.STORY_CONTENT);
                report.setUser(user);
                report.setContentId(storyId);
                storyDialogInteractor.setReport(report);
            }
        }

        if (position == 2) { //취소
            storyDialogView.navigateToBack();
        }
    }


    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            storyDialogView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            storyDialogView.showMessage(httpErrorDto.message());
        }
    }

}
