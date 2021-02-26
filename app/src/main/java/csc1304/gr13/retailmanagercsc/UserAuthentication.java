package csc1304.gr13.retailmanagercsc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import csc1304.gr13.retailmanagercsc.database.DeleteAllData;
import csc1304.gr13.retailmanagercsc.database.User;
import csc1304.gr13.retailmanagercsc.interfaces.RetrofitApiCaller;
import csc1304.gr13.retailmanagercsc.modelClass.UserDatabaseModel;
import csc1304.gr13.retailmanagercsc.staffId.CurrentLogginSession;
import csc1304.gr13.retailmanagercsc.test.SampleDataInsert;
import csc1304.gr13.retailmanagercsc.userallowedtrans.UserAllowedTransactions;

public class UserAuthentication extends AppCompatActivity {

    public static final String TAG = "ShopManager";
    private EditText nameEt;
    private EditText passwordEt;
    private Button loginBtn;
    private RetrofitApiCaller loginApi;
    private String userName;
    private String userPassword;
    private User user;
    private SampleDataInsert sampleData;

    private Boolean isFirstTimeLogin;
    private String terminal_Id,merchant_Id,merchant_name;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;
    private Handler dialogHandler;

    public static String BASE_URL=null;


    private DeleteAllData deleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BASE_URL = getString(R.string.base_url);

        this.dialogHandler = new Handler(Looper.getMainLooper());

        sampleData = new SampleDataInsert(this);

        deleteData = new DeleteAllData(this);

