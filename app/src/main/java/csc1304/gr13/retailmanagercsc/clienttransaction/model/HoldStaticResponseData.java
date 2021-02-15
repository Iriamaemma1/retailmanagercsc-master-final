package csc1304.gr13.retailmanagercsc.clienttransaction.model;

import android.content.Context;


import java.util.HashMap;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class HoldStaticResponseData {

    public static String responseMesg;
    public static HashMap<String, String> tranRequest;
    public static Context context;
    public static String transactionName;
    public static String tranCode;
    public static String tranRequestDate;
    public static Boolean isReprint = false;
    public static String structuredData;
    public static int slipNo = 2;


    public static String getStructuredData() {
        return structuredData;
    }

    public static void setStructuredData(String structuredData) {
        HoldStaticResponseData.structuredData = structuredData;
    }

    public static Boolean getIsReprint() {
        return isReprint;
    }

    public static void setIsReprint(Boolean isReprint) {
        HoldStaticResponseData.isReprint = isReprint;
    }

    public static String getTranRequestDate() {
        return tranRequestDate;
    }

    public static void setTranRequestDate(String tranRequestDate) {
        HoldStaticResponseData.tranRequestDate = tranRequestDate;
    }

    public static String getTransactionName() {
        return transactionName;
    }

    public static void setTransactionName(String transactionName_) {
        transactionName = transactionName_;
    }

    public static String getTranCode() {
        return tranCode;
    }

    public static void setTranCode(String tranCode)
    {
        HoldStaticResponseData.tranCode = tranCode;
    }



    public static String getResponseMesg() {
        return responseMesg;
    }

    public static void setResponseMesg(String responseMesg) {
        HoldStaticResponseData.responseMesg = responseMesg;
    }

    public static HashMap<String, String> getTranRequest() {
        return tranRequest;
    }

    public static void setTranRequest(HashMap<String, String> tranRequest) {
        HoldStaticResponseData.tranRequest = tranRequest;
    }

    public static int getSlipNo() {
        return slipNo;
    }

    public static void setSlipNo(int slipNo) {
        HoldStaticResponseData.slipNo = slipNo;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        HoldStaticResponseData.context = context;
    }
}
