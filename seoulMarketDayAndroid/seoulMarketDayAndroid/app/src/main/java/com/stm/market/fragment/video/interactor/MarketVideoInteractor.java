package com.stm.market.fragment.video.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketVideoInteractor {
    List<File> getFiles();

    void setFiles(List<File> files);

    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);

    void setMarketRepository();

    void setMarketRepository(String accessToken);

    void setFileRepository();

    void setFileRepository(String accessToken);

    void setFilesAddAll(List<File> newFiles);

    void getFileListByIdAndTypeAndOffset(long id, int type, long offset);

    void updateFileByHits(File file, int position);

}
