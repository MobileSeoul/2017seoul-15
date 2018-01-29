package com.stm.common.util;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class FirebaseUtil {
    public String getFireBaseToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }
}


