package com.stm.dialog.comment.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.stm.R;
import com.stm.comment.edit.actvity.CommentEditActivity;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.comment.adapter.CommentDialogToAuthorAdapter;
import com.stm.dialog.comment.adapter.CommentDialogToAuthorWithFileAdapter;
import com.stm.dialog.comment.adapter.CommentDialogToOthersAdapter;
import com.stm.dialog.comment.presenter.CommentDialogPresenter;
import com.stm.dialog.comment.presenter.impl.CommentDialogPresenterImpl;
import com.stm.dialog.comment.view.CommentDialogView;
import com.stm.repository.local.SharedPrefersManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public class CommentDialogActivity extends Activity implements CommentDialogView {
    private CommentDialogPresenter commentDialogPresenter;
    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SharedPrefersManager sharedPrefersManager;
    private CommentDialogToAuthorAdapter commentDialogToAuthorAdapter;
    private CommentDialogToOthersAdapter commentDialogToOthersAdapter;
    private CommentDialogToAuthorWithFileAdapter commentDialogToAuthorWithFileAdapter;

    @BindView(R.id.rv_commentdialog)
    RecyclerView rv_commentdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_commentdialog);
        getWindow().setLayout(android.view.WindowManager.LayoutParams.WRAP_CONTENT, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.sharedPrefersManager = new SharedPrefersManager(this);

        User user = sharedPrefersManager.getUser();
        StoryComment storyComment = (StoryComment) getIntent().getSerializableExtra("storyComment");
        int position = getIntent().getIntExtra("position", 0);

        this.commentDialogPresenter = new CommentDialogPresenterImpl(this);
        this.commentDialogPresenter.init(user, storyComment, position);
    }

    @Override
    public void setCommentDialogToAuthorAdapterItem(ArrayList<String> messages) {
        commentDialogToAuthorAdapter = new CommentDialogToAuthorAdapter(commentDialogPresenter, messages, this, R.layout.item_commentdialog);
        rv_commentdialog.setAdapter(commentDialogToAuthorAdapter);
        rv_commentdialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setCommentDialogToAuthorWithFileAdapterItem(ArrayList<String> messages) {
        commentDialogToAuthorWithFileAdapter = new CommentDialogToAuthorWithFileAdapter(commentDialogPresenter, messages, this, R.layout.item_commentdialog);
        rv_commentdialog.setAdapter(commentDialogToAuthorWithFileAdapter);
        rv_commentdialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setCommentDialogToOthersAdapterItem(ArrayList<String> messages) {
        commentDialogToOthersAdapter = new CommentDialogToOthersAdapter(commentDialogPresenter, messages, this, R.layout.item_commentdialog);
        rv_commentdialog.setAdapter(commentDialogToOthersAdapter);
        rv_commentdialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void setContentCopied(String content) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", content);
        clipboardManager.setPrimaryClip(clipData);
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
    public void navigateToBack() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    @Override
    public void navigateToBackWithResultDelete(int position) {
        Intent intent = getIntent();
        intent.putExtra("position", position);
        setResult(ActivityResultFlag.RESULT_DELETE, intent);
        finish();
    }

    @Override
    public void navigateToCommentEditActivity(StoryComment storyComment, int commentPosition) {
        Intent intent = new Intent(this, CommentEditActivity.class);
        intent.putExtra("storyComment", storyComment);
        intent.putExtra("position", commentPosition);
        startActivityForResult(intent, ActivityResultFlag.COMMENT_EDIT_REQUEST);
    }

    @Override
    public void navigateToBackWithResultEdit(StoryComment storyComment, int position) {
        Intent intent = getIntent();
        intent.putExtra("position", position);
        intent.putExtra("storyComment", storyComment);
        setResult(ActivityResultFlag.RESULT_EDIT, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        commentDialogPresenter.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.COMMENT_EDIT_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        StoryComment storyComment = (StoryComment) data.getSerializableExtra("storyComment");
                        commentDialogPresenter.onActivityResultForCommentEditResultOk(storyComment, position);
                        break;
                }
                break;
        }
    }
}
