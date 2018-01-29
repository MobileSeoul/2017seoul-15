package com.stm.dialog.exitcategory.presenter.impl;

import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.dialog.exitcategory.interactor.ExitCategoryDialogInteractor;
import com.stm.dialog.exitcategory.interactor.impl.ExitCategoryDialogInteractorImpl;
import com.stm.dialog.exitcategory.presenter.ExitCategoryDialogPresenter;
import com.stm.dialog.exitcategory.view.ExitCategoryDialogView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitCategoryDialogPresenterImpl implements ExitCategoryDialogPresenter {
    private ExitCategoryDialogInteractor exitCategoryDialogInteractor;
    private ExitCategoryDialogView exitCategoryDialogView;

    public ExitCategoryDialogPresenterImpl(  ExitCategoryDialogView exitCategoryDialogView) {
        this.exitCategoryDialogInteractor = new ExitCategoryDialogInteractorImpl(this);
        this.exitCategoryDialogView = exitCategoryDialogView;
    }

    @Override
    public void init(User user) {
        exitCategoryDialogView.showProgressDialog();
        exitCategoryDialogInteractor.setUser(user);

        String accessToken = user.getAccessToken();
        exitCategoryDialogInteractor.setExitCategoryRepository(accessToken);
        exitCategoryDialogInteractor.getExitCategoryList();
    }

    @Override
    public void onClickExitCategory(ExitCategory exitCategory) {
        exitCategoryDialogView.navigateToBackWithResultOk(exitCategory);
    }

    @Override
    public void onSuccessGetExitCategoryList(List<ExitCategory> exitCategories) {
        exitCategoryDialogView.setExitCategoryDialogAdapterItem(exitCategories);
        exitCategoryDialogView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            exitCategoryDialogView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            exitCategoryDialogView.showMessage(httpErrorDto.message());
        }
    }

}
