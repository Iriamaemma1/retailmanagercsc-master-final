package csc1304.gr13.retailmanagercsc.suppliertables;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.adapters.SupplierAdapter;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.databinding.FragmentSupplierBinding;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.suppliertables.frgs.AddSupplierActivity;

import java.util.ArrayList;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Suppliefrg extends Fragment {


    public Suppliefrg() {
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

        supplierBinding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //after the change calling the method and passing the search input
                filter(s.toString());
            }
        });
        supplierBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Refreshing Items",Toast.LENGTH_SHORT);
                listViewUpdate();
                adapter.notifyDataSetChanged();
            }
        });

        supplierBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSupplierActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void listViewUpdate() {
        try {
            allSuppliers = supplier.getAllSuppliers();
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new SupplierAdapter(getActivity(),allSuppliers);

        if (supplier.haveAnyData()){
            supplierBinding.searLayout.setVisibility(View.VISIBLE);
            supplierBinding.supplierLv.setVisibility(View.VISIBLE);
            supplierBinding.errorTv.setVisibility(View.GONE);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 1);
            supplierBinding.supplierLv.setLayoutManager(mLayoutManager);
            supplierBinding.supplierLv.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            supplierBinding.supplierLv.setItemAnimator(new DefaultItemAnimator());
            supplierBinding.supplierLv.setAdapter(adapter);
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

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<SupplierModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (SupplierModel s : allSuppliers) {
            //if the existing elements contains the search input
            if (s.getSupplier_name().toLowerCase().contains(text.toLowerCase()) || s.getSupplier_company_name().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);


    }

    @Override
    public void onResume() {
        super.onResume();
        listViewUpdate();
    }
}