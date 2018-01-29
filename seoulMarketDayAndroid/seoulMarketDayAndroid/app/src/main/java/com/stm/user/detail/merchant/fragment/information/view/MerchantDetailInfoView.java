package com.stm.user.detail.merchant.fragment.information.view;

import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface MerchantDetailInfoView {
    void showEmailText(String message);

    void showPhoneText(String message);

    void showGenderText(String message);

    void showIntroText(String message);


    void goneEmail();

    void gonePhone();

    void goneGender();

    void goneIntro();

    void onClickPhoneText();

    void showMessage(String message);

    void showFollowText(String message);

    void navigateToDial(String message);

}
