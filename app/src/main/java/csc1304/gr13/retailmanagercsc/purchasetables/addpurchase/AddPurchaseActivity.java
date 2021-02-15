package csc1304.gr13.retailmanagercsc.purchasetables.addpurchase;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.ui.fragment.MenuFragment;
import csc1304.gr13.retailmanagercsc.ui.fragment.MenuFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class AddPurchaseActivity extends FragmentActivity implements MenuFragment.OnTransactionItemClickListener {
    List<Fragment> fragmentList = new ArrayList();
    private FragmentManager fragmentManager;
    /* access modifiers changed from: private */
    public boolean isEndTimeout = false;
    private MenuFragmentPageAdapter mAdapter;
    ViewPager mViewPager;
    /* access modifiers changed from: private */
    public long startTime = 0;
    /* access modifiers changed from: private */
    public int timeout = 60;
    private TextView tranTitle;
    private AddPurchaseDBfrg addPurchaseDBfrg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_fragment);


        addPurchaseDBfrg = new AddPurchaseDBfrg();
        tranTitle = findViewById(R.id.trans_name);
        tranTitle.setText("ADD PURCHASE");
        this.mViewPager = findViewById(R.id.idtype_menu_pager);
        this.fragmentList.add(addPurchaseDBfrg);

        initView();
        //startTimeTask();
    }

    private void initView() {
        this.fragmentManager = getSupportFragmentManager();
        this.mAdapter = new MenuFragmentPageAdapter(this.fragmentManager, this.fragmentList);
        this.mViewPager.setAdapter(this.mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_home /*2131296357*/:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startTimeTask() {
        this.startTime = System.currentTimeMillis();
        this.isEndTimeout = false;
        new Thread() {
            public void run() {
                while (!AddPurchaseActivity.this.isEndTimeout) {
                    if (System.currentTimeMillis() - AddPurchaseActivity.this.startTime > ((long) (AddPurchaseActivity.this.timeout * 1000))) {
                        AddPurchaseActivity.this.finish();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}
