package csc1304.gr13.retailmanagercsc.sellTabes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.database.ProductVariation;
import csc1304.gr13.retailmanagercsc.database.Variation;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddProductFrgBinding;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductListModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationModel;
import csc1304.gr13.retailmanagercsc.modelClass.VariationModel;
import csc1304.gr13.retailmanagercsc.suppliertables.frgs.AddSupplierActivity;

import java.util.ArrayList;

import static csc1304.gr13.retailmanagercsc.database.Product.TAG;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFrg extends Fragment {

    private Handler threadHandler;
    private FragmentAddProductFrgBinding binding;
    public AddProductFrg() {
        // Required empty public constructor
    }
    private Product products;
    private ProductListModel selectedProduct;
    private ArrayList<ProductDatabaseModel> allProducts;
    private int selectedProductIndex = 0;
    private Variation variation;
    private String[] select_variation = null;
    private ArrayList<VariationModel> listVariations;
    private String variationId = "-1";
    private String productId = "-1";
    private ProductVariationModel productVariationModel;
    private ProductVariation productVariation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_product_frg, container, false);
        products = new Product(getActivity());
        variation = new Variation(getActivity());
        productVariation = new ProductVariation(getActivity());
        allProducts = products.getProducts();
        threadHandler = new Handler(Looper.getMainLooper());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
                ,new IntentFilter("clearAll"));

        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                setSpinnerValue();
            }
        });

        binding.priceSellEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                updateTotalPrice();
                return true;
            }
        });
        binding.quantitySellEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                updateTotalPrice();
                return true;
            }
        });




        binding.addProductSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTotalPrice();
                try {
                    if ((Integer.parseInt(selectedProduct.getpStockLimit()) >=
                            Integer.parseInt(binding.quantitySellEt.getText().toString()))){

                        sendMessage();

                    }else{
                        Snackbar.make(view,"Stock limit exist",Snackbar.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Snackbar.make(view,"No Product Selected "+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                }

            }
        });



    }

     private ArrayAdapter<String > suggestionAdapter;
    public void setSpinnerValue(){

        final ArrayList<String > suggestionProduct = new ArrayList<>();
        suggestionProduct.add("Select Product");

        if (products.haveAnyProduct()){
            for (ProductDatabaseModel product: allProducts){
                suggestionProduct.add(product.getProductName()+"("+product.getProductBrand()+")");
            }
        }


       suggestionAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,suggestionProduct);
        binding.selectProductSellSpinner.setTitle("Select Product");
        binding.selectProductSellSpinner.setAdapter(suggestionAdapter);

        final Product selectedProductDetails = new Product(getActivity());

        binding.selectProductSellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                    selectedProductIndex = position-1;
                    productId = products.getProducts().get((selectedProductIndex)).getProductId()+"";
                    Log.e("SelectedProductId",productId);
                    ProductListModel product = selectedProductDetails.getProductDetails(productId);
                    selectedProduct = product;

                    listVariations =  variation.getVariationsByPId(products.getProducts().get((selectedProductIndex)).getProductId()+"");

                    select_variation = new String[listVariations.size()+2];

                    if(listVariations.size()==0){
                        select_variation[0] = "Select Variation";
                        select_variation[1] = "Create New Variation";
                    }else{
                        select_variation[0] = "Select Variation";
                        select_variation[1] = "Create New Variation";
                    }
                    int count = 2;
                    for (VariationModel s : listVariations) {
                        select_variation[count] = s.getVariation_name();
                        count++;
                    }

                    ArrayAdapter<String > adapter3 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,select_variation);
                    try{
                        binding.selectProductVariationSpinner.setAdapter(adapter3);
                        binding.selectProductVariationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(select_variation[position].equalsIgnoreCase("Create Supplier")){
                                    Intent intent = new Intent(getContext(), AddSupplierActivity.class);
                                    getContext().startActivity(intent);
                                }else if(position>1){
                                    Log.e("SelectedVariation",select_variation[position]);
                                    variationId = listVariations.get(position-2).getId()+"";
                                    Log.e("variationId",variationId);
                                    selectedProduct.setpVariation(variationId);
                                    productVariationModel = productVariation.getProductVarDetails(variationId,productId);

                                    if(productVariationModel != null){
                                        Log.e("VariationStock",productVariationModel.getP_v_Stock());
                                        selectedProduct.setpStockLimit(productVariationModel.getP_v_Stock());
                                        updateProductDetails();
                                    }else{
                                        Log.e("VariationStock","0");
                                        selectedProduct.setpStockLimit("0");
                                        updateProductDetails();
                                    }
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }catch (Exception ex){
                        Log.e("Exception Occuer",ex.getMessage());
                    }

                }else {
                    selectedProductIndex = -1;
                    selectedProduct = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void updateProductDetails(){
        binding.brandSellTv.setText("Size: "+ selectedProduct.getpSize());
        binding.uitSellTv.setText("Unit: "+ selectedProduct.getpUnit());
        binding.stockSellTv.setText("Stock: "+ selectedProduct.getpStockLimit());
        binding.quantitySellEt.setText("1");
        binding.priceSellEt.setText(selectedProduct.getpPrice());
        updateTotalPrice();
    }

    public void updateTotalPrice(){

        try {
            double totalPrice  = Double.parseDouble(binding.priceSellEt.getText().toString())*Double.parseDouble(binding.quantitySellEt.getText().toString());
            binding.totalPriceTv.setText(totalPrice+" UGX");
            selectedProduct.setpSelectQuantity(binding.quantitySellEt.getText().toString());
            selectedProduct.setPriceTotal(binding.totalPriceTv.getText().toString());
        }catch (Exception e){
            if (binding.quantitySellEt.getText().toString().isEmpty()){
                binding.quantitySellEt.setText("0");
            }
            if (binding.priceSellEt.getText().toString().isEmpty()){
                binding.priceSellEt.setText("0");
            }
        }

    }


    private void sendMessage() {
        selectedProduct.setpSelectQuantity(binding.quantitySellEt.getText().toString());
        try {
            selectedProduct.setpPrice(binding.priceSellEt.getText().toString());
            selectedProduct.setPriceTotal(
                    Integer.parseInt(selectedProduct.getpPrice()) * Integer.parseInt(selectedProduct.getpSelectQuantity())+""
            );
            binding.totalPriceTv.setText(selectedProduct.getPriceTotal());
            selectedProduct.setpSelectQuantity(binding.quantitySellEt.getText().toString());

            if (selectedProduct.getPriceTotal().isEmpty()
                    || Double.parseDouble(selectedProduct.getPriceTotal()) < 1){
                Toast.makeText(getActivity(),"Please select at lest one product",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e){
            Toast.makeText(getActivity(),"Please select at lest one product",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "sendMessage: ----- "+e);
            return;
        }

        //decrease stock limit after select a product
        ProductDatabaseModel temp;
        temp = allProducts.get(selectedProductIndex);
        temp.setProductStockLimit(String.valueOf((Integer.parseInt(allProducts.get(selectedProductIndex).getProductStockLimit()) - Integer.parseInt(binding.quantitySellEt.getText().toString()))));
        allProducts.set(selectedProductIndex,temp);

        binding.priceSellEt.setText("0 UGX");
        binding.quantitySellEt.setText("0");
        binding.totalPriceTv.setText("0.0 UGX");
        binding.stockSellTv.setText("Stock : 0");
        binding.selectProductSellSpinner.setSelection(0);
        // TODO: 5/23/2017 don,t know why when autocomplit text not chantge then brodcast send same value need to work in here
        //this not proper  solution i wll fixed it later





        Intent intent = new Intent("selectedProduct");
        // You can also include some extra data.
        intent.putExtra("product", selectedProduct);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        Toast.makeText(getActivity(),"New Product Added on sell list",Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean flag = intent.getBooleanExtra("flag",false);
            if (flag){
                allProducts = products.getProducts();
                setSpinnerValue();
            }
        }
    };

}
