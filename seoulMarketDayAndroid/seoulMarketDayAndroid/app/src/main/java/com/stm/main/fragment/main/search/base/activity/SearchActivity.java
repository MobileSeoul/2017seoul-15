package com.stm.main.fragment.main.search.base.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.MarketFragmentFlag;
import com.stm.common.flag.SearchFragmentFlag;
import com.stm.common.util.ToastUtil;
import com.stm.main.fragment.main.search.base.adapter.SearchTabAdapter;
import com.stm.main.fragment.main.search.base.presenter.SearchPresenter;
import com.stm.main.fragment.main.search.base.presenter.impl.SearchPresenterImpl;
import com.stm.main.fragment.main.search.base.view.SearchView;
import com.stm.main.fragment.main.search.fragment.market.fragment.SearchMarketFragment;
import com.stm.main.fragment.main.search.fragment.user.fragment.SearchUserFragment;
import com.stm.main.fragment.main.search.fragment.tag.fragment.SearchTagFragment;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchActivity extends FragmentActivity implements SearchView, TextWatcher, TabLayout.OnTabSelectedListener {
    private SearchPresenter searchPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private Handler searchTabAdapterHandler;
    private SearchTabAdapter searchTabAdapter;

    @BindView(R.id.tl_search)
    TabLayout tl_search;

    @BindView(R.id.vp_search)
    ViewPager vp_search;

    @BindView(R.id.et_search)
    EditText et_search;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.searchTabAdapterHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.searchPresenter = new SearchPresenterImpl(this);
        this.searchPresenter.init(user);
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        searchPresenter.onResume(user);
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
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
    @OnClick(R.id.iv_search_back)
    public void onClickBack() {
        searchPresenter.onClickBack();
    }

    @Override
    @OnClick(R.id.iv_search_clear)
    public void onClickClear() {
        searchPresenter.onClickClear();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void setTabLayout() {
        tl_search.addTab(tl_search.newTab().setText("시장"));
        tl_search.addTab(tl_search.newTab().setText("유저"));
        tl_search.addTab(tl_search.newTab().setText("태그"));

        vp_search.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_search));
        tl_search.addOnTabSelectedListener(this);
    }

    @Override
    public void setSearchTabAdapter() {
        searchTabAdapter = new SearchTabAdapter(getSupportFragmentManager(), this);

        searchTabAdapter.addFragment(new SearchMarketFragment());
        searchTabAdapter.addFragment(new SearchUserFragment());
        searchTabAdapter.addFragment(new SearchTagFragment());

        vp_search.setAdapter(searchTabAdapter);
    }

    @Override
    public void setEditText(String message) {
        et_search.setText(message);
    }

    @Override
    public void setEditTextChangedListener() {
        et_search.addTextChangedListener(this);
    }

    @Override
    public void setUserFragment(String message) {
        ((SearchUserFragment) searchTabAdapter.getItem(SearchFragmentFlag.USER)).getUserListByKeyword(message);
    }

    @Override
    public void setMarketFragment(String message) {
        ((SearchMarketFragment) searchTabAdapter.getItem(SearchFragmentFlag.MARKET)).getMarketListByKeyword(message);
    }

    @Override
    public void setTagFragment(String message) {
        ((SearchTagFragment) searchTabAdapter.getItem(SearchFragmentFlag.STORY)).getStoryListByKeyword(message);
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        vp_search.setOffscreenPageLimit(limit);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchPresenter.onTextChanged(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_search.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void setTagFragmentRefresh() {
        Fragment fragment = new SearchTagFragment();
        searchTabAdapter.setFragment(SearchFragmentFlag.STORY, fragment);
        searchPresenter.onChangeFragment();
    }


    @Override
    public void setSearchTabAdapterUser(User user) {
        searchTabAdapter.setUser(user);
    }

    @Override
    public void setSearchTabAdapterNotifyDataSetChanged() {
        searchTabAdapterHandler.post(new Runnable() {
            @Override
            public void run() {
                searchTabAdapter.notifyDataSetChanged();
            }
        });
    }

}
