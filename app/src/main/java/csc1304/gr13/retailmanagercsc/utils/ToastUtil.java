package csc1304.gr13.retailmanagercsc.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import csc1304.gr13.retailmanagercsc.R;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public class ToastUtil {
    private static final long TOAST_LAST_THRESHOLD = 2000;
    private static Context context;
    private static long previous = 0;
    private static TextView tipTv;
    private static Toast toast;
    private String toastMessage;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static void toastOnUiThread(Activity activityContext, String message) {
        toastOnUiThread(activityContext, message, 0, 0, 30);
    }

    public static void toastOnUiThread(Activity activityContext, final String message, final int duration, final int xOffset, final int yOffset) {
        if (activityContext == null) {
            toast(message, duration, xOffset, yOffset);
        } else {
            activityContext.runOnUiThread(new Runnable() {
                public void run() {
                    ToastUtil.toast(message, duration, xOffset, yOffset);
                }
            });
        }
    }

    public static void toastOnUiThread(Activity activityContext, final String message, final int duration) {
        if (activityContext == null) {
            toast(message, duration);
        } else {
            activityContext.runOnUiThread(new Runnable() {
                public void run() {
                    ToastUtil.toast(message, duration);
                }
            });
        }
    }

    public static void toast(String message) {
        toast(message, 1);
    }

    public static void toast(int id) {
        toast(id, 1);
    }

    public static void toast(int id, int duration) {
        toast(context.getString(id), duration);
    }

    public static void toast(String text, int duration) {
        long now = System.currentTimeMillis();
        if (now - previous < TOAST_LAST_THRESHOLD) {
            tipTv.setText(text);
            toast.show();
        } else {
            toast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
            tipTv = (TextView) view.findViewById(R.id.toast_textView_tip);
            tipTv.setText(text);
            toast.setDuration(duration);
            toast.setView(view);
            toast.show();
        }
        previous = now;
    }

    public static void toast(String text, int duration, int xOffset, int yOffset) {
        long now = System.currentTimeMillis();
        if (now - previous < TOAST_LAST_THRESHOLD) {
            tipTv.setText(text);
            toast.show();
        } else {
            toast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
            tipTv = (TextView) view.findViewById(R.id.toast_textView_tip);
            tipTv.setText(text);
            toast.setDuration(duration);
            toast.setView(view);
            toast.setGravity(81, xOffset, yOffset);
            toast.show();
        }
        previous = now;
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
