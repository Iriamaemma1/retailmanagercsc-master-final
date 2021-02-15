package csc1304.gr13.retailmanagercsc.clienttransaction.model;


import java.io.Serializable;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class Transactions implements Serializable {
    int id;
    String tran_type;
    String terminal_id;
    String merchant_id;
    String merchant_location;
    Long amount_authorsed;
    Long surcharge;
    String device_id;
    int tran_currency;
    String tran_date;
    String stan;
    String rrn;
    String resp_code;
    String resp_desc;
    String pan;
    String fullMessageResponse;
    String staffId;
    String structuredData;
    //Payment fields

    public Transactions() {
    }

    public Transactions(int id, String tran_type, String terminal_id, String merchant_id, String merchant_location, Long amount_authorsed, Long surcharge, String device_id, int tran_currency, String tran_date, String stan, String rrn, String resp_code, String resp_desc, String pan, String fullMessageResponse, String staffId, String structuredData) {
        this.id = id;
        this.tran_type = tran_type;
        this.terminal_id = terminal_id;
        this.merchant_id = merchant_id;
        this.merchant_location = merchant_location;
        this.amount_authorsed = amount_authorsed;
        this.surcharge = surcharge;
        this.device_id = device_id;
        this.tran_currency = tran_currency;
        this.tran_date = tran_date;
        this.stan = stan;
        this.rrn = rrn;
        this.resp_code = resp_code;
        this.resp_desc = resp_desc;
        this.pan = pan;
        this.fullMessageResponse = fullMessageResponse;
        this.staffId = staffId;
        this.structuredData = structuredData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTran_type() {
        return tran_type;
    }

    public void setTran_type(String tran_type) {
        this.tran_type = tran_type;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_location() {
        return merchant_location;
    }

    public void setMerchant_location(String merchant_location) {
        this.merchant_location = merchant_location;
    }

    public Long getAmount_authorsed() {
        return amount_authorsed;
    }

    public void setAmount_authorsed(Long amount_authorsed) {
        this.amount_authorsed = amount_authorsed;
    }

    public Long getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Long surcharge) {
        this.surcharge = surcharge;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getTran_currency() {
        return tran_currency;
    }

    public void setTran_currency(int tran_currency) {
        this.tran_currency = tran_currency;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_desc() {
        return resp_desc;
    }

    public void setResp_desc(String resp_desc) {
        this.resp_desc = resp_desc;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getFullMessageResponse() {
        return fullMessageResponse;
    }

    public void setFullMessageResponse(String fullMessageResponse) {
        this.fullMessageResponse = fullMessageResponse;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStructuredData() {
        return structuredData;
    }

    public void setStructuredData(String structuredData) {
        this.structuredData = structuredData;
    }
}
