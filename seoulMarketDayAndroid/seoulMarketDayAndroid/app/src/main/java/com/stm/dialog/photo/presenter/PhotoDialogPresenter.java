package com.stm.dialog.photo.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface PhotoDialogPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);
    void init(User user, String avatar, String cover, List<File> files, File file, int position);

    void onClickClose();

    void onClickDownload(String filePath, int position);

    void onPageSelected(int position);

    void onRequestPermissionsResultForWriteExternalStorage(int[] grantResults);

    void onSuccessSetFileDownloaded(String filePath);

    void onSuccessUpdateFileByHits();

}
