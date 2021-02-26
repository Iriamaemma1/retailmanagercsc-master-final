package csc1304.gr13.retailmanagercsc.activity;

import androidx.appcompat.app.AppCompatActivity;
import csc1304.gr13.retailmanagercsc.R;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;

import java.io.IOException;

public class MoMoPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_mo_payment);

        //final gettoken gt = new gettoken();

        final RequestToPay rp = new RequestToPay();

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                try{

                    rp.rtp();

                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();


    }
}