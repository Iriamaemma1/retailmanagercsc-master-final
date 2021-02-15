package csc1304.gr13.retailmanagercsc.history.edit_entry;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.DatabaseHandler;
import csc1304.gr13.retailmanagercsc.clienttransaction.model.Transactions;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by CS1304 on 8/02/2021.
 */
public class TransactionDetailsActivity extends AppCompatActivity {

    private String tranId;

    private DatabaseHandler db;
    private Transactions transaction;
    private JsonObject response;

    public TransactionDetailsActivity() {
    }

    private TextView vtranstatus, vtrantype, vtranamount, vpan, vstan, vrrn, vtrandate,vpaymentitem,vcustomerid,vcustomername,vcustomernumber,vsurcharge,vexcise,vrequestref,vpaymenttoken;

    private LinearLayout l_payment,l_generic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        l_generic = findViewById(R.id.l_generic);
        l_payment = findViewById(R.id.l_payment);
        vpaymentitem = findViewById(R.id.paymentitem);
        vcustomerid = findViewById(R.id.customerid);
        vcustomername = findViewById(R.id.customername);
        vcustomernumber = findViewById(R.id.customernumber);
        vsurcharge = findViewById(R.id.surcharge);
        vexcise = findViewById(R.id.excise);
        vrequestref = findViewById(R.id.requestref);
        vpaymenttoken = findViewById(R.id.paymenttoken);

        vtranstatus = findViewById(R.id.transtatus);
        vtrantype = findViewById(R.id.trantype);
        vtranamount = findViewById(R.id.tranamount);
        vpan = findViewById(R.id.pan);
        vstan = findViewById(R.id.stan);
        vrrn = findViewById(R.id.rrn);
        vtrandate = findViewById(R.id.trandate);

        tranId = getIntent().getExtras().getString("transaction_id");


        Log.i("TranId", tranId);

        transaction = new Transactions();

        transaction = (Transactions) getIntent().getSerializableExtra("transaction");
        Log.e("ParceableTranType", transaction.getTran_type());

