package com.stm.media.video.player.interactor.impl;

import com.stm.common.flag.LogFlag;
import com.stm.media.video.player.interactor.VideoPlayerInteractor;
import com.stm.media.video.player.presenter.VideoPlayerPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-07-14.
 */

public class VideoPlayerInteractorImpl implements VideoPlayerInteractor {
    private VideoPlayerPresenter videoPlayerPresenter;

    private static final Logger logger = LoggerFactory.getLogger(VideoPlayerInteractorImpl.class);

    public VideoPlayerInteractorImpl(VideoPlayerPresenter videoPlayerPresenter) {
        this.videoPlayerPresenter = videoPlayerPresenter;
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
