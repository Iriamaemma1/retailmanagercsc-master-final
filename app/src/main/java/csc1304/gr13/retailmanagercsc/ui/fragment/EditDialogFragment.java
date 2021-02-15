package csc1304.gr13.retailmanagercsc.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import csc1304.gr13.retailmanagercsc.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class EditDialogFragment extends DialogFragment {
    private String cancelText;
    /* access modifiers changed from: private */
    public EditText editText1;
    /* access modifiers changed from: private */
    public EditText editText2;
    /* access modifiers changed from: private */
    public EditText editText3;
    private int gravity = 3;
    private String hint1;
    private String hint2;
    private String hint3;
    private int inputType1;
    private int inputType2;
    private int inputType3;
    /* access modifiers changed from: private */
    public String key1;
    /* access modifiers changed from: private */
    public String key2;
    /* access modifiers changed from: private */
    public String key3;
    private String okText;
    private String title;

    public interface EditDialogFragmentListener {
        void onEditDialogFragmentCancel(Fragment fragment);

        void onEditDialogFragmentConfirm(Map<String, String> map, Fragment fragment);
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public void addKey1(String key, String hint, int inputType12) {
        this.key1 = key;
        this.hint1 = hint;
        this.inputType1 = inputType12;
    }

    public void addKey2(String key, String hint, int inputType22) {
        this.key2 = key;
        this.hint2 = hint;
        this.inputType2 = inputType22;
    }

    public void addKey3(String key, String hint, int inputType32) {
        this.key3 = key;
        this.hint3 = hint;
        this.inputType3 = inputType32;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.okText == null || this.okText.isEmpty()) {
            this.okText = "Confirm";
        }
        if (savedInstanceState != null && savedInstanceState.getBoolean("hasShown")) {
            this.title = savedInstanceState.getString("title");
            this.okText = savedInstanceState.getString("okText");
            this.cancelText = savedInstanceState.getString("cancelText");
            this.gravity = savedInstanceState.getInt("gravity");
        }
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_dialog, null);
        if (this.key1 != null && !this.key1.isEmpty()) {
            this.editText1 = (EditText) view.findViewById(R.id.edittext_value1);
            this.editText1.setVisibility(View.VISIBLE);
            this.editText1.setHint(this.hint1);
            this.editText1.setInputType(this.inputType1);
        }
        if (this.key2 != null && !this.key2.isEmpty()) {
            this.editText2 = (EditText) view.findViewById(R.id.edittext_value2);
            this.editText2.setVisibility(View.VISIBLE);
            this.editText2.setHint(this.hint2);
            this.editText2.setInputType(this.inputType2);
        }
        if (this.key3 != null && !this.key3.isEmpty()) {
            this.editText3 = (EditText) view.findViewById(R.id.edittext_value3);
            this.editText3.setVisibility(View.VISIBLE);
            this.editText3.setHint(this.hint3);
            this.editText3.setInputType(this.inputType3);
        }
        Button confirmBtn = (Button) view.findViewById(R.id.button_ok);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
        TextView titleView = (TextView) view.findViewById(R.id.text_title);
        if (this.title == null || this.title.isEmpty()) {
            view.findViewById(R.id.layout_title).setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(this.title);
        }
        if (this.okText != null && !this.okText.isEmpty()) {
            confirmBtn.setVisibility(View.VISIBLE);
            confirmBtn.setText(this.okText);
            confirmBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    Map<String, String> map = new HashMap<>();
                    if (EditDialogFragment.this.key1 != null && !EditDialogFragment.this.key1.isEmpty() && EditDialogFragment.this.editText1.getVisibility() ==View.VISIBLE) {
                        map.put(EditDialogFragment.this.key1, EditDialogFragment.this.editText1.getText().toString());
                    }
                    if (EditDialogFragment.this.key2 != null && !EditDialogFragment.this.key2.isEmpty() && EditDialogFragment.this.editText2.getVisibility() == View.VISIBLE) {
                        map.put(EditDialogFragment.this.key2, EditDialogFragment.this.editText2.getText().toString());
                    }
                    if (EditDialogFragment.this.key3 != null && !EditDialogFragment.this.key3.isEmpty() && EditDialogFragment.this.editText3.getVisibility() == View.VISIBLE) {
                        map.put(EditDialogFragment.this.key3, EditDialogFragment.this.editText3.getText().toString());
                    }
                    EditDialogFragment.this.onConfirm(map);
                    EditDialogFragment.this.dismiss();
                }
            });
        }
        if (this.cancelText != null && !this.cancelText.isEmpty()) {
            cancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setText(this.cancelText);
            cancelBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    EditDialogFragment.this.onCancel();
                    EditDialogFragment.this.dismiss();
                }
            });
        }
        dialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                if (arg1 != 4 || arg2.getAction() != 1) {
                    return false;
                }
                EditDialogFragment.this.onCancel();
                EditDialogFragment.this.dismiss();
                return true;
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
        arg0.putString("title", this.title);
        arg0.putString("okText", this.okText);
        arg0.putString("cancelText", this.cancelText);
        arg0.putInt("gravity", this.gravity);
        arg0.putBoolean("hasShown", true);
    }

    public void setButtonText(String okText2, String cancelText2) {
        this.okText = okText2;
        this.cancelText = cancelText2;
    }

    public void setTextGravity(int gravity2) {
        this.gravity = gravity2;
    }

    /* access modifiers changed from: protected */
    public void onConfirm(Map<String, String> maps) {
        ((EditDialogFragmentListener) getActivity()).onEditDialogFragmentConfirm(maps, this);
    }

    /* access modifiers changed from: protected */
    public void onCancel() {
        ((EditDialogFragmentListener) getActivity()).onEditDialogFragmentCancel(this);
    }
}
