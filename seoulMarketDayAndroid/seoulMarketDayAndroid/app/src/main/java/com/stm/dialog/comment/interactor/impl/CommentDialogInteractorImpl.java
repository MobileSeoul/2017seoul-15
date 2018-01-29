package com.stm.dialog.comment.interactor.impl;

import com.stm.common.dao.Report;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.dialog.comment.interactor.CommentDialogInteractor;
import com.stm.dialog.comment.presenter.CommentDialogPresenter;
import com.stm.repository.remote.ReportRepository;
import com.stm.repository.remote.StoryCommentRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public class CommentDialogInteractorImpl implements CommentDialogInteractor {
    private CommentDialogPresenter commentDialogPresenter;
    private User user;
    private StoryComment storyComment;
    private ArrayList<String> messages;

    private int position;
    private StoryCommentRepository storyCommentRepository;
    private ReportRepository reportRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentDialogInteractorImpl.class);

    public CommentDialogInteractorImpl(CommentDialogPresenter commentDialogPresenter) {
        this.commentDialogPresenter = commentDialogPresenter;
        this.messages = new ArrayList<>();
    }

    @Override
    public void setStoryCommentRepository(String accessToken) {
        storyCommentRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryCommentRepository().create(StoryCommentRepository.class);
    }

    @Override
    public void setReportRepository(String accessToken) {
        reportRepository = new NetworkInterceptor(accessToken).getRetrofitForReportRepository().create(ReportRepository.class);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public StoryComment getStoryComment() {
        return storyComment;
    }

    @Override
    public void setStoryComment(StoryComment storyComment) {
        this.storyComment = storyComment;
    }

    @Override
    public ArrayList<String> getMessages() {
        return messages;
    }

    @Override
    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    @Override
    public void setMessagesAdd(String message) {
        messages.add(message);
    }

    @Override
    public void deleteStoryComment(StoryComment storyComment) {
        long storyCommentId = storyComment.getId();
        Call<ResponseBody> callDeleteStoryCommentApi = storyCommentRepository.deleteStoryComment(storyCommentId, storyComment);
        callDeleteStoryCommentApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    commentDialogPresenter.onSuccessDeleteStoryComment();
                } else {
                    commentDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                commentDialogPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setReport(Report report) {
        Call<ResponseBody> callSetReportApi =  reportRepository.saveReport(report);
        callSetReportApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    commentDialogPresenter.onSuccessSetReport();
                } else {
                    commentDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                commentDialogPresenter.onNetworkError(null);
            }
        });
    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }

}
