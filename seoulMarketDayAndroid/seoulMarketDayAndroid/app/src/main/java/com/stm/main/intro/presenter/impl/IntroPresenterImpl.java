package com.stm.main.intro.presenter.impl;

import com.stm.main.intro.interactor.IntroInteractor;
import com.stm.main.intro.interactor.impl.IntroInteractorImpl;
import com.stm.main.intro.presenter.IntroPresenter;
import com.stm.main.intro.view.IntroView;

/**
 * Created by Dev-0 on 2017-06-14.
 */

public class IntroPresenterImpl implements IntroPresenter {
    private IntroView introView;
    private IntroInteractor introInteractor;

    public IntroPresenterImpl(IntroView introView) {
        this.introView = introView;
        this.introInteractor = new IntroInteractorImpl(this);
    }

    @Override
    public void init() {
        introView.setFadeInAnimationListener();
        introView.setSplashFadeInAnimation();
        introView.showSplash();
    }

    @Override
    public void onDestroySplash() {
        introView.navigateToMainActivity();
    }

    @Override
    public void onAnimationEnd() {
        introView.setSplashFadeOutAnimation();
    }
}
