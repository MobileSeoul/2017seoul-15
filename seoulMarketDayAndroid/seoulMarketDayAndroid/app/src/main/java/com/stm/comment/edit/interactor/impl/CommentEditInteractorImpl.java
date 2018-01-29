package com.stm.comment.edit.interactor.impl;

import android.util.Log;

import com.stm.comment.edit.interactor.CommentEditInteractor;
import com.stm.comment.edit.presenter.CommentEditPresenter;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.StoryCommentRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public class CommentEditInteractorImpl implements CommentEditInteractor {
    private CommentEditPresenter commentEditPresenter;
    private User user;
    private StoryComment storyComment;
    private int position;
    private StoryCommentRepository storyCommentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentEditInteractorImpl.class);


    public CommentEditInteractorImpl(CommentEditPresenter commentEditPresenter) {
        this.commentEditPresenter = commentEditPresenter;
    }

    @Override
    public void setStoryCommentRepository(String accessToken) {
        storyCommentRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryCommentRepository().create(StoryCommentRepository.class);
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
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void updateStoryComment(StoryComment storyComment) {
        long storyCommentId = storyComment.getId();
        Call<ResponseBody> callUpdateStoryCommentApi = storyCommentRepository.updateStoryComment(storyCommentId, storyComment);
        callUpdateStoryCommentApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    commentEditPresenter.onSuccessUpdateStoryComment();
                } else {
                    try {
                        Log.e("sdd",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    commentEditPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                commentEditPresenter.onNetworkError(null);
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
