package com.stm.dialog.opinioncategory.activity;

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
import com.stm.common.dao.OpinionCategory;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.opinioncategory.adapter.OpinionCategoryDialogAdapter;
import com.stm.dialog.opinioncategory.presenter.OpinionCategoryDialogPresenter;
import com.stm.dialog.opinioncategory.presenter.impl.OpinionCategoryDialogPresenterImpl;
import com.stm.dialog.opinioncategory.view.OpinionCategoryDialogView;
import com.stm.repository.local.SharedPrefersManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionCategoryDialogActivity extends Activity implements OpinionCategoryDialogView {

    private OpinionCategoryDialogPresenter opinionCategoryDialogPresenter;

    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;

    private OpinionCategoryDialogAdapter opinionCategoryDialogAdapter;

    @BindView(R.id.rv_opinioncategorydialog)
    RecyclerView rv_opinioncategorydialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinioncategorydialog);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialogHandler = new Handler();
        this.progressDialog = new ProgressDialog(this);

        this.opinionCategoryDialogPresenter = new OpinionCategoryDialogPresenterImpl(this);
        this.opinionCategoryDialogPresenter.init();
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
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
    public void setOpinionCategoryDialogAdapterItem(List<OpinionCategory> opinionCategories) {
        opinionCategoryDialogAdapter = new OpinionCategoryDialogAdapter(opinionCategoryDialogPresenter, opinionCategories, this, R.layout.item_opinioncategorydialog);
        rv_opinioncategorydialog.setAdapter(opinionCategoryDialogAdapter);
        rv_opinioncategorydialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void navigateToBackWithResultOk(OpinionCategory opinionCategory) {
        Intent intent = getIntent();
        intent.putExtra("opinionCategory", opinionCategory);
        setResult(ActivityResultFlag.RESULT_OK, intent);
        finish();
    }


}
