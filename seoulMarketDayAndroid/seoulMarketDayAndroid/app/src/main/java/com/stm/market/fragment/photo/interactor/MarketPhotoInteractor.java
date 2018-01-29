package com.stm.market.fragment.photo.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketPhotoInteractor {
    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);

    List<File> getFiles();

    void setFiles(List<File> files);

    void setFilesAddAll(List<File> newFiles);

    void setMarketRepository(String accessToken);

    void setMarketRepository();

    void setFileRepository(String accessToken);

    void setFileRepository();

    void getFileListByIdAndTypeAndOffset(long id, int type, long offset);

    void updateFileByHits(File file, int position);
}
