package com.holidayme.common;

import com.holidayme.Constants.Constant;

/**
 * Created by santosh.patar on 30-07-2015.
 */
public class AuthInfo {
            String ApplicationToken = Constant.ApplicationToken_new;
            String ConsumerToken = "";
            String ApplicationName = Constant.ApplicationName;
            String MethodName;
            String TimeStamp;
            String Nonce;
            String Signature;


    public String getApplicationToken() {
        return ApplicationToken;
    }

    public void setApplicationToken(String applicationToken) {
        ApplicationToken = applicationToken;
    }

    public String getConsumerToken() {
        return ConsumerToken;
    }

    public void setConsumerToken(String consumerToken) {
        ConsumerToken = consumerToken;
    }

    public String getApplicationName() {
        return ApplicationName;
    }

    public void setApplicationName(String applicationName) {
        ApplicationName = applicationName;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getNonce() {
        return Nonce;
    }

    public void setNonce(String nonce) {
        Nonce = nonce;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
