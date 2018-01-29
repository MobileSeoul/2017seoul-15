package com.stm.comment.detail.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.comment.detail.adapter.CommentReplyAdapter;
import com.stm.comment.detail.presenter.CommentReplyPresenter;
import com.stm.comment.detail.presenter.impl.CommentReplyPresenterImpl;
import com.stm.comment.detail.view.CommentReplyView;
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
 * Created by Dev-0 on 2017-08-10.
 */

public class CommentReplyActivity extends Activity implements CommentReplyView, NestedScrollView.OnScrollChangeListener {
    private CommentReplyPresenter commentReplyPresenter;

    private SharedPrefersManager sharedPrefersManager;
    private MediaStorePathUtil mediaStorePathUtil;
    private ToastUtil toastUtil;
    private BitmapUtil bitmapUtil;

    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    private UploaderHandler uploaderHandler;

    @BindView(R.id.in_commentreply_toolbar)
    View in_commentreply_toolbar;

    @BindView(R.id.et_commentreply)
    EditText et_commentreply;

    @BindView(R.id.rv_commentreply)
    RecyclerView rv_commentreply;

    @BindView(R.id.ll_commentreply_write)
    LinearLayout ll_commentreply_write;

    @BindView(R.id.iv_commentreply_write)
    ImageView iv_commentreply_write;

    @BindView(R.id.nsv_commentreply)
    NestedScrollView nsv_commentreply;

    @BindView(R.id.fl_commentreply_loader)
    FrameLayout fl_commentreply_loader;

    @BindView(R.id.sb_commentreply_loader)
    SeekBar sb_commentreply_loader;

