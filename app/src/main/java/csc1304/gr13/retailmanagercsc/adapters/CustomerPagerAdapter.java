package csc1304.gr13.retailmanagercsc.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import csc1304.gr13.retailmanagercsc.customertables.Customerfrg;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class CustomerPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public CustomerPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Customerfrg tab1 = new Customerfrg();
                return tab1;
            /*case 1:
                AddCustomerDBfrg tab2 = new AddCustomerDBfrg();
                return tab2;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
