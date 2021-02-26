package csc1304.gr13.retailmanagercsc.test;

import android.content.Context;

import csc1304.gr13.retailmanagercsc.database.Category;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.database.Purchase;
import csc1304.gr13.retailmanagercsc.database.SellsInfo;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.database.User;
import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.PurchaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SellsDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.modelClass.UserDatabaseModel;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class SampleDataInsert {


    private Stock stock;
    private Product product;
    private Customer customer;
    private User user;
    private SellsInfo sells;
    private Supplier supplier;
    private Purchase purchase;
    private Category category;

    public SampleDataInsert(Context context) {
        stock = new Stock(context);
        product = new Product(context);
        customer = new Customer(context);
        user = new User(context);
        sells = new SellsInfo(context);
        supplier = new Supplier(context);
        purchase = new Purchase(context);
        category = new Category(context);
    }

    public void storeSomeSampleData() {


        if (!purchase.haveAnyData()) {
            // purchase.CreateNewPurchase( new PurchaseModel("1", "1", "2018-08-26 00:05:30.729", "50", "1000", "50000", "30000", "20000", "Need to payment about 20,000 UGX", "1", "", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("5", "5", "2018-08-26 00:05:30.729", "70", "2000", "140000", "100000", "40000", "Need to payment about 75,000 UGX", "1", "", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("2", "2", "2018-08-26 00:05:30.729", "30", "2000", "60000", "60000", "0", "Need to pay", "created by kamal", "1", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("3", "3", "2018-08-26 00:05:30.729", "60", "1000", "60000", "60000", "0", "Need to pay", "created by kamal", "1", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("4", "4", "2018-08-26 00:05:30.729", "250", "20000", "500000", "300000", "200000", "Need to payt about 200,000 UGX", "1", "", "2018-08-26 00:05:30.729"));
        }

        //insert data to stock page
        if (!stock.haveAnyStock()) {

            //only first time send some temporary data
            String ProductId;
            String ProductType;
            String ProductQuantity;
            String ProductFor;

            //Supplier

            supplier.CreateNewSupplier(new SupplierModel("Wasike Timothy", "Jumia Uganda Ltd.", "Mr. Backhouse", "+256 705789743", "Lumumba Ave., Kampala", "Citi Bank", "018913325", "info@jumia.com", "www.jumiaug.com", "This is the group of company", "created by kajjubi", "", "2020-07-18 00:05:30.729"));
            supplier.CreateNewSupplier(new SupplierModel("Lutaya Shafiq", "Agnel Enterprise Ltd.", "Mr. Potus", "+256 705764743", "Makindye", "DFCU Bank", "01891332125", "info@agnel.com", "www.Agnel.com", "This is the group of company", "created by kajjubi", "", "2020-07-18 00:05:30.729"));
            supplier.CreateNewSupplier(new SupplierModel("Joseph Lusoma", "John & Sons Ltd.", "Mr. Prince", "+256 705789732", "Luwuum Street, Kampala", "Centenary Bank", "0189133232125", "info@johnsons.com", "www.johnsons.com", "This is the group of company", "created by kajjubi", "", "2020-07-18 00:05:30.729"));
            supplier.CreateNewSupplier(new SupplierModel("Samira Zein", "Light the World Enterprise Ltd.", "Mrs. Sammy", "+256 705789743", "Khilkhet, Dhaka", "Opportunty Bank", "018222913325", "info@lightheworld.com", "www.lightheworld.com", "This is the group of company", "created by kajjubi", "", "2020-07-18 00:05:30.729"));
            supplier.CreateNewSupplier(new SupplierModel("Fiona Mutonyi", "Mark Enterprise Ltd.", "Mrs. Namarome", "+256 705782743", "Mukwano Plaze, Kampala", "Housing Finance Bank", "011228913325", "info@markent.com", "www.markent.com", "This is the group of company", "created by kajjubi", "", "2020-07-18 00:05:30.729"));

            //purchase table

            purchase.CreateNewPurchase(new PurchaseModel("LapTop", "1", "William Kajjubi", "1", "1000", "50", "2018-08-26 00:05:30.729", "50000", "30000", "20000", "Need to payment about 20,000 UGX", "created by kajjubi", "", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("Iphone", "5", "Emmanuel Iriama", "5", "2500", "70", "2018-08-26 00:05:30.729", "175000", "100000", "75000", "Need to payment about 75,000 UGX", "created by Emmanuel", "", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("TV", "2", "Wafula Emmanuel", "2", "2000", "30", "2018-08-26 00:05:30.729", "60000", "60000", "0", "Need to pay", "created by kajjubi", "", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("SamSung", "3", "Samson Andrew", "3", "1000", "60", "2018-08-26 00:05:30.729", "60000", "60000", "0", "Need to pay", "created by kajjubi", "", "2018-08-26 00:05:30.729"));
            purchase.CreateNewPurchase(new PurchaseModel("Nokia", "4", "Abdul Mugenyi", "4", "2000", "250", "2018-08-26 00:05:30.729", "500000", "300000", "200000", "Need to payt about 200,000 UGX", "created by kajjubi", "", "2018-08-26 00:05:30.729"));


            //stock table

            stock.storeStock(new StockDatabaseModel("990", "2", "30", "1"));
            stock.storeStock(new StockDatabaseModel("991", "2", "300", "1"));
            stock.storeStock(new StockDatabaseModel("992", "2", "40", "1"));
            stock.storeStock(new StockDatabaseModel("993", "2", "60", "1"));
            stock.storeStock(new StockDatabaseModel("994", "2", "98", "1"));
            stock.storeStock(new StockDatabaseModel("995", "2", "500", "1"));
            stock.storeStock(new StockDatabaseModel("996", "2", "60", "1"));


            stock.storeStock(new StockDatabaseModel("01", "0", "20", "1"));
            stock.storeStock(new StockDatabaseModel("02", "2", "50", "1"));
            stock.storeStock(new StockDatabaseModel("03", "2", "100", "1"));
            stock.storeStock(new StockDatabaseModel("04", "0", "15", "1"));


            //Category Table
            category.createNewCategory(new CategoryModel("Computers", ""));
            category.createNewCategory(new CategoryModel("Mobile Phones", ""));
            category.createNewCategory(new CategoryModel("TV", ""));

            //product table
            String productName;
            String productCode;
            String productPrice;
            String productSellPrice;
            String productUnit;
            String productBrand;
            String productSize;


            product.storeProductInfo(new ProductDatabaseModel("Laptop", "01", "50000", "55000", "Pcs", "Dell", "18.5 inch", "Computer"));
            product.storeProductInfo(new ProductDatabaseModel("Mobile", "02", "10000", "15000", "Pcs", "Samsung", "5.5 inch", "Mobile Phone"));
            product.storeProductInfo(new ProductDatabaseModel("Iphone", "03", "70000", "80000", "Pcs", "Apple", "5.0 inch", "Mobile Phone"));
            product.storeProductInfo(new ProductDatabaseModel("Desktop", "04", "50000", "90000", "Pcs", "Acer", "20 inch", "Computer"));

            product.storeProductInfo(new ProductDatabaseModel("Tv DTE24BF", "990", "50000", "55000", "Pcs", "DANSAT", "24 inch", "TV"));
            product.storeProductInfo(new ProductDatabaseModel("Tv DTD28BF", "991", "55000", "60000", "Pcs", "DANSAT", "28 inch", "TV"));
            product.storeProductInfo(new ProductDatabaseModel("Tv HD LED", "992", "60000", "62000", "Pcs", "Samsung", "30 inch", "TV"));
            product.storeProductInfo(new ProductDatabaseModel("Tv HD CURVE", "993", "70000", "73000", "Pcs", "Samsung", "32.5 inch", "TV"));
            product.storeProductInfo(new ProductDatabaseModel("Receiver 991", "994", "75000", "79000", "Pcs", "Samsung", "38.5 inch", "TV"));
            product.storeProductInfo(new ProductDatabaseModel("Receiver 993", "995", "90000", "93000", "Pcs", "Dell", "45 inch", "TV"));
            product.storeProductInfo(new ProductDatabaseModel("Al-Hidaya", "996", "100000", "110000", "Pcs", "WALTON", "50 inch", "TV"));


            //customer table

            String customerName;
            String customerCode;
            String customerType;
            String customerGender;
            String customerPhone;
            String customerEmail;
            String customerAddress;
            String customerDueAmount;

            customer.createNewCustomer(new CustomerModel("Wasike", "CUS1", "regular", "male", "88555320", "WalkThrough@gmail.com", "mirpur dohs", "600"));
            customer.createNewCustomer(new CustomerModel("Andrew", "CUS2", "regular", "male", "54115415", "Mikel@gmail.com", "mirpur dohs", "20"));
            customer.createNewCustomer(new CustomerModel("Fiona", "CUS3", "regular", "male", "7881544", "Anca@gmail.com", "mirpur dohs", "0"));
            customer.createNewCustomer(new CustomerModel("Emma", "CUS4", "regular", "male", "8718245", "James@gmail.com", "mirpur dohs", "100"));
            customer.createNewCustomer(new CustomerModel("Samson", "CUS5", "regular", "male", "3562565", "Jonson@gmail.com", "mirpur dohs", "500"));
            customer.createNewCustomer(new CustomerModel("Kajjubi", "CUS6", "regular", "male", "356255", "Miler@gmail.com", "mirpur dohs", "600"));
            customer.createNewCustomer(new CustomerModel("Amla", "CUS7", "regular", "male", "985414", "Amla@gmail.com", "mirpur dohs", "600"));
            customer.createNewCustomer(new CustomerModel("Timothy", "CUS8", "regular", "male", "987761", "Maxual@gmail.com", "mirpur dohs", "0"));

            //user table

            String userName;
            String userEmail;
            String userPassword;
            String userPhone;
            String userEmployeeId;

            user.createNewUser(new UserDatabaseModel("admin", "shopowner@cs1304group.com", "123456", "0773439312", "1"));
            user.createNewUser(new UserDatabaseModel("iriama", "iriamaemma1@gmail.com", "19/U/1569", "0774673302", "2"));
            user.createNewUser(new UserDatabaseModel("joshua", "joshuabatambuze2000@gmail.com", "19/U/0570", "0785519733", "3"));
            user.createNewUser(new UserDatabaseModel("wafula", "emmawafula256@gmail.com", "19/U/18025/PS", "0700959515", "4"));
            user.createNewUser(new UserDatabaseModel("kajjubi", "wkajjubi@gmail.com", "19/U/14613/PS", "0774673302", "5"));
            user.createNewUser(new UserDatabaseModel("samson", "andrewsamson246@gmail.com", "19/U/0113", "0702506594", "6"));

            //sell table
            String sellsCode;
            String customerId;
            String totalAmount;
            String discount;
            String payAmount;
            String paymentType;
            String sellDate;
            String paymentStatus;
            String sellBy;

            sells.storeSellDetails(new SellsDatabaseModel("in-1", "CUS1", "10000", "10", "8000", "0", "06-06-17", "1", "1"));
            sells.storeSellDetails(new SellsDatabaseModel("in-2", "CUS2", "5000", "300", "5000", "0", "06-06-17", "0", "1"));

        }

    }
}
