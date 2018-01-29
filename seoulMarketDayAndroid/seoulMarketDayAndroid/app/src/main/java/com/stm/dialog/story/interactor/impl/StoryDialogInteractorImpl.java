package com.stm.dialog.story.interactor.impl;

import com.stm.common.dao.Report;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.dialog.story.interactor.StoryDialogInteractor;
import com.stm.dialog.story.presenter.StoryDialogPresenter;
import com.stm.repository.remote.ReportRepository;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class StoryDialogInteractorImpl implements StoryDialogInteractor {
    private StoryDialogPresenter storyDialogPresenter;
    private User user;
    private Story story;
    private int position;
    private ArrayList<String> messages;
    private StoryRepository storyRepository;
    private ReportRepository reportRepository;

    private static final Logger logger = LoggerFactory.getLogger(StoryDialogInteractorImpl.class);

    public StoryDialogInteractorImpl(StoryDialogPresenter storyDialogPresenter) {
        this.storyDialogPresenter = storyDialogPresenter;
        this.messages = new ArrayList<>();
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
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Story getStory() {
        return story;
    }

    @Override
    public void setStory(Story story) {
        this.story = story;
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
    public void setStoryRepository(String accessToken) {
        storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setReportRepository(String accessToken) {
        reportRepository = new NetworkInterceptor(accessToken).getRetrofitForReportRepository().create(ReportRepository.class);
    }

    @Override
    public void setReport(Report report) {
        Call<ResponseBody> callSetReportApi =  reportRepository.saveReport(report);
        callSetReportApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    storyDialogPresenter.onSuccessSetReport();
                } else {
                    storyDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                storyDialogPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteStory(Story story) {
        long storyId = story.getId();
        Call<ResponseBody> callDeleteStoryApi = storyRepository.deleteStory(storyId, story);
        callDeleteStoryApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    storyDialogPresenter.onSuccessDeleteStory();
                } else {
                    storyDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                storyDialogPresenter.onNetworkError(null);
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
