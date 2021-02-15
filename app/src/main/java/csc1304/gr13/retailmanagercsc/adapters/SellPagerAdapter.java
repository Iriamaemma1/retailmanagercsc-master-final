package csc1304.gr13.retailmanagercsc.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import csc1304.gr13.retailmanagercsc.sellTabes.AddProductFrg;
import csc1304.gr13.retailmanagercsc.sellTabes.ListProductFrg;
import csc1304.gr13.retailmanagercsc.sellTabes.ReceiptFrg;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class SellPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public SellPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddProductFrg tab1 = new AddProductFrg();
                return tab1;
            case 1:
                ListProductFrg tab2 = new ListProductFrg();
                return tab2;
            case 2:
                ReceiptFrg tab3 = new ReceiptFrg();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
