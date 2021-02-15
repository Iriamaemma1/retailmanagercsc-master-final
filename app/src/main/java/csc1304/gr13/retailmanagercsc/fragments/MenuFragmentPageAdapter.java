package csc1304.gr13.retailmanagercsc.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class MenuFragmentPageAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public MenuFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList2) {
        super(fm);
        this.fragmentList = fragmentList2;
    }

    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    public int getCount() {
        return this.fragmentList.size();
    }
}

