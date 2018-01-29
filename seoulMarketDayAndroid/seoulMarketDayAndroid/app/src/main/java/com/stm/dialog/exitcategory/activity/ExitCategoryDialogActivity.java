package com.stm.dialog.exitcategory.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stm.R;
import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.OpinionCategory;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.exitcategory.adapter.ExitCategoryDialogAdapter;
import com.stm.dialog.exitcategory.presenter.ExitCategoryDialogPresenter;
import com.stm.dialog.exitcategory.presenter.impl.ExitCategoryDialogPresenterImpl;
import com.stm.dialog.exitcategory.view.ExitCategoryDialogView;
import com.stm.repository.local.SharedPrefersManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitCategoryDialogActivity extends Activity implements ExitCategoryDialogView {
    private ExitCategoryDialogPresenter exitCategoryDialogPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private ExitCategoryDialogAdapter exitCategoryDialogAdapter;

    @BindView(R.id.rv_exitcategorydialog)
    RecyclerView rv_exitcategorydialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exitcategorydialog);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.exitCategoryDialogPresenter = new ExitCategoryDialogPresenterImpl(this);
        this.exitCategoryDialogPresenter.init(user);
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
    public void setExitCategoryDialogAdapterItem(List<ExitCategory> exitCategories) {
        this.exitCategoryDialogAdapter = new ExitCategoryDialogAdapter(exitCategoryDialogPresenter, exitCategories, this, R.layout.item_exitcategorydialog);
        rv_exitcategorydialog.setAdapter(exitCategoryDialogAdapter);
        rv_exitcategorydialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void navigateToBackWithResultOk(ExitCategory exitCategory) {
        Intent intent = getIntent();
        intent.putExtra("exitCategory", exitCategory);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }
}
