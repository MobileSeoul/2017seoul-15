package com.stm.story.create.activity;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Story;
import com.stm.common.dao.StoryTag;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.common.util.MediaStorePathUtil;
import com.stm.common.util.ToastUtil;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.create.adapter.FileAdapter;
import com.stm.story.create.presenter.StoryCreatePresenter;
import com.stm.story.create.presenter.impl.StoryCreatePresenterImpl;
import com.stm.story.create.view.StoryCreateView;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-11.
 */

public class StoryCreateActivity extends Activity implements StoryCreateView, TextWatcher {
    private StoryCreatePresenter storyCreatePresenter;
    private SharedPrefersManager sharedPrefersManager;
    private ToastUtil toastUtil;
    private HashTagHelper hashTagHelper;
    private MediaStorePathUtil mediaStorePathUtil;
    private BitmapUtil bitmapUtil;
    private ProgressDialog progressDialog;


    private Handler progressDialogHandler;
    private UploaderHandler uploaderHandler;
    private FileAdapter fileAdapter;

    @BindView(R.id.et_write_text)
    EditText et_write_text;

    @BindView(R.id.rv_write_multimedia)
    RecyclerView rv_write_multimedia;

    @BindView(R.id.tv_write_submit)
    TextView tv_write_submit;

    @BindView(R.id.fl_write_loader)
    FrameLayout fl_write_loader;

    @BindView(R.id.sb_write_loader)
    SeekBar sb_write_loader;


    @BindView(R.id.tv_write_filesize)
    TextView tv_write_filesize;

    @BindView(R.id.tv_write_filetotalsize)
    TextView tv_write_filetotalsize;

    @BindColor(R.color.pointColor)
    int pointColor;

