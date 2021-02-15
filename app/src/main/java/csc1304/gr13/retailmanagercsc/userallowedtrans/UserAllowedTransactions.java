package csc1304.gr13.retailmanagercsc.userallowedtrans;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class UserAllowedTransactions {
    private static String allowTransactions_ = null;

    public static String getAllowTransactions() {
        return allowTransactions_;
    }

    public static void setAllowTransactions(String allowTransactions) {
        allowTransactions_ = allowTransactions;
    }
}
