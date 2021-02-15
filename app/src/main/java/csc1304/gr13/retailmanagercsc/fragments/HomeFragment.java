package csc1304.gr13.retailmanagercsc.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import csc1304.gr13.retailmanagercsc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by CS1304 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  implements View.OnClickListener {

    private View rootView;
    private AdvViewPagerAdapter advAdapter;
    /* access modifiers changed from: private */
    public List<View> advDotsView;
    /* access modifiers changed from: private */
    public int[] advImageIdList = {R.drawable.card_deposit, R.drawable.card_insert, R.drawable.retail, R.drawable.d};
    /* access modifiers changed from: private */
    public List<ImageView> advImages;
    /* access modifiers changed from: private */
    public int advLastPosition = 0;
    /* access modifiers changed from: private */
    public int advPosition;
    /* access modifiers changed from: private */
    public ViewPager advViewPaper;

    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            HomeFragment.this.advViewPaper.setCurrentItem(HomeFragment.this.advPosition);
        }
    };

    private MenuFragmentPageAdapter menuAdapter;
    List<Fragment> menuFragmentList = new ArrayList();
    private FragmentManager menuFragmentManager;
    private ViewPager menuViewPager;
    private ScheduledExecutorService scheduledExecutorService;
    public HomeFragment() {
        // Required empty public constructor
    }



    private class AdvViewPageTask implements Runnable {
        private AdvViewPageTask() {
        }

        public void run() {
            HomeFragment.this.advPosition = (HomeFragment.this.advPosition + 1) % HomeFragment.this.advImageIdList.length;
            HomeFragment.this.mHandler.sendEmptyMessage(0);
        }
    }


    private class AdvViewPagerAdapter extends PagerAdapter {
        private AdvViewPagerAdapter() {
        }

        public int getCount() {
            return HomeFragment.this.advImages.size();
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) HomeFragment.this.advImages.get(position));
        }

        public Object instantiateItem(ViewGroup view, int position) {
            view.addView((View) HomeFragment.this.advImages.get(position));
            return HomeFragment.this.advImages.get(position);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.activity_main_trans_menu, container, false);


        return rootView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

}
