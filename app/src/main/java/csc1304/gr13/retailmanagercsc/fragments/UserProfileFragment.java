package csc1304.gr13.retailmanagercsc.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.staffId.CurrentLogginSession;
/**
 * Created by CS1304 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
    public TextView title, username,terminalid;
    public ImageView thumbnail, overflow;
    public View view;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_user_profile, container, false);
        title = view.findViewById(R.id.title);
        username =  view.findViewById(R.id.username);
        terminalid =  view.findViewById(R.id.terminalid);
        thumbnail = view.findViewById(R.id.thumbnail);
        overflow = view.findViewById(R.id.overflow);

        title.setText("User Profile");
        terminalid.setText(CurrentLogginSession.getStaffContact());


        return view;
    }



}
