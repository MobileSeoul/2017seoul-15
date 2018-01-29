package com.stm.comment.edit.actvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.comment.edit.presenter.CommentEditPresenter;
import com.stm.comment.edit.presenter.impl.CommentEditPresenterImpl;
import com.stm.comment.edit.view.CommentEditView;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public class CommentEditActivity extends Activity implements CommentEditView {
    private CommentEditPresenter commentEditPresenter;
    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SharedPrefersManager sharedPrefersManager;
    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_commentedit_toolbar)
    View in_commentedit_toolbar;

    @BindView(R.id.et_commentedit_content)
    EditText et_commentedit_content;

    @BindView(R.id.iv_commentedit_avatar)
    ImageView iv_commentedit_avatar;

    @BindView(R.id.btn_commentedit_submit)
    Button btn_commentedit_submit;

    @BindView(R.id.btn_commentedit_cancel)
    Button btn_commentedit_cancel;

    @BindString(R.string.cloud_front_user_avatar)
    String userAvatarUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentedit);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.sharedPrefersManager = new SharedPrefersManager(this);

        User user = sharedPrefersManager.getUser();
        StoryComment storyComment = (StoryComment) getIntent().getSerializableExtra("storyComment");
        int position = getIntent().getIntExtra("position", 0);

        this.commentEditPresenter = new CommentEditPresenterImpl(this);
        this.commentEditPresenter.init(user, storyComment, position);

    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showUserAvatar(String avatar) {
        Glide.with(this).load(userAvatarUrl + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_commentedit_avatar);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_commentedit_toolbar);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showContent(String content) {
        et_commentedit_content.setText(content);
    }

    @OnClick(R.id.btn_commentedit_cancel)
    @Override
    public void onClickCancel() {
        commentEditPresenter.onClickCancel();
    }

    @OnClick(R.id.btn_commentedit_submit)
    @Override
    public void onClickSubmit() {
        String content = et_commentedit_content.getText().toString();

        commentEditPresenter.onClickSubmit(content);
    }

    @Override
    public void navigateToBack() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_CANCEL, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        commentEditPresenter.onBackPressed();
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack(){
        commentEditPresenter.onClickBack();
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
    public void navigateToBackWithResultOk(StoryComment storyComment, int position) {
        Intent intent = getIntent();
        intent.putExtra("storyComment", storyComment);
        intent.putExtra("position", position);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;

        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;
    }
}
