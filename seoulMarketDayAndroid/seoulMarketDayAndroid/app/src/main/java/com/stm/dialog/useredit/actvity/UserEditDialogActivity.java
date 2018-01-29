package com.stm.dialog.useredit.actvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.useredit.presenter.UserEditDialogPresenter;
import com.stm.dialog.useredit.presenter.impl.UserEditDialogPresenterImpl;
import com.stm.dialog.useredit.view.UserEditDialogView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-23.
 */

public class UserEditDialogActivity extends Activity implements UserEditDialogView, TextWatcher {
    private UserEditDialogPresenter userEditDialogPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private TextHandler textHandler;


    @BindView(R.id.ib_usereditdialog_close)
    ImageButton ib_usereditdialog_close;

    @BindView(R.id.tv_usereditdialog_title)
    TextView tv_usereditdialog_title;

    @BindView(R.id.tv_usereditdialog_submit)
    TextView tv_usereditdialog_submit;

    @BindView(R.id.tv_usereditdialog_message)
    TextView tv_usereditdialog_message;

    @BindView(R.id.rl_usereditdialog_textsize)
    RelativeLayout rl_usereditdialog_textsize;


    @BindView(R.id.tv_usereditdialog_textsize)
    TextView tv_usereditdialog_textsize;

    @BindView(R.id.tv_usereditdialog_textmaxsize)
    TextView tv_usereditdialog_textmaxsize;

    @BindView(R.id.et_usereditdialog_text)
    EditText et_usereditdialog_text;

    @BindView(R.id.iv_usereditdialog_cancel)
    ImageView iv_usereditdialog_cancel;

    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.darkGray)
    int darkGray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usereditdialog);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);
        this.textHandler = new TextHandler(this);

        User user = sharedPrefersManager.getUser();
        int editFlag = getIntent().getIntExtra("editFlag", 0);
        this.userEditDialogPresenter = new UserEditDialogPresenterImpl(this);
        this.userEditDialogPresenter.init(user, editFlag);
    }

    @Override
    public void setUser(User user) {
        this.sharedPrefersManager.setUser(user);
    }

    @Override
    public void setSubmitButtonClickable() {
        tv_usereditdialog_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonUnclickable() {
        tv_usereditdialog_submit.setClickable(false);
    }

    @Override
    public void setSubmitButtonColorPointColor() {
        tv_usereditdialog_submit.setTextColor(pointColor);
    }

    @Override
    public void setSubmitButtonColorDarkGray() {
        tv_usereditdialog_submit.setTextColor(darkGray);
    }

    @Override
    public void setEditTextMaxLength(int length) {
        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = new InputFilter.LengthFilter(length);
        et_usereditdialog_text.setFilters(inputFilters);
    }

    @Override
    public void setEditTextAddTextChangedListener() {
        et_usereditdialog_text.addTextChangedListener(this);
    }

    @Override
    public void setEditTextPhoneInputType() {
        et_usereditdialog_text.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showToolbarTitle(String message) {
        tv_usereditdialog_title.setText(message);
    }

    @Override
    public void showDialogMessage(String message) {
        tv_usereditdialog_message.setText(message);
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
    public void showTextSizeCount(int size) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("size", size);
        message.setData(bundle);

        textHandler.sendMessage(message);
    }

    @Override
    public void showEditText(String message) {
        et_usereditdialog_text.setText(message);
    }

    @Override
    public void showTextMaxSize(int size) {
        tv_usereditdialog_textmaxsize.setText(String.valueOf(size));
    }

    @Override
    public void showTextSize(int size) {
        tv_usereditdialog_textsize.setText(String.valueOf(size));
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToBackWithResultEdit(User user, int editFlag) {
        Intent intent = getIntent();
        intent.putExtra("user", user);
        intent.putExtra("editFlag",editFlag);
        setResult(ActivityResultFlag.RESULT_OK,intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int length = et_usereditdialog_text.getText().toString().length();
        userEditDialogPresenter.onTextChanged(length);
    }

    @Override
    public void afterTextChanged(Editable s) {
        String content = et_usereditdialog_text.getText().toString();
        userEditDialogPresenter.afterTextChanged(content);
    }


    @Override
    @OnClick(R.id.tv_usereditdialog_submit)
    public void onClickSubmit(){
        String content = et_usereditdialog_text.getText().toString();
        userEditDialogPresenter.onClickSubmit(content);
    }

    @Override
    @OnClick(R.id.ib_usereditdialog_close)
    public void onClickClose(){
        userEditDialogPresenter.onClickClose();
    }

    @Override
    @OnClick(R.id.iv_usereditdialog_cancel)
    public void onClickEditTextCancel() {
        userEditDialogPresenter.onClickEditTextCancel();
    }

    public static class TextHandler extends Handler {
        private UserEditDialogView userEditDialogView;

        public TextHandler(UserEditDialogView userEditDialogView) {
            this.userEditDialogView = userEditDialogView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int size = bundle.getInt("size", 0);
            userEditDialogView.showTextSize(size);
        }
    }
}
