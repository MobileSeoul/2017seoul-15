package com.stm.login.create.joincategory.fragment.person.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.login.create.join.activity.JoinActivity;
import com.stm.login.create.joincategory.fragment.person.presenter.PersonPresenter;
import com.stm.login.create.joincategory.fragment.person.presenter.impl.PersonPresenterImpl;
import com.stm.login.create.joincategory.fragment.person.view.PersonView;
import com.stm.repository.local.SharedPrefersManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class PersonFragment extends Fragment implements PersonView {
    private PersonPresenter personPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);

        User user = (User)getArguments().getSerializable("user");
        this.personPresenter = new PersonPresenterImpl(this);
        this.personPresenter.init(user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_joincategory_person, container, false);
        personPresenter.onCreateView();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @OnClick(R.id.btn_person_join)
    public void onClickJoin(){
        personPresenter.onClickJoin();
    }

    @Override
    public void navigateToJoinActivity(){
        Intent intent = new Intent(context, JoinActivity.class);
        startActivity(intent);
    }


    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }



    @Override
    public void navigateToBack() {
        getActivity().finish();
    }

    @Override
    public void setUser(User user) {
        sharedPrefersManager.setUser(user);
    }
}
