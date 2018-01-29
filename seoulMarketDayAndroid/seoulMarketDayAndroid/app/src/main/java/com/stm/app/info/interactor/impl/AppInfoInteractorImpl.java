package com.stm.app.info.interactor.impl;

import com.stm.app.info.interactor.AppInfoInteractor;
import com.stm.app.info.presenter.AppInfoPresenter;

/**
 * Created by Dev-0 on 2017-09-01.
 */

public class AppInfoInteractorImpl implements AppInfoInteractor {
    private AppInfoPresenter appInfoPresenter;

    public AppInfoInteractorImpl(AppInfoPresenter appInfoPresenter) {
        this.appInfoPresenter = appInfoPresenter;
    }
}
