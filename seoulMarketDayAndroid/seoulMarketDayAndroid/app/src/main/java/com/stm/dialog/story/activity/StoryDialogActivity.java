package com.stm.dialog.story.activity;

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
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.story.adapter.StoryDialogToAuthorAdapter;
import com.stm.dialog.story.adapter.StoryDialogToOthersAdapter;
import com.stm.dialog.story.presenter.StoryDialogPresenter;
import com.stm.dialog.story.presenter.impl.StoryDialogPresenterImpl;
import com.stm.dialog.story.view.StoryDialogView;
import com.stm.login.base.activity.LoginActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.edit.activity.StoryEditActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class StoryDialogActivity extends Activity implements StoryDialogView {
    private StoryDialogPresenter storyDialogPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private StoryDialogToAuthorAdapter storyDialogToAuthorAdapter;
    private StoryDialogToOthersAdapter storyDialogToOthersAdapter;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.rv_storydialog)
    RecyclerView rv_storydialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_storydialog);
        getWindow().setLayout(android.view.WindowManager.LayoutParams.WRAP_CONTENT, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        Story story = (Story) getIntent().getSerializableExtra("story");
        int position = getIntent().getIntExtra("position", 0);
        this.storyDialogPresenter = new StoryDialogPresenterImpl(this);
        this.storyDialogPresenter.init(user, story, position);
    }

    @Override
    public void setStoryDialogToAuthorAdapterItem(ArrayList<String> messages) {
        storyDialogToAuthorAdapter = new StoryDialogToAuthorAdapter(storyDialogPresenter, messages, this, R.layout.item_storydialog);
        rv_storydialog.setAdapter(storyDialogToAuthorAdapter);
        rv_storydialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setStoryDialogToOthersAdapterItem(ArrayList<String> messages) {
        storyDialogToOthersAdapter = new StoryDialogToOthersAdapter(storyDialogPresenter, messages, this, R.layout.item_storydialog);
        rv_storydialog.setAdapter(storyDialogToOthersAdapter);
        rv_storydialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setContentCopied(String content) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", content);
        clipboardManager.setPrimaryClip(clipData);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
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
        finish();
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToStoryEditActivity(Story story) {
        Intent intent = new Intent(this, StoryEditActivity.class);
        intent.putExtra("story", story);
        startActivityForResult(intent, ActivityResultFlag.STORY_EDIT_REQEUST);

    }

    @Override
    public void navigateToBackWithResultDelete(int position) {
        Intent intent = getIntent();
        intent.putExtra("position", position);
        setResult(ActivityResultFlag.RESULT_DELETE, intent);
        finish();
    }

    @Override
    public void navigateToBackWithResultEdit(Story story, int position) {
        Intent intent = getIntent();
        intent.putExtra("position", position);
        intent.putExtra("story",story);
        setResult(ActivityResultFlag.RESULT_EDIT, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ActivityResultFlag.STORY_EDIT_REQEUST:
                switch (resultCode){
                    case ActivityResultFlag.RESULT_OK:
                        Story story = (Story) data.getSerializableExtra("story");
                        storyDialogPresenter.onActivityResultForStoryEditResultOk(story);

                        break;
                }
                break;
        }
    }
}
