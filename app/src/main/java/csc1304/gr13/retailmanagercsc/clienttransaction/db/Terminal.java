package csc1304.gr13.retailmanagercsc.clienttransaction.db;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class Terminal {
    //variables


    String terminalId;
    String merchantId;
    String merchantName;
    String merchantNameLocation;
    String merchantContact;
    // Constructor with two parameters name and password


    public Terminal() {
    }

    public Terminal(String terminalId, String merchantId, String merchantNameLocation, String merchantContact) {
        this.terminalId = terminalId;
        this.merchantId = merchantId;
        this.merchantNameLocation = merchantNameLocation;
        this.merchantContact = merchantContact;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantNameLocation() {
        return merchantNameLocation;
    }

    public void setMerchantNameLocation(String merchantNameLocation) {
        this.merchantNameLocation = merchantNameLocation;
    }

    public String getMerchantContact() {
        return merchantContact;
    }

    public void setMerchantContact(String merchantContact) {
        this.merchantContact = merchantContact;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}

