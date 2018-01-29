package com.stm.market.fragment.photo.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketPhotoPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Market market);

    void onCreateView();

    void onClickPhoto(File file, int position);

    void onSuccessGetFileListByIdAndTypeAndOffset(List<File> files);

    void onSuccessUpdateFileByHits(int position);

    void onScrollChange(int difference);
}