    @BindView(R.id.tv_commentreply_loadermessage)
    TextView tv_commentreply_loadermessage;

    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.darkGray)
    int darkGray;

    @BindString(R.string.cloud_front_comment_video)
    String commentVideoUrl;

    private IncludedToolbarLayout includedToolbarLayout;
    private CommentReplyAdapter commentReplyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentreply);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.bitmapUtil = new BitmapUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.mediaStorePathUtil = new MediaStorePathUtil(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.uploaderHandler = new UploaderHandler(this);

        Story story = (Story) getIntent().getSerializableExtra("story");
        StoryComment storyComment = (StoryComment) getIntent().getSerializableExtra("storyComment");
        User user = sharedPrefersManager.getUser();
        int position = getIntent().getIntExtra("position", 0);

        this.commentReplyPresenter = new CommentReplyPresenterImpl(this);
        this.commentReplyPresenter.init(story, storyComment, user, position);

    }

    @Override
    public void showExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFlag.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionFlag.WRITE_EXTERNAL_STORAGE:
                commentReplyPresenter.onRequestPermissionsResultForReadExternalStorage(grantResults);
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    @OnClick(R.id.ll_commentreply_write)
    public void onClickWrite() {
        String content = et_commentreply.getText().toString();
        commentReplyPresenter.onClickWrite(content);
    }

    @Override
    public void showCommentEditText(String message) {
        et_commentreply.setText(message);
    }

    @Override
    public void showUploaderMessage(String message) {
        tv_commentreply_loadermessage.setText(message);
        tv_commentreply_loadermessage.bringToFront();
    }

    @Override
    public void onBackPressed() {
        int statusOfUploaderView = fl_commentreply_loader.getVisibility();
        commentReplyPresenter.onBackPressed(statusOfUploaderView);
    }

    @Override
    public void setCommentReplyAdapterItem(List<StoryComment> storyComments) {
        commentReplyAdapter = new CommentReplyAdapter(commentReplyPresenter, storyComments, this);
        rv_commentreply.setAdapter(commentReplyAdapter);
        rv_commentreply.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void navigateToBack() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_CANCEL, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    public void navigateToBackWIthResultOk(StoryComment storyComment, int position, int commentReplyCountAdded) {
        Intent intent = getIntent();
        intent.putExtra("position", position);
        intent.putExtra("commentReplyCountAdded", commentReplyCountAdded);

        if (storyComment != null) {
            intent.putExtra("storyComment", storyComment);
        }

        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long storyUserId = user.getId();
        Intent intent = new Intent(this, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }

    @Override
    public void navigateToUserDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        commentReplyPresenter.onClickBack();
    }

    @Override
    @OnTextChanged(R.id.et_commentreply)
    public void onCommentTextChanged(Editable editable) {
        commentReplyPresenter.onCommentTextChanged(editable);
    }

    @Override
    @OnClick(R.id.iv_commentreply_photo)
    public void onClickPhoto() {
        commentReplyPresenter.onClickPhoto();
    }

    @Override
    @OnClick(R.id.iv_commentreply_video)
    public void onClickVideo() {
        commentReplyPresenter.onClickVideo();
    }

    @Override
    @OnClick(R.id.iv_commentreply_vr)
    public void onClickVR360() {
        commentReplyPresenter.onClickVR360();
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_commentreply_toolbar);
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

                        commentReplyPresenter.onActivityResultForPhotoResultOk(fileDto);
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

                        commentReplyPresenter.onActivityResultForVR360ResultOk(fileDto);
                        break;
                }
                break;

            case ActivityResultFlag.PICK_VIDEO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Uri uri = data.getData();
                        String path = mediaStorePathUtil.getVideoPathFromURI_API19(uri);

                        FileDto fileDto = new FileDto();
                        fileDto.setUri(uri);
                        fileDto.setPath(path);
                        fileDto.setType(DefaultFileFlag.VIDEO_TYPE);

                        File file = new File(path);
                        fileDto.setFile(file);
                        File thumbnailFile = new File(bitmapUtil.getCacheFilePathByBitmap(bitmapUtil.getVideoThumbnailBitmapByContentUri(uri)));
                        fileDto.setThumbnail(thumbnailFile);

                        commentReplyPresenter.onActivityResultForVideoResultOk(fileDto);
                        break;
                }
                break;

            case ActivityResultFlag.COMMENT_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_DELETE:
                        int position = data.getIntExtra("position", 0);
                        commentReplyPresenter.onActivityResultForCommentResultDelete(position);
                        break;

                    case ActivityResultFlag.RESULT_EDIT:
                        position = data.getIntExtra("position", 0);
                        StoryComment storyComment = (StoryComment) data.getSerializableExtra("storyComment");
                        commentReplyPresenter.onActivityResultForCommentResultEdit(storyComment, position);
                        break;
                }
                break;
        }
    }

    @Override
    public void showActivatedWriteButton() {
        iv_commentreply_write.setColorFilter(pointColor);
    }

    @Override
    public void showDeactivatedWriteButton() {
        iv_commentreply_write.setColorFilter(darkGray);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_LOGIN, intent);
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
        fl_commentreply_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneUploaderScreen() {
        fl_commentreply_loader.setVisibility(View.GONE);
    }

    @Override
    public void clearCommentReplyAdapter() {
        if (commentReplyAdapter != null) {
            commentReplyAdapter = null;
        }
    }

    @Override
    public void commentReplyAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        commentReplyAdapter.notifyItemRangeInserted(startPosition, itemCount);
    }

    @Override
    public void commentReplyAdapterNotifyItemInserted(int position) {
        commentReplyAdapter.notifyItemInserted(position);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_commentreply.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        commentReplyPresenter.onScrollChange(difference);
    }

    @Override
    public void setNestedScrollUpPosition() {
        nsv_commentreply.scrollTo(0, 0);
    }

    @Override
    public void showUploaderData(int bytesRead) {
        sb_commentreply_loader.setProgress(bytesRead);
    }

    @Override
    public void setUploaderMax(long maxSize) {
        sb_commentreply_loader.setMax((int) maxSize);
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
    public void commentReplyAdapterNotifyItemRemoved(int position) {
        if (commentReplyAdapter != null) {
            commentReplyAdapter.notifyItemRemoved(position);
            commentReplyAdapter.notifyItemRangeChanged(position, commentReplyAdapter.getItemCount());
        }
    }

    @Override
    public void navigateToPhotoDialogActivity(com.stm.common.dao.File file) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void commentReplyAdapterNotifyItemChanged(int position) {
        if (commentReplyAdapter != null) {
            commentReplyAdapter.notifyItemChanged(position);
        }
    }

    public static class UploaderHandler extends Handler {
        private CommentReplyView commentReplyView;

        public UploaderHandler(CommentReplyView commentReplyView) {
            this.commentReplyView = commentReplyView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int bytesRead = bundle.getInt("bytesRead", 0);

            if (bytesRead > 0) {
                commentReplyView.showUploaderData(bytesRead);
            }
        }
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }
}
