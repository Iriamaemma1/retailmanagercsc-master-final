package csc1304.gr13.retailmanagercsc.options;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UserAuthentication;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.DbHandler;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.Terminal;
import csc1304.gr13.retailmanagercsc.history.models.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CS1304 on 8/02/2021.
 */
public class OptionsFragment extends PreferenceFragment {
    User user;
    ArrayList<Preference> preferences = new ArrayList<>();
    private DbHandler db;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);

        Field[] fields = R.string.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().startsWith("pref_key")) {
                try {
                    preferences.add(findPreference(getString((int) fields[i].get(null))));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Preference preference : preferences) {
           // preference.setEnabled(false);
        }

        Preference policyPreference = findPreference(getString(R.string.pref_key_app_version));
        policyPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return true;
            }
        });


        Preference customCategoriesPreference = findPreference(getString(R.string.pref_key_logout));
        customCategoriesPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
               showPopup();
                return true;
            }
        });

        Preference terminalInfoPreference = findPreference(getString(R.string.pref_key_terminal_info));
        terminalInfoPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                showTerminalInfo();
                return true;
            }
        });

        Preference appVersionPreference = findPreference(getString(R.string.pref_key_app_version));
        appVersionPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                showAppVersion();
                return true;
            }
        });

        Preference changePasswordPreference = findPreference(getString(R.string.pref_key_change_user_password));
        changePasswordPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                changePassWord();
                return true;
            }
        });

        Preference updateSecurityPreference = findPreference(getString(R.string.pref_key_security_keys));
        updateSecurityPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onPreferenceClick(Preference preference) {
                updateSecurityKeys();
                return true;
            }
        });

        //pref_resync

        Preference pref_resync = findPreference(getString(R.string.pref_resync));
        pref_resync.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onPreferenceClick(Preference preference) {




               String msg = "Resyncing Billers Failed";

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Billers");
                alertDialog.setMessage(msg);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                Toast.makeText(getActivity(),"Billers Resynced", Toast.LENGTH_LONG).show();

                return true;
            }
        });
    }
    private void showAppVersion(){

        PackageInfo pinfo = null;
        try {
            pinfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       // int versionNumber = pinfo.versionCode;
        String versionName = pinfo.versionName;

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("App Information");
        alertDialog.setMessage("App Version:  "+versionName);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showTerminalInfo(){
        String terminalId_ = "N/A",merchantId_="N/A";
        db=new DbHandler(getActivity());
        try{
            List<Terminal> terminal =  db.getAllTerminalDetails();

            for(Terminal terminal_info:terminal){
                terminalId_ = terminal_info.getTerminalId();
                merchantId_ = terminal_info.getMerchantId();

            }


        }catch (Exception ex){

        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Terminal Information");
        alertDialog.setMessage("TerminalId:  "+terminalId_+"\n\nMerchantId: "+merchantId_+"");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(),R.style.AlertDialogStyle);
        alert.setTitle("Warning")
                .setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        logout(); // Last step. Logout function

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();

        TextView titleText = alert1.findViewById(android.R.id.title);
        if(titleText != null){
            titleText.setGravity(Gravity.CENTER);
        }


        TextView messageText = alert1.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        alert1.show();
    }

    private void logout() {
        startActivity(new Intent(getActivity(), UserAuthentication.class));
        getActivity().finish();
    }

    private void changePassWord() {
      //  startActivity(new Intent(getActivity(), ChangePasswordActivity.class));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateSecurityKeys(){
    }


}


