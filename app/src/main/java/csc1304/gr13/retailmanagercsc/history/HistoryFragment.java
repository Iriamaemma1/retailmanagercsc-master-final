package csc1304.gr13.retailmanagercsc.history;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.options.OptionsActivity;

import java.util.Calendar;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class HistoryFragment extends Fragment {
    public static final CharSequence TITLE = "History";
    Calendar calendarStart;
    Calendar calendarEnd;
    private RecyclerView historyRecyclerView;
    private WalletEntriesRecyclerViewAdapter historyRecyclerViewAdapter;
    private Menu menu;
    private TextView dividerTextView;
    private BroadcastReceiver broadcastReceiver;
    private String msg;

    public static HistoryFragment newInstance() {

        return new HistoryFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
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
        inflater.inflate(R.menu.history_fragment_menu, menu);
        this.menu = menu;
        updateCalendarIcon();
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date_range:
                showSelectDateRangeDialog();
                return true;
            case R.id.action_options:
                startActivity(new Intent(getActivity(), OptionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
