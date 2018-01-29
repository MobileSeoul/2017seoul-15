package com.stm.user.detail.merchant.fragment.information.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.UserGenderFlag;
import com.stm.common.flag.UserPrivacyRangeFlag;
import com.stm.common.dto.HttpErrorDto;
import com.stm.user.detail.merchant.fragment.information.interactor.MerchantDetailInfoInteractor;
import com.stm.user.detail.merchant.fragment.information.interactor.impl.MerchantDetailInfoInteractorImpl;
import com.stm.user.detail.merchant.fragment.information.presenter.MerchantDetailInfoPresenter;
import com.stm.user.detail.merchant.fragment.information.view.MerchantDetailInfoView;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class MerchantDetailInfoPresenterImpl implements MerchantDetailInfoPresenter {
    private MerchantDetailInfoInteractor merchantDetailInfoInteractor;
    private MerchantDetailInfoView merchantDetailInfoView;

    public MerchantDetailInfoPresenterImpl(MerchantDetailInfoView merchantDetailInfoView) {
        this.merchantDetailInfoInteractor = new MerchantDetailInfoInteractorImpl(this);
        this.merchantDetailInfoView = merchantDetailInfoView;
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailInfoView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailInfoView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void init(User user, User storyUser) {
        merchantDetailInfoInteractor.setStoryUser(storyUser);
        merchantDetailInfoInteractor.setUser(user);
    }

    @Override
    public void onCreateView() {
        User storyUser = merchantDetailInfoInteractor.getStoryUser();

        String email = storyUser.getEmail();
        String phone = storyUser.getPhone();
        String intro = storyUser.getIntro();
        int gender = storyUser.getGender();
        int followCount = storyUser.getFollowerCount();

        long phoneRangeCategoryId = storyUser.getPhoneRangeCategory().getId();
        long genderRangeCategoryId = storyUser.getGenderRangeCategory().getId();

        String followMessage = followCount + "명의 팔로워가 있습니다.";
        merchantDetailInfoView.showFollowText(followMessage);

        Boolean isGenderPublic = (genderRangeCategoryId == UserPrivacyRangeFlag.PUBLIC);
        if (isGenderPublic) {
            if (gender == UserGenderFlag.FEMALE) {
                merchantDetailInfoView.showGenderText("여성");
            }
            if (gender == UserGenderFlag.MAN) {
                merchantDetailInfoView.showGenderText("남성");
            }
        } else {
            merchantDetailInfoView.goneGender();
        }

        Boolean isPhonePublic = (phoneRangeCategoryId == UserPrivacyRangeFlag.PUBLIC);
        if (phone != null) {
            if (isPhonePublic) {
                merchantDetailInfoView.showPhoneText(phone);
            } else {
                merchantDetailInfoView.gonePhone();
            }
        } else {
            merchantDetailInfoView.gonePhone();
        }

        if (intro != null) {
            merchantDetailInfoView.showIntroText(intro);
        } else {
            merchantDetailInfoView.goneIntro();
        }

        if (email.length() > 0) {
            merchantDetailInfoView.showEmailText(email);
        } else {
            merchantDetailInfoView.goneEmail();
        }
    }

    @Override
    public void onClickPhoneText(String phone) {
        merchantDetailInfoView.navigateToDial(phone);
    }


}
