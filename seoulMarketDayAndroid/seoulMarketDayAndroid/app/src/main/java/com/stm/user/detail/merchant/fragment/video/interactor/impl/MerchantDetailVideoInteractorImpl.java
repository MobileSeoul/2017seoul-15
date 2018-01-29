package com.stm.user.detail.merchant.fragment.video.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.detail.merchant.fragment.video.interactor.MerchantDetailVideoInteractor;
import com.stm.user.detail.merchant.fragment.video.presenter.MerchantDetailVideoPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public class MerchantDetailVideoInteractorImpl implements MerchantDetailVideoInteractor {
    private MerchantDetailVideoPresenter merchantDetailVideoPresenter;
    private List<File> files;
    private User user;
    private User storyUser;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private static final Logger logger = LoggerFactory.getLogger(MerchantDetailVideoInteractorImpl.class);

    public MerchantDetailVideoInteractorImpl(MerchantDetailVideoPresenter merchantDetailVideoPresenter) {
        this.merchantDetailVideoPresenter = merchantDetailVideoPresenter;
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
    public void setFilesAddAll(List<File> newFiles) {
        this.files.addAll(newFiles);
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
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void getFileListByIdAndTypeAndOffset(long storyUserId, final int type, long offset) {
        Call<List<File>> callFindStoryListByStoryUserIdAndUserIdAndOffsetApi = userRepository.findFileListByIdAndTypeAndOffset(storyUserId, type, offset);
        callFindStoryListByStoryUserIdAndUserIdAndOffsetApi.enqueue(new Callback<List<File>>() {
            @Override
            public void onResponse(Call<List<File>> call, Response<List<File>> response) {
                if (response.isSuccessful()) {
                    List<File> files = response.body();
                    merchantDetailVideoPresenter.onSuccessGetFileListByIdAndTypeAndOffset(files);
                } else {
                    merchantDetailVideoPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<File>> call, Throwable t) {
                log(t);
                merchantDetailVideoPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateFileByHits(File file, final int position) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHitsApi = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHitsApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailVideoPresenter.onSuccessUpdateFileByHits(position);
                } else {
                    merchantDetailVideoPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailVideoPresenter.onNetworkError(null);
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
