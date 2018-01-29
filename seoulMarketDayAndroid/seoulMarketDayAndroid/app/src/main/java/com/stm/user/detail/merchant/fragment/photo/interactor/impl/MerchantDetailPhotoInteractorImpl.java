package com.stm.user.detail.merchant.fragment.photo.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.detail.merchant.fragment.photo.interactor.MerchantDetailPhotoInteractor;
import com.stm.user.detail.merchant.fragment.photo.presenter.MerchantDetailPhotoPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public class MerchantDetailPhotoInteractorImpl implements MerchantDetailPhotoInteractor {
    private List<File> files;
    private MerchantDetailPhotoPresenter merchantDetailPhotoPresenter;
    private User user;
    private User storyUser;
    private UserRepository userRepository;

    private boolean isFirstChecked;
    private static final Logger logger = LoggerFactory.getLogger(MerchantDetailPhotoInteractorImpl.class);
    private FileRepository fileRepository;

    public MerchantDetailPhotoInteractorImpl(MerchantDetailPhotoPresenter merchantDetailPhotoPresenter) {
        this.merchantDetailPhotoPresenter = merchantDetailPhotoPresenter;
        this.isFirstChecked = false;
        this.files = new ArrayList<>();
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
    public User getStoryUser() {
        return storyUser;
    }

    @Override
    public void setStoryUser(User storyUser) {
        this.storyUser = storyUser;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public boolean isFirstChecked() {
        return isFirstChecked;
    }

    @Override
    public void setFirstChecked(boolean firstChecked) {
        isFirstChecked = firstChecked;
    }

    @Override
    public void setFilesAddAll(List<File> newFiles) {
        this.files.addAll(newFiles);
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
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
    public void updateFileByHits(File file, final int position) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHitsApi = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHitsApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailPhotoPresenter.onSuccessUpdateFileByHits(position);
                } else {
                    merchantDetailPhotoPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailPhotoPresenter.onNetworkError(null);
            }
        });

    }

    @Override
    public void getFileListByIdAndTypeAndOffset(long storyUserId, final int type, long offset) {
        Call<List<File>> callFindStoryListByStoryUserIdAndUserIdAndOffsetApi = userRepository.findFileListByIdAndTypeAndOffset(storyUserId, type, offset);
        callFindStoryListByStoryUserIdAndUserIdAndOffsetApi.enqueue(new Callback<List<File>>() {
            @Override
            public void onResponse(Call<List<File>> call, Response<List<File>> response) {
                if (response.isSuccessful()) {
                    List<File> files = response.body();
                    merchantDetailPhotoPresenter.onSuccessGetFileListByIdAndTypeAndOffset(files);
                } else {
                    merchantDetailPhotoPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<File>> call, Throwable t) {
                log(t);
                merchantDetailPhotoPresenter.onNetworkError(null);
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
