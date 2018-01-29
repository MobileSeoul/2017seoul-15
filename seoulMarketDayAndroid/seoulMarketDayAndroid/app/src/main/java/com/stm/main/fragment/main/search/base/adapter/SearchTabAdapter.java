package com.stm.main.fragment.main.search.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stm.common.dao.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchTabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private Context context;
    private User user;
    private String keyword;

    public SearchTabAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.fragments = new ArrayList<>();
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setFragment(int position, Fragment fragment) {
        this.fragments.set(position, fragment);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
