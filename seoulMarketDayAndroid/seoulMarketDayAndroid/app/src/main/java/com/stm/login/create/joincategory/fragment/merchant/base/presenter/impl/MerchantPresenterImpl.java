package com.stm.login.create.joincategory.fragment.merchant.base.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.UserLevelFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.login.create.joincategory.fragment.merchant.base.interactor.MerchantInteractor;
import com.stm.login.create.joincategory.fragment.merchant.base.interactor.impl.MerchantInteractorImpl;
import com.stm.login.create.joincategory.fragment.merchant.base.presenter.MerchantPresenter;
import com.stm.login.create.joincategory.fragment.merchant.base.view.MerchantView;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class MerchantPresenterImpl implements MerchantPresenter {
    private MerchantView merchantView;
    private MerchantInteractor merchantInteractor;

    public MerchantPresenterImpl(MerchantView merchantView) {
        this.merchantView = merchantView;
        this.merchantInteractor = new MerchantInteractorImpl(this);
    }

    @Override
    public void onCreateView() {
//        merchantView.setJoinDeactivated();
    }

    @Override
    public void init(User user) {
        merchantInteractor.setUser(user);
    }

    @Override
    public void onActivityResultForSearchMarketResultOk(Market market) {
        merchantInteractor.setMarket(market);
        String name = market.getName();
        merchantView.showMarketName(name);
        merchantView.showJoinButton();
    }

    @Override
    public void onClickJoin() {
        User user = merchantInteractor.getUser();
        Market market = merchantInteractor.getMarket();

        if (user != null) {
            user.setMarket(market);
            user.setLevel(UserLevelFlag.MERCHANT);
            merchantInteractor.setUser(user);
            merchantInteractor.setUserRepository();
            merchantInteractor.setUser();
        } else {
            merchantView.navigateToJoinActivity(market);
        }
    }

    @Override
    public void onClickSearchMarket() {
        merchantView.navigateToSearchMarketActivity();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessSetUser() {
        User user = merchantInteractor.getUser();
        merchantView.setUser(user);
        merchantView.navigateToBack();
    }
}
