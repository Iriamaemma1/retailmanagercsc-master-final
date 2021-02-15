package csc1304.gr13.retailmanagercsc.modelClass;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class StockDatabaseModel {


    private Integer stockId_;
    private String ProductId;
    private String ProductType;
    private String ProductQuantity;
    private String ProductFor;

    public StockDatabaseModel(){

    }

    public StockDatabaseModel(Integer stockId,String productId, String productType, String productQuantity, String productFor) {
        stockId_ = stockId;
        ProductId = productId;
        ProductType = productType;
        ProductQuantity = productQuantity;
        ProductFor = productFor;
    }

    public StockDatabaseModel(String productId, String productType, String productQuantity, String productFor) {

        ProductId = productId;
        ProductType = productType;
        ProductQuantity = productQuantity;
        ProductFor = productFor;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductFor() {
        return ProductFor;
    }

    public void setProductFor(String productFor) {
        ProductFor = productFor;
    }

    public Integer getStockId_() {
        return stockId_;
    }

    public void setStockId_(Integer stockId_) {
        this.stockId_ = stockId_;
    }
}
