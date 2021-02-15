package csc1304.gr13.retailmanagercsc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
public class CheckInternetConnection {

    //checking internet connection if internet is connected then it will return true
    // otherwise it will return false

    public static boolean netCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        boolean isConnected = (nInfo != null && nInfo.isConnectedOrConnecting());
        return isConnected;
    }

}
