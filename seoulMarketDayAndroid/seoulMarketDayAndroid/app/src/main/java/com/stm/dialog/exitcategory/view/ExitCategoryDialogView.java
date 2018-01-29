package com.stm.dialog.exitcategory.view;

import com.stm.common.dao.ExitCategory;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitCategoryDialogView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setExitCategoryDialogAdapterItem(List<ExitCategory> exitCategories);

    void navigateToBackWithResultOk(ExitCategory exitCategory);
}
