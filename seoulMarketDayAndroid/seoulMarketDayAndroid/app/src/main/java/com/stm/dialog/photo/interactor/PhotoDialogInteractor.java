package com.stm.dialog.photo.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface PhotoDialogInteractor {

    void setStoryFileDownloadRepository();

    void setCommentFileDownloadRepository();

    User getUser();

    void setUser(User user);

    void setFileRepository(String accessToken);

    void setFileRepository();

    List<File> getFiles();

    void setFiles(List<File> files);

    File getFile();

    void setFile(File file);

    int getPosition();

    void setPosition(int position);


    String getAvatar();

    void setAvatar(String avatar);

    String getCover();

    void setCover(String cover);

    void setFileDownloaded(String url, String filePath);

    void setCoverFileDownloadRepository();

    void setAvatarFileDownloadRepository();

    void updateFileByHits(File file);
}
