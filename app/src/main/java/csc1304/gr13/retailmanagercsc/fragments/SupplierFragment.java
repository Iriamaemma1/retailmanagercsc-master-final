package csc1304.gr13.retailmanagercsc.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.adapters.SupplierAdapter;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.databinding.FragmentSupplierBinding;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierFragment extends Fragment {


    public SupplierFragment() {
        // Required empty public constructor
    }


    private FragmentSupplierBinding supplierBinding;

    private View view;
    private SupplierAdapter adapter;
    private SupplierModel supplierModel;
    private Supplier supplier;
    private ArrayList<SupplierModel> allSuppliers;
    private UpdateDatabase updateDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        supplierBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_supplier, container, false);
        view = supplierBinding.getRoot();
        supplierModel = new SupplierModel();
        updateDatabase = new UpdateDatabase(getActivity());

        supplier = new Supplier(getActivity());

        Log.e("Fragment","Supplier Fragment");


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
//                ,new IntentFilter("updateDatabaseStatus"));
        listViewUpdate();//update stock data
    }

    private void listViewUpdate() {
        try {
            allSuppliers = supplier.getAllSuppliers();
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new SupplierAdapter(getActivity(),allSuppliers);

        if (supplier.haveAnyData()){
            supplierBinding.supplierLv.setVisibility(View.VISIBLE);
            supplierBinding.errorTv.setVisibility(View.GONE);

            //supplierBinding.supplierLv.setAdapter(adapter);
        }else {
            supplierBinding.supplierLv.setVisibility(View.GONE);
            supplierBinding.errorTv.setVisibility(View.VISIBLE);
        }
    }
//
//    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String status = intent.getStringExtra("status");
//            if (status.equals("stock")){
//                listViewUpdate();
//            }
//        }
//    };
}