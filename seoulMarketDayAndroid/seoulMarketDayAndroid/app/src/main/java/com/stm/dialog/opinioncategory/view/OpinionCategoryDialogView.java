package com.stm.dialog.opinioncategory.view;

import com.stm.common.dao.OpinionCategory;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionCategoryDialogView {
    void showProgressDialog();

    void goneProgressDialog();

    void setOpinionCategoryDialogAdapterItem(List<OpinionCategory> opinionCategories);

    void showMessage(String message);

    void navigateToBackWithResultOk(OpinionCategory opinionCategory);
}
