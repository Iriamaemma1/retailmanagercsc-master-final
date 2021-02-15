package csc1304.gr13.retailmanagercsc.stocktables;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.adapters.AddStockItemsPagerAdapter;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddStockItemsFrg extends Fragment {

    private View v;
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AddStockItemsPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_add_stock_items, container, false);

        context = getActivity();
        tabLayout = v.findViewById(R.id.tab_layout_2);
        viewPager = v.findViewById(R.id.pager_2);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        tabLayout.addTab(tabLayout.newTab().setText("Add Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Categories"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        adapter = new AddStockItemsPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       // viewPager.setCurrentItem(1);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
