package com.stm.story.edit.interactor.impl;

import android.util.Log;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.StoryDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.custom.ProgressListener;
import com.stm.repository.remote.custom.ProgressRequestBody;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.story.edit.interactor.StoryEditInteractor;
import com.stm.story.edit.presenter.StoryEditPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class StoryEditInteractorImpl implements StoryEditInteractor, ProgressListener {
    private StoryEditPresenter storyEditPresenter;
    private User user;
    private Story story;
    private ArrayList<FileDto> fileDtos;
    private List<File> removeFiles;
    private boolean isEdited;
    private int prevFileSize;
    private StoryRepository storyRepository;

    private static final Logger logger = LoggerFactory.getLogger(StoryEditInteractorImpl.class);

    public StoryEditInteractorImpl(StoryEditPresenter storyEditPresenter) {
        this.storyEditPresenter = storyEditPresenter;
        this.removeFiles = new ArrayList<>();
        this.fileDtos = new ArrayList<>();
        this.isEdited = false;
    }

    @Override
    public void setStoryRepository(String accessToken) {
        storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public boolean isEdited() {
        return isEdited;
    }

    @Override
    public void setEdited(boolean edited) {
        isEdited = edited;
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
    public ArrayList<FileDto> getFileDtos() {
        return fileDtos;
    }

    @Override
    public void setFileDtos(ArrayList<FileDto> fileDtos) {
        this.fileDtos = fileDtos;
    }

    @Override
    public void setRemoveFilesAdd(File file) {
        this.removeFiles.add(file);
    }

    @Override
    public void setFilesAdd(FileDto fileDto) {
        this.fileDtos.add(fileDto);
    }

    @Override
    public void setFileDtosAddAll(ArrayList<FileDto> fileDtos) {
        this.fileDtos.addAll(fileDtos);
    }

    @Override
    public void setFileDtosAdd(FileDto fileDto) {
        this.fileDtos.add(fileDto);
    }

    @Override
    public void setFileDtosRemoveAtThePosition(int position) {
        this.fileDtos.remove(position);
    }

    @Override
    public List<File> getRemoveFiles() {
        return removeFiles;
    }

    @Override
    public void setRemoveFiles(List<File> removeFiles) {
        this.removeFiles = removeFiles;
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

    @Override
    public int getPrevFileSize() {
        return prevFileSize;
    }

    @Override
    public void setPrevFileSize(int prevFileSize) {
        this.prevFileSize = prevFileSize;
    }

    @Override
    public void getStoryByUploadingStoryDtoAndFileDtos(StoryDto storyDto, ArrayList<FileDto> fIleDtos) {
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (FileDto fileDto : fIleDtos) {
            int fileType = fileDto.getType();
            java.io.File file = fileDto.getFile();

            String contentType = null;
            if (fileType == DefaultFileFlag.PHOTO_TYPE) {
                contentType = DefaultFileFlag.PHOTO_CONTENT_TYPE;
                parts.add(convertFileToMultipartBodyPartByFileAndContentType(file, contentType, this));
            }

            if (fileType == DefaultFileFlag.VIDEO_TYPE) {
                java.io.File thumbnail = fileDto.getThumbnail();
                String fileName = System.currentTimeMillis() + "";
                parts.add(convertFileToMultipartBodyPartByFileAndContentTypeAndFileName(file, DefaultFileFlag.VIDEO_CONTENT_TYPE, fileName, this));
                parts.add(convertFileToMultipartBodyPartByFileAndContentTypeAndFileName(thumbnail, DefaultFileFlag.VIDEO_THUMBNAIL_CONTENT_TYPE, fileName, this));
            }

            if (fileType == DefaultFileFlag.VR360_TYPE) {
                contentType = DefaultFileFlag.VR360_CONTENT_TYPE;
                parts.add(convertFileToMultipartBodyPartByFileAndContentType(file, contentType, this));
            }
        }
        Story story = storyDto.getStory();
        long storyId = story.getId();

        Call<Story> callGetStoryByUploadingStoryDtoAndFileDtosApi = storyRepository.updateStory(storyId, storyDto, parts);
        callGetStoryByUploadingStoryDtoAndFileDtosApi.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    Story story = response.body();
                    storyEditPresenter.onSuccessGetStoryByUploadingStoryDtoAndFileDtos(story);
                } else {
                    try {
                        Log.e("ㅇㅇㅇㅇ", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    storyEditPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                    storyEditPresenter.onErrorGetStoryByUploadingStoryDto();
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                log(t);
                t.printStackTrace();
                storyEditPresenter.onNetworkError(null);
                storyEditPresenter.onErrorGetStoryByUploadingStoryDto();
            }
        });
    }

    @Override
    public void getStoryByUploadingStoryDto(StoryDto storyDto) {
        Story story = storyDto.getStory();
        long storyId = story.getId();

        Call<Story> callGetStoryByUploadingStoryDtoApi = storyRepository.updateStory(storyId, storyDto);
        callGetStoryByUploadingStoryDtoApi.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    Story story = response.body();
                    storyEditPresenter.onSuccessGetStoryByUploadingStoryDto(story);
                } else {
                    storyEditPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                    storyEditPresenter.onErrorGetStoryByUploadingStoryDto();
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                log(t);
                storyEditPresenter.onNetworkError(null);
                storyEditPresenter.onErrorGetStoryByUploadingStoryDto();
            }
        });
    }

    public static MultipartBody.Part convertFileToMultipartBodyPartByFileAndContentType(java.io.File file, String contentType, ProgressListener progressListener) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, contentType, progressListener);
        return MultipartBody.Part.createFormData("files", file.getName(), progressRequestBody);
    }

    public static MultipartBody.Part convertFileToMultipartBodyPartByFileAndContentTypeAndFileName(java.io.File file, String contentType, String fileName, ProgressListener progressListener) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, contentType, progressListener);
        return MultipartBody.Part.createFormData("files", fileName, progressRequestBody);
    }

    @Override
    public void onStartProgress(long totalSize) {
        storyEditPresenter.onStartProgress(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        storyEditPresenter.onDestroyProgress();
    }

    @Override
    public void onUpdateProgress(long bytesRead) {
        storyEditPresenter.onUpdateProgress(bytesRead);
    }
}
