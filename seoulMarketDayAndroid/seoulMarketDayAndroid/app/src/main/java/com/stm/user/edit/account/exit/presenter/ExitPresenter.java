package com.stm.user.edit.account.exit.presenter;

import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onClickBack();

    void onClickAccountExitCategory();

    void onActivityResultForExitCategoryDialogResultOk(ExitCategory exitCategory);

    void onClickExitSubmit(String content);

    void onSuccessSetExit(Boolean isExist);

    void afterTextChanged(String message);
}
