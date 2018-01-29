package com.stm.market.fragment.video.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Report;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.market.fragment.video.interactor.MarketVideoInteractor;
import com.stm.market.fragment.video.presenter.MarketVideoPresenter;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketVideoInteractorImpl implements MarketVideoInteractor {
    private MarketVideoPresenter marketVideoPresenter;
    private List<File> files;
    private User user;
    private Market market;
    private MarketRepository marketRepository;
    private FileRepository fileRepository;
    private static final Logger logger = LoggerFactory.getLogger(MarketVideoInteractorImpl.class);

    public MarketVideoInteractorImpl(MarketVideoPresenter marketVideoPresenter) {
        this.marketVideoPresenter = marketVideoPresenter;
        this.files = new ArrayList<>();
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
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Market getMarket() {
        return market;
    }

    @Override
    public void setMarket(Market market) {
        this.market = market;
    }

    @Override
    public void setMarketRepository() {
        this.marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketRepository(String accessToken) {
        this.marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setFileRepository() {
        this.fileRepository = new NetworkInterceptor().getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setFileRepository(String accessToken) {
        this.fileRepository = new NetworkInterceptor(accessToken).getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setFilesAddAll(List<File> newFiles) {
        this.files.addAll(newFiles);
    }

    @Override
    public void getFileListByIdAndTypeAndOffset(long id, int type, long offset) {
        Call<List<File>> callFindFileListByIdAndTypeAndOffsetApi = marketRepository.findFileListByIdAndTypeAndOffset(id, type, offset);
        callFindFileListByIdAndTypeAndOffsetApi.enqueue(new Callback<List<File>>() {
            @Override
            public void onResponse(Call<List<File>> call, Response<List<File>> response) {
                if (response.isSuccessful()) {
                    List<File> files = response.body();
                    marketVideoPresenter.onSuccessGetFileListByIdAndTypeAndOffset(files);
                } else {
                    marketVideoPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<File>> call, Throwable t) {
                log(t);
                marketVideoPresenter.onNetworkError(null);
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
                    marketVideoPresenter.onSuccessUpdateFileByHits(position);
                } else {
                    marketVideoPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketVideoPresenter.onNetworkError(null);
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
