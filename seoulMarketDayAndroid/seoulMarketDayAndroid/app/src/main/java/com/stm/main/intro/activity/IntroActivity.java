package com.stm.main.intro.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.stm.R;
import com.stm.main.base.activity.MainActivity;
import com.stm.main.intro.presenter.IntroPresenter;
import com.stm.main.intro.presenter.impl.IntroPresenterImpl;
import com.stm.main.intro.view.IntroView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dev-0 on 2017-06-14.
 */

public class IntroActivity extends Activity implements IntroView, Animation.AnimationListener {
    private IntroPresenter introPresenter;
    private Handler splashHandler;
    private Animation  fadeOutAnimation;
    private Animation  fadeInAnimation;

    @BindView(R.id.iv_intro_background)
    ImageView iv_intro_background;

    @BindView(R.id.iv_intro_logo)
    ImageView iv_intro_logo;

    @BindView(R.id.iv_intro_subtitle)
    ImageView iv_intro_subtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);

        this.splashHandler = new Handler();
        this.fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        this.fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        this.introPresenter = new IntroPresenterImpl(this);
        this.introPresenter.init();

    }

    @Override
    public void showSplash() {
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                introPresenter.onDestroySplash();
            }
        }, 2000);
    }

    @Override
    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setSplashFadeOutAnimation(){
        iv_intro_subtitle.startAnimation(fadeOutAnimation);
        iv_intro_logo.startAnimation(fadeOutAnimation);
        iv_intro_background.startAnimation(fadeOutAnimation);
    }

    @Override
    public void setSplashFadeInAnimation(){
        iv_intro_subtitle.startAnimation(fadeInAnimation);
        iv_intro_logo.startAnimation(fadeInAnimation);
        iv_intro_background.startAnimation(fadeInAnimation);
    }

    @Override
    public void setFadeInAnimationListener(){
        this.fadeInAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
