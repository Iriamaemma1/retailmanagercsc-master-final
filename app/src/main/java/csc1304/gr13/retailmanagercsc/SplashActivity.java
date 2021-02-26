package csc1304.gr13.retailmanagercsc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import csc1304.gr13.retailmanagercsc.activity.MoMoPayment;


public class SplashActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler=new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Splash >> UserAuthentication
                Intent intent=new Intent(SplashActivity.this, UserAuthentication.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
