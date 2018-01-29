package com.stm.comment.base.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.common.util.MediaStorePathUtil;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.comment.activity.CommentDialogActivity;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.login.base.activity.LoginActivity;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.comment.base.adapter.CommentAdapter;
import com.stm.comment.base.presenter.CommentPresenter;
import com.stm.comment.base.presenter.impl.CommentPresenterImpl;
import com.stm.comment.base.view.CommentView;
import com.stm.comment.detail.activity.CommentReplyActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.normal.activity.UserDetailActivity;

import java.io.File;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public class CommentActivity extends Activity implements CommentView, NestedScrollView.OnScrollChangeListener {
    private CommentPresenter commentPresenter;
    private CommentAdapter commentAdapter;
    private SharedPrefersManager sharedPrefersManager;
    private MediaStorePathUtil mediaStorePathUtil;
    private ToastUtil toastUtil;
    private BitmapUtil bitmapUtil;

    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private UploaderHandler uploaderHandler;

    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.in_comment_toolbar)
    View in_comment_toolbar;

    @BindView(R.id.et_comment)
    EditText et_comment;

    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;

    @BindView(R.id.ll_comment_write)
    LinearLayout ll_comment_write;

    @BindView(R.id.iv_comment_write)
    ImageView iv_comment_write;

    @BindView(R.id.nsv_comment)
    NestedScrollView nsv_comment;

    @BindView(R.id.fl_comment_loader)
    FrameLayout fl_comment_loader;

    @BindView(R.id.sb_comment_loader)
    SeekBar sb_comment_loader;

    @BindView(R.id.tv_comment_loadermessage)
    TextView tv_comment_loadermessage;


    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.darkGray)
    int darkGray;

    @BindString(R.string.cloud_front_comment_video)
    String commentVideoUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.bitmapUtil = new BitmapUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.mediaStorePathUtil = new MediaStorePathUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.uploaderHandler = new UploaderHandler(this);
        this.progressDialogHandler = new Handler();

        Story story = (Story) getIntent().getSerializableExtra("story");
        User user = sharedPrefersManager.getUser();
        int position = getIntent().getIntExtra("position", 0);

        this.commentPresenter = new CommentPresenterImpl(this);
        this.commentPresenter.init(user, story, position);
    }

    @Override
    public void showExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFlag.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionFlag.WRITE_EXTERNAL_STORAGE:
                commentPresenter.onRequestPermissionsResultForReadExternalStorage(grantResults);
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showCommentEditText(String message) {
        et_comment.setText(message);
    }

    @Override
    public void showUploaderMessage(String message) {
        tv_comment_loadermessage.setText(message);
        tv_comment_loadermessage.bringToFront();
    }

    @Override
    public void onBackPressed() {
        int statusOfUploaderView = fl_comment_loader.getVisibility();
        commentPresenter.onBackPressed(statusOfUploaderView);
    }

    @Override
    public void setCommentAdapterItem(List<StoryComment> storyComments) {
        commentAdapter = new CommentAdapter(commentPresenter, storyComments, this);
        rv_comment.setAdapter(commentAdapter);
        rv_comment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void navigateToBack() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_CANCEL, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    public void navigateToBackWIthResultOk(int position, int commentCountAdded) {
        Intent intent = getIntent();
        intent.putExtra("position", position);
        intent.putExtra("commentCountAdded", commentCountAdded);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    @OnTextChanged(R.id.et_comment)
    public void onCommentTextChanged(Editable editable) {
        commentPresenter.onCommentTextChanged(editable);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        commentPresenter.onClickBack();
    }

    @Override
    @OnClick(R.id.ll_comment_write)
    public void onClickWrite() {
        String content = et_comment.getText().toString();
        commentPresenter.onClickWrite(content);
    }

    @Override
    @OnClick(R.id.iv_comment_photo)
    public void onClickPhoto() {
        commentPresenter.onClickPhoto();
    }

    @Override
    @OnClick(R.id.iv_comment_video)
    public void onClickVideo() {
        commentPresenter.onClickVideo();
    }

    @Override
    @OnClick(R.id.iv_comment_vr)
    public void onClickVR360() {
        commentPresenter.onClickVR360();
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_comment_toolbar);
    }

    @Override
    public void navigateToMultiMediaStoreForPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_PHOTO_REQUEST);
    }

    @Override
    public void navigateToMultiMediaStoreForVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), ActivityResultFlag.PICK_VIDEO_REQUEST);
    }

    @Override
    public void navigateToMultiMediaStoreForVR360() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_VR_REQUEST);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long storyUserId = user.getId();
        Intent intent = new Intent(this, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.PICK_PHOTO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Uri uri = data.getData();
                        String path = mediaStorePathUtil.getImagePath(uri);

                        FileDto fileDto = new FileDto();
                        fileDto.setUri(uri);
                        fileDto.setPath(path);
                        fileDto.setType(DefaultFileFlag.PHOTO_TYPE);

                        File file = new File(path);
                        fileDto.setFile(file);

                        commentPresenter.onActivityResultForPhotoResultOk(fileDto);
                        break;
                }
                break;

            case ActivityResultFlag.PICK_VR_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Uri uri = data.getData();
                        String path = mediaStorePathUtil.getImagePath(uri);

                        FileDto fileDto = new FileDto();
                        fileDto.setUri(uri);
                        fileDto.setPath(path);
                        fileDto.setType(DefaultFileFlag.VR360_TYPE);

                        File file = new File(path);
                        fileDto.setFile(file);

                        commentPresenter.onActivityResultForVR360ResultOk(fileDto);
                        break;
                }
                break;

            case ActivityResultFlag.PICK_VIDEO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Uri uri = data.getData();

                        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

                        String path;
                        if(isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
                            path = mediaStorePathUtil.getVideoPathFromURI_API19(uri);
                        } else {
                            path = mediaStorePathUtil.getVideoPathFromURI_API11to18(uri);
                        }

                        FileDto fileDto = new FileDto();
                        fileDto.setUri(uri);
                        fileDto.setPath(path);
                        fileDto.setType(DefaultFileFlag.VIDEO_TYPE);

                        File file = new File(path);
                        fileDto.setFile(file);
                        File thumbnailFile = new File(bitmapUtil.getCacheFilePathByBitmap(bitmapUtil.getVideoThumbnailBitmapByContentUri(uri)));
                        fileDto.setThumbnail(thumbnailFile);

                        commentPresenter.onActivityResultForVideoResultOk(fileDto);
                        break;
                }
                break;

            case ActivityResultFlag.STORY_COMMENT_REPLY_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        int commentCountAdded = data.getIntExtra("commentReplyCountAdded", 0);
                        StoryComment storyComment = (StoryComment) data.getSerializableExtra("storyComment");

                        commentPresenter.onActivityResultForCommentReplyResultOk(storyComment, position, commentCountAdded);
                        break;

                    case ActivityResultFlag.RESULT_LOGIN:
                        commentPresenter.onActivityResultForCommentReplyResultLogin();
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }

                break;

            case ActivityResultFlag.COMMENT_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_DELETE:
                        int position = data.getIntExtra("position", 0);
                        commentPresenter.onActivityResultForCommentResultDelete(position);
                        break;
                    case ActivityResultFlag.RESULT_EDIT:
                        position = data.getIntExtra("position", 0);

                        StoryComment storyComment = (StoryComment) data.getSerializableExtra("storyComment");
                        commentPresenter.onActivityResultForCommentResultEdit(storyComment, position);
                        break;
                }
                break;
        }
    }

    @Override
    public void commentAdapterNotifyItemChanged(int position) {
        if (commentAdapter != null) {
            commentAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void commentAdapterNotifyItemRemoved(int position) {
        if (commentAdapter != null) {
            commentAdapter.notifyItemRemoved(position);
            commentAdapter.notifyItemRangeChanged(position, commentAdapter.getItemCount());
        }
    }


    @Override
    public void showActivatedWriteButton() {
        iv_comment_write.setColorFilter(pointColor);
    }

    @Override
    public void showDeactivatedWriteButton() {
        iv_comment_write.setColorFilter(darkGray);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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
    public void showUploaderScreen() {
        fl_comment_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneUploaderScreen() {
        fl_comment_loader.setVisibility(View.GONE);
    }

    @Override
    public void clearCommentAdapter() {
        if (commentAdapter != null) {
            commentAdapter = null;
        }
    }

    @Override
    public void commentAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        commentAdapter.notifyItemRangeInserted(startPosition, itemCount);
    }

    @Override
    public void commentAdapterNotifyItemInserted(int position) {
        commentAdapter.notifyItemInserted(position);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_comment.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        commentPresenter.onScrollChange(difference);
    }

    @Override
    public void setNestedScrollUpPosition() {
        nsv_comment.scrollTo(0, 0);
    }

    @Override
    public void showUploaderData(int bytesRead) {
        sb_comment_loader.setProgress(bytesRead);
    }

    @Override
    public void setUploaderMax(long maxSize) {
        sb_comment_loader.setMax((int) maxSize);
    }

    @Override
    public void handleUploaderData(long bytesRead) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("bytesRead", (int) bytesRead);
        message.setData(bundle);

        uploaderHandler.sendMessage(message);
    }

    @Override
    public void navigateToCommentReplyActivity(Story story, StoryComment storyComment, int position) {
        Intent intent = new Intent(this, CommentReplyActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("storyComment", storyComment);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_COMMENT_REPLY_REQEUST);
    }

    @Override
    public void navigateToCommentDialogActivity(StoryComment storyComment, int position) {
        Intent intent = new Intent(this, CommentDialogActivity.class);
        intent.putExtra("storyComment", storyComment);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.COMMENT_DIALOG_REQUEST);
    }

    @Override
    public void navigateToVideoPlayerActivity(com.stm.common.dao.File file) {
        String uri = commentVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }

    @Override
    public void navigateToPhotoDialogActivity(com.stm.common.dao.File file) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void navigateToUserDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    public static class UploaderHandler extends Handler {
        private CommentView commentView;

        public UploaderHandler(CommentView commentView) {
            this.commentView = commentView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int bytesRead = bundle.getInt("bytesRead", 0);

            if (bytesRead > 0) {
                commentView.showUploaderData(bytesRead);
            }

        }
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }
}
