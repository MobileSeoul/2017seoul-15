package com.stm.market.fragment.information.presenter.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.market.fragment.information.interactor.MarketInfoInteractor;
import com.stm.market.fragment.information.interactor.impl.MarketInfoInteractorImpl;
import com.stm.market.fragment.information.presenter.MarketInfoPresenter;
import com.stm.market.fragment.information.view.MarketInfoView;

/**
 * Created by Dev-0 on 2017-07-04.
 */

public class MarketInfoPresenterImpl implements MarketInfoPresenter {
    private MarketInfoView marketInfoView;
    private MarketInfoInteractor marketInfoInteractor;

    public MarketInfoPresenterImpl(MarketInfoView marketInfoView) {
        this.marketInfoView = marketInfoView;
        this.marketInfoInteractor = new MarketInfoInteractorImpl(this);
    }

    @Override
    public void init(User user, Market market) {
        marketInfoInteractor.setUser(user);
        marketInfoInteractor.setMarket(market);
    }

    @Override
    public void onCreateView() {
        Market market = marketInfoInteractor.getMarket();
        int storeCount = market.getStoreCount();
        int merchantCount = market.getUserCount();
        int followCount = market.getFollowerCount();
        String phone = market.getPhone();
        String homepage = market.getHomepage();
        String subname = market.getSubName();
        String items = market.getTitleItems();

        marketInfoView.showStoreText(storeCount + "개의 점포가 있습니다.");
        marketInfoView.showMerchantText(merchantCount + "명의 상인이 있습니다.");
        marketInfoView.showFollowText(followCount + "명의 팔로워가 있습니다.");

        if (phone.length() > 0) {
            marketInfoView.showPhoneText(phone);
        } else {
            marketInfoView.gonePhone();
        }

        if (homepage.length() > 0) {
            marketInfoView.showHomepageText(homepage);
        } else {
            marketInfoView.goneHomepage();
        }

        if (subname.length() > 0) {
            marketInfoView.showSubnameText(subname);
        } else {
            marketInfoView.goneSubname();
        }

        if (items.length() > 0) {
            marketInfoView.showItemsText(items);
        } else {
            marketInfoView.goneItems();
        }
    }

    @Override
    public void onClickPhoneText(String phone) {
        marketInfoView.navigateToDial(phone);
    }

    @Override
    public void onClickHomepageText(String homepage) {
        marketInfoView.navigateToWeb(homepage);
    }
}
