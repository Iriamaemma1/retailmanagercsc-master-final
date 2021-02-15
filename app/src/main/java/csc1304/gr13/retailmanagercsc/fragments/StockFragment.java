package csc1304.gr13.retailmanagercsc.fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.adapters.StockAdapter;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.databinding.FragmentStockBinding;
import csc1304.gr13.retailmanagercsc.modelClass.StockModel;

import java.util.ArrayList;
/**
 * Created by CS1304 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment {


    public StockFragment() {
        // Required empty public constructor
    }


    private FragmentStockBinding stockBinding;
    private View view;
    private StockAdapter adapter;
    private StockModel stockModel;
    private Stock stock;
    private ArrayList<StockModel> allStocks;
    private UpdateDatabase updateDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        stockBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_stock, container, false);
        view = stockBinding.getRoot();
        stockModel = new StockModel();
        updateDatabase = new UpdateDatabase(getActivity());

        stock = new Stock(getActivity());



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
            allStocks = stock.getStocks();
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new StockAdapter(getActivity(),allStocks);

        if (stock.haveAnyStock()){
            stockBinding.stockLv.setVisibility(View.VISIBLE);
            stockBinding.errorTv.setVisibility(View.GONE);

            //stockBinding.stockLv.setAdapter(adapter);
        }else {
            stockBinding.stockLv.setVisibility(View.GONE);
            stockBinding.errorTv.setVisibility(View.VISIBLE);
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