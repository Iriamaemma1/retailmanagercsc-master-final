package csc1304.gr13.retailmanagercsc.clienttransaction.Utils;

import java.text.DecimalFormat;

public class NumberUtils {

    public static Long getMajorAmount(Long amount){
        return Long.valueOf(amount*100);
    }

    public static String getMinorAmount(String amount){
        long minor= Long.valueOf(amount)/100;
        return getFormatedAmount(minor);
    }

    public static String getFormatedAmount(Long amount){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatedAmount = formatter.format(amount);
        return formatedAmount;
    }

    public static String formatAmount(String amount){
        Double minor= Double.valueOf(amount);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatedAmount = formatter.format(minor);
        return formatedAmount;
    }

}
