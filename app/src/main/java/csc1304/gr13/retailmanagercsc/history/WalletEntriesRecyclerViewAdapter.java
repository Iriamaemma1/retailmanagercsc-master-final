package csc1304.gr13.retailmanagercsc.history;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UserSession;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.DatabaseHandler;
import csc1304.gr13.retailmanagercsc.clienttransaction.model.Transactions;
import csc1304.gr13.retailmanagercsc.history.edit_entry.TransactionDetailsActivity;
import csc1304.gr13.retailmanagercsc.history.models.User;
import csc1304.gr13.retailmanagercsc.history.models.WalletEntry;
import csc1304.gr13.retailmanagercsc.utils.CurrencyHelper;
import csc1304.gr13.retailmanagercsc.utils.ListDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by CS1304 on 8/02/2021.
 */
public class WalletEntriesRecyclerViewAdapter extends RecyclerView.Adapter<WalletEntryHolder> {

    private final String uid;
    private final FragmentActivity fragmentActivity;
    private List<WalletEntry> walletEntries;

    private ListDataSet<List<WalletEntry>> walletEntriesList;

    private User user;
    private boolean firstUserSync = false;
    private String tran_type,original_string;

    private Context localContext;
    public WalletEntriesRecyclerViewAdapter(FragmentActivity fragmentActivity, String uid,Context context) {
        this.fragmentActivity = fragmentActivity;
        this.uid = uid;
        this.localContext = context;
        walletEntries = new ArrayList<>();

        //Getting Transaction Records
        DatabaseHandler db = new DatabaseHandler(context);

        List<Transactions> transactions = db.getAllTransactionsByStaffId(UserSession.getEmployeeId());

        for(Transactions transaction:transactions){
            WalletEntry entry = new WalletEntry();
            entry.setBalanceDifference(Long.parseLong(transaction.getAmount_authorsed().toString()));
            entry.setCategoryID(transaction.getTran_type());
            tran_type = transaction.getTran_type();
            original_string = transaction.getFullMessageResponse();
            String tran_rspCode = transaction.getResp_code();
            if(tran_rspCode.equalsIgnoreCase("00")){
                entry.setName("Approved");
            }else{
                entry.setName("Failed");
            }
            if(transaction.getTran_date() != null){

                String transactionDate=transaction.getTran_date().split("GMT+")[0];

                    entry.setTimestamp(transactionDate);

            }else{
                entry.setTimestamp(new Date().toString());
            }

            entry.setTran_id(String.valueOf(transaction.getId()));

            entry.setFullMessageResponse(transaction.getFullMessageResponse());
            entry.setTerminalId(transaction.getTerminal_id());
            entry.setMerchantId(transaction.getMerchant_id());
            entry.setMerchantLocation(transaction.getMerchant_location());
            entry.setTranType(transaction.getTran_type());
            entry.setTransaction(transaction);
            walletEntries.add(entry);
        }
        db.close();

        user = new User();
    }

    @Override
    public WalletEntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
        View view = inflater.inflate(R.layout.history_listview_row, parent, false);
        return new WalletEntryHolder(view);
    }

    @Override
    public void onBindViewHolder(WalletEntryHolder holder, int position) {

        final WalletEntry walletEntry = walletEntries.get(position);

     //   Category category = CategoriesHelper.searchCategory(user, walletEntry.categoryID);
     //   holder.iconImageView.setImageResource();
       // holder.iconImageView.setBackgroundTintList(ColorStateList.valueOf()));
        holder.categoryTextView.setText(walletEntry.categoryID);
        holder.nameTextView.setText(walletEntry.name);


        holder.dateTextView.setText(walletEntry.timestamp);


        holder.moneyTextView.setText(CurrencyHelper.formatCurrency(user.currency, walletEntry.balanceDifference));
        holder.moneyTextView.setTextColor(ContextCompat.getColor(fragmentActivity,
                walletEntry.balanceDifference < 0 ? R.color.primary_text_expense : R.color.primary_text_income));

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createDeleteDialog(walletEntry, uid, walletEntry.balanceDifference, fragmentActivity);
                return false;
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentActivity, TransactionDetailsActivity.class);
                intent.putExtra("transaction_id", walletEntry.tran_id);
                intent.putExtra("transaction",walletEntry.getTransaction());
                fragmentActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (walletEntries == null) return 0;
        return walletEntries.size();
    }

    private void createDeleteDialog(final WalletEntry entry, String uid, long balanceDifference, final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("Do you want to print?")
                .setPositiveButton("Print", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create().show();

    }

    public void setDateRange(Calendar calendarStart, Calendar calendarEnd) {
        //WalletEntriesHistoryViewModelFactory.getModel(uid, fragmentActivity).setDateFilter(calendarStart, calendarEnd);
    }




}