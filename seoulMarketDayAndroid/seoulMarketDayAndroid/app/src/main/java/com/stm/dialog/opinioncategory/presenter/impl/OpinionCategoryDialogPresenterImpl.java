package com.stm.dialog.opinioncategory.presenter.impl;

import com.stm.common.dao.OpinionCategory;
import com.stm.common.dto.HttpErrorDto;
import com.stm.dialog.opinioncategory.interactor.OpinionCategoryDialogInteractor;
import com.stm.dialog.opinioncategory.interactor.impl.OpinionCategoryDialogInteractorImpl;
import com.stm.dialog.opinioncategory.presenter.OpinionCategoryDialogPresenter;
import com.stm.dialog.opinioncategory.view.OpinionCategoryDialogView;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionCategoryDialogPresenterImpl implements OpinionCategoryDialogPresenter {

    private OpinionCategoryDialogView opinionCategoryDialogView;
    private OpinionCategoryDialogInteractor opinionCategoryDialogInteractor;

    public OpinionCategoryDialogPresenterImpl(OpinionCategoryDialogView opinionCategoryDialogView) {
        this.opinionCategoryDialogView = opinionCategoryDialogView;
        this.opinionCategoryDialogInteractor  = new OpinionCategoryDialogInteractorImpl(this);
    }

    @Override
    public void init() {
        opinionCategoryDialogInteractor.setOpinionCategoryRepository();
        opinionCategoryDialogInteractor.getAllOpinionCategories();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            opinionCategoryDialogView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            opinionCategoryDialogView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetOpinionCategories(List<OpinionCategory> opinionCategories) {
        opinionCategoryDialogInteractor.setOpinionCategories(opinionCategories);
        opinionCategoryDialogView.setOpinionCategoryDialogAdapterItem(opinionCategories);
    }

    @Override
    public void onClickOpinionCategory(OpinionCategory opinionCategory) {
        opinionCategoryDialogView.navigateToBackWithResultOk(opinionCategory);
    }
}
