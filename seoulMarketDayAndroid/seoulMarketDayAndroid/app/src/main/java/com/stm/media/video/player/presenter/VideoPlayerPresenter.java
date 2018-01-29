package com.stm.media.video.player.presenter;


import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-07-14.
 */

public interface VideoPlayerPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init();

    void onBackPressed();

    void onClickClose();

    void onTouch(int visibility);

    void onPlayerStateChanged(int playbackState);

    void onLoadingChanged(boolean isLoading);
}
