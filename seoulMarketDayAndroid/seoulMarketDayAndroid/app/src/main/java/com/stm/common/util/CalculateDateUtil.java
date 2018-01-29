package com.stm.common.util;


import com.stm.common.flag.LogFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dev-0 on 2017-04-19.
 */

public class CalculateDateUtil {

    private static final Logger logger = LoggerFactory.getLogger(CalculateDateUtil.class);


    public static String getCalculateDateByDateTime(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            log(e);
        }

        long currentTimeMillis = System.currentTimeMillis();
        long createdTimeMillis = date.getTime();
        long differentTimeMillis = (currentTimeMillis - createdTimeMillis) / 1000;

        String message = null;

        if (differentTimeMillis < 60) {
            message = differentTimeMillis + "초전";
        } else if ((differentTimeMillis /= 60) < 60) {
            message = differentTimeMillis + "분전";
        } else if ((differentTimeMillis /= 60) < 24) {
            message = (differentTimeMillis) + "시간전";
        } else if ((differentTimeMillis /= 24) < 7) {
            message = (differentTimeMillis) + "일전";
        } else {
            simpleDateFormat= new SimpleDateFormat("yy.M.d aa h:mm");
            message = simpleDateFormat.format(date);
        }

        return message;
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
