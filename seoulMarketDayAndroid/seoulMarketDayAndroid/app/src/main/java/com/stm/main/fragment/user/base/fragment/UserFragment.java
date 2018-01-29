package com.stm.main.fragment.user.base.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.app.info.activity.AppInfoActivity;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.main.base.activity.MainActivity;
import com.stm.main.fragment.user.base.presenter.UserPresenter;
import com.stm.main.fragment.user.base.presenter.impl.UserPresenterImpl;
import com.stm.main.fragment.user.base.view.UserView;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.create.opinion.activity.OpinionActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.normal.activity.UserDetailActivity;
import com.stm.user.edit.account.base.activity.EditAccountActivity;
import com.stm.user.edit.profile.activity.UserProfileEditActivity;
import com.stm.user.list.follower.activity.FollowerActivity;
import com.stm.user.list.following.market.activity.FollowingMarketActivity;
import com.stm.user.list.following.merchant.activity.FollowingMerchantActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-07-03.
 */

public class UserFragment extends Fragment implements UserView {
    private UserPresenter userPresenter;
    private SharedPrefersManager sharedPrefersManager;
    private Context context;
    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    @BindView(R.id.iv_user_avatar)
    ImageView iv_user_avatar;

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.btn_user_edit)
    Button btn_user_edit;

    @BindView(R.id.ll_user_followcustomer)
    LinearLayout ll_user_followcustomer;

    @BindView(R.id.ll_user_followmerchant)
    LinearLayout ll_user_followmerchant;

    @BindString(R.string.cloud_front_user_avatar)
    String cloud_front_user_avatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getContext();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();

        User user = (User) getArguments().getSerializable("user");
        this.userPresenter = new UserPresenterImpl(this);
        this.userPresenter.init(user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        userPresenter.onCreateView();

        return view;
    }

    @Override
    public void showUserNameText(String message) {
        tv_user_name.setText(message);
    }

    @Override
    public void showUserAvatar(String message) {
        Glide.with(this).load(cloud_front_user_avatar + message).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_user_avatar);
    }

    @Override
    public void goneFollowCustomer() {
        ll_user_followcustomer.setVisibility(View.GONE);
    }


    @Override
    public void goneFollowMerchant() {
        ll_user_followmerchant.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.ll_user_information)
    public void onClickUserInfo() {
        userPresenter.onClickUserInfo();
    }

    @Override
    @OnClick(R.id.ll_user_followmarket)
    public void onClickFollowingMarket() {
        userPresenter.onClickFollowingMarket();
    }

    @Override
    @OnClick(R.id.ll_user_followmerchant)
    public void onClickFollowingMerchant() {
        userPresenter.onClickFollowingMerchant();
    }

    @Override
    @OnClick(R.id.ll_user_followcustomer)
    public void onClickFollower() {
        userPresenter.onClickFollower();
    }

    @Override
    @OnClick(R.id.ll_user_editaccount)
    public void onClickEditAccount() {
        userPresenter.onClickEditAccount();
    }

//    @Override
//    @OnClick(R.id.ll_user_settingsterms)
//    public void onClickSettingsTerms() {
//
//    }

    @Override
    @OnClick(R.id.ll_user_settingsexclamation)
    public void onClickSettingsExclamation() {
        userPresenter.onClickSettingsExclamation();
    }

    @Override
    @OnClick(R.id.ll_user_settingsinformartion)
    public void onClickSettingsInfo() {
        userPresenter.onClickSettingsInfo();
    }

    @Override
    @OnClick(R.id.btn_user_edit)
    public void onClickUserEdit() {
        userPresenter.onClickUserEdit();
    }

    @Override
    @OnClick(R.id.ll_user_settingslogout)
    public void onClickSettingsLogout() {
        userPresenter.onClickSettingsLogout();
    }

    @Override
    public void navigateToUserDetailActivity(User user) {
        long storyUserId = user.getId();
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long storyUserId = user.getId();
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void removeUser() {
        sharedPrefersManager.removeUser();
    }


    @Override
    public void onChangeForUserCreateFragment() {
        Fragment fragment = this;
        ((MainActivity) context).onChangeForUserCreateFragmentAndNotificationFragment(fragment);
    }

    @Override
    public void onDataChangeForUserFragment() {
        Fragment fragment = this;
        ((MainActivity) context).onChangeForUserFragmentAndNotificationFragment(fragment);
    }

    @Override
    public void navigateToUserEditActivity() {
        Intent intent = new Intent(context, UserProfileEditActivity.class);
        startActivityForResult(intent, ActivityResultFlag.USER_EDIT_REQUEST);
    }

    @Override
    public void navigateToFollowerActivity() {
        Intent intent = new Intent(context, FollowerActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToFollowingMerchantActivity() {
        Intent intent = new Intent(context, FollowingMerchantActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToFollowingMarketActivity() {
        Intent intent = new Intent(context, FollowingMarketActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToEditAccountActivity() {
        Intent intent = new Intent(context, EditAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        userPresenter.onResume(user);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);

    }

    @Override
    public void goneProgressDialog() {
        progressDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 10);
    }

    @Override
    public void navigateToOpinionActivity() {
        Intent intent = new Intent(context, OpinionActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToAppInfoActivity() {
        Intent intent = new Intent(context, AppInfoActivity.class);
        startActivity(intent);
    }


}
