package csc1304.gr13.retailmanagercsc.staticdata;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class HoldProductInfo {

    private static String productName;
    private static int CurrentStock;


    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        HoldProductInfo.productName = productName;
    }

    public static int getCurrentStock() {
        return CurrentStock;
    }

    public static void setCurrentStock(int currentStock) {
        CurrentStock = currentStock;
    }
}