        if (transaction != null) {

            Log.i("TranType", transaction.getTran_type());
            Log.i("Pan", transaction.getPan());
            Log.i("TranResponse", transaction.getFullMessageResponse());
            Log.i("RRN", transaction.getRrn());
            Log.i("Amount Authorized", String.valueOf(transaction.getAmount_authorsed()));
            Log.i("Stan", transaction.getStan());
            Log.i("TransactionDate", transaction.getTran_date());
            String DbStructuredValue = transaction.getStructuredData();
            Log.i("DbStructuredValue", transaction.getStructuredData());

            String tranamount, trantype, transactiondate, respCode = "", respDescription = "", acctBalance = "", pan = "", refference_nr = "", stan = "", authId = "", status = "";

            respCode = transaction.getResp_code();
            respDescription = transaction.getResp_desc();

            transactiondate = transaction.getTran_date();

            trantype = transaction.getTran_type();

            NumberFormat formatAmount = new DecimalFormat("#,###");;
            formatAmount.setMaximumFractionDigits(0);

            tranamount = formatAmount.format(Double.parseDouble(String.valueOf(transaction.getAmount_authorsed()))/100);



            int color = 0;
            Log.e("ResponseCode",respCode);
            if (respCode.equalsIgnoreCase("00")) {
                //acctBalance = response.get("amount").getAsString();

                status = "SUCCESSFUL";
                color = getResources().getColor(R.color.lightgreen);
            } else if (respCode.equalsIgnoreCase("09")) {
                if (trantype.toLowerCase().contains("payment") || trantype.equalsIgnoreCase("cardless payment")) {
                    status = "Transaction Pending ...";
                    color = getResources().getColor(R.color.lightgreen);
                } else {
                    status = "FAILED\n"+"(Reason)"+respDescription;
                    color = getResources().getColor(R.color.red);
                }
            } else {
                status = "FAILED\n"+"(Reason)"+respDescription;
                color = getResources().getColor(R.color.red);
            }

            vtranstatus.setText(status);
            vtranstatus.setTextColor(color);

            vtrantype.setText(trantype);
            vtranamount.setText("UGX " + tranamount);


            if(!trantype.equalsIgnoreCase("Cash")){
                response = new Gson().fromJson(transaction.getFullMessageResponse(), JsonObject.class);
                String responseCode = response.get("respCode").getAsString();
                if(responseCode.equalsIgnoreCase("00")){
                    pan = response.get("pan").getAsString();
                    refference_nr = response.get("refference_nr").getAsString();
                    stan = response.get("stan").getAsString();
                    authId = response.get("authId").toString();
                }else{
                    pan = transaction.getPan();
                    refference_nr = "-";
                    stan = transaction.getStan();
                }

                vpan.setText(pan);
                vstan.setText(stan);
                vrrn.setText(refference_nr);
                vtrandate.setText(transactiondate);

            }else {
                //Hide card payment generic view
                l_generic.setVisibility(View.GONE);
                l_payment.setVisibility(View.VISIBLE);
            }




            if(trantype.toLowerCase().equals("payment") || trantype.equalsIgnoreCase("cardless payment")){
                Log.e("TranType","This is payment");
                l_payment.setVisibility(View.VISIBLE);
                String paymentitem,customerid,customername,customernumber,surcharge,excise,requestref,paymenttoken;
                String structuredData = transaction.getStructuredData();

                if(structuredData.length()>0){
                    paymentitem = getXmlTagValue(structuredData,"ItemName");
                    customerid = getXmlTagValue(structuredData,"CustomerId");
                    customername = getXmlTagValue(structuredData,"CustomerName");
                    customernumber = getXmlTagValue(structuredData,"CustomerNumber");
                    surcharge = getXmlTagValue(structuredData,"Surcharge");
                    excise = getXmlTagValue(structuredData,"Exercise");
                    requestref = getXmlTagValue(structuredData,"RequestRef");
                    if(trantype.equalsIgnoreCase("cardless payment")){
                        paymenttoken = transaction.getFullMessageResponse();
                    }else if(trantype.equalsIgnoreCase("payment")){
                        if(!respCode.equalsIgnoreCase("00")){
                            paymenttoken = response.get("rechargePin").getAsString();
                        }else{
                            paymenttoken="-";
                        }
                    }else{
                        paymenttoken="-";
                    }

                    if(!paymentitem.equalsIgnoreCase("-1")){
                        vpaymentitem.setText(paymentitem);
                    }
                    if(!customerid.equalsIgnoreCase("-1")){
                        vcustomerid.setText(customerid);
                    }
                    if(!customername.equalsIgnoreCase("-1")){
                        vcustomername.setText(customername);
                    }
                    if(!customernumber.equalsIgnoreCase("-1")){
                        vcustomernumber.setText(customernumber);
                    }
                    if(!surcharge.equalsIgnoreCase("-1")){
                        vsurcharge.setText(surcharge);
                    }
                    if(!excise.equalsIgnoreCase("-1")){
                        vexcise.setText(excise);
                    }
                    if(!requestref.equalsIgnoreCase("-1")){
                        vrequestref.setText(requestref);
                    }
                    if(paymenttoken != null && !paymenttoken.equalsIgnoreCase("-1") && paymenttoken.length()>0){
                        vpaymenttoken.setText(paymenttoken);
                    }

                }
            }



            //processing structured data to get payment parameters
            Log.e("StrDataAmount", getXmlTagValue(DbStructuredValue, "Amount"));

        } else {
            Log.e("Transaction", "Transaction is null");
        }

    }

    private String getXmlTagValue(String xmlString, String tagName) {

        String startElement = "<" + tagName + ">";

        String endElement = "</" + tagName + ">";

        int lengthOfStartElement = startElement.length();

        int indexOfStartElement = xmlString.indexOf(startElement);


        int indexOfEndElement = xmlString.indexOf(endElement);

        /*Log.e("LengthOfXmlString", xmlString.length() + "");
        Log.e("LengthOfstarte", lengthOfStartElement + "");
        Log.e("indexofstart", indexOfStartElement + "");
        Log.e("indexofLast", indexOfEndElement + "");*/
        String value = "";
        try {
            if ((indexOfStartElement + lengthOfStartElement) < indexOfEndElement) {
                value = xmlString.substring(indexOfStartElement + lengthOfStartElement, indexOfEndElement);
            } else {
                value = "-1";
            }
        } catch (Exception ex) {
            Log.e("FailedTogetTag", ex.getMessage());
            value = "-1";
        }

        return value;

    }


}
