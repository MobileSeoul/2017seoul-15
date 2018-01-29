package com.stm.app.info.view;

/**
 * Created by Dev-0 on 2017-09-01.
 */

public interface AppInfoView {
    void showAppVersion();

    void showAppDeveloperEmail();

    void showToolbarTitle(String message);

    void setToolbarLayout();

    void onClickBack();

    void navigateToBack();
}
