package csc1304.gr13.retailmanagercsc.modelClass;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class CategoryModel {

    public int id;
    public String category_name;
    public String category_description;


    public CategoryModel() {
    }

    public CategoryModel(int id, String category_name, String category_description) {

        this.id = id;
        this.category_name = category_name;
        this.category_description = category_description;
    }


    public CategoryModel(String category_name, String category_description) {
        this.category_name = category_name;
        this.category_description = category_description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }
}
