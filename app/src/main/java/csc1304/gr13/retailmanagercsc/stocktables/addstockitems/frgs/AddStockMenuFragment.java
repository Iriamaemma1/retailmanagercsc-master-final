package csc1304.gr13.retailmanagercsc.stocktables.addstockitems.frgs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import com.jaredrummler.materialspinner.MaterialSpinner;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.ui.fragment.GridMenuFragment;

import java.util.HashMap;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class AddStockMenuFragment extends GridMenuFragment {
    MyOnClickListener myOnClickListener;
    private HashMap<String, String> tranRequest = null;
    private LinearLayout spinner_dialog_layout, inputAmountLayout;
    private Button confirm_amount;


    private View view;
    private EditText input_amount;
    private MaterialSpinner input_account_to,input_account_frm;
    private TextInputLayout l_account_to,l_account_frm;

    private TextView txtaccountto,txtaccountfrom;
    private String selectedAccountTo,selectedAccountFrom;

    class MyOnClickListener implements OnClickListener {
        MyOnClickListener() {
        }

        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.confirm_amount /*2131296321*/:

                    return;
                default:
                    return;
            }
        }
    }


    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment_generic_stock_manager, null);
        this.view = view;

/*
        this.myOnClickListener = new MyOnClickListener();
        spinner_dialog_layout = view.findViewById(R.id.spinner_dialog_layout);
        inputAmountLayout = view.findViewById(R.id.inputAmountLayout);
        input_amount = view.findViewById(R.id.input_amount);
        input_amount.addTextChangedListener(new MoneyTextWatcher(input_amount));
        input_amount.setEnabled(Boolean.FALSE);
        input_amount.setFocusable(Boolean.FALSE);

        l_account_to = view.findViewById(R.id.l_account_to);
        l_account_to.setVisibility(View.VISIBLE);
        l_account_frm = view.findViewById(R.id.l_account_frm);
        l_account_frm.setVisibility(View.VISIBLE);

        txtaccountto = view.findViewById(R.id.txtaccountto);
        txtaccountfrom = view.findViewById(R.id.txtaccountfrom);

*/


        return view;
    }

    /* access modifiers changed from: protected */
    public void onNormalItemClick(String buttonName) {
    }

    /* access modifiers changed from: protected */
    public boolean onTransactionItemClick(Object buttonName) {
        return true;
    }

    public void onResume() {
        super.onResume();
    }


}
