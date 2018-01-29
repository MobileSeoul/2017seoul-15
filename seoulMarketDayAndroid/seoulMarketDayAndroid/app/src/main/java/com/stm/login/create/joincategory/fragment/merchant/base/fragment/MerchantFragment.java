package com.stm.login.create.joincategory.fragment.merchant.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.login.create.join.activity.JoinActivity;
import com.stm.login.create.joincategory.fragment.merchant.base.presenter.MerchantPresenter;
import com.stm.login.create.joincategory.fragment.merchant.base.presenter.impl.MerchantPresenterImpl;
import com.stm.login.create.joincategory.fragment.merchant.base.view.MerchantView;
import com.stm.login.create.joincategory.fragment.merchant.search.activity.SearchMarketActivity;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class MerchantFragment extends Fragment implements MerchantView {
    private MerchantPresenter merchantPresenter;
    private Context context;
    private ToastUtil toastUtil;

    @BindView(R.id.tv_merchant_search)
    TextView tv_merchant_search;

    @BindView(R.id.btn_merchant_join)
    Button btn_merchant_join;

    @BindDrawable(R.drawable.all_roundbox_lightpink_r10)
    Drawable all_roundbox_lightpink_r10;

    @BindDrawable(R.drawable.all_roundbox_darkgray_r10)
    Drawable all_roundbox_darkgray_r10;

    private SharedPrefersManager sharedPrefersManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);

        User user = (User)getArguments().getSerializable("user");
        this.merchantPresenter = new MerchantPresenterImpl(this);
        this.merchantPresenter.init(user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_joincategory_merchant, container, false);
        ButterKnife.bind(this, view);
        merchantPresenter.onCreateView();
        return view;
    }

    @Override
    @OnClick(R.id.tv_merchant_search)
    public void onClickSearchMarket() {
        merchantPresenter.onClickSearchMarket();
    }

    @Override
    public void navigateToSearchMarketActivity() {
        Intent intent = new Intent(context, SearchMarketActivity.class);
        startActivityForResult(intent, ActivityResultFlag.SEARCH_MARKET_REQUEST);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.SEARCH_MARKET_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        Market market = (Market) data.getSerializableExtra("market");
                        merchantPresenter.onActivityResultForSearchMarketResultOk(market);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
        }
    }

    @Override
    public void setUser(User user) {
        sharedPrefersManager.setUser(user);
    }

    @Override
    public void showMarketName(String name) {
        tv_merchant_search.setText(name);
    }

//    @Override
//    public void setJoinActivated() {
//        btn_merchant_join.setClickable(true);
//        btn_merchant_join.setBackgroundDrawable(all_roundbox_lightpink_r10);
//    }
//
//    @Override
//    public void setJoinDeactivated() {
//        btn_merchant_join.setClickable(false);
//        btn_merchant_join.setBackgroundDrawable(all_roundbox_darkgray_r10);
//    }

    @Override
    public void showJoinButton(){
        btn_merchant_join.setVisibility(View.VISIBLE);
    }

    @Override
    @OnClick(R.id.btn_merchant_join)
    public void onClickJoin() {
        merchantPresenter.onClickJoin();
    }

    @Override
    public void navigateToJoinActivity(Market market) {
        Intent intent = new Intent(context, JoinActivity.class);
        intent.putExtra("market", market);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void navigateToBack() {
        getActivity().finish();
    }


}
