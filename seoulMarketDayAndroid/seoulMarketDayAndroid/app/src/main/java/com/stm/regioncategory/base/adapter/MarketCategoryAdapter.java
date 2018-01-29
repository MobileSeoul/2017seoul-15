package com.stm.regioncategory.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.MarketCategory;
import com.stm.regioncategory.base.presenter.RegionCategoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public class MarketCategoryAdapter extends RecyclerView.Adapter<MarketCategoryAdapter.RegionCategoryWithSortViewHolder> {
    private List<MarketCategory> marketCategories;
    private Context context;
    private RegionCategoryPresenter regionCategoryPresenter;
    private int layout;

    public MarketCategoryAdapter(RegionCategoryPresenter regionCategoryPresenter, List<MarketCategory> marketCategories, Context context, int layout) {
        this.marketCategories = marketCategories;
        this.context = context;
        this.regionCategoryPresenter = regionCategoryPresenter;
        this.layout = layout;
    }

    @Override
    public RegionCategoryWithSortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RegionCategoryWithSortViewHolder regionCategorySortViewHolder = new RegionCategoryWithSortViewHolder(regionCategoryPresenter, marketCategories, LayoutInflater.from(context).inflate(layout, parent, false));
        return regionCategorySortViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketCategoryAdapter.RegionCategoryWithSortViewHolder holder, int position) {
        String name = marketCategories.get(position).getName();
        holder.tv_regioncategory_marketcategory.setText(name);
    }

    @Override
    public int getItemCount() {
        return marketCategories.size();
    }

    public static class RegionCategoryWithSortViewHolder extends RecyclerView.ViewHolder {
        private RegionCategoryPresenter regionCategoryPresenter;
        private List<MarketCategory> marketCategories;

        @BindView(R.id.ll_regioncategory_marketcategory)
        LinearLayout ll_regioncategory_marketcategory;

        @BindView(R.id.tv_regioncategory_marketcategory)
        TextView tv_regioncategory_marketcategory;

        public RegionCategoryWithSortViewHolder(RegionCategoryPresenter regionCategoryPresenter, List<MarketCategory> marketCategories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.regionCategoryPresenter = regionCategoryPresenter;
            this.marketCategories = marketCategories;

        }

        @OnClick(R.id.ll_regioncategory_marketcategory)
        public void onClickMarketCategoryItem() {
            int position = getAdapterPosition();
            MarketCategory marketCategory =  marketCategories.get(position);

            regionCategoryPresenter.onClickMarketCategoryItem(marketCategory);
        }

    }

}
