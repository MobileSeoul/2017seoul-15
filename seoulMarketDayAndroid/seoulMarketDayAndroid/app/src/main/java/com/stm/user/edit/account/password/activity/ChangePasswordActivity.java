package com.stm.user.edit.account.password.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.edit.account.password.presenter.ChangePasswordPresenter;
import com.stm.user.edit.account.password.presenter.impl.ChangePasswordPresenterImpl;
import com.stm.user.edit.account.password.view.ChangePasswordView;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public class ChangePasswordActivity extends Activity implements ChangePasswordView, TextWatcher {
    private ChangePasswordPresenter changePasswordPresenter;
    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SharedPrefersManager sharedPrefersManager;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_changepassword_toolbar)
    View in_changepassword_toolbar;

    @BindView(R.id.et_changepassword_currentpassword)
    EditText et_changepassword_currentpassword;

    @BindView(R.id.et_changepassword_newpassword)
    EditText et_changepassword_newpassword;

    @BindView(R.id.et_changepassword_newpasswordconfirm)
    EditText et_changepassword_newpasswordconfirm;

    @BindView(R.id.btn_changepassword)
    Button btn_changepassword;

    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.gray)
    int gray;

    @BindColor(R.color.darkGray)
    int darkGray;

    @BindDrawable(R.drawable.all_roundbox_pointcolorfill_r10)
    Drawable roundBoxWithPointColor;

    @BindDrawable(R.drawable.all_roundbox_grayfill_r10)
    Drawable roundBoxWithGray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.changePasswordPresenter = new ChangePasswordPresenterImpl(this);
        this.changePasswordPresenter.init(user);

    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
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
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setIncludedToolbarLayout() {
        this.includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_changepassword_toolbar);
    }

    @Override
    @OnClick(R.id.btn_changepassword)
    public void onClickSubmit() {
        String currentPassword = et_changepassword_currentpassword.getText().toString();
        String newPassword = et_changepassword_newpassword.getText().toString();
        String newPasswordConfirm = et_changepassword_newpasswordconfirm.getText().toString();

        changePasswordPresenter.onClickSubmit(currentPassword, newPassword, newPasswordConfirm);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack(){
        changePasswordPresenter.onClickBack();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void setEditTextAddTextChangedListener() {
        et_changepassword_currentpassword.addTextChangedListener(this);
        et_changepassword_newpassword.addTextChangedListener(this);
        et_changepassword_newpasswordconfirm.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String currentPassword = et_changepassword_currentpassword.getText().toString();
        String newPassword = et_changepassword_newpassword.getText().toString();
        String newPasswordConfirm = et_changepassword_newpasswordconfirm.getText().toString();

        int currentPasswordLength = currentPassword.length();
        int newPasswordLength = newPassword.length();
        int newPasswordConfirmLength = newPasswordConfirm.length();

        if (currentPasswordLength > 0 && newPasswordLength > 0 && newPasswordConfirmLength > 0){
            btn_changepassword.setBackground(roundBoxWithPointColor);
            btn_changepassword.setTextColor(white);
        } else {
            btn_changepassword.setBackground(roundBoxWithGray);
            btn_changepassword.setTextColor(darkGray);
        }
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;
    }

}
