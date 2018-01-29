package com.stm.user.detail.merchant.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.stm.common.dao.User;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-07-05.
 */

public class MerchantDetailTabAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private User user;
    private Context context;

    public MerchantDetailTabAdapter(FragmentManager fm, User user, Context context) {
        super(fm);
        this.user = user;
        this.context = context;
        this.fragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("storyUser", user);
        Fragment fragment = fragments.get(position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFragment(int position, Fragment fragment){
        fragments.set(position,fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
