package csc1304.gr13.retailmanagercsc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class GenerateUnqueId {

    public static String getUniqueId(String customString){

        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("yyMMddHHmmss");
        Date getInstance = new Date();
        String currentDate = formatter.format(getInstance);
        return customString+getAlphaNumericString(3)+currentDate;
    }

    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
