package com.stm.dialog.exitcategory.presenter;

import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitCategoryDialogPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user);

    void onClickExitCategory(ExitCategory exitCategory);

    void onSuccessGetExitCategoryList(List<ExitCategory> exitCategories);
}
