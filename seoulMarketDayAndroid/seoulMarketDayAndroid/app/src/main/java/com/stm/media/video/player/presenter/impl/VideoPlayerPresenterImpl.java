package com.stm.media.video.player.presenter.impl;

import com.google.android.exoplayer2.ExoPlayer;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.VisibilityFlag;
import com.stm.media.video.player.interactor.VideoPlayerInteractor;
import com.stm.media.video.player.interactor.impl.VideoPlayerInteractorImpl;
import com.stm.media.video.player.presenter.VideoPlayerPresenter;
import com.stm.media.video.player.view.VideoPlayerView;

/**
 * Created by ㅇㅇ on 2017-07-14.
 */

public class VideoPlayerPresenterImpl implements VideoPlayerPresenter {
    private VideoPlayerView videoPlayerView;
    private VideoPlayerInteractor videoPlayerInteractor;

    public VideoPlayerPresenterImpl(VideoPlayerView videoPlayerView) {
        this.videoPlayerView = videoPlayerView;
        this.videoPlayerInteractor = new VideoPlayerInteractorImpl(this);
    }

    @Override
    public void init() {
        videoPlayerView.showProgressDialog();
        videoPlayerView.setExoPlayerControllerHideOnTouch(true);
        videoPlayerView.setExoPlayer();
        videoPlayerView.setExoPlayerPrepare();
        videoPlayerView.setAnimationsDuration(200);
        videoPlayerView.setExoPlayerAddListener();
    }

    @Override
    public void onBackPressed() {
        videoPlayerView.navigateToBack();
    }

    @Override
    public void onClickClose() {
        videoPlayerView.navigateToBack();
    }

    @Override
    public void onTouch(int visibility) {
        if (visibility == VisibilityFlag.VISIBLE) {
            videoPlayerView.showCloseButton();
            videoPlayerView.setControllerVisibleAnimationStart();
        }

        if (visibility == VisibilityFlag.GONE) {
            videoPlayerView.setControllerGoneAnimationStart();
            videoPlayerView.goneCloseButton();
        }
    }

    @Override
    public void onPlayerStateChanged(int playbackState) {
        if(playbackState == ExoPlayer.STATE_ENDED){
            videoPlayerView.setExoPlayerSeekTo(0);
            videoPlayerView.setExoPlayerPlayWhenReady(false);
        }
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        if(!isLoading){
            videoPlayerView.goneProgressDialog();
            videoPlayerView.setVideoPlayerClickable();
        }else {
            videoPlayerView.setVideoPlayerUnClickable();
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            videoPlayerView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            videoPlayerView.showMessage(httpErrorDto.message());
        }
    }
}
