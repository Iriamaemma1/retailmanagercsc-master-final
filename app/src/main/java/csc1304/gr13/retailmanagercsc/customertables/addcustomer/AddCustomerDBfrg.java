package csc1304.gr13.retailmanagercsc.customertables.addcustomer;


import android.app.ProgressDialog;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.adapters.StockAdapter;
import csc1304.gr13.retailmanagercsc.database.Category;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddCustomerDbBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockModel;
import csc1304.gr13.retailmanagercsc.utils.GenerateUniqueNumber;
import csc1304.gr13.retailmanagercsc.utils.GenerateUnqueId;
import csc1304.gr13.retailmanagercsc.utils.GetDeviceId;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomerDBfrg extends Fragment {


    public AddCustomerDBfrg() {
        // Required empty public constructor
    }


    private FragmentAddCustomerDbBinding customerBinding;
    private View view;
    private StockAdapter adapter;
    //private StockModel stockModel;
    private Stock stock;
    private ArrayList<StockModel> allStocks;
    private UpdateDatabase updateDatabase;
    private Category category;
    private String[] customerType = new String []{"Select Type","Regular","Walk-Through","Non-Regular"};
    private String[] gender = new String []{"Select Gender","Male","Female"};
    private Customer customer;
    private GetDeviceId getDeviceId;
    private ProgressDialog progressDialog;
    private Handler dialogHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        customerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_customer_db, container, false);
        view = customerBinding.getRoot();
      //  stockModel = new StockModel();
        updateDatabase = new UpdateDatabase(getActivity());

        customer = new Customer(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.dialogHandler = new Handler(Looper.getMainLooper());

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,customerType);
        customerBinding.custTypeSpinner.setTitle("Select Product Unit");
        customerBinding.custTypeSpinner.setAdapter(adapter);

        customerBinding.custTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("CustomerType",customerType[position]);
                if(customerType[position].equalsIgnoreCase("Walk-Through")){

                    customerBinding.custEmailEditText.setText("N/A");
                    customerBinding.custAddressEditText.setText("N/A");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,gender);
        customerBinding.custGenderSpinner.setTitle("Select Product Unit");
        customerBinding.custGenderSpinner.setAdapter(adapter);

        customerBinding.saveCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                GenerateUniqueNumber getUniquieNumber = new GenerateUniqueNumber(getActivity());

                String custName,custCode,custType,custPhone,custGender,custEmail,custAddress,custDueAmount;

                custName = customerBinding.custNameEditText.getText().toString();
                custCode = "C"+GenerateUnqueId.getUniqueId("").toUpperCase();
                custType = customerBinding.custTypeSpinner.getSelectedItem().toString();
                custPhone = customerBinding.custPhoneEditText.getText().toString();
                custGender = customerBinding.custGenderSpinner.getSelectedItem().toString();
                custEmail = customerBinding.custEmailEditText.getText().toString();
                custAddress = customerBinding.custAddressEditText.getText().toString();
                custDueAmount = customerBinding.custDueAmountEditText.getText().toString();

                Log.e("CustCode",custCode);
                if(custName.equals("") || custName.length()==0){
                    customerBinding.custNameTextInput.setErrorEnabled(true);
                    customerBinding.custNameTextInput.setError("Customer Name Required");
                }
                else if(custType.equalsIgnoreCase("Select Type") ||custType.length()==0 ){
                    SetError("Customer Type Required",customerBinding.custTypeSpinner,customerBinding.tvInvisibleError2);

                } else if(custPhone.equals("") ||custPhone.length()==0 ){
                    customerBinding.custPhoneTextInput.setErrorEnabled(true);
                    customerBinding.custPhoneTextInput.setError("Customer Phone Required");
                }else if(custGender.equalsIgnoreCase("Select Gender")||custGender.length()==0 ){
                    SetError("Gender Required",customerBinding.custGenderSpinner,customerBinding.tvInvisibleError);

                }else{

                    CustomerModel customerData =  new CustomerModel(custName,custCode,custType,custGender,custPhone,custEmail,custAddress,custDueAmount);
                    /*try {
                        PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
                        postTransaction.createCustomer(getString(R.string.base_url) +"/inventory/create/customer",customer,customerData,customerBinding);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    Boolean save = customer.createNewCustomer(customerData);

                    if (save) {
                        clearForm(customerBinding.addCustomerLayout);
                        new DialogUIActivity(v.getContext()).showDialog("Add Customer", "Customer has been saved successfully");
                    } else {
                        new DialogUIActivity(v.getContext()).showDialog("Add Customer", "Customer failed to be saved");
                    }
                }
            }
        });
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