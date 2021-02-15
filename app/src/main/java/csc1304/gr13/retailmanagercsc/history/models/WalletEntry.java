package csc1304.gr13.retailmanagercsc.history.models;


import csc1304.gr13.retailmanagercsc.clienttransaction.model.Transactions;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class WalletEntry {

    public String categoryID;
    public String name;
    public String timestamp;
    public long balanceDifference;
    public String tran_id;
    public long balance;
    public String fullMessageResponse;

    public String merchantId;
    public String merchantLocation;
    public String terminalId;
    public String tranType;

    public Transactions transaction;


    public WalletEntry() {

    }

    public WalletEntry(String categoryID, String name, String timestamp, long balanceDifference, Transactions transaction_) {
        this.categoryID = categoryID;
        this.name = name;
        this.timestamp = timestamp;
        this.balanceDifference = balanceDifference;
        this.transaction = transaction_;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getBalanceDifference() {
        return balanceDifference;
    }

    public void setBalanceDifference(long balanceDifference) {
        this.balanceDifference = balanceDifference;
    }

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getFullMessageResponse() {
        return fullMessageResponse;
    }

    public void setFullMessageResponse(String fullMessageResponse) {
        this.fullMessageResponse = fullMessageResponse;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }
}