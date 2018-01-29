package com.stm.story.create.interactor.impl;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.custom.ProgressListener;
import com.stm.repository.remote.custom.ProgressRequestBody;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.story.create.interactor.StoryCreateInteractor;
import com.stm.story.create.presenter.StoryCreatePresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-11.
 */

public class StoryCreateInteractorImpl implements StoryCreateInteractor, ProgressListener {
    private StoryCreatePresenter storyCreatePresenter;
    private ArrayList<FileDto> fileDtos;
    private User user;

    private StoryRepository storyRepository;
    private static final Logger logger = LoggerFactory.getLogger(StoryCreateInteractorImpl.class);

    public StoryCreateInteractorImpl(StoryCreatePresenter storyCreatePresenter) {
        this.storyCreatePresenter = storyCreatePresenter;
        this.fileDtos = new ArrayList<>();
    }


    @Override
    public void setStoryRepository(String accessToken) {
        storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
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
    public void setFileDtosAdd(FileDto fileDto) {
        fileDtos.add(fileDto);
    }

    @Override
    public void setFileDtosAddAll(ArrayList<FileDto> fileDtos) {
        this.fileDtos.addAll(fileDtos);
    }

    @Override
    public ArrayList<FileDto> getFileDtos() {
        return fileDtos;
    }

    @Override
    public void setFileDtos(ArrayList<FileDto> fileDtos) {
        this.fileDtos = fileDtos;
    }

    @Override
    public void getStoryIdByUploadingStoryAndFileDtos(final Story story, ArrayList<FileDto> fIleDtos) {
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (FileDto fileDto : fIleDtos) {
            int fileType = fileDto.getType();
            File file = fileDto.getFile();

            String contentType = null;
            if (fileType == DefaultFileFlag.PHOTO_TYPE) {
                contentType = DefaultFileFlag.PHOTO_CONTENT_TYPE;
                parts.add(convertFileToMultipartBodyPartByFileAndContentType(file, contentType, this));
            }

            if (fileType == DefaultFileFlag.VIDEO_TYPE) {
                File thumbnail = fileDto.getThumbnail();
                String fileName = System.currentTimeMillis() + "";
                parts.add(convertFileToMultipartBodyPartByFileAndContentTypeAndFileName(file, DefaultFileFlag.VIDEO_CONTENT_TYPE, fileName, this));
                parts.add(convertFileToMultipartBodyPartByFileAndContentTypeAndFileName(thumbnail, DefaultFileFlag.VIDEO_THUMBNAIL_CONTENT_TYPE, fileName, this));
            }

            if (fileType == DefaultFileFlag.VR360_TYPE) {
                contentType = DefaultFileFlag.VR360_CONTENT_TYPE;
                parts.add(convertFileToMultipartBodyPartByFileAndContentType(file, contentType, this));
            }

        }

        Call<Integer> callSetStoryApi = storyRepository.saveStory(story, parts);
        callSetStoryApi.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    long storyId = response.body();
                    storyCreatePresenter.onSuccessGetStoryIdByUploadingStoryAndFileDtos(storyId);
                } else {
                    storyCreatePresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                    storyCreatePresenter.onErrorGetStoryIdByUploadingStory();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                log(t);
                storyCreatePresenter.onNetworkError(null);
                storyCreatePresenter.onErrorGetStoryIdByUploadingStory();
            }
        });
    }

    @Override
    public void getStoryIdByUploadingStory(Story story) {
        Call<Integer> callSetStoryApi = storyRepository.saveStory(story);
        callSetStoryApi.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    long storyId = response.body();
                    storyCreatePresenter.onSuccessGetStoryIdByUploadingStory(storyId);
                } else {
                    storyCreatePresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                log(t);
                storyCreatePresenter.onNetworkError(null);
            }
        });
    }

    public static MultipartBody.Part convertFileToMultipartBodyPartByFileAndContentType(File file, String contentType, ProgressListener progressListener) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, contentType, progressListener);
        return MultipartBody.Part.createFormData("files", file.getName(), progressRequestBody);
    }

    public static MultipartBody.Part convertFileToMultipartBodyPartByFileAndContentTypeAndFileName(File file, String contentType, String fileName, ProgressListener progressListener) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, contentType, progressListener);
        return MultipartBody.Part.createFormData("files", fileName, progressRequestBody);
    }


    @Override
    public void onStartProgress(long totalSize) {
        storyCreatePresenter.onStartProgress(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        storyCreatePresenter.onDestroyProgress();
    }

    @Override
    public void onUpdateProgress(long bytesRead) {
        storyCreatePresenter.onUpdateProgress(bytesRead);
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
