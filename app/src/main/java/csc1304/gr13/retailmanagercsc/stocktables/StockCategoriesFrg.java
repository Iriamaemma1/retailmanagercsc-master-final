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
import csc1304.gr13.retailmanagercsc.adapters.CategoryAdapter;
import csc1304.gr13.retailmanagercsc.database.Category;
import csc1304.gr13.retailmanagercsc.databinding.FragmentListCategoryFrgBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.AddCategoryActivity;

import java.util.ArrayList;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public class StockCategoriesFrg extends Fragment {

    private Handler threadHandler;
    private FragmentListCategoryFrgBinding binder;
    private CategoryAdapter adapter;
    private ArrayList<CategoryModel> allCategories;
    private View view;
    private CategoryModel categoryModel;
    private Category category;
    private UpdateDatabase updateDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        threadHandler = new Handler(Looper.getMainLooper());
/*
        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_list_category_frg, container, false);
        categories = new ArrayList<>();

        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_list_category_frg, container, false);
        return binder.getRoot();*/

        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_list_category_frg, container, false);
        view = binder.getRoot();
        categoryModel = new CategoryModel();
        updateDatabase = new UpdateDatabase(getActivity());

        category = new Category(getActivity());



        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        binder.editTextSearch.addTextChangedListener(new TextWatcher() {
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

        binder.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Refreshing Items",Toast.LENGTH_SHORT);
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listViewUpdate();
                    }
                });

            }
        });
        binder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCategoryActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }



    private void listViewUpdate() {
        try {
            allCategories = category.getAllCategories();
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new CategoryAdapter(getActivity(),allCategories);

        if (category.haveAnyData()){
            binder.searLayout.setVisibility(View.VISIBLE);
            binder.productLv.setVisibility(View.VISIBLE);
            binder.errorTv.setVisibility(View.GONE);

            //binder.productLv.setAdapter(adapter);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
            binder.productLv.setLayoutManager(mLayoutManager);
            binder.productLv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            binder.productLv.setItemAnimator(new DefaultItemAnimator());
            binder.productLv.setAdapter(adapter);

          //  prepareAlbums();

            /*try {
                Glide.with(view.getContext()).load(R.drawable.default_product_img).into((ImageView) view.findViewById(R.id.backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }else {
            binder.productLv.setVisibility(View.GONE);
            binder.errorTv.setVisibility(View.VISIBLE);
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
        ArrayList<CategoryModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (CategoryModel s : allCategories) {
            //if the existing elements contains the search input
            if (s.getCategory_name().toLowerCase().contains(text.toLowerCase())) {
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
                listViewUpdate();
            }
        });

    }
}
