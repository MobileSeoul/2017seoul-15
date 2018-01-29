package com.stm.user.edit.account.exit.activity;

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
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Exit;
import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.exitcategory.activity.ExitCategoryDialogActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.edit.account.exit.presenter.ExitPresenter;
import com.stm.user.edit.account.exit.presenter.impl.ExitPresenterImpl;
import com.stm.user.edit.account.exit.view.ExitView;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitActivity extends Activity implements ExitView, TextWatcher {
    private ExitPresenter exitPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_exit_toolbar)
    View in_exit_toolbar;

    @BindView(R.id.tv_exit_name)
    TextView tv_exit_name;

    @BindView(R.id.ll_exit_category)
    LinearLayout ll_exit_category;

    @BindView(R.id.tv_exit_category)
    TextView tv_exit_category;

    @BindView(R.id.et_exit_content)
    EditText et_exit_content;

    @BindView(R.id.btn_exit_submit)
    Button btn_exit_submit;

    @BindDrawable(R.drawable.all_roundbox_pointcolorfill_r10)
    Drawable roundBoxWithPointColor;

    @BindDrawable(R.drawable.all_roundbox_grayfill_r10)
    Drawable roundBoxWithGray;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.darkGray)
    int darkGray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.exitPresenter = new ExitPresenterImpl(this);
        this.exitPresenter.init(user);
    }

    @Override
    public void setIncludedToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_exit_toolbar);
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
    public void showUserName(String message) {
        tv_exit_name.setText(message);
    }

    @Override
    public void showExitCategoryName(String message) {
        tv_exit_category.setText(message);
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
    public void setEditTextChangedListener(){
        et_exit_content.addTextChangedListener(this);
    }

    @Override
    public void setSubmitButtonActivated(){
        btn_exit_submit.setBackground(roundBoxWithPointColor);
        btn_exit_submit.setTextColor(white);
        btn_exit_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonInactivated(){
        btn_exit_submit.setBackground(roundBoxWithGray);
        btn_exit_submit.setTextColor(darkGray);
        btn_exit_submit.setClickable(false);
    }


    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        exitPresenter.onClickBack();
    }

    @Override
    @OnClick(R.id.ll_exit_category)
    public void onClickExitCategory() {
        exitPresenter.onClickAccountExitCategory();
    }

    @Override
    @OnClick(R.id.btn_exit_submit)
    public void onClickExitSubmit() {
        String content = et_exit_content.getText().toString();
        exitPresenter.onClickExitSubmit(content);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToExitCategoryDialogActivity() {
        Intent intent = new Intent(this, ExitCategoryDialogActivity.class);
        startActivityForResult(intent, ActivityResultFlag.EXIT_CATEGORY_DIALOG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.EXIT_CATEGORY_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        ExitCategory exitCategory = (ExitCategory) data.getSerializableExtra("exitCategory");
                        exitPresenter.onActivityResultForExitCategoryDialogResultOk(exitCategory);
                        break;
                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        exitPresenter.afterTextChanged(s.toString());
    }

    static class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;
    }
}
