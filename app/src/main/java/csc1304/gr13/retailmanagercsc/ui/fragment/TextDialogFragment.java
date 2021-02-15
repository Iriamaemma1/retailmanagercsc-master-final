package csc1304.gr13.retailmanagercsc.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import csc1304.gr13.retailmanagercsc.R;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class TextDialogFragment extends DialogFragment {
    private String cancelText;
    private int gravity = 3;
    private TextView infoView;
    private String msg;
    private String okText;
    private String title;

    public void setArguments(String title2, String msg2) {
        this.title = title2;
        this.msg = msg2;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.okText == null || this.okText.isEmpty()) {
            this.okText = "Confirm";
        }
        if (savedInstanceState != null && savedInstanceState.getBoolean("hasShown")) {
            this.title = savedInstanceState.getString("title");
            this.msg = savedInstanceState.getString("msg");
            this.okText = savedInstanceState.getString("okText");
            this.cancelText = savedInstanceState.getString("cancelText");
            this.gravity = savedInstanceState.getInt("gravity");
        }
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.base_dialog, null);
        this.infoView = (TextView) view.findViewById(R.id.text_info);
        this.infoView.setText(this.msg);
        this.infoView.setGravity(this.gravity);
        this.infoView.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                TextDialogFragment.this.onItemClick();
            }
        });
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
                    TextDialogFragment.this.onConfirm();
                    TextDialogFragment.this.dismiss();
                }
            });
        }
        if (this.cancelText != null && !this.cancelText.isEmpty()) {
            cancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setText(this.cancelText);
            cancelBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    TextDialogFragment.this.onCancel();
                    TextDialogFragment.this.dismiss();
                }
            });
        }
        dialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                if (arg1 == 4) {
                    return true;
                }
                return false;
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {

                if (TextDialogFragment.this.getDialog() != null) {
                    TextDialogFragment.this.getDialog().getWindow().setType(2009);
                }
            }
        });
        return dialog;
    }

    public void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
        arg0.putString("title", this.title);
        arg0.putString("msg", this.msg);
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
