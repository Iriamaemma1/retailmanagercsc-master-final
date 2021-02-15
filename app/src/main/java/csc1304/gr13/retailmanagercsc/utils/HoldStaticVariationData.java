package csc1304.gr13.retailmanagercsc.utils;

import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationHolderClass;

import java.util.HashMap;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class HoldStaticVariationData {

    private static HashMap<Integer,String> variationData=new HashMap<Integer,String>();

    public static HashMap<Integer, String> getVariationData() {
        return variationData;
    }

    public static void setVariationData(HashMap<Integer, String> variationData) {
        HoldStaticVariationData.variationData = variationData;
    }



    public static void chekedValue(int position,ProductVariationHolderClass selectedItems,Boolean isChecked){
        if(isChecked){
            variationData.put(position,selectedItems.getTitle().split(",")[0]);
        }else{
            variationData.remove(position);

        }

    }
}
