package com.stm.story.create.view;

import com.stm.common.dto.FileDto;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-07-11.
 */

public interface StoryCreateView {
    void setSubmitButtonClickable();

    void setSubmitButtonUnclickable();

    void setUploaderMax(long maxSize);

    void setSubmitButtonColorPointColor();

    void setSubmitButtonColorDarkGray();

    void setContentAddTextChangedListener();

    void setFileDtoItem(ArrayList<FileDto> fileDtos);

    void showFileAdapterNotifyDataSetChanged();

    void showFileAdapterNotifyItemInserted(int position);

    void showFileAdapterNotifyItemRangeInserted(int positionStart, int itemCount);

    void setHashTagHelper();

    void showExternalStoragePermission();

    void navigateToMultiMediaStoreForPhoto();

    void navigateToMultiMediaStoreForVR360();

    void navigateToMultiMediaStoreForVideo();

    void onClickSubmit();

    void onClickClose();

    void onClickPhoto();

    void onClickVideo();

    void onClickHashTag();

    void onClickVR360();

    void showProgressDialog();

    void goneProgressDialog();

    void showMessage(String message);

    void showFileSizeText(String message);

    void showFileTotalSizeText(String message);

    void navigateToBack();

    void setFileAdapterDelete(int position);

    void showEditTextContent(String content);

    void setEditTextSelection();

    void navigateToVideoPlayerActivity(FileDto fileDto);

    void showUploaderScreen();

    void goneUploaderScreen();

    void showUploaderData(int bytesRead);

    void handleUploaderData(long bytesRead);

    void showFileSizePlusText();

    void navigateToBackWithStoryId(long storyId);
}
