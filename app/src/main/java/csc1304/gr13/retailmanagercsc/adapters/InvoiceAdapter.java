package csc1304.gr13.retailmanagercsc.adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import csc1304.gr13.retailmanagercsc.invoiceTab.DetailsInvoiceFragment;
import csc1304.gr13.retailmanagercsc.invoiceTab.SearchInvoiceTabFrg;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class InvoiceAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public InvoiceAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                SearchInvoiceTabFrg tab1 = new SearchInvoiceTabFrg();
                return tab1;

            case 1:
                DetailsInvoiceFragment tab2 = new DetailsInvoiceFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
