package com.stm.market.base.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketTabAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private Market market;
    private User user;

    public MarketTabAdapter(FragmentManager fm, User user, Market market) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.user = user;
        this.market = market;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("user",user);
        bundle.putSerializable("market", market);

        Fragment fragment = fragments.get(position);
        fragment.setArguments(bundle);
        return fragment;
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
