package com.stm.user.create.opinion.activity;

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
import com.stm.common.dao.OpinionCategory;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.opinioncategory.activity.OpinionCategoryDialogActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.create.opinion.presenter.OpinionPresenter;
import com.stm.user.create.opinion.presenter.impl.OpinionPresenterImpl;
import com.stm.user.create.opinion.view.OpinionVIew;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionActivity extends Activity implements OpinionVIew, TextWatcher {
    private OpinionPresenter opinionPresenter;

    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_opinion_toolbar)
    View in_opinion_toolbar;

    @BindView(R.id.tv_opinion_text)
    EditText tv_opinion_text;

    @BindView(R.id.tv_opinion_category)
    TextView tv_opinion_category;

    @BindView(R.id.ll_opinion_category)
    LinearLayout ll_opinion_category;

    @BindView(R.id.btn_opinion_submit)
    Button btn_opinion_submit;

    @BindView(R.id.tv_opinion_name)
    TextView tv_opinion_name;

    @BindColor(R.color.mint)
    int mint;

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
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.opinionPresenter = new OpinionPresenterImpl(this);
        this.opinionPresenter.init(user);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);

    }

    @Override
    public void showOpinionCategoryName(String message) {
        tv_opinion_category.setText(message);
    }

    @OnClick(R.id.ib_toolbar_back)
    @Override
    public void onClickBack() {
        opinionPresenter.onClickBack();
    }

    @OnClick(R.id.ll_opinion_category)
    @Override
    public void onClickOpinionCategory() {
        opinionPresenter.onClickOpinionCategory();
    }

    @OnClick(R.id.btn_opinion_submit)
    @Override
    public void onClickOpinionSubmit() {
        String content = tv_opinion_text.getText().toString();
        opinionPresenter.onClickOpinionSubmit(content);
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
    public void showUserName(String message){
        tv_opinion_name.setText(message);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_opinion_toolbar);
    }

    @Override
    public void setEditTextChangedListener(){
        tv_opinion_text.addTextChangedListener(this);
    }

    @Override
    public void setSubmitButtonActivated(){
        btn_opinion_submit.setBackground(roundBoxWithPointColor);
        btn_opinion_submit.setTextColor(white);
        btn_opinion_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonInactivated(){
        btn_opinion_submit.setBackground(roundBoxWithGray);
        btn_opinion_submit.setTextColor(darkGray);
        btn_opinion_submit.setClickable(false);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }


    @Override
    public void navigateToOpinionCategoryDialogActivity() {
        Intent intent = new Intent(this, OpinionCategoryDialogActivity.class);
        startActivityForResult(intent, ActivityResultFlag.OPINION_CATEGORY_DIALOG_REQUEST);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        opinionPresenter.afterTextChanged(s.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.OPINION_CATEGORY_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        OpinionCategory opinionCategory = (OpinionCategory) data.getSerializableExtra("opinionCategory");

                        opinionPresenter.onActivityResultForOpinionCategoryDialogResultOk(opinionCategory);

                        break;
                }
                break;

        }
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

    }

}
