package csc1304.gr13.retailmanagercsc.modelClass;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class ProductVariationModel {
    private Integer id;
    private String variation_id;
    private String product_id;
    private String p_v_Stock;

    public ProductVariationModel() {
    }

    public ProductVariationModel(Integer id, String variation_id, String product_id, String stock) {
        this.id = id;
        this.variation_id = variation_id;
        this.product_id = product_id;
        this.p_v_Stock = stock;
    }

    public ProductVariationModel(String variation_id, String product_id, String stock) {
        this.variation_id = variation_id;
        this.product_id = product_id;
        this.p_v_Stock = stock;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(String variation_id) {
        this.variation_id = variation_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getP_v_Stock() {
        return p_v_Stock;
    }

    public void setP_v_Stock(String p_v_Stock) {
        this.p_v_Stock = p_v_Stock;
    }
}
