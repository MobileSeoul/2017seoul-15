package com.stm.user.create.opinion.presenter;

import com.stm.common.dao.OpinionCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionPresenter {
    void onClickBack();

    void init(User user);

    void onClickOpinionSubmit(String content);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onClickOpinionCategory();

    void onActivityResultForOpinionCategoryDialogResultOk(OpinionCategory opinionCategory);

    void onSuccessInsertOpinion();

    void afterTextChanged(String message);
}
