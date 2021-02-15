package csc1304.gr13.retailmanagercsc.stocktables.addstockitems;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import csc1304.gr13.retailmanagercsc.database.Variation;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddVariationDbBinding;
import csc1304.gr13.retailmanagercsc.modelClass.VariationModel;
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
public class AddVariationDBfrg extends Fragment {


    public AddVariationDBfrg() {
        // Required empty public constructor
    }


    private FragmentAddVariationDbBinding addVariationDbBinding;
    private View view;
    private ArrayList<StockModel> allStocks;
    private UpdateDatabase updateDatabase;
    private Variation variation;
    private ProgressDialog progressDialog;
    private Handler dialogHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        addVariationDbBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_variation_db, container, false);
        view = addVariationDbBinding.getRoot();

        updateDatabase = new UpdateDatabase(getActivity());

        variation = new Variation(getActivity());

        dialogHandler = new Handler(Looper.getMainLooper());

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addVariationDbBinding.saveCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(addVariationDbBinding.varNameEditText.getText().equals("") || addVariationDbBinding.varNameEditText.getText().length()==0){
                    addVariationDbBinding.varNameTextInput.setErrorEnabled(true);
                    addVariationDbBinding.varNameTextInput.setError("Variation Name Required");
                }
                if(addVariationDbBinding.varDescEditText.getText().equals("") ||addVariationDbBinding.varDescEditText.getText().length()==0 ){

                }
                if(addVariationDbBinding.varNameEditText.getText().length()>0){

                   VariationModel variationModel =  new VariationModel(addVariationDbBinding.varNameEditText.getText().toString(),addVariationDbBinding.varDescEditText.getText().toString());

                    /*try {
                        PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
                        postTransaction.createVariation(getString(R.string.base_url) +"/inventory/create/variation",variation,variationModel,addVariationDbBinding);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    Long saveVariation = variation.CreateVariation(variationModel);
                    Log.e("VariationId", saveVariation + "");
                    if (saveVariation > 0) {

                        new DialogUIActivity(view.getContext()).showDialog("Add Variation", "Variable " + addVariationDbBinding.varNameEditText.getText().toString() + " has been saved successfully");
                        addVariationDbBinding.varNameEditText.setText("");
                        addVariationDbBinding.varDescEditText.setText("");

                        Toast.makeText(view.getContext(), "New Variation details created", Toast.LENGTH_LONG);

                    } else {
                        Toast.makeText(view.getContext(), "New Variation details creation failed", Toast.LENGTH_LONG);
                        new DialogUIActivity(view.getContext()).showDialog("Add Variation", "Variation " + addVariationDbBinding.varNameEditText.getText().toString() + " failed to be saved,\n Please try again");

                        //categoryBinding.catNameEditText.setText("");
                        //categoryBinding.catDescEditText.setText("");
                    }

                }
            }
        });
    }



}