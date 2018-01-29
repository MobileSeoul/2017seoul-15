package com.stm.common.util;

import android.content.Context;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class DeviceUtil {
    private Context context;

    public DeviceUtil(Context context) {
        this.context = context;
    }

    public String getDeviceId() {
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID).toString();
    }
}
