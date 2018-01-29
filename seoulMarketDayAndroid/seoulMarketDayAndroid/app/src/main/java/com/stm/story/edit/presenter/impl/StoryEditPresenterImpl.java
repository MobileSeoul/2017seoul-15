package com.stm.story.edit.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.dto.StoryDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.flag.ViewFlag;
import com.stm.story.edit.interactor.StoryEditInteractor;
import com.stm.story.edit.interactor.impl.StoryEditInteractorImpl;
import com.stm.story.edit.presenter.StoryEditPresenter;
import com.stm.story.edit.view.StoryEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class StoryEditPresenterImpl implements StoryEditPresenter {
    private StoryEditInteractor storyEditInteractor;
    private StoryEditView storyEditView;

    public StoryEditPresenterImpl(StoryEditView storyEditView) {
        this.storyEditInteractor = new StoryEditInteractorImpl(this);
        this.storyEditView = storyEditView;
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            storyEditView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            storyEditView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void init(User user, Story story) {
        storyEditView.showExternalStoragePermission();

        storyEditInteractor.setUser(user);
        storyEditInteractor.setStory(story);

        String accessToken = user.getAccessToken();
        storyEditInteractor.setStoryRepository(accessToken);

        List<File> files = story.getFiles();
        ArrayList<FileDto> fileDtos = storyEditInteractor.getFileDtos();

        int fileSize = files.size();
        storyEditInteractor.setPrevFileSize(fileSize);
        storyEditView.showEditTextContent(story);
        storyEditView.setHashTagHelper();
        storyEditView.setUrlFileAdapterItem(files);
        storyEditView.setContentUriFileAdapterItem(fileDtos);
        storyEditView.setContentAddTextChangedListener();
        storyEditView.setSubmitButtonUnclickable();
        storyEditView.setEditTextSelection();
    }

    @Override
    public void onClickHashTag() {
        String content = " #";
        storyEditView.showEditTextContent(content);
        storyEditView.setEditTextSelection();
    }

    @Override
    public void onClickRemoveForUrlFile(File file, int position) {
        Story story = storyEditInteractor.getStory();
        List<File> files = story.getFiles();
        File prevFile  =  files.get(position);
        int prevFileType = prevFile.getType();

        if(prevFileType == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE){
            storyEditView.setUrlFileAdapterIsVideoFull(false);
        }

        files.remove(position);

        storyEditInteractor.setRemoveFilesAdd(file);
        storyEditView.setUrlFileAdapterNotifyItemRemoved(position);

        boolean isEdited = storyEditInteractor.isEdited();
        if (!isEdited) {
            storyEditInteractor.setEdited(true);
            storyEditView.setSubmitButtonColorPointColor();
            storyEditView.setSubmitButtonClickable();
        }
    }

    @Override
    public void onClickRemoveForContentUriFile(FileDto fileDto, int position) {
        storyEditInteractor.setFileDtosRemoveAtThePosition(position);
        storyEditView.setContentUriFileAdapterNotifyItemRemoved(position);

        int type = fileDto.getType();
        if (type == DefaultFileFlag.VIDEO_TYPE) {
            storyEditView.setContentUriFileAdapterVideoFull(false);
        }

        List<FileDto> fileDtos =storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        int prevFileSize = storyEditInteractor.getPrevFileSize();

        Story story = storyEditInteractor.getStory();
        List<File> files = story.getFiles();
        int fileSize = files.size();

        if(fileDtoSize == 0 && fileSize == prevFileSize){
            storyEditView.setSubmitButtonColorDarkGray();
            storyEditView.setSubmitButtonUnclickable();
        }
    }

    @Override
    public void onClickPhoto() {
        List<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (fileDtoSize + prevFileSize > DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditView.navigateToMultiMediaStoreForPhoto();
        }
    }

    @Override
    public void onClickVideo(boolean isVideoFull) {
        List<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (isVideoFull) {
            storyEditView.showMessage("동영상은 1개까지 등록이 가능합니다.");
        } else if (fileDtoSize + prevFileSize > DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditView.navigateToMultiMediaStoreForVideo();
        }
    }

    @Override
    public void onClickVR360() {
        List<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (fileDtoSize + prevFileSize > DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditView.navigateToMultiMediaStoreForVR360();
        }
    }

    @Override
    public void onActivityResultForPhotoListResultOk(ArrayList<FileDto> newFileDtos) {
        List<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        int newFileDtoSize = newFileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (newFileDtoSize + fileDtoSize + prevFileSize > DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditInteractor.setFileDtosAddAll(newFileDtos);
            storyEditView.showContentUriFileAdapterNotifyItemRangeInserted(fileDtoSize, newFileDtoSize);

            boolean isEdited = storyEditInteractor.isEdited();
            if (!isEdited) {
                storyEditInteractor.setEdited(true);
                storyEditView.setSubmitButtonColorPointColor();
                storyEditView.setSubmitButtonClickable();
            }
        }
    }

    @Override
    public void onActivityResultForPhotoResultOk(FileDto fileDto) {
        ArrayList<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (fileDtoSize + prevFileSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditInteractor.setFileDtosAdd(fileDto);
            storyEditView.showContentUriFileAdapterNotifyItemInserted(fileDtoSize);

            boolean isEdited = storyEditInteractor.isEdited();
            if (!isEdited) {
                storyEditInteractor.setEdited(true);
                storyEditView.setSubmitButtonColorPointColor();
                storyEditView.setSubmitButtonClickable();
            }
        }
    }

    @Override
    public void onActivityResultForVideoResultOk(FileDto newFileDto, boolean isVideoFull) {
        ArrayList<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        long fileLength = newFileDto.getFile().length();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (fileDtoSize + prevFileSize > DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else if (fileLength > DefaultFileFlag.MAX_FILE_SIZE) {
            storyEditView.showMessage("동영상 파일은 최대 20MB 크기까지 등록이 가능합니다.");
        } else {
            storyEditInteractor.setFileDtosAdd(newFileDto);
            storyEditView.showContentUriFileAdapterNotifyItemInserted(fileDtoSize);

            boolean isEdited = storyEditInteractor.isEdited();
            if (!isEdited) {
                storyEditInteractor.setEdited(true);
                storyEditView.setSubmitButtonColorPointColor();
                storyEditView.setSubmitButtonClickable();
            }
        }

    }

    @Override
    public void onActivityResultForVR360ResultOk(FileDto fileDto) {
        ArrayList<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (fileDtoSize + prevFileSize >= DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditInteractor.setFileDtosAdd(fileDto);
            storyEditView.showContentUriFileAdapterNotifyItemInserted(fileDtoSize);

            boolean isEdited = storyEditInteractor.isEdited();
            if (!isEdited) {
                storyEditInteractor.setEdited(true);
                storyEditView.setSubmitButtonColorPointColor();
                storyEditView.setSubmitButtonClickable();
            }
        }
    }

    @Override
    public void onActivityResultForVR360ListResultOk(ArrayList<FileDto> newFileDtos) {
        List<FileDto> fileDtos = storyEditInteractor.getFileDtos();
        int fileDtoSize = fileDtos.size();
        int newFileDtoSize = newFileDtos.size();

        Story story = storyEditInteractor.getStory();
        List<File> prevFiles = story.getFiles();
        int prevFileSize = prevFiles.size();

        if (newFileDtoSize + fileDtoSize + prevFileSize > DefaultFileFlag.MAX_FILE_COUNT) {
            storyEditView.showMessage("파일은 최대 10개까지 등록이 가능합니다.");
        } else {
            storyEditInteractor.setFileDtosAddAll(newFileDtos);
            storyEditView.showContentUriFileAdapterNotifyItemRangeInserted(fileDtoSize, newFileDtoSize);

            boolean isEdited = storyEditInteractor.isEdited();
            if (!isEdited) {
                storyEditInteractor.setEdited(true);
                storyEditView.setSubmitButtonColorPointColor();
                storyEditView.setSubmitButtonClickable();
            }
        }
    }

    @Override
    public void onClickSubmit(StoryDto storyDto, boolean isVideoFull) {

        List<File> files = storyEditInteractor.getStory().getFiles();
        int fileSize = files.size();

        ArrayList<FileDto> fIleDtos = storyEditInteractor.getFileDtos();
        int fIleDtoSize = fIleDtos.size();

        List<File> removeFiles = storyEditInteractor.getRemoveFiles();
        storyDto.setRemoveFiles(removeFiles);

        Story prevStory = storyEditInteractor.getStory();
        long prevStoryId = prevStory.getId();
        User user = prevStory.getUser();

        Story story = storyDto.getStory();
        story.setUser(user);
        story.setId(prevStoryId);

        if (fIleDtoSize > 0) {
            storyEditView.showUploaderScreen();
            if (isVideoFull) {
                fIleDtoSize += 1;
            }
            storyEditView.showFileTotalSizeText(fIleDtoSize + "");
            storyEditInteractor.getStoryByUploadingStoryDtoAndFileDtos(storyDto, fIleDtos);
        } else {
            storyEditView.showProgressDialog();
            storyEditInteractor.getStoryByUploadingStoryDto(storyDto);
        }
    }

    @Override
    public void onUpdateProgress(long bytesRead) {
        storyEditView.handleUploaderData(bytesRead);
    }
    @Override
    public void onStartProgress(long totalSize) {
        storyEditView.setUploaderMax(totalSize);
    }

    @Override
    public void onDestroyProgress() {
        storyEditView.setUploaderMax(0);
        storyEditView.showFileSizePlusText();
    }

    @Override
    public void afterTextChanged(String content) {
        if (content.length() > 0) {
            Story story = storyEditInteractor.getStory();
            String prevContent = story.getContent();
            if (!prevContent.equals(content)) {
                boolean isEdited = storyEditInteractor.isEdited();
                if (!isEdited) {
                    storyEditInteractor.setEdited(true);
                    storyEditView.setSubmitButtonColorPointColor();
                    storyEditView.setSubmitButtonClickable();
                }
            } else {
                storyEditInteractor.setEdited(false);
                storyEditView.setSubmitButtonColorDarkGray();
                storyEditView.setSubmitButtonUnclickable();
            }
        } else {
            storyEditInteractor.setEdited(false);
            storyEditView.setSubmitButtonColorDarkGray();
            storyEditView.setSubmitButtonUnclickable();
        }
    }

    @Override
    public void onHashTagClicked(String hashTag) {

    }

    @Override
    public void onBackPressed(int statusOfUploaderView) {
        if (statusOfUploaderView == ViewFlag.VIEW_GONE) {
            storyEditView.navigateToBack();
        }
    }

    @Override
    public void onRequestPermissionsResultForWriteExternalStorage(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            storyEditView.showMessage("권한을 허가해주세요.");
            storyEditView.navigateToBack();
        }
    }

    @Override
    public void onErrorGetStoryByUploadingStoryDto() {
        storyEditView.goneUploaderScreen();
    }

    @Override
    public void onSuccessGetStoryByUploadingStoryDtoAndFileDtos(Story story) {
        storyEditView.navigateToBackWithStory(story);
    }

    @Override
    public void onSuccessGetStoryByUploadingStoryDto(Story story) {
        storyEditView.goneProgressDialog();
        storyEditView.navigateToBackWithStory(story);
    }

}
