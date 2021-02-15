package csc1304.gr13.retailmanagercsc.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.databinding.FragmentDueBinding;
import csc1304.gr13.retailmanagercsc.interfaces.DueLvInterface;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class DueFragment extends Fragment implements DueLvInterface{

    private Fragment fragment;
    private FragmentDueBinding binder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater,R.layout.fragment_due, container, false);
        fragment = new DueListFragment();
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,fragment);
        transaction.commit();
    }

    @Override
    public void dueDetails(String invoiceNumber) {
        fragment = new DueDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }
}
