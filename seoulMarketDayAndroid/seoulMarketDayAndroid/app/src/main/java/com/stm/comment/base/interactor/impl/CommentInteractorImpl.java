package com.stm.comment.base.interactor.impl;

import com.stm.comment.base.interactor.CommentInteractor;
import com.stm.comment.base.presenter.CommentPresenter;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.StoryCommentRepository;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.custom.ProgressListener;
import com.stm.repository.remote.custom.ProgressRequestBody;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public class CommentInteractorImpl implements CommentInteractor, ProgressListener {
    private CommentPresenter commentPresenter;
    private User user;
    private Story story;
    private int position;
    private int commentCountAdded;
    private List<StoryComment> storyComments;
    private StoryRepository storyRepository;
    private FileRepository fileRepository;
    private StoryCommentRepository storyCommentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentInteractorImpl.class);


    public CommentInteractorImpl(CommentPresenter commentPresenter) {
        this.commentPresenter = commentPresenter;
        this.storyComments = new ArrayList<>();
        this.commentCountAdded = 0;
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
    public List<StoryComment> getStoryComments() {
        return storyComments;
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
    public void setStoryComments(List<StoryComment> storyComments) {
        this.storyComments = storyComments;
    }

    @Override
    public void setStoryCommentRepository() {
        storyCommentRepository = new NetworkInterceptor().getRetrofitForStoryCommentRepository().create(StoryCommentRepository.class);
    }

    @Override
    public void setStoryCommentRepository(String accessToken) {
        storyCommentRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryCommentRepository().create(StoryCommentRepository.class);
    }

    @Override
    public void setStoryRepository() {
        storyRepository = new NetworkInterceptor().getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setStoryRepository(String accessToken) {
        storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
    }


    @Override
    public void setFileRepository(String accessToken) {
        fileRepository = new NetworkInterceptor(accessToken).getRetrofitForFileRepository().create(FileRepository.class);
    }


    @Override
    public void setFileRepository() {
        fileRepository = new NetworkInterceptor().getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void getStoryCommentListByIdAndOffset(long id, int offset) {
        Call<List<StoryComment>> callGetStoryCommentListByIdAndOffsetApi = storyRepository.findStoryCommentListById(id, offset);
        callGetStoryCommentListByIdAndOffsetApi.enqueue(new Callback<List<StoryComment>>() {
            @Override
            public void onResponse(Call<List<StoryComment>> call, Response<List<StoryComment>> response) {
                if (response.isSuccessful()) {
                    List<StoryComment> storyComments = response.body();
                    commentPresenter.onSuccessGetStoryCommentListById(storyComments);
                } else {
                    commentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<StoryComment>> call, Throwable t) {
                log(t);
                commentPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getStoryCommentListByIdAndUserIdAndOffset(long id, long userId, int offset) {
        Call<List<StoryComment>> callGetStoryCommentListByIdApi = storyRepository.findStoryCommentListById(id, userId, offset);
        callGetStoryCommentListByIdApi.enqueue(new Callback<List<StoryComment>>() {
            @Override
            public void onResponse(Call<List<StoryComment>> call, Response<List<StoryComment>> response) {
                if (response.isSuccessful()) {
                    List<StoryComment> storyComments = response.body();
                    commentPresenter.onSuccessGetStoryCommentListById(storyComments);
                } else {
                    commentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<StoryComment>> call, Throwable t) {
                log(t);
                commentPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setStoryCommentsAddAll(List<StoryComment> newStoryComments) {
        this.storyComments.addAll(newStoryComments);
    }

    @Override
    public void setStoryCommentsAddAtThePosition(StoryComment newStoryComment, int position) {
        this.storyComments.add(position, newStoryComment);
    }

    @Override
    public void setStoryCommentsRemoveAtThePosition(int position) {
        this.storyComments.remove(position);
    }

    @Override
    public void getStoryCommentByUploadingStoryCommentAndFileDto(StoryComment storyComment, FileDto fileDto) {
        List<MultipartBody.Part> parts = new ArrayList<>();

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

        Call<StoryComment> callGetStoryCommentByUploadingStoryCommentAndFileDtoApi = storyCommentRepository.saveStoryComment(storyComment, parts);
        callGetStoryCommentByUploadingStoryCommentAndFileDtoApi.enqueue(new Callback<StoryComment>() {
            @Override
            public void onResponse(Call<StoryComment> call, Response<StoryComment> response) {
                if (response.isSuccessful()) {
                    StoryComment storyComment = response.body();
                    commentPresenter.onSuccessGetStoryCommentByUploadingStoryCommentAndFileDto(storyComment);
                } else {
                    commentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<StoryComment> call, Throwable t) {
                log(t);
                commentPresenter.onNetworkError(null);
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
    public void getStoryCommentByUploadingStoryComment(StoryComment storyComment) {
        Call<StoryComment> callGetStoryCommentByUploadingStoryCommentApi = storyCommentRepository.saveStoryComment(storyComment);
        callGetStoryCommentByUploadingStoryCommentApi.enqueue(new Callback<StoryComment>() {
            @Override
            public void onResponse(Call<StoryComment> call, Response<StoryComment> response) {
                if (response.isSuccessful()) {
                    StoryComment storyComment = response.body();
                    commentPresenter.onSuccessGetStoryCommentByUploadingStoryComment(storyComment);
                } else {
                    commentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<StoryComment> call, Throwable t) {
                log(t);
                commentPresenter.onNetworkError(null);
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

    @Override
    public void onStartProgress(long totalSize) {
        commentPresenter.onStartProgress(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        commentPresenter.onDestroyProgress();
    }

    @Override
    public void onUpdateProgress(long bytesRead) {
        commentPresenter.onUpdateProgress(bytesRead);
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
    public int getCommentCountAdded() {
        return commentCountAdded;
    }

    @Override
    public void setCommentCountAddedPlusCount(int count) {
        this.commentCountAdded += count;
    }

    @Override
    public void setCommentCountAddedMinusCount(int count) {
        this.commentCountAdded -= count;
    }

    @Override
    public void updateFileByHits(final com.stm.common.dao.File file) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHitsApi = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHitsApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    commentPresenter.onSuccessUpdateFileByHits(file);
                } else {
                    commentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                commentPresenter.onNetworkError(null);
            }
        });
    }
}
