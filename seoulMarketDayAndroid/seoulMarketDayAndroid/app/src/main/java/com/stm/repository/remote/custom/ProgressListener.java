package com.stm.repository.remote.custom;

/**
 * Created by Dev-0 on 2017-07-17.
 */

public interface ProgressListener {


    void onStartProgress(long totalSize);

    void onDestroyProgress();

    void onUpdateProgress(long bytesRead);
}
