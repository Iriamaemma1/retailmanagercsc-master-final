package csc1304.gr13.retailmanagercsc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import csc1304.gr13.retailmanagercsc.p25.MainActivityPrinter;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class DialogUIActivity extends AppCompatActivity {

    private Context context;

    public DialogUIActivity() {

    }

    public DialogUIActivity(Context context) {
        this.context = context;
    }

    public void showDialog(String title, String msg) {
        final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
        dialog1.setTitle(title);
        dialog1.setContentView(R.layout.activity_dialog_ui_2);
        dialog1.setCancelable(true);
        TextView msgtitle = dialog1.findViewById(R.id.dialogTitle);
        TextView msgbody = dialog1.findViewById(R.id.dialogMsg);
        msgtitle.setText(title);
        msgbody.setText(msg);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    public void showDialogAndQuick(String title, String msg, final String printInfo) {

        final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
        dialog1.setTitle(title);
        dialog1.setContentView(R.layout.activity_dialog_ui);
        dialog1.setCancelable(true);
        TextView msgtitle = dialog1.findViewById(R.id.dialogTitle);
        TextView msgbody = dialog1.findViewById(R.id.dialogMsg);
        msgtitle.setText(title);
        msgbody.setText(msg);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                goHome(context, printInfo);
            }
        });
        dialog1.show();
    }

    public void goHome(Context context, String printInfo) {

        Intent intent = new Intent(context, MainActivityPrinter.class);
        intent.putExtra("printData", printInfo);
        context.startActivity(intent);
    }

}
