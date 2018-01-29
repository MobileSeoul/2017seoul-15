package com.stm.user.edit.profile.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.util.MediaStorePathUtil;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.useredit.actvity.UserEditDialogActivity;
import com.stm.repository.local.SharedPrefersManager;

import com.stm.user.edit.profile.presenter.UserProfileEditPresenter;
import com.stm.user.edit.profile.presenter.impl.UserProfileEditPresenterImpl;
import com.stm.user.edit.profile.view.UserProfileEditView;


import java.io.File;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-22.
 */

public class UserProfileEditActivity extends Activity implements UserProfileEditView {
    private UserProfileEditPresenter userProfileEditPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private MediaStorePathUtil mediaStorePathUtil;

    private IncludedToolbarLayout includedToolbarLayout;


    @BindString(R.string.cloud_front_user_avatar)
    String userAvatarUrl;

    @BindString(R.string.cloud_front_user_cover)
    String userCoverUrl;


    @BindView(R.id.in_useredit_toolbar)
    View in_useredit_toolbar;

    @BindView(R.id.iv_useredit_editavatar)
    ImageView iv_useredit_editavatar;

    @BindView(R.id.iv_useredit_editcover)
    ImageView iv_useredit_editcover;


    @BindView(R.id.iv_useredit_cover)
    ImageView iv_useredit_cover;

    @BindView(R.id.iv_useredit_avatar)
    ImageView iv_useredit_avatar;

    @BindView(R.id.iv_useredit_editname)
    ImageView iv_useredit_editname;

    @BindView(R.id.tv_useredit_intro)
    TextView tv_useredit_intro;

    @BindView(R.id.tv_useredit_name)
    TextView tv_useredit_name;

    @BindView(R.id.tv_useredit_email)
    TextView tv_useredit_email;

    @BindView(R.id.tv_useredit_phone)
    TextView tv_useredit_phone;


    @BindView(R.id.sw_useredit_phone)
    Switch sw_useredit_phone;

    @BindView(R.id.sw_useredit_gender)
    Switch sw_useredit_gender;

    @BindView(R.id.ll_useredit_intro)
    LinearLayout ll_useredit_intro;


    @BindView(R.id.ll_useredit_phone)
    LinearLayout ll_useredit_phone;

    @BindView(R.id.rb_useredit_genderman)
    RadioButton rb_useredit_genderman;

    @BindView(R.id.rb_useredit_genderwoman)
    RadioButton rb_useredit_genderwoman;

