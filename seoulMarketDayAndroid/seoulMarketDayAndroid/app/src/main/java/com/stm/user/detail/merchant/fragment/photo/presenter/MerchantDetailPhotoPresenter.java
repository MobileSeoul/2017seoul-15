package com.stm.user.detail.merchant.fragment.photo.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public interface MerchantDetailPhotoPresenter {
    void onCreateView();

    void onScrollChange(int difference);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, User storyUser);

    void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newPhotos);

    void onClickPhoto(File file, int position);

    void onSuccessUpdateFileByHits(int position);
}
