package csc1304.gr13.retailmanagercsc.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.AddCategoryDBfrg;
import csc1304.gr13.retailmanagercsc.stocktables.addstockitems.AddProductDBfrg;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class AddStockItemsPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public AddStockItemsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddProductDBfrg tab1 = new AddProductDBfrg();
                return tab1;
            case 1:
                AddCategoryDBfrg tab2 = new AddCategoryDBfrg();
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
