package com.stm.story.edit.view;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dto.FileDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface StoryEditView {
    void showMessage(String message);

    void showEditTextContent(Story story);

    void showEditTextContent(String message);

    void setHashTagHelper();

    void onClickClose();

    void onClickSubmit();

    void onClickPhoto();

    void onClickVideo();

    void onClickVR360();

    void onClickHashTag();

    void navigateToMultiMediaStoreForVR360();

    void showUrlFileAdapterNotifyDataSetChanged();

    void navigateToMultiMediaStoreForVideo();

    void setContentUriFileAdapterItem(ArrayList<FileDto> fileDto);

    void setUrlFileAdapterItem(List<File> files);

    void setUrlFileAdapterNotifyItemRemoved(int position);

    void navigateToMultiMediaStoreForPhoto();

    void showContentUriFileAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void showContentUriFileAdapterNotifyItemInserted(int position);

    void setContentUriFileAdapterNotifyItemRemoved(int position);

    void showProgressDialog();

    void goneProgressDialog();

    void setContentUriFileAdapterVideoFull(boolean isVideoFull);

    void showUploaderScreen();

    void goneUploaderScreen();

    void showFileSizeText(String message);

    void showFileTotalSizeText(String message);

    void showUploaderData(int bytesRead);

    void setUploaderMax(long maxSize);

    void handleUploaderData(long bytesRead);

    void showFileSizePlusText();

    void setContentAddTextChangedListener();

    void setSubmitButtonColorPointColor();

    void setSubmitButtonClickable();

    void setSubmitButtonColorDarkGray();

    void setSubmitButtonUnclickable();

    void showExternalStoragePermission();

    void navigateToBack();

    void navigateToBackWithStory(Story story);

    void setUrlFileAdapterIsVideoFull(boolean isVideoFull);

    void setEditTextSelection();
}
