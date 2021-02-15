package csc1304.gr13.retailmanagercsc.modelClass;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class CustomerDuelDatabaseModel {

    private int dueId;
    private String customerId;
    private String totalAmount;
    private String totalPayAmount;
    private String dueAmount;
    private String sellsCode;
    private String payDueDate;
    private String payDeposit;


    public CustomerDuelDatabaseModel() {
    }



    public CustomerDuelDatabaseModel(int dueId,String customerId, String totalAmount, String totalPayAmount, String due, String sells_code, String payDueDate) {
        this.dueId = dueId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.totalPayAmount = totalPayAmount;
        this.dueAmount = due;
        this.sellsCode = sells_code;
        this.payDueDate = payDueDate;
    }

    public CustomerDuelDatabaseModel(int dueId,String customerId, String totalAmount, String totalPayAmount, String dueAmount, String sellsCode, String payDueDate, String payDeposit) {
        this.dueId = dueId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.totalPayAmount = totalPayAmount;
        this.dueAmount = dueAmount;
        this.sellsCode = sellsCode;
        this.payDueDate = payDueDate;
        this.payDeposit = payDeposit;
    }



    public String getPayDeposit() {
        return payDeposit;
    }

    public void setPayDeposit(String payDeposit) {
        this.payDeposit = payDeposit;
    }

    public int getDueId() {
        return dueId;
    }

    public void setDueId(int duseId) {
        this.dueId = duseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(String totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getSellsCode() {
        return sellsCode;
    }

    public void setSellsCode(String sellsCode) {
        this.sellsCode = sellsCode;
    }

    public String getPayDueDate() {
        return payDueDate;
    }

    public void setPayDueDate(String payDueDate) {
        this.payDueDate = payDueDate;
    }
}
