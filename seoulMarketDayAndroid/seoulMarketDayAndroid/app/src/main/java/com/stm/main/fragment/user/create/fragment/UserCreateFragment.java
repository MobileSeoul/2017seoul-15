package com.stm.main.fragment.user.create.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.login.base.activity.LoginActivity;
import com.stm.login.create.joincategory.base.activity.JoinCategoryActivity;
import com.stm.main.base.activity.MainActivity;
import com.stm.main.fragment.user.create.presenter.UserCreatePresenter;
import com.stm.main.fragment.user.create.presenter.impl.UserCreatePresenterImpl;
import com.stm.main.fragment.user.create.view.UserCreateView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class UserCreateFragment extends Fragment implements UserCreateView {
    private UserCreatePresenter userCreatePresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);

        this.userCreatePresenter = new UserCreatePresenterImpl(this);
        this.userCreatePresenter.init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usercreate, container, false);
        ButterKnife.bind(this, view);
        userCreatePresenter.onCreateView();

        return view;
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void navigateToJoinCategoryActivity() {
        Intent intent = new Intent(getContext(), JoinCategoryActivity.class);
        startActivity(intent);
    }


    @Override
    @OnClick(R.id.btn_usercreate_login)
    public void onClickLogin() {
        navigateToLoginActivity();
    }


    @Override
    @OnClick(R.id.ll_usercreate_join)
    public void onClickJoin() {
        navigateToJoinCategoryActivity();
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void onChangeForUserFragment() {
        Fragment fragment = this;
        ((MainActivity) context).onChangeForUserFragmentAndNotificationFragment(fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        userCreatePresenter.onResume(user);
    }
}
