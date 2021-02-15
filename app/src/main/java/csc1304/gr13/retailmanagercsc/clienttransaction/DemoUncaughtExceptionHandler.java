package csc1304.gr13.retailmanagercsc.clienttransaction;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class DemoUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private static String TAG = new Throwable().getStackTrace()[0].getClassName();
    private Application application;

    public DemoUncaughtExceptionHandler(Application application2) {
        this.application = application2;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "Application error，Please restart app");
        Log.e(TAG, Log.getStackTraceString(ex));
        System.out.println("Error info：\n" + Log.getStackTraceString(ex));
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
