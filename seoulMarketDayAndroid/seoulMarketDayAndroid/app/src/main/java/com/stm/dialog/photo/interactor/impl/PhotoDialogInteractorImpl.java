package com.stm.dialog.photo.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.dialog.photo.interactor.PhotoDialogInteractor;
import com.stm.dialog.photo.presenter.PhotoDialogPresenter;
import com.stm.repository.remote.FileDownloadRepository;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class PhotoDialogInteractorImpl implements PhotoDialogInteractor {
    private List<File> files;
    private User user;
    private File file;
    private int position;
    private String avatar;
    private String cover;
    private PhotoDialogPresenter photoDialogPresenter;
    private FileDownloadRepository fileDownloadRepository;
    private FileRepository fileRepository;

    private static final Logger logger = LoggerFactory.getLogger(PhotoDialogInteractorImpl.class);


    public PhotoDialogInteractorImpl(PhotoDialogPresenter photoDialogPresenter) {
        this.photoDialogPresenter = photoDialogPresenter;
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
    public void setFileRepository(String accessToken) {
        fileRepository = new NetworkInterceptor(accessToken).getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setFileRepository() {
        fileRepository = new NetworkInterceptor().getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setStoryFileDownloadRepository() {
        fileDownloadRepository = new NetworkInterceptor().getRetrofitForStoryFileDownloadRepository().create(FileDownloadRepository.class);
    }

    @Override
    public void setCommentFileDownloadRepository() {
        fileDownloadRepository = new NetworkInterceptor().getRetrofitForCommentFileDownloadRepository().create(FileDownloadRepository.class);
    }

    @Override
    public void setCoverFileDownloadRepository() {
        fileDownloadRepository = new NetworkInterceptor().getRetrofitForCoverFileDownloadRepository().create(FileDownloadRepository.class);
    }

    @Override
    public void setAvatarFileDownloadRepository() {
        fileDownloadRepository = new NetworkInterceptor().getRetrofitForAvatarFileDownloadRepository().create(FileDownloadRepository.class);
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
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
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
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getCover() {
        return cover;
    }

    @Override
    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public void setFileDownloaded(String url, final String filePath) {
        Call<ResponseBody> callDownloadFileByUrlApi = fileDownloadRepository.downloadFileByUrl(url);
        callDownloadFileByUrlApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    java.io.File file = new java.io.File(filePath);
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                        bos.write(response.body().bytes());
                        bos.flush();
                        bos.close();

                    } catch (IOException e) {
                        log(e);
                    }

                    photoDialogPresenter.onSuccessSetFileDownloaded(filePath);

                } else {
                    photoDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                photoDialogPresenter.onNetworkError(null);
            }
        });

    }

    @Override
    public void updateFileByHits(final File file) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHitsApi = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHitsApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    photoDialogPresenter.onSuccessUpdateFileByHits();
                } else {
                    photoDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                photoDialogPresenter.onNetworkError(null);
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
