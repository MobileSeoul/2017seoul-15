package com.stm.dialog.exitcategory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.ExitCategory;
import com.stm.dialog.exitcategory.presenter.ExitCategoryDialogPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitCategoryDialogAdapter extends RecyclerView.Adapter<ExitCategoryDialogAdapter.ExitCategoryDialogViewHolder> {
    private ExitCategoryDialogPresenter exitCategoryDialogPresenter;
    private List<ExitCategory> exitCategories;
    private Context context;
    private int layout;

    public ExitCategoryDialogAdapter(ExitCategoryDialogPresenter exitCategoryDialogPresenter, List<ExitCategory> exitCategories, Context context, int layout) {
        this.exitCategoryDialogPresenter = exitCategoryDialogPresenter;
        this.exitCategories = exitCategories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public ExitCategoryDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ExitCategoryDialogViewHolder exitCategoryDialogViewHolder = new ExitCategoryDialogViewHolder(exitCategoryDialogPresenter, exitCategories, LayoutInflater.from(context).inflate(layout, parent, false));
        return exitCategoryDialogViewHolder;
    }

    @Override
    public void onBindViewHolder(ExitCategoryDialogViewHolder holder, int position) {
        ExitCategory exitCategory = exitCategories.get(position);
        String name = exitCategory.getName();
        holder.tv_exitcategorydialog.setText(name);
    }

    @Override
    public int getItemCount() {
        return exitCategories.size();
    }

    public static class ExitCategoryDialogViewHolder extends RecyclerView.ViewHolder {
        private ExitCategoryDialogPresenter exitCategoryDialogPresenter;
        private List<ExitCategory> exitCategories;

        @BindView(R.id.tv_exitcategorydialog)
        TextView tv_exitcategorydialog;

        @BindView(R.id.ll_exitcategorydialog)
        LinearLayout ll_exitcategorydialog;

        public ExitCategoryDialogViewHolder(ExitCategoryDialogPresenter exitCategoryDialogPresenter, List<ExitCategory> exitCategories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.exitCategoryDialogPresenter = exitCategoryDialogPresenter;
            this.exitCategories = exitCategories;
        }

        @OnClick(R.id.ll_exitcategorydialog)
        public void onClickExitCategory() {
            int position = getAdapterPosition();
            ExitCategory exitCategory = exitCategories.get(position);
            exitCategoryDialogPresenter.onClickExitCategory(exitCategory);
        }
    }
}
