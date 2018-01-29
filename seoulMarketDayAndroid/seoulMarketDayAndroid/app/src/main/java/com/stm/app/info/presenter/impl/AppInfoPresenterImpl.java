package com.stm.app.info.presenter.impl;

import com.stm.app.info.interactor.AppInfoInteractor;
import com.stm.app.info.interactor.impl.AppInfoInteractorImpl;
import com.stm.app.info.presenter.AppInfoPresenter;
import com.stm.app.info.view.AppInfoView;

/**
 * Created by Dev-0 on 2017-09-01.
 */

public class AppInfoPresenterImpl implements AppInfoPresenter {
    private AppInfoView appInfoView;
    private AppInfoInteractor appInfoInteractor;
    public AppInfoPresenterImpl(AppInfoView appInfoView) {
        this.appInfoView =appInfoView;
        this.appInfoInteractor = new AppInfoInteractorImpl(this);
    }

    @Override
    public void init() {
        appInfoView.setToolbarLayout();
        appInfoView.showToolbarTitle("프로그램 정보");
        appInfoView.showAppDeveloperEmail();
        appInfoView.showAppVersion();
    }

    @Override
    public void onClickBack() {
        appInfoView.navigateToBack();
    }
}
