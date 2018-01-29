package com.stm.main.base.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.PermissionFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.main.base.interactor.MainInteractor;
import com.stm.main.base.interactor.impl.MainInteractorImpl;
import com.stm.main.base.presenter.MainPresenter;
import com.stm.main.base.view.MainView;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl(this);
    }

    @Override
    public void init(User user) {
        mainInteractor.setUser(user);
        mainView.setTabLayout();
        mainView.showLocationPermission();
        mainView.setToolbar();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            mainView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            mainView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onRequestPermissionsResultForAccessFineLocation(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PermissionFlag.PERMISSION_DENIED) {
            //권한 등록 실패
        }
        User user = mainInteractor.getUser();
        mainView.setTabAdapter(user);
    }

    @Override
    public void onChangeForLoginAndLogout(User user) {
        mainInteractor.setUser(user);

        mainView.setTabAdapterUser(user);
        mainView.showProgressDialog();
        mainView.setTabAdapterNotifyDataSetChanged();
        mainView.goneProgressDialog();
    }

    @Override
    public void onClickSearch() {
        mainView.navigateToSearchActivity();
    }


}
