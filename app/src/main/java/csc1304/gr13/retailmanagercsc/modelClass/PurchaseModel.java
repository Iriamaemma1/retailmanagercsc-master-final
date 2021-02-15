package csc1304.gr13.retailmanagercsc.modelClass;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class PurchaseModel {

    public Integer id;
    public  String product_name;
    public  String product_id;
    public String product_variation_id;
    public  String supplier_name;
    public  String supplier_id;
    public  String purchase_date;
    public  String purchase_product_quantity;
    public  String purchase_product_price;
    public  String purchase_amount;
    public  String purchase_payment;
    public  String purchase_balance;
    public  String purchase_description;
    public  String created_by_id;
    public  String updated_by_id;
    public  String created_at;


    public PurchaseModel() {
    }

    public PurchaseModel(Integer id,String product_id,String product_variation_id,String supplier_id, String purchase_date, String purchase_product_quantity, String purchase_product_price, String purchase_amount, String purchase_payment, String purchase_balance, String purchase_description, String created_by_id, String updated_by_id, String created_at) {
        this.id = id;
        this.product_id = product_id;
        this.product_variation_id =product_variation_id;
        this.supplier_id = supplier_id;
        this.purchase_date = purchase_date;
        this.purchase_product_quantity = purchase_product_quantity;
        this.purchase_product_price = purchase_product_price;
        this.purchase_amount = purchase_amount;
        this.purchase_payment = purchase_payment;
        this.purchase_balance = purchase_balance;
        this.purchase_description = purchase_description;
        this.created_by_id = created_by_id;
        this.updated_by_id = updated_by_id;
        this.created_at = created_at;
    }


    public PurchaseModel(String product_id,String product_variation_id,String supplier_id, String purchase_date, String purchase_product_quantity, String purchase_product_price, String purchase_amount, String purchase_payment, String purchase_balance, String purchase_description, String created_by_id, String updated_by_id, String created_at) {
        this.product_id = product_id;
        this.product_variation_id =product_variation_id;
        this.supplier_id = supplier_id;
        this.purchase_date = purchase_date;
        this.purchase_product_quantity = purchase_product_quantity;
        this.purchase_product_price = purchase_product_price;
        this.purchase_amount = purchase_amount;
        this.purchase_payment = purchase_payment;
        this.purchase_balance = purchase_balance;
        this.purchase_description = purchase_description;
        this.created_by_id = created_by_id;
        this.updated_by_id = updated_by_id;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getPurchase_product_quantity() {
        return purchase_product_quantity;
    }

    public void setPurchase_product_quantity(String purchase_product_quantity) {
        this.purchase_product_quantity = purchase_product_quantity;
    }

    public String getPurchase_product_price() {
        return purchase_product_price;
    }

    public void setPurchase_product_price(String purchase_product_price) {
        this.purchase_product_price = purchase_product_price;
    }

    public String getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(String purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public String getPurchase_payment() {
        return purchase_payment;
    }

    public void setPurchase_payment(String purchase_payment) {
        this.purchase_payment = purchase_payment;
    }

    public String getPurchase_balance() {
        return purchase_balance;
    }

    public void setPurchase_balance(String purchase_balance) {
        this.purchase_balance = purchase_balance;
    }

    public String getPurchase_description() {
        return purchase_description;
    }

    public void setPurchase_description(String purchase_description) {
        this.purchase_description = purchase_description;
    }

    public String getCreated_by_id() {
        return created_by_id;
    }

    public void setCreated_by_id(String created_by_id) {
        this.created_by_id = created_by_id;
    }

    public String getUpdated_by_id() {
        return updated_by_id;
    }

    public void setUpdated_by_id(String updated_by_id) {
        this.updated_by_id = updated_by_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProduct_variation_id() {
        return product_variation_id;
    }

    public void setProduct_variation_id(String product_variation_id) {
        this.product_variation_id = product_variation_id;
    }
}
