package csc1304.gr13.retailmanagercsc.stocktables.addstockitems;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.UserSession;
import csc1304.gr13.retailmanagercsc.adapters.ProductVariationAdapter;
import csc1304.gr13.retailmanagercsc.adapters.StockAdapter;
import csc1304.gr13.retailmanagercsc.database.Category;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.database.ProductVariation;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.database.Variation;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddProductDbBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationHolderClass;
import csc1304.gr13.retailmanagercsc.modelClass.StockDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockModel;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationModel;
import csc1304.gr13.retailmanagercsc.modelClass.VariationModel;
import csc1304.gr13.retailmanagercsc.suppliertables.frgs.AddSupplierActivity;
import csc1304.gr13.retailmanagercsc.utils.GenerateUniqueProductNumber;
import csc1304.gr13.retailmanagercsc.utils.GenerateUnqueId;
import csc1304.gr13.retailmanagercsc.utils.HoldStaticVariationData;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductDBfrg extends Fragment {


    public AddProductDBfrg() {
        // Required empty public constructor
    }


    private FragmentAddProductDbBinding stockBinding;
    private View view;
    private StockAdapter adapter;
    //private StockModel stockModel;
    private Product product;
    private Stock stock;
    private Category category;
    private Supplier supplier;
    private Variation variation;
    private ProductVariation productVariation;
    private ArrayList<VariationModel> allVariations;
    private ArrayList<StockModel> allStocks;
    private ArrayList<CategoryModel> allCategories;
    private ArrayList<SupplierModel> allSuppliers;
    private UpdateDatabase updateDatabase;
    private Integer categoryId = -1;
    private String[] productUnit = new String []{"Select Unit","Pcs","Kg","m","cm"};

    private String[] categoryNames = null;
    private String[] SupplierNames = null;
    String prdName,prdCode,prdPrice,prdSellPrice,prdUnit,prdBrand,prdSize,prdCategory,prdStock,prdSupplier,prdVariation;

    String[] select_variation = null;/* {
            "Select Variation","Create Variation", "Small", "Medium", "Large", "XLarge"};*/

    private ProgressDialog progressDialog;
    private Handler dialogHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        stockBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_product_db, container, false);
        view = stockBinding.getRoot();
      //  stockModel = new StockModel();
      //  updateDatabase = new UpdateDatabase(getActivity());

        stock = new Stock(getActivity());
        product = new Product(getActivity());
        category = new Category(getActivity());
        supplier = new Supplier(getActivity());
        variation = new Variation(getActivity());
        productVariation = new ProductVariation(getActivity());

        dialogHandler = new Handler(Looper.getMainLooper());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GenerateUniqueProductNumber number = new GenerateUniqueProductNumber(getActivity());
        stockBinding.prdCodeEditText.setText("PRD"+String.format("%06d", number.getCode()));
        stockBinding.prdCodeEditText.setEnabled(Boolean.FALSE);

        stockBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    updateDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        updateView();
    }


    private void updateView(){

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,productUnit);
        stockBinding.prdUnitpinner.setTitle("Select Product Unit");
        stockBinding.prdUnitpinner.setAdapter(adapter);

        allCategories = category.getAllCategories();

        if(allCategories != null){

            categoryNames = new String[allCategories.size()+2];

            if(allCategories.size()==0){
                categoryNames[0] = "Select Category";
                categoryNames[1] = "Create Category";

            }else{
                categoryNames[0] = "Select Category";
                categoryNames[1] = "Create Category";
            }

            int count = 2;
            for (CategoryModel s : allCategories) {
                categoryNames[count] = s.getCategory_name();
                count++;
            }
            if(categoryNames.length>0){
                ArrayAdapter<String > adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categoryNames);
                try{
                    stockBinding.prdCategorySpinner.setTitle("Select Product Category");
                    stockBinding.prdCategorySpinner.setAdapter(adapter2);
                }catch (Exception ex){
                    Log.e("Exception Occuer",ex.getMessage());
                }


            }
        }else {
            categoryNames = new String[2];
            categoryNames[0] = "Select Category";
            categoryNames[1] = "Create Category";

            ArrayAdapter<String > adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categoryNames);
            try{
                stockBinding.prdCategorySpinner.setAdapter(adapter2);
            }catch (Exception ex){
                Log.e("Exception Occuer",ex.getMessage());
            }
        }

        stockBinding.prdCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(categoryNames[position].equalsIgnoreCase("Create Category")){
                    Intent intent = new Intent(getContext(), AddCategoryActivity.class);
                    getContext().startActivity(intent);
                }else if(categoryNames[position].equalsIgnoreCase("Select Category")){

                }else if(position>1){
                    categoryId = allCategories.get(position-2).getId();
                    Log.e("SelectedCategoryId",categoryId+"");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Getting Suppliers*/


        allSuppliers = supplier.getAllSuppliers();
        if(allSuppliers != null){

            SupplierNames = new String[allSuppliers.size()+2];

            if(allSuppliers.size()==0){
                SupplierNames[0] = "Select Supplier";
                SupplierNames[1] = "Create Supplier";
            }else{
                SupplierNames[0] = "Select Supplier";
                SupplierNames[1] = "Create Supplier";
            }

            int count = 2;
            for (SupplierModel s : allSuppliers) {
                SupplierNames[count] = s.getSupplier_name()+"("+s.getSupplier_company_name()+")";
                count++;
            }
            if(SupplierNames.length>0){
                ArrayAdapter<String > adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,SupplierNames);
                try{
                    stockBinding.prdSupplierSpinner.setTitle("Select Product Supplier");
                    stockBinding.prdSupplierSpinner.setAdapter(adapter2);
                }catch (Exception ex){
                    Log.e("Exception Occuer",ex.getMessage());
                }

            }
        }else {
            SupplierNames = new String[2];
            SupplierNames[0] = "Select Supplier";
            SupplierNames[1] = "Create Supplier";

            ArrayAdapter<String > adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,SupplierNames);
            try{
                stockBinding.prdSupplierSpinner.setAdapter(adapter2);
            }catch (Exception ex){
                Log.e("Exception Occuer",ex.getMessage());
            }
        }

        stockBinding.prdSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(SupplierNames[position].equalsIgnoreCase("Create Supplier")){
                    Intent intent = new Intent(getContext(), AddSupplierActivity.class);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(variation.haveAnyVariation()){
            allVariations = variation.getAllVariations();
            select_variation = new String[allVariations.size()+2];

            if(allVariations.size()==0){
                select_variation[0] = "Select Variation";
                select_variation[1] = "Create New Variation";
            }else{
                select_variation[0] = "Select Variation";
                select_variation[1] = "Create New Variation";
            }
            int count = 2;
            for (VariationModel s : allVariations) {
                select_variation[count] = s.getId()+","+s.getVariation_name();
                count++;
            }
        }else{
            select_variation = new String[2];
            select_variation[0] = "Select Variation";
            select_variation[1] = "Create New Variation";

        }



        ArrayList<ProductVariationHolderClass> listVOs = new ArrayList<>();

        for (int i = 0; i < select_variation.length; i++) {
            ProductVariationHolderClass stateVO = new ProductVariationHolderClass();
            stateVO.setTitle(select_variation[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        ProductVariationAdapter myAdapter = new ProductVariationAdapter(getActivity(), 0,
                listVOs);
        stockBinding.prdVariationpinner.setAdapter(myAdapter);
        stockBinding.prdVariationpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Variation",select_variation[position]);
                if(select_variation[position].equalsIgnoreCase("Create New Variation")){
                    Intent intent = new Intent(getContext(), AddVariationActivity.class);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateDatabase() throws IOException {


        prdName = stockBinding.prdNameEditText.getText().toString();
        prdCode = "P"+GenerateUnqueId.getUniqueId("").toUpperCase();//stockBinding.prdCodeEditText.getText().toString();
        prdPrice = stockBinding.prdPriceEditText.getText().toString();
        prdSellPrice = stockBinding.prdSellPriceEditText.getText().toString();
        prdUnit = stockBinding.prdUnitpinner.getSelectedItem().toString();
        prdBrand = stockBinding.prdBrandEditText.getText().toString();
        prdSize = stockBinding.prdSizeEditText.getText().toString();
        //prdCategory = stockBinding.prdCategorySpinner.getSelectedItem().toString();
        prdCategory = categoryId+"";
        prdStock = stockBinding.prdStockEditText.getText().toString();
        prdSupplier = stockBinding.prdSupplierSpinner.getSelectedItem().toString();
        prdVariation =stockBinding.prdVariationpinner.getSelectedItem().toString();


        Log.e("FinalVariationValue", HoldStaticVariationData.getVariationData().toString());




        if(prdName.equalsIgnoreCase("") || prdName.length()==0){
            stockBinding.prdNameTextInput.setError("Product Name Required");
        }else if(prdPrice.equalsIgnoreCase("") || prdPrice.length()==0){
            stockBinding.prdPriceTextInput.setError("Product Price Required");
        }else if(prdSellPrice.equalsIgnoreCase("") || prdSellPrice.length()==0){
            stockBinding.prdSellPriceTextInput.setError("Product Sell Price Required");
        }else if(prdBrand.equalsIgnoreCase("") || prdBrand.length()==0){
            stockBinding.prdBrandTextInput.setError("Product Brand Required");
        }else if(prdSize.equalsIgnoreCase("") || prdSize.length()==0){
            stockBinding.prdSizeTextInput.setError("Product Size Required");
        }else if(HoldStaticVariationData.getVariationData().size()==0){
            SetError2("Select Product Variation",stockBinding.prdVariationpinner,stockBinding.tvInvisibleError4);
        }else if(prdStock.equalsIgnoreCase("") || prdStock.length()==0){
            stockBinding.prdStockTextInput.setError("Product Stock Required");
        }else if(prdUnit.equalsIgnoreCase("Select Unit")){
            SetError("Product Unit required",stockBinding.prdUnitpinner,stockBinding.tvInvisibleError);
        }else if(prdVariation.equalsIgnoreCase("Select Product Variation")){
            //SetError("Product Variation required",stockBinding.prdVariationpinner,stockBinding.tvInvisibleError4);
        }else if(prdCategory.equalsIgnoreCase("Create Category")){
            SetError("Go to  \"Add Categories\" ",stockBinding.prdCategorySpinner,stockBinding.tvInvisibleError2);
        }else if(prdCategory.equalsIgnoreCase("Select Category")){
            SetError("Product Category required",stockBinding.prdCategorySpinner,stockBinding.tvInvisibleError2);
        }else{
            ProductDatabaseModel productDatabaseModel = new ProductDatabaseModel(prdName,prdCode,prdPrice,prdSellPrice,prdUnit,prdBrand,prdSize,prdCategory);

            /*PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
            postTransaction.createProduct(getString(R.string.base_url),getString(R.string.base_url) +"/inventory/create/product",product,stock,productVariation,productDatabaseModel,stockBinding);
            */

            Long productId = product.storeProductInfo(productDatabaseModel);


            if (productId > 0) {

                StockDatabaseModel stockDatabaseModel = new StockDatabaseModel("" + productId, productDatabaseModel.getProductCategory(), "0", UserSession.getEmployeeId());
                // createProductStock(baseUrl, stock, productVariation, stockDatabaseModel, stockBinding);

                Boolean save = stock.storeStock(stockDatabaseModel);

                if (save) {

                    //createProductVariation(baseUrl, stock, productVariation, stockDatabaseModel, stockBinding);


                    Long productVariationId = -1L;
                    for (Map.Entry entry : HoldStaticVariationData.getVariationData().entrySet()) {

                        Log.e(entry.getKey().toString(), entry.getValue().toString());
                        productVariationId = productVariation.storeProductVariationInfo(new ProductVariationModel(entry.getValue().toString(), stockDatabaseModel.getProductId() + "", "0"));
                        Log.e("ProductVariationId", productVariationId + "");
                    }

                    if (productVariationId > 0) {
                        new DialogUIActivity(view.getContext()).showDialog("Add Product", "Item has been saved successfully");
                        clearForm((ViewGroup) stockBinding.addProductLayout);

                    } else {
                        new DialogUIActivity(view.getContext()).showDialog("Add Product", "Item failed to be saved");
                    }

                } else {
                    new DialogUIActivity(view.getContext()).showDialog("Add Product", "Item failed to be saved");
                }


                //Long productVariationId = productVariation.storeProductVariationInfo(new ProductVariationModel(productId,))
            }



        }


    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    public void SetError(String errorMessage, SearchableSpinner spinner,TextView errorView)
    {
        View view = spinner.getSelectedView();

        // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
        // appears, and then shake control if in error
        TextView tvListItem = (TextView)view;

        // Set fake TextView to be in error so that the error message appears
        TextView tvInvisibleError = errorView;

        // Shake and set error if in error state, otherwise clear error
        if(errorMessage != null)
        {
            tvListItem.setError(errorMessage);
            tvListItem.requestFocus();

            // Shake the spinner to highlight that current selection
            // is invalid -- SEE COMMENT BELOW
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            spinner.startAnimation(shake);

            tvInvisibleError.requestFocus();
            tvInvisibleError.setError(errorMessage);
        }
        else
        {
            tvListItem.setError(null);
            tvInvisibleError.setError(null);
        }
    }


    public void SetError2(String errorMessage, Spinner spinner, TextView errorView)
    {
        View view = spinner.getSelectedView();

        // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
        // appears, and then shake control if in error
        TextView tvListItem = (TextView)view;

        // Set fake TextView to be in error so that the error message appears
        TextView tvInvisibleError = errorView;

        // Shake and set error if in error state, otherwise clear error
        if(errorMessage != null)
        {
            tvListItem.setError(errorMessage);
            tvListItem.requestFocus();

            // Shake the spinner to highlight that current selection
            // is invalid -- SEE COMMENT BELOW
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            spinner.startAnimation(shake);

            tvInvisibleError.requestFocus();
            tvInvisibleError.setError(errorMessage);
        }
        else
        {
            tvListItem.setError(null);
            tvInvisibleError.setError(null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createProduct(String loginUrl, final Product product, final ProductDatabaseModel productDatabaseModel) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Customer",false);
            }
        });
        String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        /*HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);*/
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/

                        if(s.equals("8b08323b471a.ngrok.io")){
                            return true;
                        }else{
                            return false;
                        }
                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("productName", productDatabaseModel.getProductName());
            postdata.put("productCode",productDatabaseModel.getProductCode());
            postdata.put("productPrice",Integer.parseInt(productDatabaseModel.getProductPrice()));
            postdata.put("sellPrice",Integer.parseInt(productDatabaseModel.getProductSellPrice()));
            postdata.put("unit",productDatabaseModel.getProductUnit());
            postdata.put("brand",productDatabaseModel.getProductBrand());
            postdata.put("size",productDatabaseModel.getProductSize());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid",1);
            postdata.put("prdCategory",Integer.parseInt(productDatabaseModel.getProductCategory()));

            List<ProductVariation> productVariations = new ArrayList<>();

            for(Map.Entry entry:HoldStaticVariationData.getVariationData().entrySet()){
                Log.e(entry.getKey().toString(),entry.getValue().toString());

            }


            postdata.put("productVariationList",Integer.parseInt(productDatabaseModel.getProductCategory()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse",response.toString());
                if(response.isSuccessful()){

                    if(response.code()==200){

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if(parseId>0){
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        productDatabaseModel.setProductId(parseId);

                                        Long productId =  product.storeProductInfo(productDatabaseModel);



                                        if(productId >0){

                                            Boolean save = stock.storeStock(new StockDatabaseModel(""+productId,prdCategory,"0", UserSession.getEmployeeId()));

                                            if(save){

                                                Long productVariationId=-1L;
                                                for(Map.Entry entry:HoldStaticVariationData.getVariationData().entrySet()){
                                                    Log.e(entry.getKey().toString(),entry.getValue().toString());
                                                    productVariationId = productVariation.storeProductVariationInfo(new ProductVariationModel(entry.getValue().toString(),productId+"","0"));
                                                    Log.e("ProductVariationId",productVariationId+"");
                                                }

                                                if(productVariationId>0){
                                                    new DialogUIActivity(getActivity()).showDialog("Add Product","Item has been saved successfully");
                                                    clearForm((ViewGroup)stockBinding.addProductLayout);

                                                }else {
                                                    new DialogUIActivity(getActivity()).showDialog("Add Product","Item failed to be saved");
                                                }

                                            }else {
                                                new DialogUIActivity(getActivity()).showDialog("Add Product","Item failed to be saved");
                                            }


                                            //Long productVariationId = productVariation.storeProductVariationInfo(new ProductVariationModel(productId,))
                                        }

                                    }
                                });
                            }else{
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(getActivity()).showDialog("Add Product","Item failed to be saved");
                                    }
                                });
                            }

                        }else{
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(getActivity()).showDialog("Add Product","Item failed to be saved");
                                }
                            });
                        }



                    }else{
                        formatFailureResponse();
                    }
                }else{
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    private void progressDialog(String msg,boolean cancelable) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }


    private void respDialog(String title, String msg, final String originalmsg, final boolean isSuccessful, boolean cancelable) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(cancelable)
                .show();
    }


    public void formatFailureResponse() {
        progressDialog.dismiss();
        final String msg ="Posting transaction failed\nReason: Bad or No network";
        this.dialogHandler.post(new Runnable() {
            public void run() {
                //Instanitiating Alert Dialog
                respDialog("Transaction response",msg,null,false,true);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }
}