package com.stm.media.video.player.view;

/**
 * Created by ㅇㅇ on 2017-07-14.
 */

public interface VideoPlayerView {

    void setExoPlayer();


    void setExoPlayerPrepare();

    void setExoPlayerAddListener();

    void setAnimationsDuration(long duration);

    void setExoPlayerControllerHideOnTouch(boolean controllerHideOnTouch);

    void showCloseButton();

    void goneCloseButton();

    void setControllerClearAnimation();

    void setControllerVisibleAnimationStart();

    void setControllerGoneAnimationStart();

    void showMessage(String message);


    void onClickClose();

    void navigateToBack();

    void setExoPlayerSeekTo(long positionMs);

    void setExoPlayerPlayWhenReady(boolean playWhenReady);

    void showProgressDialog();

    void goneProgressDialog();

    void setVideoPlayerUnClickable();

    void setVideoPlayerClickable();
}
