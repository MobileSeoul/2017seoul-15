package com.stm.dialog.opinioncategory.interactor;

import com.stm.common.dao.OpinionCategory;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionCategoryDialogInteractor {
    void setOpinionCategoryRepository();

    void getAllOpinionCategories();

    List<OpinionCategory> getOpinionCategories();

    void setOpinionCategories(List<OpinionCategory> opinionCategories);
}
