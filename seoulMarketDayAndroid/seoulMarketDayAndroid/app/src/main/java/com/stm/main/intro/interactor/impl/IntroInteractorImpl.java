package com.stm.main.intro.interactor.impl;

import com.stm.main.intro.interactor.IntroInteractor;
import com.stm.main.intro.presenter.IntroPresenter;
import com.stm.main.intro.presenter.impl.IntroPresenterImpl;

/**
 * Created by Dev-0 on 2017-06-14.
 */

public class IntroInteractorImpl implements IntroInteractor {
    private IntroPresenter introPresenter;

    public IntroInteractorImpl(IntroPresenterImpl introPresenter) {
        this.introPresenter = introPresenter;
    }
}
