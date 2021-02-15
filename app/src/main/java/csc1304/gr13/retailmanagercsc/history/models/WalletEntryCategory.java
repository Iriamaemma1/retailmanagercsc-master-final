package csc1304.gr13.retailmanagercsc.history.models;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class WalletEntryCategory {
    public String htmlColorCode;
    public String visibleName;

    public WalletEntryCategory() {

    }

    public WalletEntryCategory(String visibleName, String htmlColorCode) {
        this.htmlColorCode = htmlColorCode;
        this.visibleName = visibleName;
    }

}