package com.stm.main.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stm.common.dao.User;
import com.stm.repository.local.SharedPrefersManager;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private User user;
    private FragmentManager fragmentManager;
    private SharedPrefersManager sharedPrefersManager;
    private Context context;

    public MainTabAdapter(FragmentManager fm, User user, Context context) {
        super(fm);
        this.fragmentManager = fm;

        this.fragments = new ArrayList<>();
        this.user = user;
        this.context = context;
        this.sharedPrefersManager = new SharedPrefersManager(context);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void removeFragment(Fragment fragment) {
        fragments.remove(fragment);
    }

    public void removeFragment(int position) {
        fragments.remove(position);
    }

    public void setFragmentAtThePosition(int position, Fragment fragment) {
        fragments.set(position, fragment);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("user", user);

        if (fragment.getArguments() == null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        Fragment fragment = (Fragment) object;
        int position = fragments.indexOf(fragment);
        if (position >= 0) {
            return position;
        } else {
            return POSITION_NONE;
        }
    }

}
