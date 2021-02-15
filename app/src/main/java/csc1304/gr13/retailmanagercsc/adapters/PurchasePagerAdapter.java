package csc1304.gr13.retailmanagercsc.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import csc1304.gr13.retailmanagercsc.purchasetables.PurchaseFrg;
import csc1304.gr13.retailmanagercsc.purchasetables.PurchasePendingPaymentFrg;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class PurchasePagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PurchasePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PurchaseFrg tab1 = new PurchaseFrg();
                return tab1;
            case 1:
                PurchasePendingPaymentFrg tab2 = new PurchasePendingPaymentFrg();
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
