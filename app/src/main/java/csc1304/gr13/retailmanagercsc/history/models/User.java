package csc1304.gr13.retailmanagercsc.history.models;


import java.util.HashMap;
import java.util.Map;
/**
 * Created by CS1304 on 8/02/2021.
 */

public class User {
    public Currency currency = new Currency(" UGX", true, true);
    public UserSettings userSettings = new UserSettings();
    public Wallet wallet = new Wallet();
    public Map<String, WalletEntryCategory> customCategories = new HashMap<>();

    public User() {

    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Map<String, WalletEntryCategory> getCustomCategories() {
        return customCategories;
    }

    public void setCustomCategories(Map<String, WalletEntryCategory> customCategories) {
        this.customCategories = customCategories;
    }
}