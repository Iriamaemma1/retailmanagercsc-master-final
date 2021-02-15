package csc1304.gr13.retailmanagercsc.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


import csc1304.gr13.retailmanagercsc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public class ChooseDialogFragment extends DialogFragment {
    /* access modifiers changed from: private */
    public int itemIndex = 0;
    private ListView listView;
    private String[] optionList;
    private String title;

    public void setArguments(String title2, String[] optionList2) {
        this.title = title2;
        this.optionList = optionList2;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("hasShown")) {
            this.title = savedInstanceState.getString("title");
            this.optionList = savedInstanceState.getStringArray("optionList");
        }
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.choose, null);
        this.listView = (ListView) view.findViewById(R.id.listView_choose);
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < this.optionList.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("text", getOptionText(i));
            data.add(item);
        }
        this.listView.setAdapter(new SimpleAdapter(getActivity(), data, R.layout.choose_row, new String[]{"text"}, new int[]{R.id.textView_chooseRow}));
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                ChooseDialogFragment.this.dismiss();
                ChooseDialogFragment.this.itemIndex = arg2;
                ChooseDialogFragment.this.onItemClick();
            }
        });
        dialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface arg0) {
                ChooseDialogFragment.this.getActivity().getWindow().setType(2009);
            }
        });
        TextView titleView = (TextView) view.findViewById(R.id.trans_name);
        if (this.title == null || this.title.isEmpty()) {
            view.findViewById(R.id.layout_title).setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(this.title);
        }
        view.findViewById(R.id.back_home).setVisibility(View.GONE);
        dialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                if (arg1 != 4 || arg2.getAction() != 1) {
                    return false;
                }
                ChooseDialogFragment.this.dismiss();
                ChooseDialogFragment.this.onCancel();
                return true;
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private String getOptionText(int index) {
        return (index + 1) + "." + this.optionList[index];
    }

    public void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
        arg0.putString("title", this.title);
        arg0.putStringArray("optionList", this.optionList);
        arg0.putBoolean("hasShown", true);
    }

    public int getItemIndex() {
        return this.itemIndex;
    }

    /* access modifiers changed from: protected */
    public void onConfirm() {
        ((DialogFragmentListener) getActivity()).onFragmentConfirm(this);
    }

    /* access modifiers changed from: protected */
    public void onItemClick() {
        ((DialogFragmentListener) getActivity()).onFragmentItemClick(this);
    }

    /* access modifiers changed from: protected */
    public void onCancel() {
        ((DialogFragmentListener) getActivity()).onFragmentCancel(this);
    }
}