        user = new User(this);
        nameEt = (EditText) findViewById(R.id.userNameEt);
        passwordEt = (EditText) findViewById(R.id.userPasswordEt);
        loginBtn = (Button) findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nameEt.getText()) || TextUtils.isEmpty(passwordEt.getText())){
                    if (TextUtils.isEmpty(nameEt.getText())) nameEt.setError("user name can,t be empty");
                    if (TextUtils.isEmpty(passwordEt.getText())) nameEt.setError("user password can,t be empty");
                }else {
                    authentication();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void authentication() {


        if(!user.haveAnyUser()){
            sampleData.storeSomeSampleData();//this method will store some sample data on database

        }

        //deleteData.deleteDueInfo();

        userName = nameEt.getText().toString();
        userPassword = passwordEt.getText().toString();


        /*if (user.haveAnySeller()){
            if (user.getUserDetails().getUserName().toLowerCase().equals(userName.toLowerCase())
                    && user.getUserDetails().getUserPassword().toLowerCase().equals(userPassword.toLowerCase())){

                startActivity(new Intent(UserAuthentication.this,MainActivity.class));
                finish();

                return;
            }else{
                showToast("Wrong user name or password");
                return;
            }
        }*/
        if(user.AuthenticateUser(userName,userPassword) != null){
            UserDatabaseModel person = user.AuthenticateUser(userName,userPassword);
            UserSession.setEmployeeId(person.getUserEmployeeId());
            CurrentLogginSession.setStaffId(person.getUserEmployeeId());
            CurrentLogginSession.setStaffEmail(person.getUserEmail());
            CurrentLogginSession.setStaffContact(person.getUserPhone());
            CurrentLogginSession.setStaffName(person.getUserName());
            CurrentLogginSession.setStaffPrimaryId(person.getUserEmployeeId());
            CurrentLogginSession.setInstitutionId(person.getUserEmployeeId());

            startActivity(new Intent(UserAuthentication.this, MainActivity.class));
            finish();

            return;

        }else{
            showToast("Wrong user name or password");
            return;

        }


    }


    public void showToast(String str){
        Toast.makeText(UserAuthentication.this,str,Toast.LENGTH_SHORT).show();
    }


    public void formatSucessResponse(final String mMessage) {

        JsonObject formatResponseAsJson = new Gson().fromJson(mMessage, JsonObject.class);
        JsonObject req_resp = formatResponseAsJson;
        String status = req_resp.get("status").getAsString();
        String terminal_status = null;
        String role =null;
        String allowedTransactions = null;
        String staffId = null;
        String staffPrimaryId = null;
        String staffName = null;
        String staffEmail = null;
        String staffContact = null;
        String institutionId = null;
        String institutionName = null;

        boolean isSucessful = false;

        if(status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("ChangePassword")){
            terminal_status= req_resp.get("terminal_status").getAsString();
            isSucessful = true;
            role = req_resp.get("role").getAsString();
        }

        if(isSucessful){
            progressDialog.dismiss();


            if(terminal_status.equalsIgnoreCase("TerminalNotConfigured")){
                String terminalId_,merchantId_,merchantNameLoc_,merchantContact_,merchantName_;
                terminalId_ = req_resp.get("terminalId").getAsString();
                merchantId_ = req_resp.get("merchantId").getAsString();
                merchantNameLoc_ = req_resp.get("merchantNameLoc").getAsString();
                merchantContact_ = req_resp.get("merchantContact").getAsString();
                merchantName_ = req_resp.get("merchantName").getAsString();

                //ConstantUtils.setTerminalId(terminal_Id);

            }
            if(role.equalsIgnoreCase("TerminalAdmin")){
                allowedTransactions = req_resp.get("userAllowTransaction").getAsString();
                UserAllowedTransactions.setAllowTransactions(allowedTransactions);
                //startActivity(new Intent(this, SetCommuActivity.class));
                // finish();
            }

            if(role.equalsIgnoreCase("TerminalAgent")){

                if(status.equalsIgnoreCase("Approved")){
                    allowedTransactions = req_resp.get("userAllowTransaction").getAsString();
                    UserAllowedTransactions.setAllowTransactions(allowedTransactions);

                    staffId = req_resp.get("staffId").getAsString();

                    staffName =  req_resp.get("staffName").getAsString();
                    staffEmail =  req_resp.get("staffEmail").getAsString();
                    staffContact =  req_resp.get("staffContact").getAsString();
                    institutionId =  req_resp.get("institutionId").getAsString();
                    staffPrimaryId = req_resp.get("userId").getAsString();
                    institutionName = req_resp.get("institutionName").getAsString();


                    UserSession.setEmployeeId(staffPrimaryId);
                    CurrentLogginSession.setStaffId(staffId);
                    CurrentLogginSession.setStaffEmail(staffEmail);
                    CurrentLogginSession.setStaffContact(staffContact);
                    CurrentLogginSession.setStaffName(staffName);
                    CurrentLogginSession.setStaffPrimaryId(staffPrimaryId);
                    CurrentLogginSession.setInstitutionId(institutionId);
                    if(!institutionName.equalsIgnoreCase("-1")){
                        CurrentLogginSession.setInstitutionName(institutionName);
                    }else{
                        CurrentLogginSession.setInstitutionName(merchant_Id);
                    }


                    if(!user.getUserDetailsById(staffPrimaryId)){
                        Log.e("User","User does not exists, creating ...");
                        user.createNewUser(new UserDatabaseModel(staffName,staffEmail,"*****",staffContact,staffPrimaryId));

                    }else{
                        // user.deleteUser(staffPrimaryId);
                        Log.e("User","User exists exists ...");
                    }


                    Intent intent = new Intent(UserAuthentication.this, MainActivity.class);


                    if(intent != null){
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        //Intent intent = new Intent(LoginActivity.this, CardTransactionsActivity.class);
                        startActivity(intent);

                        setResult(Activity.RESULT_OK);

                        //Complete and destroy login activity once successful
                        finish();
                    }
                }
                if(status.equalsIgnoreCase("ChangePassword")){
                    Intent intent =null;// new Intent(UserAuthentication.this, ChangePasswordActivity.class);

                    if(intent != null){

                        startActivity(intent);

                        setResult(Activity.RESULT_OK);

                    }
                }


            }

            if(role.equalsIgnoreCase("TerminalManager")){
                //startActivity(new Intent(this, TerminalManagerActivity.class));
            }

        }else{
            if(status.equalsIgnoreCase("LoginFailed")){
                formatFailureResponse("Wrong Username or Password");
            }

        }

    }

    public void formatFailureResponse(final String msg) {
        progressDialog.dismiss();
        // final String msg ="Login Failed";
        this.dialogHandler.post(new Runnable() {
            public void run() {
                new DialogUIActivity(UserAuthentication.this).showDialog("Login Failed",msg);

            }
        });
    }

    private void progressDialog(String msg, boolean cancelable) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }



}
