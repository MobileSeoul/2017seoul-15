package com.stm.login.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.LoginFlag;
import com.stm.common.util.DeviceUtil;
import com.stm.common.util.FirebaseUtil;
import com.stm.common.util.LoginUtil;
import com.stm.common.util.ToastUtil;
import com.stm.login.base.presenter.LoginPresenter;
import com.stm.login.base.presenter.impl.LoginPresenterImpl;
import com.stm.login.base.view.LoginView;
import com.stm.login.create.joincategory.base.activity.JoinCategoryActivity;
import com.stm.login.find.password.activity.FindPasswordActivity;
import com.stm.repository.local.SharedPrefersManager;

import java.util.Arrays;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class LoginActivity extends Activity implements LoginView {
    private LoginPresenter loginPresenter;
    private ToastUtil toastUtil;
    private IncludedToolbarLayout includedToolbarLayout;

    private DeviceUtil deviceUtil;
    private FirebaseUtil firebaseUtil;
    private SharedPrefersManager sharedPrefersManager;

    private OAuthLogin oAuthLogin;

    @BindView(R.id.et_login_email)
    EditText et_login_email;

    @BindView(R.id.et_login_pwd)
    EditText et_login_pwd;

    @BindView(R.id.in_login_toolbar)
    View in_login_toolbar;

    @BindView(R.id.ll_login_naver)
    LinearLayout ll_login_naver;

    @BindView(R.id.oAuthLoginButton)
    OAuthLoginButton oAuthLoginButton;

    @BindString(R.string.naver_client_id)
    String naverClientId;

    @BindString(R.string.naver_client_secret)
    String naverClientSecret;

    @BindString(R.string.naver_oauth_url)
    String naverOauthUrl;

    @BindString(R.string.app_name)
    String appName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.loginPresenter = new LoginPresenterImpl(this);
        this.loginPresenter.beforeInit();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.deviceUtil = new DeviceUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.firebaseUtil = new FirebaseUtil();
        this.loginPresenter.init();
    }

    @Override
    public void init() {
        ll_login_naver.bringToFront();
    }

    @Override
    public void initFacebookSdk() {
        FacebookSdk.sdkInitialize(this);
    }

    @Override
    public void initNaverOauthLogin() {
        oAuthLogin = OAuthLogin.getInstance();
        oAuthLogin.init(this, naverClientId, naverClientSecret, appName);
    }

    @Override
    public void setNaverOAuthLoginHandler(OAuthLoginHandler oAuthLoginHandler) {
        oAuthLoginButton.setOAuthLoginHandler(oAuthLoginHandler);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_login_toolbar);
    }

    @Override
    public String getNaverOAuthResponse() {
        String token = oAuthLogin.getAccessToken(this);
        String response = oAuthLogin.requestApi(this, token, naverOauthUrl);
        return response;
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        loginPresenter.onBackPressed();
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        String email = et_login_email.getText().toString();
        String password = et_login_pwd.getText().toString();
        String deviceId = deviceUtil.getDeviceId();
        String fcmToken = firebaseUtil.getFireBaseToken();

        String encryptedPassword = LoginUtil.encryptWithSHA256(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setDeviceId(deviceId);
        user.setFcmToken(fcmToken);

        loginPresenter.onClickLogin(user);
    }

    @Override
    @OnClick(R.id.ll_login_facebook)
    public void onClickFacebookLogin() {
        String deviceId = deviceUtil.getDeviceId();
        String fcmToken = firebaseUtil.getFireBaseToken();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        User user = new User();
        user.setDeviceId(deviceId);
        user.setFcmToken(fcmToken);
        user.setLoginCategoryId(LoginFlag.FACEBOOK);
        loginPresenter.onClickFacebookLogin(user);
    }

    @Override
    @OnClick({R.id.ll_login_naver, R.id.oAuthLoginButton})
    public void onClickNaverLogin() {
        String deviceId = deviceUtil.getDeviceId();
        String fcmToken = firebaseUtil.getFireBaseToken();

        User user = new User();
        user.setDeviceId(deviceId);
        user.setFcmToken(fcmToken);
        user.setLoginCategoryId(LoginFlag.NAVER);
        loginPresenter.onClickNaverLogin(user);
    }



    @Override
    @OnClick(R.id.btn_login_findpwd)
    public void onClickFindPwd() {
        loginPresenter.onClickFindPwd();
    }

    @Override
    @OnClick(R.id.btn_login_join)
    public void onClickJoin() {
        loginPresenter.onClickJoin();
    }


    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void navigateToJoinCategoryActivity() {
        Intent intent = new Intent(this, JoinCategoryActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void navigateToJoinCategoryActivity(User user) {
        Intent intent = new Intent(this, JoinCategoryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void setUser(User user) {
        sharedPrefersManager.setUser(user);
    }

    @Override
    public User getUser() {
        return sharedPrefersManager.getUser();
    }

    @Override
    public void navigateToOauthLoginActivity(OAuthLoginHandler oAuthLoginHandler) {
        oAuthLogin.startOauthLoginActivity(this, oAuthLoginHandler);
    }

    @Override
    public void navigateToFindPasswordActivity() {
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        loginPresenter.onResume(user);
    }
}
