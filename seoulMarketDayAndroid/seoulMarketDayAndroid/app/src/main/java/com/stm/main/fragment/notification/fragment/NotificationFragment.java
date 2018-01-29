package com.stm.main.fragment.notification.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.User;
import com.stm.common.flag.MainFragmentFlag;
import com.stm.common.util.ToastUtil;
import com.stm.main.base.activity.MainActivity;
import com.stm.main.fragment.notification.adapter.NotificationAdapter;
import com.stm.main.fragment.notification.presenter.NotificationPresenter;
import com.stm.main.fragment.notification.presenter.impl.NotificationPresenterImpl;
import com.stm.main.fragment.notification.view.NotificationView;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.detail.activity.StoryDetailActivity;
import com.stm.user.detail.normal.activity.UserDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class NotificationFragment extends Fragment implements NotificationView, NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener {
    private NotificationPresenter notificationPresenter;

    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    private NotificationAdapter notificationAdapter;


    @BindView(R.id.ll_notification_empty)
    LinearLayout ll_notification_empty;

    @BindView(R.id.nsv_notification_info)
    NestedScrollView nsv_notification_info;


    @BindView(R.id.tv_notification_emptymessage)
    TextView tv_notification_emptymessage;


    @BindView(R.id.btn_notification_login)
    Button btn_notification_login;

    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;

    @BindView(R.id.srl_notification)
    SwipeRefreshLayout srl_notification;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialogHandler = new Handler();

        User user = this.sharedPrefersManager.getUser();
        this.notificationPresenter = new NotificationPresenterImpl(this);
        this.notificationPresenter.init(user);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);

        this.notificationPresenter.onCreateView();
        return view;
    }

    @OnClick(R.id.btn_notification_login)
    @Override
    public void onClickLoginButton() {
        ((MainActivity) context).getViewPager().setCurrentItem(MainFragmentFlag.MY_PAGE);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
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
        }, 400);
    }

    @Override
    public void setOnScrollChangeListener() {
        this.nsv_notification_info.setOnScrollChangeListener(this);
    }

    @Override
    public void setOnRefreshListener() {
        srl_notification.setOnRefreshListener(this);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void goneEmptyView() {
        ll_notification_empty.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        ll_notification_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyViewMessage(String message) {
        tv_notification_emptymessage.setText(message);
    }

    @Override
    public void goneLoginPageButton() {
        btn_notification_login.setVisibility(View.GONE);
    }

    @Override
    public void showLoginPageButton() {
        btn_notification_login.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneNotificationView() {
        nsv_notification_info.setVisibility(View.GONE);
    }

    @Override
    public void showNotificationView() {
        nsv_notification_info.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        this.notificationPresenter.onScrollChange(difference);
    }

    @Override
    public void setFirebaseNotificationsItem(List<FirebaseNotification> firebaseNotifications) {
        notificationAdapter = new NotificationAdapter(notificationPresenter, firebaseNotifications, context, R.layout.item_fragmentnotification_notification);
        rv_notification.setAdapter(notificationAdapter);
        rv_notification.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void notificationAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (notificationAdapter != null) {
            notificationAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void navigateToStoryDetailActivity(FirebaseNotification firebaseNotification, int position) {
        long storyId = firebaseNotification.getNavigationId();

        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra("storyId", storyId);
        startActivity(intent);
    }

    @Override
    public void navigateToUserDetailActivity(FirebaseNotification firebaseNotification, int position) {
        long storyUserId = firebaseNotification.getNavigationId();

        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }


    @Override
    public void notificationAdapterNotifyItemChanged(int position) {
        notificationAdapter.notifyItemChanged(position);
    }

    @Override
    public void onRefresh() {
        notificationPresenter.onRefresh();
    }

    @Override
    public void setSwipeRefreshLayoutRefreshing(boolean refreshing) {
        srl_notification.setRefreshing(refreshing);
    }
}
