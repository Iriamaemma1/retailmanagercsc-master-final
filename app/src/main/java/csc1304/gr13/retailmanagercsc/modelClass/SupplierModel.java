package csc1304.gr13.retailmanagercsc.modelClass;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class SupplierModel {

    public Integer id;
    //=======================| SuppliersModel |=======================

    public String supplier_name;
    public String supplier_company_name;
    public String supplier_contact_person;
    public String supplier_phone_number;
    public String supplier_address;
    public String supplier_bank_name;
    public String supplier_bank_account;
    public String supplier_email;
    public String supplier_website;
    public String supplier_description;
    public String created_by_id;
    public String updated_by_id;
    public String created_at;


    public SupplierModel() {
    }

    public SupplierModel(Integer id, String supplier_name, String supplier_company_name, String supplier_contact_person, String supplier_phone_number, String supplier_address, String supplier_bank_name, String supplier_bank_account, String supplier_email, String supplier_website, String supplier_description, String created_by_id, String updated_by_id, String created_at) {
        this.id = id;
        this.supplier_name = supplier_name;
        this.supplier_company_name = supplier_company_name;
        this.supplier_contact_person = supplier_contact_person;
        this.supplier_phone_number = supplier_phone_number;
        this.supplier_address = supplier_address;
        this.supplier_bank_name = supplier_bank_name;
        this.supplier_bank_account = supplier_bank_account;
        this.supplier_email = supplier_email;
        this.supplier_website = supplier_website;
        this.supplier_description = supplier_description;
        this.created_by_id = created_by_id;
        this.updated_by_id = updated_by_id;
        this.created_at = created_at;
    }

    public SupplierModel(String supplier_name, String supplier_company_name, String supplier_contact_person, String supplier_phone_number, String supplier_address, String supplier_bank_name, String supplier_bank_account, String supplier_email, String supplier_website, String supplier_description, String created_by_id, String updated_by_id, String created_at) {
        this.supplier_name = supplier_name;
        this.supplier_company_name = supplier_company_name;
        this.supplier_contact_person = supplier_contact_person;
        this.supplier_phone_number = supplier_phone_number;
        this.supplier_address = supplier_address;
        this.supplier_bank_name = supplier_bank_name;
        this.supplier_bank_account = supplier_bank_account;
        this.supplier_email = supplier_email;
        this.supplier_website = supplier_website;
        this.supplier_description = supplier_description;
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


    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_company_name() {
        return supplier_company_name;
    }

    public void setSupplier_company_name(String supplier_company_name) {
        this.supplier_company_name = supplier_company_name;
    }

    public String getSupplier_contact_person() {
        return supplier_contact_person;
    }

    public void setSupplier_contact_person(String supplier_contact_person) {
        this.supplier_contact_person = supplier_contact_person;
    }

    public String getSupplier_phone_number() {
        return supplier_phone_number;
    }

    public void setSupplier_phone_number(String supplier_phone_number) {
        this.supplier_phone_number = supplier_phone_number;
    }

    public String getSupplier_address() {
        return supplier_address;
    }

    public void setSupplier_address(String supplier_address) {
        this.supplier_address = supplier_address;
    }

    public String getSupplier_bank_name() {
        return supplier_bank_name;
    }

    public void setSupplier_bank_name(String supplier_bank_name) {
        this.supplier_bank_name = supplier_bank_name;
    }

    public String getSupplier_bank_account() {
        return supplier_bank_account;
    }

    public void setSupplier_bank_account(String supplier_bank_account) {
        this.supplier_bank_account = supplier_bank_account;
    }

    public String getSupplier_email() {
        return supplier_email;
    }

    public void setSupplier_email(String supplier_email) {
        this.supplier_email = supplier_email;
    }

    public String getSupplier_website() {
        return supplier_website;
    }

    public void setSupplier_website(String supplier_website) {
        this.supplier_website = supplier_website;
    }

    public String getSupplier_description() {
        return supplier_description;
    }

    public void setSupplier_description(String supplier_description) {
        this.supplier_description = supplier_description;
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
}
