package com.stm.story.edit.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.dto.StoryDto;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface StoryEditPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Story story);

    void onClickHashTag();

    void onClickRemoveForUrlFile(File file, int position);


    void onClickRemoveForContentUriFile(FileDto fileDto, int position);

    void onClickPhoto();

    void onActivityResultForPhotoListResultOk(ArrayList<FileDto> fileDtos);

    void onClickVR360();

    void onClickVideo(boolean isVideoFull);

    void onActivityResultForPhotoResultOk(FileDto fileDto);

    void onActivityResultForVideoResultOk(FileDto fileDto, boolean isVideoFull);

    void onActivityResultForVR360ResultOk(FileDto fileDto);

    void onActivityResultForVR360ListResultOk(ArrayList<FileDto> fileDtos);

    void onClickSubmit(StoryDto content, boolean allHashTags);

    void onUpdateProgress(long bytesRead);

    void onStartProgress(long totalSize);

    void onDestroyProgress();

    void afterTextChanged(String content);

    void onHashTagClicked(String hashTag);

    void onBackPressed(int statusOfUploaderView);

    void onRequestPermissionsResultForWriteExternalStorage(int[] grantResults);

    void onErrorGetStoryByUploadingStoryDto();

    void onSuccessGetStoryByUploadingStoryDtoAndFileDtos(Story story);

    void onSuccessGetStoryByUploadingStoryDto(Story story);
}
