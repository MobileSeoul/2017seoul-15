package com.stm.app.info.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stm.R;
import com.stm.app.info.presenter.AppInfoPresenter;
import com.stm.app.info.presenter.impl.AppInfoPresenterImpl;
import com.stm.app.info.view.AppInfoView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-09-01.
 */

public class AppInfoActivity extends Activity implements AppInfoView {
    private AppInfoPresenter appInfoPresenter;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindString(R.string.app_version)
    String appVersion;

    @BindString(R.string.app_developer_email)
    String appDeveloperEmail;

    @BindView(R.id.tv_appinfo_developeremail)
    TextView tv_appinfo_developeremail;

    @BindView(R.id.tv_appinfo_version)
    TextView tv_appinfo_version;

    @BindView(R.id.in_appinfo_toolbar)
    View in_appinfo_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        ButterKnife.bind(this);

        this.appInfoPresenter = new AppInfoPresenterImpl(this);
        this.appInfoPresenter.init();
    }

    @Override
    public void showAppVersion() {
        tv_appinfo_version.setText(appVersion);
    }

    @Override
    public void showAppDeveloperEmail() {
        tv_appinfo_developeremail.setText(appDeveloperEmail);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }


    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_appinfo_toolbar);
    }

    @OnClick(R.id.ib_toolbar_back)
    @Override
    public void onClickBack() {
        appInfoPresenter.onClickBack();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

    }

}
