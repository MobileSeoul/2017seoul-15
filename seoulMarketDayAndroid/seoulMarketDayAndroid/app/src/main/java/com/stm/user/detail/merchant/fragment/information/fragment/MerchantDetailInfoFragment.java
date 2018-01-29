package com.stm.user.detail.merchant.fragment.information.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.fragment.information.presenter.MerchantDetailInfoPresenter;
import com.stm.user.detail.merchant.fragment.information.presenter.impl.MerchantDetailInfoPresenterImpl;
import com.stm.user.detail.merchant.fragment.information.view.MerchantDetailInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class MerchantDetailInfoFragment extends Fragment implements MerchantDetailInfoView {
    private MerchantDetailInfoPresenter merchantDetailInfoPresenter;
    private ToastUtil toastUtil;
    private Context context;
    private SharedPrefersManager sharedPrefersManager;

    @BindView(R.id.tv_merchantdetailinfo_email)
    TextView tv_merchantdetailinfo_email;

    @BindView(R.id.tv_merchantdetailinfo_phone)
    TextView tv_merchantdetailinfo_phone;

    @BindView(R.id.tv_merchantdetailinfo_gender)
    TextView tv_merchantdetailinfo_gender;

    @BindView(R.id.tv_merchantdetailinfo_intro)
    TextView tv_merchantdetailinfo_intro;

    @BindView(R.id.tv_merchantdetailinfo_follow)
    TextView tv_merchantdetailinfo_follow;

    @BindView(R.id.ll_merchantdetailinfo_email)
    LinearLayout ll_merchantdetailinfo_email;

    @BindView(R.id.ll_merchantdetailinfo_phone)
    LinearLayout ll_merchantdetailinfo_phone;

    @BindView(R.id.ll_merchantdetailinfo_gender)
    LinearLayout ll_merchantdetailinfo_gender;

    @BindView(R.id.ll_merchantdetailinfo_intro)
    LinearLayout ll_merchantdetailinfo_intro;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);

        User storyUser = (User) getArguments().getSerializable("storyUser");
        User user = sharedPrefersManager.getUser();
        this.merchantDetailInfoPresenter = new MerchantDetailInfoPresenterImpl(this);
        this.merchantDetailInfoPresenter.init(user, storyUser);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantdetailinfo, container, false);
        ButterKnife.bind(this, view);

        merchantDetailInfoPresenter.onCreateView();
        return view;
    }

    @Override
    public void showEmailText(String message) {
        tv_merchantdetailinfo_email.setText(message);
    }

    @Override
    public void showPhoneText(String message) {
        tv_merchantdetailinfo_phone.setText(message);
    }

    @Override
    public void showGenderText(String message) {
        tv_merchantdetailinfo_gender.setText(message);
    }

    @Override
    public void showIntroText(String message) {
        tv_merchantdetailinfo_intro.setText(message);
    }

    @Override
    public void showFollowText(String message) {
        tv_merchantdetailinfo_follow.setText(message);
    }

    @Override
    public void navigateToDial(String message) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + message));
        startActivity(intent);
    }

    @Override
    public void goneEmail() {
        ll_merchantdetailinfo_email.setVisibility(View.GONE);
    }

    @Override
    public void gonePhone() {
        ll_merchantdetailinfo_phone.setVisibility(View.GONE);
    }

    @Override
    public void goneGender() {
        ll_merchantdetailinfo_gender.setVisibility(View.GONE);
    }

    @Override
    public void goneIntro() {
        ll_merchantdetailinfo_intro.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.tv_merchantdetailinfo_phone)
    public void onClickPhoneText() {
        String phone = tv_merchantdetailinfo_phone.getText().toString();
        merchantDetailInfoPresenter.onClickPhoneText(phone);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

}
