package com.stm.user.create.opinion.presenter.impl;

import com.stm.common.dao.Opinion;
import com.stm.common.dao.OpinionCategory;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.create.opinion.interactor.OpinionInteractor;
import com.stm.user.create.opinion.interactor.impl.OpinionInteractorImpl;
import com.stm.user.create.opinion.presenter.OpinionPresenter;
import com.stm.user.create.opinion.view.OpinionVIew;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionPresenterImpl implements OpinionPresenter {
    private OpinionVIew opinionVIew;
    private OpinionInteractor opinionInteractor;

    public OpinionPresenterImpl(OpinionVIew opinionView) {
        this.opinionVIew = opinionView;
        this.opinionInteractor = new OpinionInteractorImpl(this);
    }

    @Override
    public void onClickBack() {
        opinionVIew.navigateToBack();
    }

    @Override
    public void init(User user) {
        opinionVIew.showProgressDialog();

        opinionInteractor.setUser(user);
        String accessToken = user.getAccessToken();
        opinionInteractor.setOpinionRepository(accessToken);
        opinionVIew.setToolbarLayout();
        opinionVIew.showToolbarTitle("의견 보내기");
        opinionVIew.setEditTextChangedListener();

        String name  = user.getName();
        opinionVIew.showUserName(name);

        opinionVIew.goneProgressDialog();
    }

    @Override
    public void onClickOpinionSubmit(String content) {
        opinionVIew.showProgressDialog();
        OpinionCategory opinionCategory = opinionInteractor.getOpinionCategory();
        if (opinionCategory != null) {
            if (content.length() > 0) {
                opinionVIew.showProgressDialog();
                User user = opinionInteractor.getUser();

                Opinion opinion = new Opinion();
                opinion.setUser(user);
                opinion.setOpinionCategory(opinionCategory);
                opinion.setContent(content);
                opinionInteractor.insertOpinion(opinion);
            } else {
                opinionVIew.showMessage("의견을 입력해주세요.");
            }
        } else {
            opinionVIew.showMessage("카테고리를 선택해주세요");
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            opinionVIew.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            opinionVIew.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onClickOpinionCategory() {
        opinionVIew.navigateToOpinionCategoryDialogActivity();
    }

    @Override
    public void onActivityResultForOpinionCategoryDialogResultOk(OpinionCategory opinionCategory) {
        opinionInteractor.setOpinionCategory(opinionCategory);
        String name = opinionCategory.getName();
        opinionVIew.showOpinionCategoryName(name);
    }

    @Override
    public void onSuccessInsertOpinion() {
        opinionVIew.goneProgressDialog();
        opinionVIew.showMessage("소중한 의견 감사합니다");
        opinionVIew.navigateToBack();
    }

    @Override
    public void afterTextChanged(String message) {
        int messageLength = message.length();
        if (messageLength > 0) {
            opinionVIew.setSubmitButtonActivated();
        } else {
            opinionVIew.setSubmitButtonInactivated();
        }
    }
}
