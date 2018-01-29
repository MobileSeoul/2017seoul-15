package com.stm.user.detail.merchant.fragment.video.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.detail.merchant.fragment.video.adapter.MerchantDetailVideoAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public interface MerchantDetailVideoPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, User storyUser);

    void onCreateView();

    void onSuccessGetFileListByIdAndTypeAndOffset(List<File> files);

    void onClickVideo(File file, int position);

    void onScrollChange(int difference);

    void onSuccessUpdateFileByHits(int position);
}
