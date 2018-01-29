package com.stm.user.edit.account.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.edit.account.base.presenter.EditAccountPresenter;
import com.stm.user.edit.account.base.presenter.impl.EditAccountPresenterImpl;
import com.stm.user.edit.account.base.view.EditAccountView;
import com.stm.user.edit.account.exit.activity.ExitActivity;
import com.stm.user.edit.account.password.activity.ChangePasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public class EditAccountActivity extends Activity implements EditAccountView {
    private EditAccountPresenter editAccountPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_editaccount_toolbar)
    View in_editaccount_toolbar;

    @BindView(R.id.sw_editaccount_alarm)
    Switch sw_editaccount_alarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        boolean isAllowedForNotification = sharedPrefersManager.isAllowedForNotification();
        this.editAccountPresenter = new EditAccountPresenterImpl(this);
        this.editAccountPresenter.init(user, isAllowedForNotification);
    }

    @Override
    public void setIncludedToolbarLayout() {
        this.includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_editaccount_toolbar);
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
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
    public void setSwitchChecked(boolean checked) {
        sw_editaccount_alarm.setChecked(checked);
    }

    @Override
    @OnCheckedChanged(R.id.sw_editaccount_alarm)
    public void onNotificationCheckedChanged(boolean checked){
        editAccountPresenter.onNotificationCheckedChanged(checked);
    }

    @Override
    @OnClick(R.id.ll_editaccount_password)
    public void onClickChangePassword() {
        editAccountPresenter.onClickChangePassword();
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        editAccountPresenter.onClickBack();
    }

    @Override
    @OnClick(R.id.ll_editaccount_exit)
    public void onClickExit() {
        editAccountPresenter.onClickExit();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToChangePasswordActivity() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void setAllowedForNotification(boolean checked) {
        sharedPrefersManager.setAllowedForNotification(checked);
    }

    @Override
    public void navigateToExitActivity() {
        Intent intent = new Intent(this, ExitActivity.class);
        startActivity(intent);
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;
    }
}