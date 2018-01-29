package com.stm.user.detail.merchant.fragment.video.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public interface MerchantDetailVideoInteractor {
    User getUser();

    void setUser(User user);

    User getStoryUser();

    void setStoryUser(User storyUser);

    List<File> getFiles();

    void setFiles(List<File> files);

    void setUserRepository(String accessToken);

    void setUserRepository();

    void setFileRepository(String accessToken);

    void setFileRepository();

    void getFileListByIdAndTypeAndOffset(long storyUserId, int type, long offset);

    void setFilesAddAll(List<File> newFiles);

    void updateFileByHits(File file, int position);
}
