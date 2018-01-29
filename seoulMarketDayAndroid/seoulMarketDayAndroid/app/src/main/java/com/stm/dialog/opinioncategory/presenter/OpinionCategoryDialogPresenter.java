package com.stm.dialog.opinioncategory.presenter;

import com.stm.common.dao.OpinionCategory;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionCategoryDialogPresenter {
    void init();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetOpinionCategories(List<OpinionCategory> opinionCategories);

    void onClickOpinionCategory(OpinionCategory opinionCategory);
}
