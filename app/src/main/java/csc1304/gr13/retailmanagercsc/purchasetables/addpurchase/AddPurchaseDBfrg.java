package csc1304.gr13.retailmanagercsc.purchasetables.addpurchase;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.UserSession;
import csc1304.gr13.retailmanagercsc.adapters.StockAdapter;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.database.ProductVariation;
import csc1304.gr13.retailmanagercsc.database.Purchase;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.database.Variation;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddPurchaseDbBinding;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationModel;
import csc1304.gr13.retailmanagercsc.modelClass.PurchaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.modelClass.VariationModel;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.AddProductActivity;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.AddVariationActivity;
import csc1304.gr13.retailmanagercsc.suppliertables.frgs.AddSupplierActivity;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddPurchaseDBfrg extends Fragment {


    public AddPurchaseDBfrg() {
        // Required empty public constructor
    }


    private FragmentAddPurchaseDbBinding purchaseBinding;
    private View view;
    private StockAdapter adapter;
    //private StockModel stockModel;

    private UpdateDatabase updateDatabase;
    private Product product;
    private Purchase purchase;
    private ArrayList<ProductDatabaseModel> allProducts;
    private String productId ="-1";
    private String productCategory = "-1";
    private String supplierId = "-1";
    private String variationId = "-1";
    private String[] productNames = null;
    private String[] select_variation = null;
    private Variation variation;
    private ArrayList<VariationModel> allVariations;

    private ProductVariation productVariation;
    private Supplier supplier;
    private ArrayList<SupplierModel> allSuppliers;
    private String[] SupplierNames = null;

    private Stock stock;
    String selectedPrd, selectedVariation,selectedSupplier, purchaseDate,purchaseTime, purcchaseQty, purchaseUnitPrice, totalAmount, purchasePayment, payAmount, paidWith;
    private String[] paymentType = new String []{"Choose Payment","Cash","Card","Mobile Money","Pay Later","Bank"};


    DatePickerDialog picker;
    TimePickerDialog timePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        purchaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_purchase_db, container, false);
        view = purchaseBinding.getRoot();
        //  stockModel = new StockModel();
        updateDatabase = new UpdateDatabase(getActivity());

        variation = new Variation(getActivity());

        product = new Product(getActivity());

        supplier = new Supplier(getActivity());

        purchase = new Purchase(getActivity());

        stock = new Stock(getActivity());

        productVariation = new ProductVariation(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateView();


        purchaseBinding.purchaseDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                purchaseBinding.purchaseDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                picker.show();
            }

        });

        purchaseBinding.purchaseTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        purchaseBinding.purchaseTimeEditText.setText( selectedHour + ":" + String.format("%02d",selectedMinute));
                    }
                }, hour, minute, false);//Yes 24 hour time
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        purchaseBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Log.e("Radio", "Id Checked:" + checkedId);
                if (checkedId == purchaseBinding.radioFullPayment.getId()) {


                    String temp2[] = purchaseBinding.totalAmountEditText.getText().toString().trim().split(" UGX");
                    if (Integer.parseInt(temp2[0]) < 1) {
                        Toast.makeText(getActivity(), "Select At lest one product", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int totalPayAmount = Integer.parseInt(temp2[0]);


                    purchaseBinding.payementAmountEditText.setText(totalPayAmount + "");

                    purchaseBinding.payementAmountEditText.setEnabled(Boolean.FALSE);

                    Log.e("Radio1", "Id Checked:" + checkedId);
                } else if (checkedId == purchaseBinding.radioPartial.getId()) {
                    //Snackbar.make(v,"Partial Payment",Snackbar.LENGTH_LONG);
                    Log.e("Radio2", "Id Checked:" + checkedId);
                    purchaseBinding.payementAmountEditText.setText("0");
                    purchaseBinding.payementAmountEditText.setEnabled(Boolean.TRUE);

                } else if (checkedId == purchaseBinding.radioPayLater.getId()) {
                    //Snackbar.make(v,"Pay Later",Snackbar.LENGTH_LONG);
                    Log.e("Radio3", "Id Checked:" + checkedId);
                    purchaseBinding.payementAmountEditText.setText("0");
                    purchaseBinding.payementAmountEditText.setEnabled(Boolean.FALSE);
                }


            }
        });

        purchaseBinding.purchaseQtyEditText.addTextChangedListener(new InputTextWatcher(purchaseBinding.purchaseQtyEditText));

        purchaseBinding.purchaseUnitPriceEditText.addTextChangedListener(new InputTextWatcher(purchaseBinding.purchaseUnitPriceEditText));

        purchaseBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                updateDatabase();

            }
        });


        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,paymentType);
        purchaseBinding.paymentTypeSpinner.setTitle("Select Payment Type");
        purchaseBinding.paymentTypeSpinner.setAdapter(adapter);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateDatabase() {
        selectedPrd = purchaseBinding.prdSpinner.getSelectedItem().toString();
        selectedVariation = purchaseBinding.prdVariationpinner.getSelectedItem().toString();
        selectedSupplier = purchaseBinding.prdSupplierSpinner.getSelectedItem().toString();
        purchaseDate = purchaseBinding.purchaseDateEditText.getText().toString();
        purchaseTime = purchaseBinding.purchaseTimeEditText.getText().toString();
        purcchaseQty = purchaseBinding.purchaseQtyEditText.getText().toString();
        purchaseUnitPrice = purchaseBinding.purchaseUnitPriceEditText.getText().toString();
        totalAmount = purchaseBinding.totalAmountEditText.getText().toString();
        payAmount = purchaseBinding.payementAmountEditText.getText().toString();
        purchasePayment =purchaseBinding.payementAmountEditText.getText().toString();
        paidWith = purchaseBinding.paymentTypeSpinner.getSelectedItem().toString();

        if(selectedPrd.equalsIgnoreCase("Select Product")){
            SetError("Product required",purchaseBinding.prdSpinner,purchaseBinding.tvInvisibleError2);
        }else if(selectedVariation.equalsIgnoreCase("Select Variation")){
            SetError("Product Variation required",purchaseBinding.prdVariationpinner,purchaseBinding.tvInvisibleError5);
        }else if(selectedSupplier.equalsIgnoreCase("Select Supplier")){
            SetError("Supplier required",purchaseBinding.prdSupplierSpinner,purchaseBinding.tvInvisibleError3);
        }else if(purchaseDate.equalsIgnoreCase("") || purchaseDate.length()==0){
            purchaseBinding.purchaseDateTextInput.setError("Purchase Date Required");
        }else if(purchaseTime.equalsIgnoreCase("") || purchaseTime.length()==0){
            purchaseBinding.purchaseTimeTextInput.setError("Time Required");
        }else if(purcchaseQty.equalsIgnoreCase("") || purcchaseQty.length()==0){
            purchaseBinding.purchaseQtyTextInput.setError("Product Sell Price Required");
        }else if(purchaseUnitPrice.equalsIgnoreCase("") || purchaseUnitPrice.length()==0){
            purchaseBinding.purchaseUnitPriceTextInput.setError("Product Unit Price Required");
        }else if(totalAmount.equalsIgnoreCase("") || totalAmount.length()==0){
            purchaseBinding.totalAmountTextInput.setError("Ensure Product Quantity and unit price fields are filled");
        }else if(payAmount.equalsIgnoreCase("") || payAmount.length()==0){
            purchaseBinding.payementAmountTextInput.setError("Amount Payed Required");
        }else if(paidWith.equalsIgnoreCase("Choose Payment") || paidWith.length()==0){
            SetError("Paid with required",purchaseBinding.paymentTypeSpinner,purchaseBinding.tvInvisibleError4);
        }else{
            //Long purchaseId =   product.storeProductInfo(new ProductDatabaseModel(prdName,prdCode,prdPrice,prdSellPrice,prdUnit,prdBrand,prdSize,prdCategory));

            int purchaseBalance = Integer.parseInt(totalAmount)-Integer.parseInt(purchasePayment);

            String purchaseDesc ="";

            android.text.format.DateFormat df = new android.text.format.DateFormat();


            String now =  ""+df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date());;//DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(tranDateFormat);

           PurchaseModel purchaseModel= new PurchaseModel(productId,variationId,supplierId,purchaseDate,purcchaseQty,purchaseUnitPrice,totalAmount+"",purchasePayment,""+purchaseBalance,purchaseDesc,UserSession.getEmployeeId(),"",now);
            /*try {
                PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
                postTransaction.createPurchase(getString(R.string.base_url),getString(R.string.base_url) +"/inventory/create/productcategory",purchase,stock,productVariation,purchaseModel,productCategory,purchaseBinding);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            Long purchaseId = purchase.CreateNewPurchase(purchaseModel);

            if (purchaseId > 0) {

                Log.e("Stock", "Exists");
                StockDatabaseModel stockDatabaseModel;

                stockDatabaseModel = new StockDatabaseModel();
                stockDatabaseModel.setProductId(purchaseModel.getProduct_id());
                stockDatabaseModel.setProductQuantity(String.valueOf(purchaseModel.getPurchase_product_quantity()));

                Boolean save = false;
                String newStock = "0";
                String currentStock = stock.getCurrentTock(purchaseModel.getProduct_id());
                if(!currentStock.equalsIgnoreCase("-1")){
                     newStock = String.valueOf(Integer.parseInt(currentStock)+Integer.parseInt(stockDatabaseModel.getProductQuantity()));
                    save = stock.updateStock(stockDatabaseModel.getProductId(), newStock);
                }else{
                    save = stock.storeStock(new StockDatabaseModel("" + purchaseModel.getProduct_id(), stockDatabaseModel.getProductFor(), purchaseModel.purchase_product_quantity, UserSession.getEmployeeId()));

                }

                if (save) {

                    ProductVariationModel pvModel = productVariation.getProductVarDetails(purchaseModel.getProduct_variation_id(),purchaseModel.getProduct_id());

                    if(pvModel ==null){
                        newStock = purchaseModel.getPurchase_product_quantity();
                    }else{
                        newStock = String.valueOf(Integer.parseInt(pvModel.getP_v_Stock())+Integer.parseInt(purchaseModel.getPurchase_product_quantity()));
                    }

                    Boolean save_pv_stock = productVariation.updatePvStock(purchaseModel.getProduct_variation_id(), purchaseModel.getProduct_id(), newStock);

                    if (save_pv_stock) {
                        Log.e("PVStock", "Updated Successfully");
                        new DialogUIActivity(view.getContext()).showDialog("Add Purchase", "Purchase has been saved successfully");
                        clearForm((ViewGroup) purchaseBinding.addPurchaseLayout);
                    } else {
                        Log.e("PVStock", "Failed to updated");
                        new DialogUIActivity(view.getContext()).showDialog("Add Purchase", "Purchase failed to be saved");
                    }

                } else {
                    new DialogUIActivity(view.getContext()).showDialog("Add Purchase", "Purchase failed to be saved");
                }


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

    public void SetError(String errorMessage, SearchableSpinner spinner, TextView errorView)
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

    private class InputTextWatcher implements TextWatcher {
        private final WeakReference<EditText> editTextWeakReference;


        public InputTextWatcher(EditText editText) {
            editTextWeakReference = new WeakReference<EditText>(editText);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            String s = editable.toString();

            if (s.isEmpty()) return;
            //editText.removeTextChangedListener(this);

            if (editText.getId() == purchaseBinding.purchaseQtyEditText.getId()) {
                Log.d("PurchaseQty", s);
                String unitPx = purchaseBinding.purchaseUnitPriceEditText.getText().toString();
                if (!unitPx.isEmpty() && !(unitPx.length() == 0)) {
                    Log.d("PurchaseUnitPrice", unitPx);
                    int totalAmount = Integer.parseInt(s) * Integer.parseInt(unitPx);
                    setTotalAmount(totalAmount);
                }

            }
            if (editText.getId() == purchaseBinding.purchaseUnitPriceEditText.getId()) {
                Log.d("PurchaseUnitPrice", s);
                String purchaseQty = purchaseBinding.purchaseQtyEditText.getText().toString();
                if (!purchaseQty.isEmpty() && !(purchaseQty.length() == 0)) {
                    Log.d("PurchaseQty", purchaseQty);
                    int totalAmount = Integer.parseInt(s) * Integer.parseInt(purchaseQty);
                    setTotalAmount(totalAmount);
                }
            }
        }
    }

    private void setTotalAmount(int totalAmount){

        purchaseBinding.totalAmountEditText.setText(String.valueOf(totalAmount));
    }

    private void updateView() {
        try {
            allProducts = product.getProducts();
            if (allProducts != null) {
                productNames = new String[allProducts.size() + 2];
                if (allProducts.size() == 0) {
                    productNames[0] = "Select Product";
                    productNames[1] = "Create Product";
                } else {
                    productNames[0] = "Select Product";
                    productNames[1] = "Create Product";
                }

                int count = 2;
                for (ProductDatabaseModel s : allProducts) {
                    productNames[count] = s.getProductName() + "(" + s.getProductBrand() + ")";
                    count++;
                }

                if (productNames.length > 0) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productNames);
                    try {
                        purchaseBinding.prdSpinner.setTitle("Select Product");
                        purchaseBinding.prdSpinner.setAdapter(adapter2);
                    } catch (Exception ex) {
                        Log.e("Exception Occuer", ex.getMessage());
                    }

                }
            } else {
                productNames = new String[2];
                productNames[0] = "Select Supplier";
                productNames[1] = "Create Supplier";

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productNames);
                try {
                    purchaseBinding.prdSpinner.setAdapter(adapter2);
                } catch (Exception ex) {
                    Log.e("Exception Occuer", ex.getMessage());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        purchaseBinding.prdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(productNames[position].equalsIgnoreCase("Create Product")){
                    Intent intent = new Intent(getContext(), AddProductActivity.class);
                    getContext().startActivity(intent);
                }else if(position>1){
                    Log.e("SelectedProduct",productNames[position]);
                    productId = allProducts.get(position-2).getProductId()+"";
                    productCategory = allProducts.get(position-2).getProductCategory();

                    Log.e("ProductId",productId);
                    Log.e("Category",productCategory);

                    if(variation.haveAnyVariation()){
                        allVariations = variation.getVariationsByPId(productId);
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
                            select_variation[count] = s.getVariation_name();
                            count++;
                        }
                    }else{
                        select_variation = new String[2];
                        select_variation[0] = "Select Variation";
                        select_variation[1] = "Create New Variation";

                    }
                    ArrayAdapter<String > adapter3 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,select_variation);
                    try{
                        purchaseBinding.prdVariationpinner.setAdapter(adapter3);
                        purchaseBinding.prdVariationpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(select_variation[position].equalsIgnoreCase("Create Supplier")){
                                    Intent intent = new Intent(getContext(), AddVariationActivity.class);
                                    getContext().startActivity(intent);
                                }else if(position>1){
                                    Log.e("SelectedVariation",select_variation[position]);
                                    variationId = allVariations.get(position-2).getId()+"";
                                    Log.e("variationId",variationId);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }catch (Exception ex){
                        Log.e("Exception Occuer",ex.getMessage());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        //Retreiving list of suppliers
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
                    purchaseBinding.prdSupplierSpinner.setTitle("Select Product Supplier");
                    purchaseBinding.prdSupplierSpinner.setAdapter(adapter2);
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
                purchaseBinding.prdSupplierSpinner.setAdapter(adapter2);
            }catch (Exception ex){
                Log.e("Exception Occuer",ex.getMessage());
            }
        }

        purchaseBinding.prdSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(SupplierNames[position].equalsIgnoreCase("Create Supplier")){
                    Intent intent = new Intent(getContext(), AddSupplierActivity.class);
                    getContext().startActivity(intent);
                }else if(position>1){
                    Log.e("SelectedSupplier",SupplierNames[position]);
                    supplierId = allSuppliers.get(position-2).getId()+"";
                    Log.e("SupplierId",supplierId);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }








    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }
}