package com.stm.main.base.presenter;


import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public interface MainPresenter {

    void init(User user);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onRequestPermissionsResultForAccessFineLocation(int[] grantResults);

    void onChangeForLoginAndLogout(User user);

    void onClickSearch();
}
