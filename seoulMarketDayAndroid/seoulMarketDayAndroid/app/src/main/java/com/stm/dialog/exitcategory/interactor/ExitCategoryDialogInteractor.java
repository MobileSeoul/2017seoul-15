package com.stm.dialog.exitcategory.interactor;

import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitCategoryDialogInteractor {
    User getUser();

    void setUser(User user);

    List<ExitCategory> getExitCategories();

    void setExitCategories(List<ExitCategory> exitCategories);

    void setExitCategoryRepository(String accessToken);

    void getExitCategoryList();
}
