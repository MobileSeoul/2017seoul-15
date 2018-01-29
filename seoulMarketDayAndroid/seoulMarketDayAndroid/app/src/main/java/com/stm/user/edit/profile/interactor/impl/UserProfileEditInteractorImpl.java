package com.stm.user.edit.profile.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.custom.ProgressListener;
import com.stm.repository.remote.custom.ProgressRequestBody;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.edit.profile.interactor.UserProfileEditInteractor;
import com.stm.user.edit.profile.presenter.UserProfileEditPresenter;

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
 * Created by Dev-0 on 2017-08-22.
 */

public class UserProfileEditInteractorImpl implements UserProfileEditInteractor, ProgressListener {
    private UserProfileEditPresenter userProfileEditPresenter;
    private User user;
    private boolean isFirstChecked;

    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserProfileEditInteractorImpl.class);


    public UserProfileEditInteractorImpl(UserProfileEditPresenter userProfileEditPresenter) {
        this.userProfileEditPresenter = userProfileEditPresenter;
        this.isFirstChecked = false;
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
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
    public void updateUser(User user, final FileDto fileDto) {
        long userId = user.getId();
        List<MultipartBody.Part> parts = new ArrayList<>();

        int fileType = fileDto.getType();
        File file = fileDto.getFile();

        String contentType = null;
        if (fileType == DefaultFileFlag.USER_AVATAR_TYPE) {
            contentType = DefaultFileFlag.USER_AVATAR_CONTENT_TYPE;
        }

        if (fileType == DefaultFileFlag.USER_COVER_TYPE) {
            contentType = DefaultFileFlag.USER_COVER_CONTENT_TYPE;
        }

        parts.add(convertFileToMultipartBodyPartByFileAndContentType(file, contentType, this));

        Call<User> callUpdateUserApi = userRepository.updateUser(userId, user, parts);
        callUpdateUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    userProfileEditPresenter.onSuccessUpdateUserByFileDto(user);
                } else {
                    userProfileEditPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                userProfileEditPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateUser(User user) {
        long userId = user.getId();
        Call<User> callUpdateUserApi = userRepository.updateUser(userId, user);
        callUpdateUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    userProfileEditPresenter.onSuccessUpdateUser(user);
                } else {
                    userProfileEditPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                userProfileEditPresenter.onNetworkError(null);
            }
        });
    }



    public static MultipartBody.Part convertFileToMultipartBodyPartByFileAndContentType(File file, String contentType, ProgressListener progressListener) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, contentType, progressListener);
        return MultipartBody.Part.createFormData("files", file.getName(), progressRequestBody);
    }

    @Override
    public void onStartProgress(long totalSize) {

    }

    @Override
    public void onDestroyProgress() {

    }

    @Override
    public void onUpdateProgress(long bytesRead) {

    }

    @Override
    public boolean isFirstChecked() {
        return isFirstChecked;
    }

    @Override
    public void setFirstChecked(boolean firstChecked) {
        isFirstChecked = firstChecked;
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
