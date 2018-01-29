package com.stm.story.create.presenter.impl;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.flag.ViewFlag;
import com.stm.story.create.interactor.StoryCreateInteractor;
import com.stm.story.create.interactor.impl.StoryCreateInteractorImpl;
import com.stm.story.create.presenter.StoryCreatePresenter;
import com.stm.story.create.view.StoryCreateView;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-07-11.
 */

public class StoryCreatePresenterImpl implements StoryCreatePresenter {
    private StoryCreateInteractor storyCreateInteractor;
    private StoryCreateView storyCreateView;

    public StoryCreatePresenterImpl(StoryCreateView storyCreateView) {
        this.storyCreateView = storyCreateView;
        this.storyCreateInteractor = new StoryCreateInteractorImpl(this);
    }

    @Override
    public void init(User user) {
        String accessToken = user.getAccessToken();

        storyCreateInteractor.setUser(user);
        storyCreateInteractor.setStoryRepository(accessToken);
        storyCreateView.setContentAddTextChangedListener();

        ArrayList<FileDto> fIleDtos = storyCreateInteractor.getFileDtos();
        storyCreateView.showExternalStoragePermission();
        storyCreateView.setHashTagHelper();
        storyCreateView.setFileDtoItem(fIleDtos);
    }

    @Override
    public void onHashTagClicked(String message) {
        storyCreateView.showMessage(message);
    }

    @Override
    public void onClickSubmit(Story story, boolean isVideoFull) {
        ArrayList<FileDto> fIleDtos = storyCreateInteractor.getFileDtos();
        int fIleDtoSize = fIleDtos.size();
        User user = storyCreateInteractor.getUser();
        story.setUser(user);

        if (fIleDtoSize > 0) {
            storyCreateView.showUploaderScreen();
            if (isVideoFull) {
                fIleDtoSize += 1;
            }
            storyCreateView.showFileTotalSizeText(fIleDtoSize + "");
            storyCreateInteractor.getStoryIdByUploadingStoryAndFileDtos(story, fIleDtos);
        } else {
            storyCreateView.showProgressDialog();
            storyCreateInteractor.getStoryIdByUploadingStory(story);
        }
    }

    @Override
    public void onClickPhoto() {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        if (fileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyCreateView.navigateToMultiMediaStoreForPhoto();
        }
    }

    @Override
    public void onClickVR360() {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        if (fileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyCreateView.navigateToMultiMediaStoreForVR360();
        }
    }

    @Override
    public void onActivityResultForPhotoResultOk(FileDto newFileDto) {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        if (fileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyCreateInteractor.setFileDtosAdd(newFileDto);
            storyCreateView.showFileAdapterNotifyItemInserted(fileDtoSize);
        }
    }

    @Override
    public void onActivityResultForPhotoListResultOk(ArrayList<FileDto> newFileDtos) {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        int newFileDtoSize = newFileDtos.size();
        if (fileDtoSize + newFileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyCreateInteractor.setFileDtosAddAll(newFileDtos);
            storyCreateView.showFileAdapterNotifyItemRangeInserted(fileDtoSize, newFileDtoSize);
        }
    }

    @Override
    public void onActivityResultForVR360ResultOk(FileDto newFileDto) {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        if (fileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyCreateInteractor.setFileDtosAdd(newFileDto);
            storyCreateView.showFileAdapterNotifyItemInserted(fileDtoSize);
            storyCreateView.showFileAdapterNotifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResultForVR360ListResultOk(ArrayList<FileDto> newFileDtos) {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        int newFileDtoSize = newFileDtos.size();
        if (fileDtoSize + newFileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyCreateInteractor.setFileDtosAddAll(newFileDtos);
            storyCreateView.showFileAdapterNotifyItemRangeInserted(fileDtoSize, newFileDtoSize);
            storyCreateView.showFileAdapterNotifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResultForVideoResultOk(FileDto newFileDto) {
        ArrayList<FileDto> fileDtos = storyCreateInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        long fileLength = newFileDto.getFile().length();

        if (fileDtoSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyCreateView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else if (fileLength > DefaultFileFlag.MAX_FILE_SIZE) {
            storyCreateView.showMessage("동영상 파일은 최대 20MB 크기까지 등록이 가능합니다.");
        } else {
            storyCreateInteractor.setFileDtosAdd(newFileDto);
            storyCreateView.showFileAdapterNotifyItemInserted(fileDtoSize);
        }
    }

    @Override
    public void onContentTextChanged(CharSequence content) {
        if (content.length() != 0) {
            storyCreateView.setSubmitButtonColorPointColor();
            storyCreateView.setSubmitButtonClickable();
        } else {
            storyCreateView.setSubmitButtonColorDarkGray();
            storyCreateView.setSubmitButtonUnclickable();
        }
    }

    @Override
    public void onClickPlayerButton(FileDto fileDto) {
        storyCreateView.navigateToVideoPlayerActivity(fileDto);
    }


    @Override
    public void onSuccessGetStoryIdByUploadingStory(long storyId) {
        storyCreateView.goneProgressDialog();
        storyCreateView.navigateToBackWithStoryId(storyId);
    }


    @Override
    public void onSuccessGetStoryIdByUploadingStoryAndFileDtos(long storyId) {
        storyCreateView.navigateToBackWithStoryId(storyId);
    }

    @Override
    public void onBackPressed(int statusOfUploaderView) {
        if (statusOfUploaderView == ViewFlag.VIEW_GONE) {
            storyCreateView.navigateToBack();
        }
    }
    @Override
    public void onUpdateProgress(long bytesRead) {
        storyCreateView.handleUploaderData(bytesRead);
    }
    @Override
    public void onStartProgress(long totalSize) {
        storyCreateView.setUploaderMax(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        storyCreateView.setUploaderMax(0);
        storyCreateView.showFileSizePlusText();
    }

    @Override
    public void onErrorGetStoryIdByUploadingStory() {
        storyCreateView.goneUploaderScreen();
    }

    @Override
    public void onClickFileAdapterRemoveButton(int position) {
        storyCreateView.setFileAdapterDelete(position);
        storyCreateView.showFileAdapterNotifyDataSetChanged();
        storyCreateView.showMessage("파일이 삭제 되었습니다.");
    }

    @Override
    public void onClickVideo(boolean isVideoFull) {
        if (isVideoFull) {
            storyCreateView.showMessage("동영상은 1개까지 등록이 가능합니다.");
        } else {
            storyCreateView.navigateToMultiMediaStoreForVideo();
        }
    }


    @Override
    public void onClickHashTag() {
        String content = " #";
        storyCreateView.showEditTextContent(content);
        storyCreateView.setEditTextSelection();
    }

    @Override
    public void onRequestPermissionsResultForReadExternalStorage(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            storyCreateView.showMessage("권한을 허가해주세요.");
            storyCreateView.navigateToBack();
        }
    }

    @Override
    public void onClickClose() {
        storyCreateView.navigateToBack();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            storyCreateView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            storyCreateView.showMessage(httpErrorDto.message());
        }
    }


}
