package com.stm.login.create.joincategory.fragment.merchant.base.adapter.region;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stm.R;
import com.stm.common.dao.RegionCategory;
import com.stm.login.create.joincategory.fragment.merchant.base.presenter.MerchantPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class RegionCategoryAdapter extends BaseAdapter {
    private MerchantPresenter merchantPresenter;
    private ArrayList<RegionCategory> regionCategories;
    private LayoutInflater inflater;
    private Context context;

    private InflatedSpinnnerLayout inflatedSpinnnerLayout;

    public RegionCategoryAdapter(MerchantPresenter merchantPresenter, ArrayList<RegionCategory> regionCategories, Context context) {
        this.merchantPresenter = merchantPresenter;
        this.regionCategories = regionCategories;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        inflatedSpinnnerLayout = new InflatedSpinnnerLayout();
    }

    @Override
    public int getCount() {
        return regionCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_joincategory_spinner, parent, false);
        ButterKnife.bind(inflatedSpinnnerLayout, view);

        return view;
    }

    public class InflatedSpinnnerLayout {
        @BindView(R.id.tv_joincategory_spinner)
        TextView tv_joincategory_spinner;
    }
}
