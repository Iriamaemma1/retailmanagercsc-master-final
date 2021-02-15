package csc1304.gr13.retailmanagercsc.clienttransaction.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

public class GetDeviceId {
    TelephonyManager telephonyManager;

    private Context myContext;

    public GetDeviceId(Context context) {

        this.myContext = context;

    }

    public String getUniqueId(){
        telephonyManager = (TelephonyManager) myContext.getSystemService(myContext.
                TELEPHONY_SERVICE);

        /*
         * getDeviceId() returns the unique device ID.
         * For example,the IMEI for GSM and the MEID or ESN for CDMA phones.
         */
        @SuppressLint("MissingPermission")
        String deviceId = telephonyManager.getDeviceId();
        /*
         * getSubscriberId() returns the unique subscriber ID,
         * For example, the IMSI for a GSM phone.
         */
        @SuppressLint("MissingPermission")
        String subscriberId = telephonyManager.getSubscriberId();

        return deviceId;
    }
}
