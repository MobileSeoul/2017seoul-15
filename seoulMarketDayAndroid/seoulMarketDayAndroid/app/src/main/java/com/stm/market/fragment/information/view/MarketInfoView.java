package com.stm.market.fragment.information.view;

/**
 * Created by Dev-0 on 2017-07-04.
 */

public interface MarketInfoView {
    void showStoreText(String message);

    void showMerchantText(String message);

    void showFollowText(String message);

    void showPhoneText(String message);

    void showHomepageText(String message);

    void showSubnameText(String message);

    void showItemsText(String message);

    void gonePhone();

    void goneHomepage();

    void goneSubname();

    void goneItems();

    void onClickPhoneText();

    void onClickHomepageText();

    void navigateToWeb(String message);

    void navigateToDial(String message);
}
