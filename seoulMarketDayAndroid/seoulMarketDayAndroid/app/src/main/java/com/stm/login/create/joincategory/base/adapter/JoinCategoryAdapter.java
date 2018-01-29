package com.stm.login.create.joincategory.base.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stm.common.dao.User;
import com.stm.login.create.joincategory.fragment.merchant.base.fragment.MerchantFragment;
import com.stm.login.create.joincategory.fragment.person.fragment.PersonFragment;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class JoinCategoryAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private User user;

    public JoinCategoryAdapter(FragmentManager fm, int tabCount, User user) {
        super(fm);
        this.tabCount = tabCount;
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("user", user);

        switch (position) {
            case 0:
                PersonFragment personFragment = new PersonFragment();
                personFragment.setArguments(bundle);
                return personFragment;
            case 1:
                MerchantFragment merchantFragment = new MerchantFragment();
                merchantFragment.setArguments(bundle);
                return merchantFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
