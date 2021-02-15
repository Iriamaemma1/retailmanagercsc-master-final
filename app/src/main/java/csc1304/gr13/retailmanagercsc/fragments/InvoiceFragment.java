package csc1304.gr13.retailmanagercsc.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.adapters.InvoiceAdapter;
import csc1304.gr13.retailmanagercsc.databinding.FragmentInvoiceBinding;

/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment {

    private InvoiceAdapter adapter;

    public InvoiceFragment() {
        // Required empty public constructor
    }

    private FragmentInvoiceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_invoice, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.invoiceTabLayout.addTab(binding.invoiceTabLayout.newTab().setText("Search"));
        binding.invoiceTabLayout.addTab(binding.invoiceTabLayout.newTab().setText("Details"));
        binding.invoiceTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new InvoiceAdapter
                (getActivity().getSupportFragmentManager(), binding.invoiceTabLayout.getTabCount());
        binding.invoicePager.setAdapter(adapter);
        binding.invoicePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.invoiceTabLayout));

        binding.invoiceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.invoicePager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
