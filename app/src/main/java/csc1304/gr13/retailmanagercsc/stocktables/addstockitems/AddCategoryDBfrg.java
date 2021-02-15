package csc1304.gr13.retailmanagercsc.stocktables.addstockitems;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UpdateDatabase;
import csc1304.gr13.retailmanagercsc.adapters.StockAdapter;
import csc1304.gr13.retailmanagercsc.database.Category;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddCategoryDbBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockModel;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryDBfrg extends Fragment {


    public AddCategoryDBfrg() {
        // Required empty public constructor
    }


    private FragmentAddCategoryDbBinding categoryBinding;
    private View view;
    private StockAdapter adapter;
    //private StockModel stockModel;
    private Stock stock;
    private ArrayList<StockModel> allStocks;
    private UpdateDatabase updateDatabase;
    private Category category;

    private ProgressDialog progressDialog;
    private Handler dialogHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        categoryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_category_db, container, false);
        view = categoryBinding.getRoot();

        updateDatabase = new UpdateDatabase(getActivity());

        category = new Category(getActivity());

        dialogHandler = new Handler(getActivity().getMainLooper());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        categoryBinding.saveCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(categoryBinding.catNameEditText.getText().equals("") || categoryBinding.catNameEditText.getText().length()==0){
                    categoryBinding.catNameTextInput.setErrorEnabled(true);
                    categoryBinding.catNameTextInput.setError("Category Name Required");
                }
                if(categoryBinding.catDescEditText.getText().equals("") ||categoryBinding.catDescEditText.getText().length()==0 ){

                }
                if(categoryBinding.catNameEditText.getText().length()>0){

                    CategoryModel categoryModel = new CategoryModel(categoryBinding.catNameEditText.getText().toString(),categoryBinding.catDescEditText.getText().toString());
                    /*try {
                        PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
                        postTransaction.createCategory(getString(R.string.base_url) +"/inventory/create/productcategory",category,categoryModel,categoryBinding);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    Boolean saveCategory = category.createNewCategory(categoryModel);

                    if (saveCategory) {

                        new DialogUIActivity(v.getContext()).showDialog("Add Category", "Stock category " + categoryBinding.catNameEditText.getText().toString() + " has been saved successfully");
                        categoryBinding.catNameEditText.setText("");
                        categoryBinding.catDescEditText.setText("");

                        Toast.makeText(v.getContext(), "New Category details inserted", Toast.LENGTH_LONG);


                    } else {
                        Toast.makeText(v.getContext(), "New Category details insert failed", Toast.LENGTH_LONG);
                        new DialogUIActivity(v.getContext()).showDialog("Add Category", "Stock category " + categoryBinding.catNameEditText.getText().toString() + " failed to be saved,\n Please try again");

                    }

                }
            }
        });
    }



}