package csc1304.gr13.retailmanagercsc.customertables;


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
import csc1304.gr13.retailmanagercsc.adapters.CustomerAdapter;
import csc1304.gr13.retailmanagercsc.customertables.addcustomer.AddCustomerActivity;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.databinding.FragmentCustomerBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;

import java.util.ArrayList;
/**
 * Created by CS1304 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Customerfrg extends Fragment {


    public Customerfrg() {
        // Required empty public constructor
    }


    private FragmentCustomerBinding customerBinding;

    private View view;
    private CustomerAdapter adapter;
    private CustomerModel customerModel;
    private Customer customer;
    private ArrayList<CustomerModel> allCustomers;
    private UpdateDatabase updateDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        customerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer, container, false);
        view = customerBinding.getRoot();
        customerModel = new CustomerModel();
        updateDatabase = new UpdateDatabase(getActivity());

        customer = new Customer(getActivity());

        Log.e("Fragment", "Supplier Fragment");


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
//                ,new IntentFilter("updateDatabaseStatus"));
        listViewUpdate();//update stock data

        customerBinding.editTextSearch.addTextChangedListener(new TextWatcher() {
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
        customerBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Refreshing Items",Toast.LENGTH_SHORT);
                listViewUpdate();
                adapter.notifyDataSetChanged();
            }
        });
        customerBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCustomerActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void listViewUpdate() {
        try {
            allCustomers = customer.getAllCustomer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new CustomerAdapter(getActivity(), allCustomers);

        if (customer.haveAnyData()) {
            customerBinding.searLayout.setVisibility(View.VISIBLE);
            customerBinding.customerLv.setVisibility(View.VISIBLE);
            customerBinding.errorTv.setVisibility(View.GONE);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 1);
            customerBinding.customerLv.setLayoutManager(mLayoutManager);
            customerBinding.customerLv.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            customerBinding.customerLv.setItemAnimator(new DefaultItemAnimator());
            customerBinding.customerLv.setAdapter(adapter);
        } else {
            customerBinding.customerLv.setVisibility(View.GONE);
            customerBinding.errorTv.setVisibility(View.VISIBLE);
        }
    }


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
        ArrayList<CustomerModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (CustomerModel s : allCustomers) {
            //if the existing elements contains the search input
            if (s.getCustomerName().toLowerCase().contains(text.toLowerCase())) {
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