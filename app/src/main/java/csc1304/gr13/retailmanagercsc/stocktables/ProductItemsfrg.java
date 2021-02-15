package csc1304.gr13.retailmanagercsc.stocktables;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
import csc1304.gr13.retailmanagercsc.adapters.ProductAdapter;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.databinding.FragmentProductItemsBinding;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.AddProductActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductItemsfrg extends Fragment {

    private Handler threadHandler;

    public ProductItemsfrg() {
        // Required empty public constructor
    }


    private FragmentProductItemsBinding productBinding;
    private View view;
    private ProductAdapter adapter;
    //private StockModel stockModel;
    private Product product;
    private ArrayList<ProductDatabaseModel> allProducts;
    private UpdateDatabase updateDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        productBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_items, container, false);
        view = productBinding.getRoot();
      //  stockModel = new StockModel();
        updateDatabase = new UpdateDatabase(getActivity());

        product = new Product(getActivity());



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
//                ,new IntentFilter("updateDatabaseStatus"));

        threadHandler = new Handler(Looper.getMainLooper());
        productBinding.editTextSearch.addTextChangedListener(new TextWatcher() {
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

        productBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Refreshing Items",Toast.LENGTH_SHORT);
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listViewUpdate();//update stock data
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        productBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddProductActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void listViewUpdate() {
        try {
            allProducts = product.getProducts();
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new ProductAdapter(getActivity(),allProducts);

        if (product.haveAnyProduct()){
            productBinding.searLayout.setVisibility(View.VISIBLE);
            productBinding.stockLv.setVisibility(View.VISIBLE);
            productBinding.errorTv.setVisibility(View.GONE);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
            productBinding.stockLv.setLayoutManager(mLayoutManager);
            productBinding.stockLv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            productBinding.stockLv.setItemAnimator(new DefaultItemAnimator());
            productBinding.stockLv.setAdapter(adapter);

        }else {
            productBinding.stockLv.setVisibility(View.GONE);
            productBinding.errorTv.setVisibility(View.VISIBLE);
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
        ArrayList<ProductDatabaseModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ProductDatabaseModel s : allProducts) {
            //if the existing elements contains the search input
            if (s.getProductName().toLowerCase().contains(text.toLowerCase()) || s.getProductBrand().toLowerCase().contains(text.toLowerCase())) {
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
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                listViewUpdate();//update stock data
            }
        });

    }
}