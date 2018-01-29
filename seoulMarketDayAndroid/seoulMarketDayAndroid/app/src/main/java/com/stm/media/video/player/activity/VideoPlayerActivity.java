package com.stm.media.video.player.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.stm.R;
import com.stm.common.util.ToastUtil;
import com.stm.media.video.player.presenter.VideoPlayerPresenter;
import com.stm.media.video.player.presenter.impl.VideoPlayerPresenterImpl;
import com.stm.media.video.player.view.VideoPlayerView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-14.
 */

public class VideoPlayerActivity extends Activity implements VideoPlayerView, ExoPlayer.EventListener, PlaybackControlView.VisibilityListener {
    private VideoPlayerPresenter videoPlayerPresenter;
    private ToastUtil toastUtil;
    private Animation visibleAnimation;
    private Animation goneAnimation;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;
    private Handler handler;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector trackSelector;
    private SimpleExoPlayer simpleExoPlayer;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.fl_videoplayer)
    FrameLayout fl_videoplayer;

    @BindView(R.id.fl_videoplayer_close)
    FrameLayout fl_videoplayer_close;

    @BindString(R.string.app_name)
    String app_name;

    @BindView(R.id.sepv_videoplayer)
    SimpleExoPlayerView sepv_videoplayer;

    @BindView(R.id.exo_play)
    ImageView exo_play;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_videoplayer);
        getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        ButterKnife.bind(this);

        String uri = getIntent().getStringExtra("uri");
        this.toastUtil = new ToastUtil(this);
        this.visibleAnimation = new AlphaAnimation(0, 1);
        this.goneAnimation = new AlphaAnimation(1, 0);

        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        this.handler = new Handler();
        this.defaultBandwidthMeter = new DefaultBandwidthMeter();
        this.trackSelectionFactory = new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
        this.trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        this.simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        this.dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, app_name), defaultBandwidthMeter);
        this.extractorsFactory = new DefaultExtractorsFactory();
        this.mediaSource = new ExtractorMediaSource(Uri.parse(uri), dataSourceFactory, extractorsFactory, null, null);

        this.videoPlayerPresenter = new VideoPlayerPresenterImpl(this);
        this.videoPlayerPresenter.init();
    }

    @Override
    public void setExoPlayer() {
        sepv_videoplayer.setPlayer(simpleExoPlayer);
    }


    @Override
    public void setExoPlayerPrepare() {
        simpleExoPlayer.prepare(mediaSource);
    }

    @Override
    public void setExoPlayerAddListener() {
        simpleExoPlayer.addListener(this);
    }

    @Override
    public void setAnimationsDuration(long duration) {
        visibleAnimation.setDuration(duration);
        goneAnimation.setDuration(duration);
    }

    @Override
    public void setExoPlayerControllerHideOnTouch(boolean controllerHideOnTouch) {
        sepv_videoplayer.setControllerHideOnTouch(controllerHideOnTouch);
        sepv_videoplayer.setControllerVisibilityListener(this);
    }

    @Override
    public void showCloseButton() {
        fl_videoplayer_close.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneCloseButton() {
        fl_videoplayer_close.setVisibility(View.GONE);
    }

    @Override
    public void setControllerClearAnimation() {
        fl_videoplayer_close.clearAnimation();
    }

    @Override
    public void setControllerVisibleAnimationStart() {
        fl_videoplayer_close.startAnimation(visibleAnimation);
    }

    @Override
    public void setControllerGoneAnimationStart() {
        fl_videoplayer_close.startAnimation(goneAnimation);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    @OnClick(R.id.ib_videoplayer_close)
    public void onClickClose() {
        videoPlayerPresenter.onClickClose();
    }

    @Override
    public void onBackPressed() {
        videoPlayerPresenter.onBackPressed();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        videoPlayerPresenter.onLoadingChanged(isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        videoPlayerPresenter.onPlayerStateChanged(playbackState);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void setExoPlayerSeekTo(long positionMs) {
        simpleExoPlayer.seekTo(positionMs);
    }

    @Override
    public void setExoPlayerPlayWhenReady(boolean playWhenReady) {
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
    }


    @Override
    protected void onResume() {
        simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition());
        super.onResume();
    }

    @Override
    public void onVisibilityChange(int visibility) {
        videoPlayerPresenter.onTouch(visibility);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);

    }

    @Override
    public void goneProgressDialog() {
        progressDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 10);
    }

    @Override
    public void setVideoPlayerUnClickable(){
        exo_play.setClickable(false);
    }

    @Override
    public void setVideoPlayerClickable(){
        exo_play.setClickable(true);
    }
}