    @BindColor(R.color.darkGray)
    int darkGray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storycreate);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.mediaStorePathUtil = new MediaStorePathUtil(this);
        this.bitmapUtil = new BitmapUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.uploaderHandler = new UploaderHandler(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User storyUser = (User) getIntent().getSerializableExtra("storyUser");
        storyUser.setAccessToken(this.sharedPrefersManager.getUser().getAccessToken());
        this.storyCreatePresenter = new StoryCreatePresenterImpl(this);
        this.storyCreatePresenter.init(storyUser);
    }

    @Override
    public void setSubmitButtonClickable() {
        tv_write_submit.setClickable(true);
    }

    @Override
    public void setSubmitButtonUnclickable() {
        tv_write_submit.setClickable(false);
    }

    @Override
    public void setUploaderMax(long maxSize) {
        sb_write_loader.setMax((int) maxSize);
    }

    @Override
    public void setSubmitButtonColorPointColor() {
        tv_write_submit.setTextColor(pointColor);
    }

    @Override
    public void setSubmitButtonColorDarkGray() {
        tv_write_submit.setTextColor(darkGray);
    }


    @Override
    public void setContentAddTextChangedListener() {
        et_write_text.addTextChangedListener(this);
    }

    @Override
    public void setFileDtoItem(ArrayList<FileDto> fileDtos) {
        fileAdapter = new FileAdapter(storyCreatePresenter, fileDtos, this, R.layout.item_storycreate_file);
        rv_write_multimedia.setAdapter(fileAdapter);
        rv_write_multimedia.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showFileAdapterNotifyDataSetChanged() {
        fileAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFileAdapterNotifyItemInserted(int position) {
        fileAdapter.notifyItemInserted(position);
    }

    @Override
    public void showFileAdapterNotifyItemRangeInserted(int positionStart, int itemCount) {
        fileAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void setHashTagHelper() {
        hashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.pointColor), new HashTagHelper.OnHashTagClickListener() {
            @Override
            public void onHashTagClicked(String hashTag) {
                storyCreatePresenter.onHashTagClicked(hashTag);
            }
        });

        hashTagHelper.handle(et_write_text);
    }

    @Override
    public void showExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFlag.WRITE_EXTERNAL_STORAGE);
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
    public void navigateToMultiMediaStoreForVR360() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), ActivityResultFlag.PICK_VR_REQUEST);
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
    @OnClick(R.id.tv_write_submit)
    public void onClickSubmit() {
        String content = et_write_text.getText().toString();
        List<String> allHashTags = hashTagHelper.getAllHashTags();

        Story story = new Story();
        story.setContent(content);

        List<StoryTag> storyTags = story.convertToStroyTagsFromStrings(allHashTags);
        story.setStoryTags(storyTags);
        boolean isVideoFull = fileAdapter.isVideoFull();
        storyCreatePresenter.onClickSubmit(story, isVideoFull);
    }

    @Override
    @OnClick(R.id.ib_write_close)
    public void onClickClose() {
        storyCreatePresenter.onClickClose();
    }

    @Override
    @OnClick(R.id.ib_write_photo)
    public void onClickPhoto() {
        storyCreatePresenter.onClickPhoto();
    }

    @Override
    @OnClick(R.id.ib_write_video)
    public void onClickVideo() {
        boolean isVideoFull = fileAdapter.isVideoFull();
        storyCreatePresenter.onClickVideo(isVideoFull);
    }


    @Override
    @OnClick(R.id.ib_write_hashtag)
    public void onClickHashTag() {
        storyCreatePresenter.onClickHashTag();
    }

    @Override
    @OnClick(R.id.ib_write_vr)
    public void onClickVR360() {
        storyCreatePresenter.onClickVR360();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.PICK_PHOTO_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        ClipData clipdata = data.getClipData();
                        if (clipdata == null) {
                            Uri uri = data.getData();
                            String path = mediaStorePathUtil.getImagePath(uri);
                            FileDto fileDto = new FileDto();
                            fileDto.setUri(uri);
                            fileDto.setPath(path);
                            fileDto.setType(DefaultFileFlag.PHOTO_TYPE);

                            File file = new File(path);
                            fileDto.setFile(file);

                            storyCreatePresenter.onActivityResultForPhotoResultOk(fileDto);

                        } else {
                            int clipDataSize = clipdata.getItemCount();
                            ArrayList<FileDto> fileDtos = new ArrayList<>();
                            for (int i = 0; i < clipDataSize; i++) {
                                Uri uri = clipdata.getItemAt(i).getUri();
                                String path = mediaStorePathUtil.getImagePath(uri);

                                FileDto fileDto = new FileDto();
                                fileDto.setUri(uri);
                                fileDto.setPath(path);
                                fileDto.setType(DefaultFileFlag.PHOTO_TYPE);

                                File file = new File(path);
                                fileDto.setFile(file);

                                fileDtos.add(fileDto);
                            }

                            storyCreatePresenter.onActivityResultForPhotoListResultOk(fileDtos);

                        }
                        break;
                }
                break;

            case ActivityResultFlag.PICK_VR_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        ClipData clipdata = data.getClipData();
                        if (clipdata == null) {
                            Uri uri = data.getData();
                            String path = mediaStorePathUtil.getImagePath(uri);

                            FileDto fileDto = new FileDto();
                            fileDto.setUri(uri);
                            fileDto.setPath(path);
                            fileDto.setType(DefaultFileFlag.VR360_TYPE);

                            File file = new File(path);
                            fileDto.setFile(file);

                            storyCreatePresenter.onActivityResultForVR360ResultOk(fileDto);

                        } else {
                            int clipDataSize = clipdata.getItemCount();
                            ArrayList<FileDto> fileDtos = new ArrayList<>();

                            for (int i = 0; i < clipDataSize; i++) {
                                Uri uri = clipdata.getItemAt(i).getUri();
                                String path = mediaStorePathUtil.getImagePath(uri);

                                FileDto fileDto = new FileDto();
                                fileDto.setUri(uri);
                                fileDto.setPath(path);
                                fileDto.setType(DefaultFileFlag.VR360_TYPE);

                                File file = new File(path);
                                fileDto.setFile(file);

                                fileDtos.add(fileDto);

                            }
                            storyCreatePresenter.onActivityResultForVR360ListResultOk(fileDtos);

                        }
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

                        storyCreatePresenter.onActivityResultForVideoResultOk(fileDto);
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionFlag.WRITE_EXTERNAL_STORAGE:
                storyCreatePresenter.onRequestPermissionsResultForReadExternalStorage(grantResults);
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }


    @Override
    public void showFileSizeText(String message) {
        tv_write_filesize.setText(message);
    }

    @Override
    public void showFileTotalSizeText(String message) {
        tv_write_filetotalsize.setText(message);
    }

    @Override
    public void navigateToBack() {
        Intent intent = getIntent();
        setResult(ActivityResultFlag.RESULT_CANCEL, intent);
        finish();
    }

    @Override
    public void setFileAdapterDelete(int position) {
        fileAdapter.setFileDelete(position);
    }

    @Override
    public void showEditTextContent(String content) {
        et_write_text.append(content);
    }

    @Override
    public void setEditTextSelection(){
        int length = et_write_text.getText().length();
        et_write_text.setSelection(length);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence content, int start, int before, int count) {
        storyCreatePresenter.onContentTextChanged(content);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void navigateToVideoPlayerActivity(FileDto fileDto) {
        String path = fileDto.getPath();
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("uri", path);
        startActivity(intent);
    }

    @Override
    public void showUploaderScreen() {
        fl_write_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneUploaderScreen() {
        fl_write_loader.setVisibility(View.GONE);
    }

    @Override
    public void showUploaderData(int bytesRead) {
        sb_write_loader.setProgress(bytesRead);
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
        int size = Integer.parseInt(tv_write_filesize.getText().toString());
        size += 1;

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("size", size);
        message.setData(bundle);

        uploaderHandler.sendMessage(message);
    }

    @Override
    public void navigateToBackWithStoryId(long storyId) {
        Intent intent = getIntent();
        intent.putExtra("storyId", storyId);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        int statusOfUploaderView = fl_write_loader.getVisibility();
        storyCreatePresenter.onBackPressed(statusOfUploaderView);
    }

    public static class UploaderHandler extends Handler {
        private StoryCreateView storyCreateView;

        public UploaderHandler(StoryCreateView storyCreateView) {
            this.storyCreateView = storyCreateView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int bytesRead = bundle.getInt("bytesRead", 0);
            int size = bundle.getInt("size", 0);

            if (bytesRead > 0) {
                storyCreateView.showUploaderData(bytesRead);
            }

            if (size > 0) {
                storyCreateView.showFileSizeText(size + "");
            }

        }
    }
}
