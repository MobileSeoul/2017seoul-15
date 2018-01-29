package com.stm.login.create.joincategory.fragment.merchant.search.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.login.create.joincategory.fragment.merchant.search.adapter.MarketBySearchAdapter;
import com.stm.login.create.joincategory.fragment.merchant.search.presenter.SearchMarketPresenter;
import com.stm.login.create.joincategory.fragment.merchant.search.presenter.impl.SearchMarketPresenterImpl;
import com.stm.login.create.joincategory.fragment.merchant.search.view.SearchMarketView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class SearchMarketActivity extends Activity implements SearchMarketView, NestedScrollView.OnScrollChangeListener {
    private SearchMarketPresenter searchMarketPresenter;
    private ToastUtil toastUtil;
    private IncludedToolbarLayout includedToolbarLayout;
    private MarketBySearchAdapter marketBySearchAdapter;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;


    @BindView(R.id.rv_searchmarket)
    RecyclerView rv_searchmarket;

    @BindView(R.id.in_searchmarket_toolbar)
    View in_searchmarket_toolbar;

    @BindView(R.id.nsv_searchmarket)
    NestedScrollView nsv_searchmarket;

    @BindView(R.id.et_searchmarket)
    EditText et_searchmarket;

    @BindView(R.id.ll_searchmarket_empty)
    LinearLayout ll_searchmarket_empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmarket);
        ButterKnife.bind(this);
        this.toastUtil = new ToastUtil(this);
        this.progressDialogHandler = new Handler();

        this.searchMarketPresenter = new SearchMarketPresenterImpl(this);
        this.searchMarketPresenter.init();
    }

    @Override
    public void setOnScrollChangeListener() {
        nsv_searchmarket.setOnScrollChangeListener(this);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_searchmarket_toolbar);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @OnTextChanged(value = R.id.et_searchmarket, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(Editable editable) {
        searchMarketPresenter.onTextChanged(editable);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        searchMarketPresenter.onClickBack();
    }

    @Override
    @OnClick(R.id.iv_searchmarket_clear)
    public void onClickClear() {
        et_searchmarket.setText("");
    }


    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @Override
    public void clearMarketAdapter() {
        if (marketBySearchAdapter != null) {
            marketBySearchAdapter = null;
        }
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
    public void notifyItemRangeInserted(int startPosition, int itemCount) {
        if(marketBySearchAdapter != null) {
            marketBySearchAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));

        String keyword = et_searchmarket.getText().toString();
        searchMarketPresenter.onScrollChange(difference, keyword);
    }

    @Override
    public void setMarketByKeywordAndOffsetItem(List<Market> markets) {
        marketBySearchAdapter = new MarketBySearchAdapter(searchMarketPresenter, markets, this, R.layout.item_searchmarket);
        rv_searchmarket.setAdapter(marketBySearchAdapter);
        rv_searchmarket.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;
    }

    @Override
    public void navigateToBackForResultOk(Market market){
        Intent intent = getIntent();
        intent.putExtra("market", market);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }

    @Override
    public void navigateToBackForResultCancel(){
        setResult(ActivityResultFlag.RESULT_CANCEL);
        finish();
    }

    @Override
    public void goneEmptyView() {
        ll_searchmarket_empty.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        ll_searchmarket_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        searchMarketPresenter.onClickBack();
    }
}
