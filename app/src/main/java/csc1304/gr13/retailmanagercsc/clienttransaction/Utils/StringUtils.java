package csc1304.gr13.retailmanagercsc.clienttransaction.Utils;

public class StringUtils {

    public static String CUSTOMER_ID="CUSTOMER ID: ";
    public static String NAME="CUSTOMER: ";
    public static String PHONE_NO="PHONE NO: ";
    public static String AMOUNT="AMOUNT: UGX ";
    public static String EXCISE="EXCISE: UGX ";
    public static String SURCHARGE="SURCHARGE: UGX ";
    public static String TOTAL="TOTAL: UGX ";
    public static String BILLER="BILLER: ";
    public static String ITEM="ITEM: ";
    public static String NARRATION="NARRATION: ";



    public static String generateUniqueRef(){

        return "";
    }

    public static String cleanEncodedString(String str){
        str=str.replace("&#40;","(");
        str=str.replace("&#41;",")");
        str=str.replace("&#47;&#61;","/=");
        str=str.replace("&#9;","");
        str=str.replace("&#43;","-");
        str=str.replace("&#47;","/");
        return str;
    }

    public static  boolean isEmptyString(String str){
        if(str.equals(""))
        return true;
        return false;

    }

    public  static boolean isAmountInclusiveOfCharges(String surchargeType){
        if(Boolean.getBoolean(surchargeType))
            return true;
        return  false;
    }
}
