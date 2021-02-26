package csc1304.gr13.retailmanagercsc.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.config.UserRegistration;
import csc1304.gr13.retailmanagercsc.options.OptionsActivity;
/**
 * Created by CS1304 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessProfileFragment extends Fragment {
    private Menu menu;


    public BusinessProfileFragment NewInstance() {

        return new BusinessProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_profile, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.business_profile_fragment_menu, menu);
        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_user:
                //
                startActivity(new Intent(getActivity(), UserRegistration.class));
                return true;
            case R.id.action_options:
                startActivity(new Intent(getActivity(), OptionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
