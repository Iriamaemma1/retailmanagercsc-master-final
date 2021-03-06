package csc1304.gr13.retailmanagercsc.fragments;


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
import csc1304.gr13.retailmanagercsc.adapters.StockPagerAdapter;
/**
 * Created by CS1304 on 8/02/2021.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class StockProductsFragment extends Fragment {

    private View v;
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StockPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_stock_products, container, false);

        context = getActivity();

        tabLayout = v.findViewById(R.id.tab_layout);
        viewPager = v.findViewById(R.id.pager);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Stock"));
        tabLayout.addTab(tabLayout.newTab().setText("Category"));
        /*tabLayout.addTab(tabLayout.newTab().setText("ADD Items"));*/

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new StockPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(2);
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
