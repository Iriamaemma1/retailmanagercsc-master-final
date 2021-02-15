package csc1304.gr13.retailmanagercsc.clienttransaction.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckConnection {

    public CheckConnection(){

    }
    public boolean checkNetworkConnection(String remoteUrl){

        URL url = null;
        try {
            url = new URL(remoteUrl);
        } catch (MalformedURLException e) {
           return false;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            return false;
        }
        int code = 0;
        try {
            code = connection.getResponseCode();

        } catch (IOException e) {
            return false;
        }

        if(code == 200) {
            // reachable
            return true;
        } else {
            return false;
        }

    }
}
