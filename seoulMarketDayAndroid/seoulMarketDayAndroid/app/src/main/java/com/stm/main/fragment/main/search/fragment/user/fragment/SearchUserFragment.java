package com.stm.main.fragment.main.search.fragment.user.fragment;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.main.fragment.main.search.fragment.user.adapter.SearchUserAdapter;
import com.stm.main.fragment.main.search.fragment.user.presenter.SearchUserPresenter;
import com.stm.main.fragment.main.search.fragment.user.presenter.impl.SearchUserPresenterImpl;
import com.stm.main.fragment.main.search.fragment.user.view.SearchUserView;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.normal.activity.UserDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchUserFragment extends Fragment implements SearchUserView, NestedScrollView.OnScrollChangeListener {
    private SearchUserPresenter searchUserPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SearchUserAdapter searchUserAdapter;

    @BindView(R.id.nsv_searchuser)
    NestedScrollView nsv_searchuser;

    @BindView(R.id.ll_searchuser_empty)
    LinearLayout ll_searchuser_empty;

    @BindView(R.id.rv_searchuser)
    RecyclerView rv_searchuser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.searchUserPresenter = new SearchUserPresenterImpl(this);
        this.searchUserPresenter.init(user);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchuser, container, false);
        ButterKnife.bind(this, view);
        searchUserPresenter.onCreateView();
        return view;
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showEmptyView(){
        ll_searchuser_empty.setVisibility(View.VISIBLE);
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
    public void setOnScrollChangeListener() {
        nsv_searchuser.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        searchUserPresenter.onScrollChange(difference);
    }

    @Override
    public void setSearchUserAdapterItem(List<User> users) {
        searchUserAdapter = new SearchUserAdapter(searchUserPresenter, users, context, R.layout.item_searchuser);
        rv_searchuser.setAdapter(searchUserAdapter);
        rv_searchuser.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void getUserListByKeyword(String keyword) {
        searchUserPresenter.getUserListByKeyword(keyword);
    }

    @Override
    public void clearSearchUserAdapter() {
        if(searchUserAdapter != null){
            searchUserAdapter = null;
        }
    }

    @Override
    public void notifyItemRangeInserted(int startPosition, int itemCount) {
        if(searchUserAdapter != null){
            searchUserAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }


    @Override
    public void goneEmptyView() {
        ll_searchuser_empty.setVisibility(View.GONE);
    }

    @Override
    public void navigateToUserDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }
}
