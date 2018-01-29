package com.stm.market.fragment.video.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketVideoPresenter {
    void init(User user, Market market);

    void onCreateView();

    void onScrollChange(int difference);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onClickVideo(File file, int position);

    void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newFiles);

    void onSuccessUpdateFileByHits(int position);
}
