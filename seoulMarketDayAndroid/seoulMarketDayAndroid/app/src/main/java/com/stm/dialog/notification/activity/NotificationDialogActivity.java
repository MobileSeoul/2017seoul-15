package com.stm.dialog.notification.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.notification.presenter.NotificationDialogPresenter;
import com.stm.dialog.notification.presenter.impl.NotificationDialogPresenterImpl;
import com.stm.dialog.notification.view.NotificationDialogView;
import com.stm.main.base.activity.MainActivity;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-30.
 */

public class NotificationDialogActivity extends Activity implements NotificationDialogView {
    private NotificationDialogPresenter notificationDialogPresenter;

    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.tv_notificationdialog_message)
    TextView tv_notificationdialog_message;

    @BindView(R.id.btn_notificationdialog_confirm)
    Button btn_notificationdialog_confirm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notificationdialog);
        getWindow().setLayout(android.view.WindowManager.LayoutParams.WRAP_CONTENT, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);



        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();

        String notificationMessage = getIntent().getStringExtra("notificationMessage");
        this.notificationDialogPresenter = new NotificationDialogPresenterImpl(this);
        this.notificationDialogPresenter.init(notificationMessage);
    }

    @Override
    public void showNotificationMessage(String message){
        tv_notificationdialog_message.setText(message);
    }

    @Override
    public void onBackPressed() {
        notificationDialogPresenter.onBackPressed();
    }

    @Override
    public void removeUser() {
        sharedPrefersManager.removeUser();
    }

    @Override
    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    @OnClick(R.id.btn_notificationdialog_confirm)
    public void onClickConfirm(){
        notificationDialogPresenter.onClickConfirm();
    }

}
