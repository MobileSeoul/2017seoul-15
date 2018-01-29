package com.stm.main.fragment.main.search.base.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onResume(User user);

    void onClickBack();

    void onClickClear();

    void onTextChanged(String message);

    void onChangeFragment();
}
