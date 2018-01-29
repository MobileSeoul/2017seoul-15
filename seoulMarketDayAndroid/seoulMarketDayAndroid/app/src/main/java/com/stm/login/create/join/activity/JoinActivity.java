package com.stm.login.create.join.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.util.DeviceUtil;
import com.stm.common.util.FirebaseUtil;
import com.stm.common.util.LoginUtil;
import com.stm.common.util.ToastUtil;
import com.stm.login.create.join.presenter.JoinPresenter;
import com.stm.login.create.join.presenter.impl.JoinPresenterImpl;
import com.stm.login.create.join.view.JoinView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class JoinActivity extends Activity implements JoinView {
    private JoinPresenter joinPresenter;
    private ToastUtil toastUtil;
    private InflatedToolbarLayout inflatedToolbarLayout;
    private FirebaseUtil firebaseUtil;
    private DeviceUtil deviceUtil;
    private LoginUtil loginUtil;
    private SharedPrefersManager sharedPrefersManager;

    @BindView(R.id.in_join_toolbar)
    View in_join_toolbar;

    @BindView(R.id.btn_join)
    Button btn_join;

    @BindView(R.id.et_join_email)
    EditText et_join_email;

    @BindView(R.id.et_join_pwd)
    EditText et_join_pwd;

    @BindView(R.id.et_join_pwdconfirm)
    EditText et_join_pwdconfirm;

    @BindView(R.id.et_join_name)
    EditText et_join_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.deviceUtil = new DeviceUtil(this);
        this.firebaseUtil = new FirebaseUtil();
        this.loginUtil = new LoginUtil();
        this.sharedPrefersManager = new SharedPrefersManager(this);

        Market market = (Market) getIntent().getSerializableExtra("market");
        this.joinPresenter = new JoinPresenterImpl(this);
        this.joinPresenter.init(market);
    }

    @Override
    public void setToolbarLayout() {
        inflatedToolbarLayout = new InflatedToolbarLayout();
        ButterKnife.bind(inflatedToolbarLayout, in_join_toolbar);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showToolbarTitle(String message) {
        inflatedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    @OnClick(R.id.btn_join)
    public void onClickJoin() {
        String email = et_join_email.getText().toString();
        String password = et_join_pwd.getText().toString();
        String passwordConfirm = et_join_pwdconfirm.getText().toString();
        String name = et_join_name.getText().toString();
        String deviceId = deviceUtil.getDeviceId();
        String firebaseToken = firebaseUtil.getFireBaseToken();

        String encryptedPassword = LoginUtil.encryptWithSHA256(password);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setDeviceId(deviceId);
        user.setFcmToken(firebaseToken);

        boolean isValidEmail = loginUtil.isValidEmail(email);
        joinPresenter.onClickJoin(user, password, passwordConfirm, encryptedPassword, isValidEmail);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        finish();
    }

    @Override
    public void setUser(User user) {
        sharedPrefersManager.setUser(user);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    public class InflatedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }
}
