package csc1304.gr13.retailmanagercsc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AboutApp extends AppCompatActivity {
    TextView tvTermOfServices, tvPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        initToolbar();
        tvTermOfServices = (TextView) findViewById(R.id.tvTermsOfService);

        tvTermOfServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermServicesDialog();
            }
        });


        tvPrivacyPolicy = (TextView) findViewById(R.id.tv_privacy_and_policy);

        tvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon((int) R.drawable.ic_samuel_background);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle((CharSequence) "About App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToMain);
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twezimbe_interface, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showTermServicesDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_term_of_services);
        dialog.setCancelable(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(AboutApp.this.getApplicationContext(), "Button Accept Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(AboutApp.this.getApplicationContext(), "You will be logged out", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

}
