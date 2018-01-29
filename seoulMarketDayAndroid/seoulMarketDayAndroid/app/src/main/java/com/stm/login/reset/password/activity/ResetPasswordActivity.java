package com.stm.login.reset.password.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.login.reset.password.presenter.ResetPasswordPresenter;
import com.stm.login.reset.password.presenter.impl.ResetPasswordPresenterImpl;
import com.stm.login.reset.password.view.ResetPasswordView;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public class ResetPasswordActivity extends Activity implements ResetPasswordView {
    private ResetPasswordPresenter resetPasswordPresenter;

    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.iv_resetpassword_useravatar)
    ImageView iv_resetpassword_useravatar;

    @BindView(R.id.tv_resetpassword_username)
    TextView tv_resetpassword_username;

    @BindView(R.id.in_resetpassword_toolbar)
    View in_resetpassword_toolbar;

    @BindView(R.id.tv_resetpassword_email)
    TextView tv_resetpassword_email;

    @BindView(R.id.rb_resetpassword_email)
    RadioButton rb_resetpassword_email;

    @BindView(R.id.tv_resetpassword_submit)
    TextView tv_resetpassword_submit;

    @BindString(R.string.cloud_front_user_avatar)
    String userAvatarUrl;

    @BindDrawable(R.drawable.all_roundbox_pointcolorfill_r10)
    Drawable all_roundbox_pointcolorfill_r10;

    @BindDrawable(R.drawable.all_roundbox_grayfill_r10)
    Drawable all_roundbox_grayfill_r10;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.darkGray)
    int darkGray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswrod);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = (User) getIntent().getSerializableExtra("user");
        this.resetPasswordPresenter = new ResetPasswordPresenterImpl(this);
        this.resetPasswordPresenter.init(user);
    }


    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        resetPasswordPresenter.onClickBack();
    }

    @Override
    public void showUserAvatar(String avatar) {
        Glide.with(this).load(userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_resetpassword_useravatar);
    }

    @Override
    public void showUserName(String name) {
        tv_resetpassword_username.setText(name);
    }

    @Override
    public void showUserEmail(String email) {
        tv_resetpassword_email.setText(email);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void setSubmitButtonClickable() {
        tv_resetpassword_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonUnclickable() {
        tv_resetpassword_submit.setClickable(false);
    }

    @Override
    public void setSubmitButtonColorPointColor() {
        tv_resetpassword_submit.setBackgroundDrawable(all_roundbox_pointcolorfill_r10);
    }

    @Override
    public void setSubmitButtonColorGray() {
        tv_resetpassword_submit.setBackgroundDrawable(all_roundbox_grayfill_r10);
    }

    @Override
    public void setSubmitButtonTextColorDarkGray() {
        tv_resetpassword_submit.setTextColor(darkGray);
    }

    @Override
    public void setSubmitButtonTextColorWhite() {
        tv_resetpassword_submit.setTextColor(white);
    }

    @OnClick(R.id.tv_resetpassword_submit)
    @Override
    public void onClickSubmitButton() {
        resetPasswordPresenter.onClickSubmitButton();
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
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_resetpassword_toolbar);
    }

    @Override
    public void navigateToBackWithResultOk() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    @OnCheckedChanged(R.id.rb_resetpassword_email)
    public void onEmailChecked(boolean checked) {
        resetPasswordPresenter.onEmailChecked(checked);
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

    }

}
