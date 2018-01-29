package com.stm.user.detail.normal.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.normal.presenter.UserDetailPresenter;
import com.stm.user.detail.normal.presenter.impl.UserDetailPresenterImpl;
import com.stm.user.detail.normal.view.UserDetailView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class UserDetailActivity extends Activity implements UserDetailView {
    private UserDetailPresenter userDetailPresenter;
    private ToastUtil toastUtil;
    private IncludedToolbarLayout includedToolbarLayout;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.ll_userdetail_email)
    LinearLayout ll_userdetail_email;

    @BindView(R.id.ll_userdetail_gender)
    LinearLayout ll_userdetail_gender;

    @BindView(R.id.ll_userdetail_phone)
    LinearLayout ll_userdetail_phone;

    @BindView(R.id.iv_userdetail_cover)
    ImageView iv_userdetail_cover;

    @BindView(R.id.iv_userdetail_avatar)
    ImageView iv_userdetail_avatar;

    @BindView(R.id.tv_userdetail_name)
    TextView tv_userdetail_name;

    @BindView(R.id.tv_userdetail_email)
    TextView tv_userdetail_email;

    @BindView(R.id.tv_userdetail_phone)
    TextView tv_userdetail_phone;

    @BindView(R.id.tv_userdetail_gender)
    TextView tv_userdetail_gender;

    @BindView(R.id.in_userdetail_toolbar)
    View in_userdetail_toolbar;

    @BindString(R.string.cloud_front_user_avatar)
    String cloud_front_user_avatar;

    @BindString(R.string.cloud_front_user_cover)
    String cloud_front_user_cover;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.userDetailPresenter = new UserDetailPresenterImpl(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        long storyUserId = getIntent().getLongExtra("storyUserId", 0);
        User user = sharedPrefersManager.getUser();

        this.userDetailPresenter.init(user, storyUserId);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_userdetail_toolbar);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showUserNameText(String message) {
        tv_userdetail_name.setText(message);
    }

    @Override
    public void showUserEmailText(String message) {
        tv_userdetail_email.setText(message);
    }

    @Override
    public void showUserPhoneText(String message) {
        tv_userdetail_phone.setText(message);
    }

    @Override
    public void showUserAvatar(String message) {
        Glide.with(this).load(cloud_front_user_avatar + message).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_userdetail_avatar);
    }

    @Override
    public void showUserCover(String message) {
        Glide.with(this).load(cloud_front_user_cover + message).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_userdetail_cover);
    }

    @Override
    public void goneGender() {
        ll_userdetail_gender.setVisibility(View.GONE);
    }

    @Override
    public void goneEmail() {
        ll_userdetail_email.setVisibility(View.GONE);
    }

    @Override
    public void gonePhone() {
        ll_userdetail_phone.setVisibility(View.GONE);
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
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    @OnClick(R.id.iv_userdetail_avatar)
    public void onClickAvatar(){
        userDetailPresenter.onClickAvatar();
    }

    @Override
    @OnClick(R.id.iv_userdetail_cover)
    public void onClickCover(){
        userDetailPresenter.onClickCover();
    }

    @Override
    public void showUserGenderText(String message) {
        tv_userdetail_gender.setText(message);
    }

    @Override
    public void navigateToPhotoDialogActivityWithAvatar(String message) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("avatar", message);
        startActivity(intent);
    }

    @Override
    public void navigateToPhotoDialogActivityWithCover(String message) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("cover", message);
        startActivity(intent);
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;
    }
}
