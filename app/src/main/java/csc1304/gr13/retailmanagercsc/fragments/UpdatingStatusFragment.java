package csc1304.gr13.retailmanagercsc.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.databinding.FragmentUpdatingStatusBinding;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class UpdatingStatusFragment extends Fragment {
    private FragmentUpdatingStatusBinding binding;
    private int updateComplete = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_updating_status,container,false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
                ,new IntentFilter("completationMessage"));

        return binding.getRoot();
    }
    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int percent = intent.getIntExtra("percentage",0);

            updateComplete += percent;
            binding.progress.setText(updateComplete+" %");

        }
    };


}
