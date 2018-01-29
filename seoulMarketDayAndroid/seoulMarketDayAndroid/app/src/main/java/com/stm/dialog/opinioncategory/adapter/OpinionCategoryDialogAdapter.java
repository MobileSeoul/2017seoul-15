package com.stm.dialog.opinioncategory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.OpinionCategory;
import com.stm.dialog.opinioncategory.presenter.OpinionCategoryDialogPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionCategoryDialogAdapter extends RecyclerView.Adapter<OpinionCategoryDialogAdapter.OpinionCategoryDialogViewHolder> {
    private OpinionCategoryDialogPresenter opinionCategoryDialogPresenter;
    private List<OpinionCategory> opinionCategories;
    private Context context;
    private int layout;

    public OpinionCategoryDialogAdapter(OpinionCategoryDialogPresenter opinionCategoryDialogPresenter, List<OpinionCategory> opinionCategories, Context context, int layout) {
        this.opinionCategoryDialogPresenter = opinionCategoryDialogPresenter;
        this.context = context;
        this.opinionCategories = opinionCategories;
        this.layout = layout;
    }

    @Override
    public OpinionCategoryDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OpinionCategoryDialogViewHolder opinionCategoryDialogViewHolder = new OpinionCategoryDialogViewHolder(opinionCategoryDialogPresenter, opinionCategories, LayoutInflater.from(context).inflate(layout, parent, false));
        return opinionCategoryDialogViewHolder;
    }

    @Override
    public void onBindViewHolder(OpinionCategoryDialogViewHolder holder, int position) {
        OpinionCategory opinionCategory = opinionCategories.get(position);
        String name = opinionCategory.getName();
        holder.tv_opinioncategorydialog.setText(name);
    }

    @Override
    public int getItemCount() {
        return opinionCategories.size();
    }

    public static class OpinionCategoryDialogViewHolder extends RecyclerView.ViewHolder {
        private OpinionCategoryDialogPresenter opinionCategoryDialogPresenter;
        private List<OpinionCategory> opinionCategories;

        @BindView(R.id.tv_opinioncategorydialog)
        TextView tv_opinioncategorydialog;

        public OpinionCategoryDialogViewHolder(OpinionCategoryDialogPresenter opinionCategoryDialogPresenter, List<OpinionCategory> opinionCategories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.opinionCategoryDialogPresenter = opinionCategoryDialogPresenter;
            this.opinionCategories = opinionCategories;
        }

        @OnClick(R.id.ll_opinioncategorydialog)
        public void onClickOpinionCategory(){
            int position = getAdapterPosition();
            OpinionCategory opinionCategory = opinionCategories.get(position);
            opinionCategoryDialogPresenter.onClickOpinionCategory(opinionCategory);
        }

    }
}