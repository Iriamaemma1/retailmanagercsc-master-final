package csc1304.gr13.retailmanagercsc.sellTabes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.adapters.ProductListAdapter;
import csc1304.gr13.retailmanagercsc.databinding.FragmentListProductFrgBinding;
import csc1304.gr13.retailmanagercsc.modelClass.ProductListModel;

import java.util.ArrayList;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public class ListProductFrg extends Fragment {

    private FragmentListProductFrgBinding binder;
    private ProductListAdapter adapter;
    private ArrayList<ProductListModel> products;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_list_product_frg, container, false);
        products = new ArrayList<>();

        //binder = DataBindingUtil.inflate(inflater,R.layout.fragment_list_product_frg, container, false);
        return binder.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver
                ,new IntentFilter("selectedProduct"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
                ,new IntentFilter("clearAll"));

        updateListView();
        binder.productListLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Are sure you want to remove this item?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        products.remove(position);
                        sendBroadCastToReceiptPage(position);
                        updateListView();
                    }
                });
                dialog.setNegativeButton("No",null);

                dialog.show();
                return true;
            }
        });
    }

    public void updateListView(){
        adapter = new ProductListAdapter(getActivity(),products);
        binder.productListLv.setAdapter(adapter);
    }

    private void sendBroadCastToReceiptPage(int position) {
        Intent intent = new Intent("removeProduct");
        intent.putExtra("position",position);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ProductListModel temp = (ProductListModel) intent.getSerializableExtra("product");
            products.add(temp);
            updateListView();
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean flag = intent.getBooleanExtra("flag",false);
            if (flag){
                products = new ArrayList<>();
                updateListView();
            }
        }
    };

}
