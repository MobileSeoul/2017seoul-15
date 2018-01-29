package com.stm.common.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.stm.common.flag.LogFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 지오코더 util 클래스
 * <p>
 * 현재 위치 정보
 */

public class GeocoderUtil {
    private Context context;
    private Geocoder geocoder;
    private List<Address> address;
    private String currentAddress;
    private static final Logger logger = LoggerFactory.getLogger(GeocoderUtil.class);


    public GeocoderUtil(Context context) {
        this.context = context;
        this.geocoder = new Geocoder(context, Locale.KOREA);
    }

    public String getCurrentAddress(double latitude, double longitude) {
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(latitude, longitude, 1);
                if (address != null && address.size() > 0) {
                    String locality = address.get(0).getLocality();
                    String subLocality = address.get(0).getSubLocality();

                    if (locality != null && subLocality != null) {
                        currentAddress = locality + " " + subLocality;
                    } else if (locality != null) {
                        currentAddress = locality;
                    } else if (subLocality != null) {
                        currentAddress = subLocality;
                    } else {
                        currentAddress = "반경 5KM 내 시장 추천";
                    }

                }
            }

        } catch (IOException e) {
            log(e);
        }
        return currentAddress;
    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }
}
