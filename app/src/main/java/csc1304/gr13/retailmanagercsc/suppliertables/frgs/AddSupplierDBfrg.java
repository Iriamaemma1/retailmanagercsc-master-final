package csc1304.gr13.retailmanagercsc.suppliertables.frgs;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.UserSession;
import csc1304.gr13.retailmanagercsc.adapters.StockAdapter;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddSupplierDbBinding;
import csc1304.gr13.retailmanagercsc.modelClass.StockModel;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSupplierDBfrg extends Fragment {


    public AddSupplierDBfrg() {
        // Required empty public constructor
    }


    private FragmentAddSupplierDbBinding supplierDbBinding;
    private View view;
    private StockAdapter adapter;
    //private StockModel stockModel;
    private Stock stock;
    private ArrayList<StockModel> allStocks;
    private UpdateDatabase updateDatabase;
    private String[] customerType = new String []{"Select Type","Regular","Walk-Throught","Non-Regular"};
    private String[] gender = new String []{"Select Gender","Male","Female"};
    private Supplier supplier;
    private ProgressDialog progressDialog;
    private Handler dialogHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        supplierDbBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_supplier_db, container, false);
        view = supplierDbBinding.getRoot();
      //  stockModel = new StockModel();
        updateDatabase = new UpdateDatabase(getActivity());


        supplier = new Supplier(getActivity());

        dialogHandler = new Handler(Looper.getMainLooper());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        supplierDbBinding.saveCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String supplier_name,supplier_company_name,supplier_contact_person,supplier_phone_number,supplier_address;
                String supplier_bank_name,supplier_bank_account,supplier_email,supplier_website,supplier_description,created_by_id;
                String updated_by_id,created_at;

                supplier_name = supplierDbBinding.supplierNameEditText.getText().toString();
                supplier_company_name = supplierDbBinding.supplierCompanyNameEditText.getText().toString();

                supplier_phone_number = supplierDbBinding.supplierPhoneNumberEditText.getText().toString();
                supplier_address = supplierDbBinding.supplierAddressEditText.getText().toString();
                supplier_bank_name = supplierDbBinding.supplierBankNameEditText.getText().toString();
                supplier_bank_account = supplierDbBinding.supplierBankAccountEditText.getText().toString();
                supplier_email = supplierDbBinding.supplierEmailEditText.getText().toString();
                supplier_website = supplierDbBinding.supplierWebsiteEditText.getText().toString();
                supplier_description = supplierDbBinding.supplierDescriptionEditText.getText().toString();

                created_by_id = UserSession.getEmployeeId();
                updated_by_id = "";

                android.text.format.DateFormat df = new android.text.format.DateFormat();

                String now =  ""+df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date());;//DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(tranDateFormat);

                created_at = now;

                if(supplier_company_name.equalsIgnoreCase("") ||supplier_company_name.length()==0 ){
                    supplierDbBinding.supplierCompanyNameTextInput.setErrorEnabled(true);
                    supplierDbBinding.supplierCompanyNameTextInput.setError("Supplier Company Name Required");

                } else if(supplier_name.equals("") || supplier_name.length()==0){
                    supplierDbBinding.supplierNameTextInput.setErrorEnabled(true);
                    supplierDbBinding.supplierNameTextInput.setError("Supplier Name Required");
                }else if(supplier_phone_number.equalsIgnoreCase("") ||supplier_phone_number.length()==0 ){
                    supplierDbBinding.supplierPhoneNumberTextInput.setErrorEnabled(true);
                    supplierDbBinding.supplierPhoneNumberTextInput.setError("Supplier Phone Number Required");

                }else if(supplier_address.equalsIgnoreCase("") ||supplier_address.length()==0 ){
                    supplierDbBinding.supplierAddressTextInput.setErrorEnabled(true);
                    supplierDbBinding.supplierAddressTextInput.setError("Supplier Address Required");

                }else{
                    SupplierModel supplierModel = new SupplierModel(supplier_name, supplier_company_name, "" ,supplier_phone_number, supplier_address, supplier_bank_name, supplier_bank_account, supplier_email, supplier_website, supplier_description, created_by_id, updated_by_id, created_at);

                    /*try {
                        PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
                        postTransaction.createSupplier(getString(R.string.base_url) +"/inventory/create/supplier",supplier,supplierModel,supplierDbBinding);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/


                    Boolean save = supplier.CreateNewSupplier(supplierModel);


                    if (save) {
                        clearForm((ViewGroup) supplierDbBinding.addSupplierLayout);
                        new DialogUIActivity(v.getContext()).showDialog("Add Supplier", "Supplier has been saved successfully");
                    } else {
                        new DialogUIActivity(v.getContext()).showDialog("Add Supplier", "Supplier failed to be saved");
                    }
                }

            }
        });

    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }
}