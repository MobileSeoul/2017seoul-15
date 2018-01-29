package com.stm.user.detail.merchant.fragment.photo.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public interface MerchantDetailPhotoInteractor {
    User getUser();

    void setUser(User user);

    User getStoryUser();

    void setStoryUser(User storyUser);


    void setFilesAddAll(List<File> newFiles);

    void setUserRepository(String accessToken);

    void setUserRepository();


    void setFileRepository(String accessToken);

    void setFileRepository();

    void updateFileByHits(File file, int position);

    void getFileListByIdAndTypeAndOffset(long storyUserId, int type, long offset);

    List<File> getFiles();

    void setFiles(List<File> files);

    boolean isFirstChecked();

    void setFirstChecked(boolean firstChecked);


}
