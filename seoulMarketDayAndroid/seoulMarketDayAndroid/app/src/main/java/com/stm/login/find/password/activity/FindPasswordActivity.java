package com.stm.login.find.password.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.login.find.password.presenter.FindPasswordPresenter;
import com.stm.login.find.password.presenter.impl.FindPasswordPresenterImpl;
import com.stm.login.find.password.view.FindPasswordView;
import com.stm.login.reset.password.activity.ResetPasswordActivity;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public class FindPasswordActivity extends Activity implements FindPasswordView {
    private FindPasswordPresenter findPasswordPresenter;

    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SharedPrefersManager sharedPrefersManager;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_findpassword_toolbar)
    View in_findpassword_toolbar;

    @BindView(R.id.et_findpassword_email)
    EditText et_findpassword_email;

    @BindView(R.id.btn_findpassword_submit)
    Button btn_findpassword_submit;

    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.darkGray)
    int darkGray;

    @BindColor(R.color.white)
    int white;

    @BindDrawable(R.drawable.all_roundbox_pointcolorfill_r10)
    Drawable all_roundbox_pointcolorfill_r10;

    @BindDrawable(R.drawable.all_roundbox_grayfill_r10)
    Drawable all_roundbox_grayfill_r10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.sharedPrefersManager = new SharedPrefersManager(this);

        this.findPasswordPresenter = new FindPasswordPresenterImpl(this);
        this.findPasswordPresenter.init();

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
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void setSubmitButtonClickable() {
        btn_findpassword_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonUnclickable() {
        btn_findpassword_submit.setClickable(false);
    }

    @Override
    public void setSubmitButtonColorPointColor() {
        btn_findpassword_submit.setBackgroundDrawable(all_roundbox_pointcolorfill_r10);
    }

    @Override
    public void setSubmitButtonColorGray() {
        btn_findpassword_submit.setBackgroundDrawable(all_roundbox_grayfill_r10);
    }

    @Override
    public void setSubmitButtonTextColorDarkGray(){
        btn_findpassword_submit.setTextColor(darkGray);
    }

    @Override
    public void setSubmitButtonTextColorWhite(){
        btn_findpassword_submit.setTextColor(white);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_findpassword_toolbar);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToResetPasswordActivity(User user) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("user", user);
        startActivityForResult(intent, ActivityResultFlag.USER_EDIT_REQUEST);
    }

    @Override
    @OnTextChanged(value = R.id.et_findpassword_email, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable editable) {
        findPasswordPresenter.onTextChanged(editable);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        findPasswordPresenter.onClickBack();
    }

    @Override
    @OnClick(R.id.btn_findpassword_submit)
    public void onClickSubmitButton() {
        String content = et_findpassword_email.getText().toString();
        findPasswordPresenter.onClickSubmitButton(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ActivityResultFlag.USER_EDIT_REQUEST:
                switch (resultCode){
                    case ActivityResultFlag.RESULT_OK:
                        findPasswordPresenter.onActivityResultUserEditResultOk();
                        break;
                }
                break;
        }
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

    }
}
