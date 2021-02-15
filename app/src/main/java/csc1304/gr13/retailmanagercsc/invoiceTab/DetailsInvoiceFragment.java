package csc1304.gr13.retailmanagercsc.invoiceTab;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.adapters.InvoiceLVAdapter;
import csc1304.gr13.retailmanagercsc.databinding.FragmentInvoiceDetailsBinding;
import csc1304.gr13.retailmanagercsc.modelClass.InvoiceLvModel;
/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsInvoiceFragment extends Fragment {


    public DetailsInvoiceFragment() {
        // Required empty public constructor
    }


    private FragmentInvoiceDetailsBinding binder;
    private InvoiceLVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_invoice_details, container, false);
        adapter = new InvoiceLVAdapter(getActivity(),new InvoiceLvModel().getAllSampleInvoiceData());
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binder.detailsInLv.setAdapter(adapter);
    }
}
