package com.stm.story.create.presenter;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-07-11.
 */

public interface StoryCreatePresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onHashTagClicked(String message);

    void onClickSubmit(Story story, boolean isVideoFull);

    void onClickPhoto();

    void onClickHashTag();

    void onRequestPermissionsResultForReadExternalStorage(int[] grantResults);

    void onClickClose();

    void onClickVR360();

    void onActivityResultForPhotoResultOk(FileDto newFileDto);

    void onActivityResultForPhotoListResultOk(ArrayList<FileDto> newFileDtos);

    void onActivityResultForVR360ResultOk(FileDto newFileDto);

    void onActivityResultForVR360ListResultOk(ArrayList<FileDto> newFileDtos);

    void onClickFileAdapterRemoveButton(int position);

    void onClickVideo(boolean isVideoFull);

    void onActivityResultForVideoResultOk(FileDto newFileDto);

    void onContentTextChanged(CharSequence content);

    void onClickPlayerButton(FileDto fileDto);

    void onUpdateProgress(long bytesRead);

    void onSuccessGetStoryIdByUploadingStory(long storyId);

    void onSuccessGetStoryIdByUploadingStoryAndFileDtos(long storyId);

    void onBackPressed(int statusOfUploaderView);

    void onStartProgress(long totalSize);

    void onDestroyProgress();

    void onErrorGetStoryIdByUploadingStory();
}
