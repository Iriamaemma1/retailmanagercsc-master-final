package csc1304.gr13.retailmanagercsc.fragments;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import csc1304.gr13.retailmanagercsc.MainActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UserAuthentication;
import csc1304.gr13.retailmanagercsc.history.WalletEntriesRecyclerViewAdapter;
import csc1304.gr13.retailmanagercsc.options.OptionsActivity;

/**
 * Created by CS1304 on 8/02/2021.
 */
public class AboutFragment extends Fragment {
    public static final CharSequence TITLE = "About";
    Calendar calendarStart;
    Calendar calendarEnd;
    private RecyclerView historyRecyclerView;
    private WalletEntriesRecyclerViewAdapter historyRecyclerViewAdapter;
    private Menu menu;
    private TextView dividerTextView;
    private BroadcastReceiver broadcastReceiver;
    private String msg;

    public static AboutFragment newInstance() {

        return new AboutFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        dividerTextView = view.findViewById(R.id.divider_textview);
        dividerTextView.setText("Recent Transactions");
        historyRecyclerView = view.findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
       // registerReceiver();

    }
/*
    private void registerReceiver() {
        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                msg = intent.getStringExtra("msg");

                if (msg.equalsIgnoreCase("load_data")) {
                    if(historyRecyclerViewAdapter !=null){
                        historyRecyclerViewAdapter.notifyDataSetChanged();

                    }
                    historyRecyclerViewAdapter = new WalletEntriesRecyclerViewAdapter(getActivity(), "sd",getContext());
                    historyRecyclerView.setAdapter(historyRecyclerViewAdapter);

                    historyRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onItemRangeInserted(int positionStart, int itemCount) {
                            historyRecyclerView.smoothScrollToPosition(0);
                        }
                    });


                } else if (msg.equalsIgnoreCase("nothing")) {

                }

            }
        };

        getContext().registerReceiver(broadcastReceiver, new IntentFilter("com.interswitchug.ftrust.ui.main.history.HistoryFragment"));

    }
*/
    private void updateView(){
        /*DatabaseHandler db = new DatabaseHandler(getActivity());
        db.dropTable();*/
        Log.e("UpdatingView","ViewUpdated!!!");
        if(historyRecyclerViewAdapter !=null){
            historyRecyclerViewAdapter.notifyDataSetChanged();

        }
        historyRecyclerViewAdapter = new WalletEntriesRecyclerViewAdapter(getActivity(), "sd",getContext());
        historyRecyclerView.setAdapter(historyRecyclerViewAdapter);

        historyRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                historyRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.twezimbe_interface, menu);
        this.menu = menu;
        updateCalendarIcon();
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), OptionsActivity.class));
                return true;
            case R.id.action_share_application:
                //showSelectDateRangeDialog();
                return true;
            case R.id.action_logout:
                showPopup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(),R.style.AlertDialogStyle);
        alert.setTitle("Warning")
                .setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        logout(); // Last step. Logout function

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();

        TextView titleText = alert1.findViewById(android.R.id.title);
        if(titleText != null){
            titleText.setGravity(Gravity.CENTER);
        }


        TextView messageText = alert1.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        alert1.show();
    }

    private void logout() {
        startActivity(new Intent(getActivity(), UserAuthentication.class));
        getActivity().finish();
    }

    private void updateCalendarIcon() {
        MenuItem calendarIcon = menu.findItem(R.id.action_date_range);
        if (calendarIcon == null) return;

            calendarIcon.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_calendar));

            dividerTextView.setText("Recent Transactions:");
        }


    private void showSelectDateRangeDialog() {
        SmoothDateRangePickerFragment datePicker = SmoothDateRangePickerFragment.newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
            @Override
            public void onDateRangeSet(SmoothDateRangePickerFragment view, int yearStart, int monthStart, int dayStart, int yearEnd, int monthEnd, int dayEnd) {
                calendarStart = Calendar.getInstance();
                calendarStart.set(yearStart, monthStart, dayStart);
                calendarStart.set(Calendar.HOUR_OF_DAY, 0);
                calendarStart.set(Calendar.MINUTE, 0);
                calendarStart.set(Calendar.SECOND, 0);

                calendarEnd = Calendar.getInstance();
                calendarEnd.set(yearEnd, monthEnd, dayEnd);
                calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
                calendarEnd.set(Calendar.MINUTE, 59);
                calendarEnd.set(Calendar.SECOND, 59);
                calendarUpdated();
                updateCalendarIcon();
            }
        });
        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                calendarStart = null;
                calendarEnd = null;
                calendarUpdated();
                updateCalendarIcon();
            }
        });
        datePicker.show(getActivity().getFragmentManager(), "TAG");
        //todo library doesn't respect other method than deprecated
    }

    private void calendarUpdated() {
        historyRecyclerViewAdapter.setDateRange(calendarStart, calendarEnd);
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (broadcastReceiver != null) {
            try {
                getContext().unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
                Log.e("UnregisterReciever",e.getMessage());
            }

        }*/
    }
}