    @BindView(R.id.fl_useredit_avatar)
    FrameLayout fl_useredit_avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);
        this.mediaStorePathUtil = new MediaStorePathUtil(this);

        User user = sharedPrefersManager.getUser();
        userProfileEditPresenter = new UserProfileEditPresenterImpl(this);
        userProfileEditPresenter.init(user);
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
        userProfileEditPresenter.onClickBack();
    }

    @Override
    @OnClick({R.id.iv_useredit_editname, R.id.tv_useredit_name})
    public void onClickEditName() {
        userProfileEditPresenter.onClickEditName();
    }

    @OnClick(R.id.ll_useredit_intro)
    @Override
    public void onClickEditIntro() {
        userProfileEditPresenter.onClickEditIntro();
    }

    @OnClick(R.id.ll_useredit_phone)
    @Override
    public void onClickEditPhone() {
        userProfileEditPresenter.onClickEditPhone();
    }

    @OnClick({R.id.iv_useredit_editavatar, R.id.iv_useredit_avatar})
    @Override
    public void onClickEditAvatar() {
        userProfileEditPresenter.onClickEditAvatar();
    }

    @OnClick({R.id.iv_useredit_editcover, R.id.iv_useredit_cover})
    @Override
    public void onClickEditCover() {
        userProfileEditPresenter.onClickEditCover();
    }

    @OnClick(R.id.rb_useredit_genderman)
    @Override
    public void onClickGenderForMan() {
        userProfileEditPresenter.onClickGenderForMan();
    }

    @OnClick(R.id.rb_useredit_genderwoman)
    @Override
    public void onClickGenderForWoman() {
        userProfileEditPresenter.onClickGenderForWoman();
    }

    @Override
    public void setGenderForManChecked(boolean isChecked) {
        rb_useredit_genderman.setChecked(isChecked);
    }

    @Override
    public void setGenderForWomanChecked(boolean isChecked) {
        rb_useredit_genderwoman.setChecked(isChecked);

    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_useredit_toolbar);
    }

    @Override
    public void setAvatarBringToFront(){
        fl_useredit_avatar.bringToFront();
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
    public void showUserNameText(String message) {
        tv_useredit_name.setText(message);
    }

    @Override
    public void showUserEmailText(String message) {
        tv_useredit_email.setText(message);
    }

    @Override
    public void showUserPhoneText(String message) {
        tv_useredit_phone.setText(message);
    }

    @Override
    public void showUserIntroText(String message) {
        tv_useredit_intro.setText(message);
    }

    @Override
    public void setUserGenderRangeChecked(boolean isChecked) {
        sw_useredit_gender.setChecked(isChecked);
    }

    @Override
    public void setUserPhoneRangeChecked(boolean isChecked) {
        sw_useredit_phone.setChecked(isChecked);
    }

    @Override
    public void showExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFlag.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionFlag.WRITE_EXTERNAL_STORAGE:
                userProfileEditPresenter.onRequestPermissionsResultForWriteExternalStorage(grantResults);
                break;
        }
    }


    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToMultiMediaStoreForAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_AVATAR_PHOTO_REQUEST);
    }

    @Override
    public void navigateToMultiMediaStoreForCover() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_COVER_PHOTO_REQUEST);
    }

    @Override
    public void showUserAvatar(String message) {
        Glide.with(this).load(userAvatarUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_useredit_avatar);
    }


    @Override
    public void showUserCover(String message) {
        Glide.with(this).load(userCoverUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_useredit_cover);
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

    }

    @OnCheckedChanged(R.id.sw_useredit_gender)
    public void onGenderRangeCheckedChanged(boolean isChecked) {
        userProfileEditPresenter.onGenderRangeCheckedChanged(isChecked);
    }

    @OnCheckedChanged(R.id.sw_useredit_phone)
    public void onPhoneRangeCheckedChanged(boolean isChecked) {
        userProfileEditPresenter.onPhoneRangeCheckedChanged(isChecked);
    }

    @Override
    public void setUser(User user) {
        this.sharedPrefersManager.setUser(user);
    }

    @Override
    public void navigateToUserEditDialogActivity(int editFlag) {
        Intent intent = new Intent(this, UserEditDialogActivity.class);
        intent.putExtra("editFlag", editFlag);
        startActivityForResult(intent, ActivityResultFlag.USER_EDIT_DIALOG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.USER_EDIT_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int editFlag = data.getIntExtra("editFlag", 0);
                        User user = (User) data.getSerializableExtra("user");

                        userProfileEditPresenter.onActivityResultForUserEditResultEdit(user, editFlag);

                        break;
                }
                break;

            case ActivityResultFlag.PICK_AVATAR_PHOTO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Uri uri = data.getData();
                        String path = mediaStorePathUtil.getImagePath(uri);
                        FileDto fileDto = new FileDto();
                        fileDto.setUri(uri);
                        fileDto.setPath(path);
                        fileDto.setType(DefaultFileFlag.USER_AVATAR_TYPE);
                        File file = new File(path);
                        fileDto.setFile(file);

                        userProfileEditPresenter.onActivityResultForAvatarPhotoResultOk(fileDto);
                        break;
                }
                break;

            case ActivityResultFlag.PICK_COVER_PHOTO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Uri uri = data.getData();
                        String path = mediaStorePathUtil.getImagePath(uri);
                        FileDto fileDto = new FileDto();
                        fileDto.setUri(uri);
                        fileDto.setPath(path);
                        fileDto.setType(DefaultFileFlag.USER_COVER_TYPE);

                        File file = new File(path);
                        fileDto.setFile(file);

                        userProfileEditPresenter.onActivityResultForCoverPhotoResultOk(fileDto);
                        break;
                }
                break;

        }
    }

}
