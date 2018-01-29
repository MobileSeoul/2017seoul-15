package com.stm.story.edit.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryTag;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.StoryDto;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.common.util.MediaStorePathUtil;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.edit.adapter.ContentUriFileAdapter;
import com.stm.story.edit.adapter.UrlFileAdapter;
import com.stm.story.edit.presenter.StoryEditPresenter;
import com.stm.story.edit.presenter.impl.StoryEditPresenterImpl;
import com.stm.story.edit.view.StoryEditView;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class StoryEditActivity extends Activity implements StoryEditView, TextWatcher {
    private StoryEditPresenter storyEditPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ContentUriFileAdapter contentUriFileAdapter;
    private UrlFileAdapter urlFileAdapter;
    private MediaStorePathUtil mediaStorePathUtil;
    private BitmapUtil bitmapUtil;
    private HashTagHelper hashTagHelper;
    private UploaderHandler uploaderHandler;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.et_edit_content)
    EditText et_edit_content;

    @BindView(R.id.rv_edit_urlfile)
    RecyclerView rv_edit_urlfile;

    @BindView(R.id.rv_edit_contenturifile)
    RecyclerView rv_edit_contenturifile;

    @BindView(R.id.fl_edit_loader)
    FrameLayout fl_edit_loader;

    @BindView(R.id.sb_edit_loader)
    SeekBar sb_edit_loader;

    @BindView(R.id.tv_edit_filesize)
    TextView tv_edit_filesize;

    @BindView(R.id.tv_edit_filetotalsize)
    TextView tv_edit_filetotalsize;

    @BindView(R.id.tv_edit_submit)
    TextView tv_edit_submit;

    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.darkGray)
    int darkGray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyedit);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.mediaStorePathUtil = new MediaStorePathUtil(this);
        this.bitmapUtil = new BitmapUtil(this);
        this.uploaderHandler = new UploaderHandler(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        Story story = (Story) getIntent().getSerializableExtra("story");
        int position = getIntent().getIntExtra("position", 0);

        this.storyEditPresenter = new StoryEditPresenterImpl(this);
        this.storyEditPresenter.init(user, story);

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
        }, 400);
    }


    @Override
    public void setContentUriFileAdapterVideoFull(boolean isVideoFull) {
        contentUriFileAdapter.setVideoFull(isVideoFull);
    }

    @Override
    public void showEditTextContent(Story story) {
        String content = story.getContent();
        et_edit_content.setText(content);
    }

    @Override
    public void showEditTextContent(String message) {
        et_edit_content.append(message);
    }

    @Override
    public void setHashTagHelper() {
        hashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.pointColor), new HashTagHelper.OnHashTagClickListener() {
            @Override
            public void onHashTagClicked(String hashTag) {
                storyEditPresenter.onHashTagClicked(hashTag);
            }
        });

        hashTagHelper.handle(et_edit_content);
    }


    @Override
    @OnClick(R.id.ib_edit_close)
    public void onClickClose() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_CANCEL, intent);
        finish();
    }

    @Override
    @OnClick(R.id.tv_edit_submit)
    public void onClickSubmit() {
        String content = et_edit_content.getText().toString();
        List<String> allHashTags = hashTagHelper.getAllHashTags();

        StoryDto storyDto = new StoryDto();
        Story story = new Story();
        List<StoryTag> storyTags = story.convertToStroyTagsFromStrings(allHashTags);
        boolean isVideoFull = contentUriFileAdapter.isVideoFull();
        story.setContent(content);
        story.setStoryTags(storyTags);

        storyDto.setStory(story);
        storyEditPresenter.onClickSubmit(storyDto, isVideoFull);
    }

    @Override
    @OnClick(R.id.ib_edit_photo)
    public void onClickPhoto() {
        storyEditPresenter.onClickPhoto();
    }

    @Override
    @OnClick(R.id.ib_edit_video)
    public void onClickVideo() {
        boolean isUrlFileVideoFull = urlFileAdapter.isVideoFull();
        boolean isContentUriFileVideoFull = contentUriFileAdapter.isVideoFull();

        boolean isVideoFull = false;

        if (isUrlFileVideoFull || isContentUriFileVideoFull) {
            isVideoFull = true;
        }
        storyEditPresenter.onClickVideo(isVideoFull);
    }

    @Override
    @OnClick(R.id.ib_edit_vr)
    public void onClickVR360() {
        storyEditPresenter.onClickVR360();
    }

    @Override
    @OnClick(R.id.ib_edit_hashtag)
    public void onClickHashTag() {
        storyEditPresenter.onClickHashTag();
    }

    @Override
    public void setUrlFileAdapterItem(List<File> files) {
        urlFileAdapter = new UrlFileAdapter(storyEditPresenter, files, this, R.layout.item_storyedit_file);
        rv_edit_urlfile.setAdapter(urlFileAdapter);
        rv_edit_urlfile.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void navigateToMultiMediaStoreForPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_PHOTO_REQUEST);
    }

    @Override
    public void showContentUriFileAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (contentUriFileAdapter != null) {
            contentUriFileAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void showContentUriFileAdapterNotifyItemInserted(int position) {
        if (contentUriFileAdapter != null) {
            contentUriFileAdapter.notifyItemInserted(position);
        }
    }

    @Override
    public void setContentUriFileAdapterNotifyItemRemoved(int position) {
        if (contentUriFileAdapter != null) {
            contentUriFileAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void showUrlFileAdapterNotifyDataSetChanged() {
        if (urlFileAdapter != null) {
            urlFileAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUrlFileAdapterNotifyItemRemoved(int position) {
        if (urlFileAdapter != null) {
            urlFileAdapter.notifyItemRemoved(position);
        }
    }


    @Override
    public void navigateToMultiMediaStoreForVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), ActivityResultFlag.PICK_VIDEO_REQUEST);
    }

    @Override
    public void navigateToMultiMediaStoreForVR360() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_VR_REQUEST);
    }

    @Override
    public void setContentUriFileAdapterItem(ArrayList<FileDto> fileDtos) {
        contentUriFileAdapter = new ContentUriFileAdapter(storyEditPresenter, fileDtos, this, R.layout.item_storyedit_file);
        rv_edit_contenturifile.setAdapter(contentUriFileAdapter);
        rv_edit_contenturifile.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onBackPressed() {
        int statusOfUploaderView = fl_edit_loader.getVisibility();
        storyEditPresenter.onBackPressed(statusOfUploaderView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.PICK_PHOTO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        ClipData clipData = data.getClipData();

                        if (clipData != null) {
                            int clipDataSize = clipData.getItemCount();
                            ArrayList<FileDto> fileDtos = new ArrayList<>();

                            for (int i = 0; i < clipDataSize; i++) {
                                Uri uri = clipData.getItemAt(i).getUri();
                                String path = mediaStorePathUtil.getImagePath(uri);

                                FileDto fileDto = new FileDto();
                                fileDto.setUri(uri);
                                fileDto.setPath(path);
                                fileDto.setType(DefaultFileFlag.PHOTO_TYPE);

                                java.io.File file = new java.io.File(path);
                                fileDto.setFile(file);

                                fileDtos.add(fileDto);
                            }

                            storyEditPresenter.onActivityResultForPhotoListResultOk(fileDtos);

                        } else {
                            Uri uri = data.getData();
                            String path = mediaStorePathUtil.getImagePath(uri);

                            FileDto fileDto = new FileDto();
                            fileDto.setUri(uri);
                            fileDto.setPath(path);
                            fileDto.setType(DefaultFileFlag.PHOTO_TYPE);

                            java.io.File file = new java.io.File(path);
                            fileDto.setFile(file);

                            storyEditPresenter.onActivityResultForPhotoResultOk(fileDto);
                        }

                        break;
                    case ActivityResultFlag.RESULT_CANCEL:
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

                        java.io.File file = new java.io.File(path);
                        fileDto.setFile(file);
                        java.io.File thumbnailFile = new java.io.File(bitmapUtil.getCacheFilePathByBitmap(bitmapUtil.getVideoThumbnailBitmapByContentUri(uri)));
                        fileDto.setThumbnail(thumbnailFile);
                        boolean isVideoFull = urlFileAdapter.isVideoFull();
                        storyEditPresenter.onActivityResultForVideoResultOk(fileDto, isVideoFull);

                        break;
                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;

            case ActivityResultFlag.PICK_VR_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        ClipData clipData = data.getClipData();

                        if (clipData != null) {
                            int clipDataSize = clipData.getItemCount();
                            ArrayList<FileDto> fileDtos = new ArrayList<>();

                            for (int i = 0; i < clipDataSize; i++) {
                                Uri uri = clipData.getItemAt(i).getUri();
                                String path = mediaStorePathUtil.getImagePath(uri);

                                FileDto fileDto = new FileDto();
                                fileDto.setUri(uri);
                                fileDto.setPath(path);
                                fileDto.setType(DefaultFileFlag.VR360_TYPE);

                                java.io.File file = new java.io.File(path);
                                fileDto.setFile(file);

                                fileDtos.add(fileDto);
                            }
                            storyEditPresenter.onActivityResultForVR360ListResultOk(fileDtos);

                        } else {
                            Uri uri = data.getData();
                            String path = mediaStorePathUtil.getImagePath(uri);

                            FileDto fileDto = new FileDto();
                            fileDto.setUri(uri);
                            fileDto.setPath(path);
                            fileDto.setType(DefaultFileFlag.VR360_TYPE);

                            java.io.File file = new java.io.File(path);
                            fileDto.setFile(file);

                            storyEditPresenter.onActivityResultForVR360ResultOk(fileDto);
                        }

                        break;
                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;
        }
    }


    @Override
    public void showUploaderScreen() {
        fl_edit_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneUploaderScreen() {
        fl_edit_loader.setVisibility(View.GONE);
    }

    @Override
    public void showFileSizeText(String message) {
        tv_edit_filesize.setText(message);
    }


    @Override
    public void showFileTotalSizeText(String message) {
        tv_edit_filetotalsize.setText(message);
    }

    @Override
    public void showUploaderData(int bytesRead) {
        sb_edit_loader.setProgress(bytesRead);
    }

    @Override
    public void setUploaderMax(long maxSize) {
        sb_edit_loader.setMax((int) maxSize);
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
    public void showFileSizePlusText() {
        int size = Integer.parseInt(tv_edit_filesize.getText().toString());
        size += 1;

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("size", size);
        message.setData(bundle);

        uploaderHandler.sendMessage(message);
    }

    @Override
    public void setContentAddTextChangedListener() {
        et_edit_content.addTextChangedListener(this);
    }

    @Override
    public void setSubmitButtonColorPointColor() {
        tv_edit_submit.setTextColor(pointColor);
    }

    @Override
    public void setSubmitButtonColorDarkGray() {
        tv_edit_submit.setTextColor(darkGray);
    }

    @Override
    public void setSubmitButtonClickable() {
        tv_edit_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonUnclickable() {
        tv_edit_submit.setClickable(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence content, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable e) {
        String content = et_edit_content.getText().toString();
        storyEditPresenter.afterTextChanged(content);
    }


    public static class UploaderHandler extends Handler {
        private StoryEditView storyEditView;

        public UploaderHandler(StoryEditView storyEditView) {
            this.storyEditView = storyEditView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int bytesRead = bundle.getInt("bytesRead", 0);
            int size = bundle.getInt("size", 0);

            if (bytesRead > 0) {
                storyEditView.showUploaderData(bytesRead);
            }

            if (size > 0) {
                storyEditView.showFileSizeText(size + "");
            }

        }
    }

    @Override
    public void showExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFlag.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToBackWithStory(Story story) {
        Intent intent = getIntent();
        intent.putExtra("story", story);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    @Override
    public void setUrlFileAdapterIsVideoFull(boolean isVideoFull) {
        urlFileAdapter.setVideoFull(isVideoFull);
    }

    @Override
    public void setEditTextSelection(){
        int length = et_edit_content.getText().length();
        et_edit_content.setSelection(length);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionFlag.WRITE_EXTERNAL_STORAGE:
                storyEditPresenter.onRequestPermissionsResultForWriteExternalStorage(grantResults);
                break;
        }
    }
}
