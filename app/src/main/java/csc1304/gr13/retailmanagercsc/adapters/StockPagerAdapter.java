package csc1304.gr13.retailmanagercsc.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import csc1304.gr13.retailmanagercsc.stocktables.ProductItemsfrg;
import csc1304.gr13.retailmanagercsc.stocktables.StockCategoriesFrg;
import csc1304.gr13.retailmanagercsc.stocktables.StockItemsfrg;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class StockPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public StockPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductItemsfrg tab1 = new ProductItemsfrg();
                return tab1;
            case 1:
                StockItemsfrg tab2 = new StockItemsfrg();
                return tab2;
            case 2:
                StockCategoriesFrg tab3 = new StockCategoriesFrg();
                return tab3;
            /*case 3:
                AddStockItemsFrg tab4 = new AddStockItemsFrg();
                return tab4;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
