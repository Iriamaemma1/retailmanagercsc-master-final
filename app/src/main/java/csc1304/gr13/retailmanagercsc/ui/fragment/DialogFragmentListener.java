package csc1304.gr13.retailmanagercsc.ui.fragment;


import androidx.fragment.app.Fragment;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public interface DialogFragmentListener {
    void onFragmentCancel(Fragment fragment);

    void onFragmentConfirm(Fragment fragment);

    void onFragmentItemClick(Fragment fragment);
}
