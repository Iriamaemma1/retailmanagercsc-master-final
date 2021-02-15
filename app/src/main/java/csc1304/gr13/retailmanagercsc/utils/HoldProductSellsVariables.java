package csc1304.gr13.retailmanagercsc.utils;

import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.CustomerDue;
import csc1304.gr13.retailmanagercsc.database.ProductVariation;
import csc1304.gr13.retailmanagercsc.database.SellsInfo;
import csc1304.gr13.retailmanagercsc.database.SoldProductInfo;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.databinding.FragmentDueDetailsBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;
import csc1304.gr13.retailmanagercsc.modelClass.DueDetailsModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductListModel;
import csc1304.gr13.retailmanagercsc.p25.PrintSellInfo;

import java.util.ArrayList;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class HoldProductSellsVariables {
    static String baseUrl;
    static Stock stock;
    static ProductVariation productVariation;
    static ArrayList<ProductListModel> products;
    static CustomerModel selectedCustomer;
    static Customer customerDatabase;
    static PrintSellInfo printInfo;
    static int paymentStatus;
    static CustomerDue customerDue;
    static SellsInfo sellsInfo;
    static SoldProductInfo soldProductInfo;
    static String dueDbId;
    static String date;
    static String requestType;

    static FragmentDueDetailsBinding binding;
    static DueDetailsModel dueDetails;
    static int amountPaid;
    static String paymentType;



    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        HoldProductSellsVariables.baseUrl = baseUrl;
    }

    public static Stock getStock() {
        return stock;
    }

    public static void setStock(Stock stock) {
        HoldProductSellsVariables.stock = stock;
    }

    public static ProductVariation getProductVariation() {
        return productVariation;
    }

    public static void setProductVariation(ProductVariation productVariation) {
        HoldProductSellsVariables.productVariation = productVariation;
    }

    public static ArrayList<ProductListModel> getProducts() {
        return products;
    }

    public static void setProducts(ArrayList<ProductListModel> products) {
        HoldProductSellsVariables.products = products;
    }

    public static CustomerModel getSelectedCustomer() {
        return selectedCustomer;
    }

    public static void setSelectedCustomer(CustomerModel selectedCustomer) {
        HoldProductSellsVariables.selectedCustomer = selectedCustomer;
    }

    public static Customer getCustomerDatabase() {
        return customerDatabase;
    }

    public static void setCustomerDatabase(Customer customerDatabase) {
        HoldProductSellsVariables.customerDatabase = customerDatabase;
    }

    public static PrintSellInfo getPrintInfo() {
        return printInfo;
    }

    public static void setPrintInfo(PrintSellInfo printInfo) {
        HoldProductSellsVariables.printInfo = printInfo;
    }

    public static int getPaymentStatus() {
        return paymentStatus;
    }

    public static void setPaymentStatus(int paymentStatus) {
        HoldProductSellsVariables.paymentStatus = paymentStatus;
    }

    public static CustomerDue getCustomerDue() {
        return customerDue;
    }

    public static void setCustomerDue(CustomerDue customerDue) {
        HoldProductSellsVariables.customerDue = customerDue;
    }

    public static SellsInfo getSellsInfo() {
        return sellsInfo;
    }

    public static void setSellsInfo(SellsInfo sellsInfo) {
        HoldProductSellsVariables.sellsInfo = sellsInfo;
    }

    public static SoldProductInfo getSoldProductInfo() {
        return soldProductInfo;
    }

    public static void setSoldProductInfo(SoldProductInfo soldProductInfo) {
        HoldProductSellsVariables.soldProductInfo = soldProductInfo;
    }

    public static String getDueDbId() {
        return dueDbId;
    }

    public static void setDueDbId(String dueDbId) {
        HoldProductSellsVariables.dueDbId = dueDbId;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        HoldProductSellsVariables.date = date;
    }

    public static String getRequestType() {
        return requestType;
    }

    public static void setRequestType(String requestType) {
        HoldProductSellsVariables.requestType = requestType;
    }

    public static FragmentDueDetailsBinding getBinding() {
        return binding;
    }

    public static void setBinding(FragmentDueDetailsBinding binding) {
        HoldProductSellsVariables.binding = binding;
    }

    public static DueDetailsModel getDueDetails() {
        return dueDetails;
    }

    public static void setDueDetails(DueDetailsModel dueDetails) {
        HoldProductSellsVariables.dueDetails = dueDetails;
    }

    public static int getAmountPaid() {
        return amountPaid;
    }

    public static void setAmountPaid(int amountPaid) {
        HoldProductSellsVariables.amountPaid = amountPaid;
    }

    public static String getPaymentType() {
        return paymentType;
    }

    public static void setPaymentType(String paymentType) {
        HoldProductSellsVariables.paymentType = paymentType;
    }
}
