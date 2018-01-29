package com.stm.login.create.joincategory.base.presenter.impl;

import android.support.design.widget.TabLayout;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.create.joincategory.base.interactor.JoinCategoryInteractor;
import com.stm.login.create.joincategory.base.interactor.impl.JoinCategoryInteractorImpl;
import com.stm.login.create.joincategory.base.presenter.JoinCategoryPresenter;
import com.stm.login.create.joincategory.base.view.JoinCategoryView;


/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class JoinCategoryPresenterImpl implements JoinCategoryPresenter {
    private JoinCategoryView joinCategoryView;
    private JoinCategoryInteractor joinCategoryInteractor;

    public JoinCategoryPresenterImpl(JoinCategoryView joinCategoryView) {
        this.joinCategoryView = joinCategoryView;
        this.joinCategoryInteractor = new JoinCategoryInteractorImpl(this);
    }

    @Override
    public void init(User user) {
        joinCategoryView.setTabLayout();
        joinCategoryView.setTabAdapter(user);
        joinCategoryView.setToolbarLayout();
        joinCategoryView.showToolbarTitle("회원가입");
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            joinCategoryView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            joinCategoryView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        joinCategoryView.setTabSelected(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        joinCategoryView.setTabUnselected(tab);
    }

    @Override
    public void onResume(User user) {
        if (user != null) {
            joinCategoryView.navigateToBack();
        }
    }

}
