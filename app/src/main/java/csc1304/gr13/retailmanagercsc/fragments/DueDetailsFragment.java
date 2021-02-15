package csc1304.gr13.retailmanagercsc.fragments;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.CustomerDue;
import csc1304.gr13.retailmanagercsc.database.SellsInfo;
import csc1304.gr13.retailmanagercsc.databinding.FragmentDueDetailsBinding;
import csc1304.gr13.retailmanagercsc.modelClass.DueDetailsModel;
import csc1304.gr13.retailmanagercsc.utils.HoldProductSellsVariables;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static csc1304.gr13.retailmanagercsc.fragments.DueListFragment.TAG;
/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class DueDetailsFragment extends Fragment {


    private String dueDbId;
    private FragmentDueDetailsBinding binding;
    private CustomerDue customerDue;
    private DueDetailsModel dueDetails;
    private SellsInfo sellsInfo;

    private Customer customerDatabase;
    private String date = "170605";
    private Calendar calendar;
    private View v;
    private String[] paymentMethod = new String []{"Pay With","Cash","Card","MoMo Pay"};
    HashMap<String, String> tranRequest = null;

    public DueDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_due_details, container, false);
        dueDbId = getArguments().getString("dueId");
        customerDue = new CustomerDue(getActivity());
        customerDatabase = new Customer(getActivity());
        sellsInfo = new SellsInfo(getActivity());
        dueDetails = customerDue.getDueDetails(dueDbId);

        Log.d(TAG, "onCreateView: "+dueDetails.getSellCode());
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");//example date 06-11-17
        date = dateFormat.format(calendar.getTime());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            updateDetails();
        }catch (Exception e){
            Log.d(TAG, "onViewCreated: "+e);
        }

        binding.saveBtn.setOnClickListener(listener);
    }



    public void updateDetails(){
        NumberFormat formatBal = new DecimalFormat("#,###");;
        formatBal.setMaximumFractionDigits(0);

        binding.nameTv.setText(dueDetails.getName());
        binding.phoneTv.setText(dueDetails.getPhone());
        binding.emailTv.setText(dueDetails.getEmail());

        binding.paymentSpinner.setTitle("Select Payment Method");

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,paymentMethod);
        binding.paymentSpinner.setAdapter(adapter);

        binding.invoiceNoTv.setText(dueDetails.getSellCode());
        binding.totalBIllTv.setText(formatBal.format(Double.parseDouble(dueDetails.getTotalAmount())));
        binding.payAmountTv.setText(formatBal.format(Double.parseDouble(dueDetails.getPaid())));
        binding.dateTv.setText(dueDetails.getDate());


        Log.e("DueValue",dueDetails.getDue());
        binding.presentDueTv.setText(formatBal.format(Double.parseDouble(dueDetails.getDue())));
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            v = view;
            int previousPaid = getInteger(binding.newPaidTv.getText().toString());
            int newPaid = getInteger(binding.payAmountEt.getText().toString());
            int previousDue = getInteger(binding.presentDueTv.getText().toString().replace(",",""));
            String paymentMethod = binding.paymentSpinner.getSelectedItem().toString();



            if(paymentMethod.equalsIgnoreCase("Pay With")){
                SetError("Choose Payment",binding.paymentSpinner,binding.tvInvisibleError3);
            }else{
                if(newPaid>0){
                    if (previousDue == 0){
                        binding.presentDueTv.setText("0");
                        showMessage("No Due Left");
                        new DialogUIActivity(getContext()).showDialog("","No Due Left");

                    }else{

                        HoldProductSellsVariables.setBaseUrl(getString(R.string.base_url));
                        HoldProductSellsVariables.setCustomerDatabase(customerDatabase);
                        HoldProductSellsVariables.setCustomerDue(customerDue);
                        HoldProductSellsVariables.setSellsInfo(sellsInfo);
                        HoldProductSellsVariables.setDate(date);
                        HoldProductSellsVariables.setDueDetails(dueDetails);
                        HoldProductSellsVariables.setAmountPaid(newPaid);
                        HoldProductSellsVariables.setPaymentType(paymentMethod);
                        HoldProductSellsVariables.setRequestType("Due");


                        if(paymentMethod.equalsIgnoreCase("Card")){
                           Toast.makeText(v.getContext(),"Payment by card",Toast.LENGTH_LONG);
                        }else{

                            PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
                            try {
                                postTransaction.updateSellsDueInfo(getString(R.string.base_url),binding,dueDetails,sellsInfo,customerDue,customerDatabase,date,newPaid,paymentMethod);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                        //int totalPaid = getInteger(dueDetails.getPaid())+getInteger(binding.newPaidTv.getText().toString());

                        //updateDueDatabase((previousDue - newPaid),String.valueOf(totalPaid));
                        //updateSellDatabase((previousDue - newPaid),String.valueOf(totalPaid),dueDetails.getSellCode());
                       // updateCustomerDatabase(previousDue - newPaid);

                    }
                }else{
                    new DialogUIActivity(getContext()).showDialog("","Amount Greater than zero, required");
                }

            }


        }
    };

    private void updateCustomerDatabase(int dueAmount) {
        //customer info update
        boolean status = customerDatabase.updateCustomerDueAmount(dueDetails.getCustomerCode(), String.valueOf(dueAmount));
    }

    private void updateSellDatabase(int dueAmount,String paidAmount,String sellCode) {

        boolean status;
        if (dueAmount == 0){
            status = sellsInfo.updateSellDetails(sellCode,paidAmount,"","0");
        }else {
            status = sellsInfo.updateSellDetails(sellCode,paidAmount,"","1");
        }

        if (status){
            Log.d(TAG, "updateDueDatabase: success");
        }else {
            Log.d(TAG, "updateDueDatabase: faield");

        }
    }

    private void updateDueDatabase(int dueAmount, String paidAmount) {

        if (dueAmount == 0){
            customerDue.deleteDue(dueDbId);
        }else {
            customerDue.updateDueDetails(String.valueOf(dueAmount),paidAmount,dueDbId);
        }
    }

    public void showMessage(String str){
        Snackbar.make(v, str,Snackbar.LENGTH_SHORT).show();
    }

    public int getInteger(String str){

        if(str.equalsIgnoreCase("") || str.isEmpty()){
            return 0;
        }else{
            return Integer.parseInt(str);
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
}
