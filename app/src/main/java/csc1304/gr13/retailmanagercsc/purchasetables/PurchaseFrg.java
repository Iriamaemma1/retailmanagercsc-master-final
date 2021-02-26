package csc1304.gr13.retailmanagercsc.purchasetables;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
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
import csc1304.gr13.retailmanagercsc.adapters.PurchaseAdapter;
import csc1304.gr13.retailmanagercsc.database.Purchase;
import csc1304.gr13.retailmanagercsc.databinding.FragmentPurchaseBinding;
import csc1304.gr13.retailmanagercsc.modelClass.PurchaseModel;
import csc1304.gr13.retailmanagercsc.purchasetables.addpurchase.AddPurchaseActivity;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFrg extends Fragment {


    public PurchaseFrg() {
        // Required empty public constructor
    }


    private FragmentPurchaseBinding purchaseBinding;

    private View view;
    private PurchaseAdapter adapter;
    private PurchaseModel purchaseModel;
    private Purchase purchase;
    private ArrayList<PurchaseModel> allPurchases;
    private UpdateDatabase updateDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        purchaseBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_purchase, container, false);
        view = purchaseBinding.getRoot();
        purchaseModel = new PurchaseModel("1", "1", "2018-08-26 00:05:30.729", "50", "1000", "50000", "30000", "20000", "Need to payment about 20,000 UGX", "1", "", "2018-08-26 00:05:30.729");
        updateDatabase = new UpdateDatabase(getActivity());

        purchase = new Purchase(getActivity());

        Log.e("Fragment","Supplier Fragment");


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
//                ,new IntentFilter("updateDatabaseStatus"));
        if(purchase.haveAnyData()){
            listViewUpdate();//update stock data
        }


        purchaseBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Refreshing Items",Toast.LENGTH_SHORT);
                if(purchase.haveAnyData()){
                    listViewUpdate();//update stock data
                }
                adapter.notifyDataSetChanged();
            }
        });

        purchaseBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddPurchaseActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void listViewUpdate() {
        try {
            allPurchases = purchase.getAllPurchases();
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new PurchaseAdapter(Objects.requireNonNull(getActivity()),allPurchases);

        if (purchase.haveAnyData()){
            purchaseBinding.purchaseLv.setVisibility(View.VISIBLE);
            purchaseBinding.errorTv.setVisibility(View.GONE);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 1);
            purchaseBinding.purchaseLv.setLayoutManager(mLayoutManager);
            purchaseBinding.purchaseLv.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            purchaseBinding.purchaseLv.setItemAnimator(new DefaultItemAnimator());

            purchaseBinding.purchaseLv.setAdapter(adapter);
        }else {
            purchaseBinding.purchaseLv.setVisibility(View.GONE);
            purchaseBinding.errorTv.setVisibility(View.VISIBLE);
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
        ArrayList<PurchaseModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (PurchaseModel s : allPurchases) {
            //if the existing elements contains the search input
            if (s.getProduct_name().toLowerCase().contains(text.toLowerCase()) || s.getSupplier_name().toLowerCase().contains(text.toLowerCase())) {
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
        listViewUpdate();//update stock data
    }
}