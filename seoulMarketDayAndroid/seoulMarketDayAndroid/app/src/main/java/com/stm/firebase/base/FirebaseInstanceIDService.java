package com.stm.firebase.base;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dev-0 on 2017-06-21.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "";

    @Override
    public void onCreate() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        super.onCreate();

    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        super.onTokenRefresh();
    }


}
