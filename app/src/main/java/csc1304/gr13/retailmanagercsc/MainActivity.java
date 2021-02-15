package csc1304.gr13.retailmanagercsc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import csc1304.gr13.retailmanagercsc.fragments.CustomerPagerFragment;
import csc1304.gr13.retailmanagercsc.fragments.DueDetailsFragment;
import csc1304.gr13.retailmanagercsc.fragments.DueFragment;
import csc1304.gr13.retailmanagercsc.fragments.InvoiceFragment;
import csc1304.gr13.retailmanagercsc.fragments.ProductSupplierFragment;
import csc1304.gr13.retailmanagercsc.fragments.PurchaseProductsFragment;
import csc1304.gr13.retailmanagercsc.fragments.ReportFragment;
import csc1304.gr13.retailmanagercsc.fragments.SellsFragment;
import csc1304.gr13.retailmanagercsc.fragments.StockFragment;
import csc1304.gr13.retailmanagercsc.fragments.StockProductsFragment;
import csc1304.gr13.retailmanagercsc.fragments.UpdatingStatusFragment;
import csc1304.gr13.retailmanagercsc.history.HistoryFragment;
import csc1304.gr13.retailmanagercsc.interfaces.DueLvInterface;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DueLvInterface {

    public static final String TAG = "csc1304_13_retail";
    private int updateComplete = 0;
    private RelativeLayout stockRl;
    private RelativeLayout sellsRl;
    private RelativeLayout navSuppliers,navCustomers,navpurchases,navHome,navTransactions;
    private RelativeLayout invoiceRl;
    private RelativeLayout dueRl;
    private RelativeLayout reportRl;
    private ImageButton updateBtn;

    private UpdateDatabase updateDatabase;

    private Fragment fragment;
    private FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


    private Handler threadHandler;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        threadHandler = new Handler(getMainLooper());


        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        Toolbar toolbar = findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);

       DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initializeAll();
        fragmentTransaction.add(R.id.container,fragment);
        fragmentTransaction.commit();

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver
                ,new IntentFilter("completationMessage"));

    }


    private void initializeAll() {

        stockRl = findViewById(R.id.navStock);
        sellsRl = findViewById(R.id.navSells);
        navHome = findViewById(R.id.navHome);
        navSuppliers = findViewById(R.id.navSuppliers);
        navCustomers = findViewById(R.id.navCustomers);
        navpurchases = findViewById(R.id.navpurchases);

        invoiceRl = findViewById(R.id.navInvoice);
        navTransactions = findViewById(R.id.navTransactions);
        dueRl = findViewById(R.id.navDue);
        reportRl = findViewById(R.id.navReport);
        //updateBtn =findViewById(R.id.btnUpdate);


        stockRl.setOnClickListener(this);
        sellsRl.setOnClickListener(this);
        navSuppliers.setOnClickListener(this);
        navCustomers.setOnClickListener(this);
        navpurchases.setOnClickListener(this);
        navHome.setOnClickListener(this);
        invoiceRl.setOnClickListener(this);
        navTransactions.setOnClickListener(this);
        dueRl.setOnClickListener(this);
        reportRl.setOnClickListener(this);
       // updateBtn.setOnClickListener(this);

        fragment = new StockProductsFragment();
        //fragment = new HomeFragment();
        //fragment = new StockFragment();
        //fragment = new SellsFragment();
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                updateDatabase = new UpdateDatabase(MainActivity.this);
            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.navHome:
                //fragment = new HomeFragment();
                fragment = new StockProductsFragment();
                closeNavBar();
                break;
            case R.id.navStock:
                fragment = new StockFragment();
                closeNavBar();
                break;
            case R.id.navSells:
                fragment = new SellsFragment();
                closeNavBar();
                break;
            case R.id.navSuppliers:
                //fragment = new SupplierFragment();
                fragment = new ProductSupplierFragment();
                closeNavBar();
                break;
            case R.id.navCustomers:
                //fragment = new Customerfrg();
                fragment = new CustomerPagerFragment();
                closeNavBar();
                break;
            case R.id.navpurchases:
                fragment = new PurchaseProductsFragment();
                closeNavBar();
                break;
            case R.id.navInvoice:
                fragment = new InvoiceFragment();
                closeNavBar();
                break;
            case R.id.navTransactions:
                fragment = new HistoryFragment();
                closeNavBar();
                break;
            case R.id.navDue:
                fragment = new DueFragment();
                closeNavBar();
                break;
            case R.id.navReport:
                fragment = new ReportFragment();
                closeNavBar();
                break;
            case R.id.btnUpdate:
                updateData();
                break;
            case R.id.navLogout:
                logout();
                break;
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();//need to re initialize it otherwise it will show already commited
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }

    public void closeNavBar(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        logout();
    }


    private void logout(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure you to  logout?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this,UserAuthentication.class);
                    startActivity(intent);
                    finish();
                }
            });

            dialog.setNegativeButton("No",null);

            dialog.show();
        }
    }
    @Override
    public void dueDetails(String invoiceNumber) {
        fragment = new DueDetailsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("dueId",invoiceNumber);
        fragment.setArguments(bundle);
        transaction.replace(R.id.container,fragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateData(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        updateBtn.setClickable(false);
        fragment = new UpdatingStatusFragment();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();

        updateDatabase.updateData();
    }

    Thread thread;
    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            boolean status = intent.getBooleanExtra("status",true);
//            if (status){
//                updateBtn.setClickable(true);
//                fragment = new StockFragment();
//                fragmentTransaction.replace(R.id.container,fragment);
//                fragmentTransaction.commit();
//
//                return;
//            }

            int percent = intent.getIntExtra("percentage",0);
            updateComplete += percent;


            try {
                if (updateComplete == 100 || updateComplete > 100){

//                    thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//
//                            }
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    updateBtn.setClickable(true);
//                                    fragment = new StockFragment();
//                                    fragmentTransaction.replace(R.id.container,fragment);
//                                    fragmentTransaction.commit();
//                                    thread.interrupt();
//                                }
//                            });
//                        }
//                    });
//
//
//                    thread.start();

                    updateBtn.setClickable(true);
                    fragment = new StockFragment();
                    fragmentTransaction.replace(R.id.container,fragment);
                    fragmentTransaction.commit();
                    thread.interrupt();

                }
            }catch (Exception e){

            }

        }
    };

}
