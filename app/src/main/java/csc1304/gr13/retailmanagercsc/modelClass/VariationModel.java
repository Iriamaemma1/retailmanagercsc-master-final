package csc1304.gr13.retailmanagercsc.modelClass;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class VariationModel {
    private Integer variationid;
    private String variation_name;
    private String variation_description;

    public VariationModel() {
    }

    public VariationModel(Integer id, String variation_name, String variation_description) {
        this.variationid = id;
        this.variation_name = variation_name;
        this.variation_description = variation_description;
    }

    public VariationModel(String variation_name, String variation_description) {
        this.variation_name = variation_name;
        this.variation_description = variation_description;
    }

    public Integer getId() {
        return variationid;
    }

    public void setId(Integer id) {
        this.variationid = id;
    }

    public String getVariation_name() {
        return variation_name;
    }

    public void setVariation_name(String variation_name) {
        this.variation_name = variation_name;
    }

    public String getVariation_description() {
        return variation_description;
    }

    public void setVariation_description(String variation_description) {
        this.variation_description = variation_description;
    }
}
