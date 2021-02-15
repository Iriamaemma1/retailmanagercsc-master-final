package csc1304.gr13.retailmanagercsc.modelClass;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class SoldProductModel {
    private int productSoldId;
    private String productSellsCode;
    private String productSellId;
    private String productId;
    private String productPrice;
    private String productQuantity;
    private String productTotalPrice;
    private String productPendingStatus;
    private String productVariationId;

    public SoldProductModel() {
    }

    public SoldProductModel(int productSoldId,String productSellsCode, String productSellId, String productId,String productVariationId,  String productPrice, String productQuantity, String productTotalPrice, String productPendingStatus) {
        this.productSoldId =productSoldId;
        this.productSellsCode = productSellsCode;
        this.productSellId = productSellId;
        this.productId = productId;
        this.productVariationId = productVariationId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
        this.productPendingStatus = productPendingStatus;
    }

    public SoldProductModel(String productSellsCode, String productSellId, String productId,String productVariationId,  String productPrice, String productQuantity, String productTotalPrice, String productPendingStatus) {
        this.productSoldId =productSoldId;
        this.productSellsCode = productSellsCode;
        this.productSellId = productSellId;
        this.productId = productId;
        this.productVariationId = productVariationId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
        this.productPendingStatus = productPendingStatus;
    }

    public String getProductSellsCode() {
        return productSellsCode;
    }

    public void setProductSellsCode(String productSellsCode) {
        this.productSellsCode = productSellsCode;
    }

    public String getProductSellId() {
        return productSellId;
    }

    public void setProductSellId(String productSellId) {
        this.productSellId = productSellId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getProductPendingStatus() {
        return productPendingStatus;
    }

    public void setProductPendingStatus(String productPendingStatus) {
        this.productPendingStatus = productPendingStatus;
    }

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String productVariationId) {
        this.productVariationId = productVariationId;
    }

    public int getProductSoldId() {
        return productSoldId;
    }

    public void setProductSoldId(int productSoldId) {
        this.productSoldId = productSoldId;
    }
}
