package com.stm.common.util;

import com.stm.common.flag.LogFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ㅇㅇ on 2017-06-22.
 */

public class LoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static String encryptWithSHA256(String message) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(message.getBytes(), 0, message.length());

        } catch (NoSuchAlgorithmException e) {
            log(e);
        }

        return String.format("%064x", new java.math.BigInteger(1, messageDigest.digest()));
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
