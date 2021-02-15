package csc1304.gr13.retailmanagercsc.modelClass;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class DueModel {

    private String serialNo;
    private String dueAmount;
    private String customerName;
    private String customerPhone;
    private String dueId;

    public DueModel(String serialNo, String dueAmount, String customerName, String customerPhone) {
        this.serialNo = serialNo;
        this.dueAmount = dueAmount;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public DueModel(String serialNo, String dueAmount, String customerName, String customerPhone, String dueId) {
        this.serialNo = serialNo;
        this.dueAmount = dueAmount;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.dueId = dueId;
    }

    public String getDueId() {
        return dueId;
    }

    public void setDueId(String dueId) {
        this.dueId = dueId;
    }

    public DueModel() {
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ArrayList<DueModel> getAllDuesList(){
        ArrayList<DueModel> dues = new ArrayList<>();
        dues.add(new DueModel("01","6000 UGX","Atik","01772465146"));
        dues.add(new DueModel("02","10000 UGX","asif","01772465146"));
        dues.add(new DueModel("03","60000 UGX","fahim","01772465146"));
        dues.add(new DueModel("04","3000 UGX","dipto","01772465146"));

        return dues;
    }
}
