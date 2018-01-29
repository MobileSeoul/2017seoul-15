package com.stm.market.fragment.information.fragment;

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
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.market.fragment.information.presenter.MarketInfoPresenter;
import com.stm.market.fragment.information.presenter.impl.MarketInfoPresenterImpl;
import com.stm.market.fragment.information.view.MarketInfoView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketInfoFragment extends Fragment implements MarketInfoView{
    private MarketInfoPresenter marketInfoPresenter;

    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;

    @BindView(R.id.tv_marketinfo_store)
    TextView tv_marketinfo_store;

    @BindView(R.id.tv_marketinfo_merchant)
    TextView tv_marketinfo_merchant;

    @BindView(R.id.tv_marketinfo_follow)
    TextView tv_marketinfo_follow;

    @BindView(R.id.tv_marketinfo_phone)
    TextView tv_marketinfo_phone;

    @BindView(R.id.tv_marketinfo_homepage)
    TextView tv_marketinfo_homepage;

    @BindView(R.id.tv_marketinfo_subname)
    TextView tv_marketinfo_subname;

    @BindView(R.id.tv_marketinfo_items)
    TextView tv_marketinfo_items;

    @BindView(R.id.ll_marketinfo_phone)
    LinearLayout ll_marketinfo_phone;

    @BindView(R.id.ll_marketinfo_homepage)
    LinearLayout ll_marketinfo_homepage;

    @BindView(R.id.ll_marketinfo_subname)
    LinearLayout ll_marketinfo_subname;

    @BindView(R.id.ll_marketinfo_items)
    LinearLayout ll_marketinfo_items;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);

        User user = (User) getArguments().getSerializable("user");
        Market market = (Market) getArguments().getSerializable("market");

        this.marketInfoPresenter = new MarketInfoPresenterImpl(this);
        this.marketInfoPresenter.init(user,market);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_marketinfo, container, false);
        ButterKnife.bind(this, view);

        this.marketInfoPresenter.onCreateView();

        return view;
    }

    @Override
    public void showStoreText(String message) {
        tv_marketinfo_store.setText(message);
    }

    @Override
    public void showMerchantText(String message) {
        tv_marketinfo_merchant.setText(message);
    }

    @Override
    public void showFollowText(String message) {
        tv_marketinfo_follow.setText(message);
    }

    @Override
    public void showPhoneText(String message) {
        tv_marketinfo_phone.setText(message);
    }

    @Override
    public void showHomepageText(String message) {
        tv_marketinfo_homepage.setText(message);
    }

    @Override
    public void showSubnameText(String message) {
        tv_marketinfo_subname.setText(message);
    }

    @Override
    public void showItemsText(String message) {
        tv_marketinfo_items.setText(message);
    }

    @Override
    public void gonePhone() {
        ll_marketinfo_phone.setVisibility(View.GONE);
    }

    @Override
    public void goneHomepage() {
        ll_marketinfo_homepage.setVisibility(View.GONE);
    }

    @Override
    public void goneSubname() {
        ll_marketinfo_subname.setVisibility(View.GONE);
    }

    @Override
    public void goneItems() {
        ll_marketinfo_items.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.tv_marketinfo_phone)
    public void onClickPhoneText() {
        String phone = tv_marketinfo_phone.getText().toString();
        marketInfoPresenter.onClickPhoneText(phone);
    }

    @Override
    @OnClick(R.id.tv_marketinfo_homepage)
    public void onClickHomepageText() {
        String homepage = tv_marketinfo_homepage.getText().toString();
        marketInfoPresenter.onClickHomepageText(homepage);
    }


    @Override
    public void navigateToWeb(String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
        startActivity(intent);
    }

    @Override
    public void navigateToDial(String message) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + message));
        startActivity(intent);
    }
}
