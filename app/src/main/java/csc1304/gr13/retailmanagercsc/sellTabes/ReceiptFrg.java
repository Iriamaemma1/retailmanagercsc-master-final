package csc1304.gr13.retailmanagercsc.sellTabes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UserSession;
import csc1304.gr13.retailmanagercsc.clienttransaction.Utils.GetDeviceId;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.DatabaseHandler;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.DbHandler;
import csc1304.gr13.retailmanagercsc.clienttransaction.db.Terminal;
import csc1304.gr13.retailmanagercsc.clienttransaction.model.GenerateStan;
import csc1304.gr13.retailmanagercsc.clienttransaction.model.HoldStaticResponseData;
import csc1304.gr13.retailmanagercsc.clienttransaction.model.Transactions;
import csc1304.gr13.retailmanagercsc.customertables.addcustomer.AddCustomerActivity;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.CustomerDue;
import csc1304.gr13.retailmanagercsc.database.ProductVariation;
import csc1304.gr13.retailmanagercsc.database.SellsInfo;
import csc1304.gr13.retailmanagercsc.database.SoldProductInfo;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.database.User;
import csc1304.gr13.retailmanagercsc.databinding.FragmentReceiptFrgBinding;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerDuelDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductListModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationModel;
import csc1304.gr13.retailmanagercsc.modelClass.SellsDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SoldProductModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.UserDatabaseModel;
import csc1304.gr13.retailmanagercsc.p25.PrintSellInfo;
import csc1304.gr13.retailmanagercsc.utils.GenerateUnqueId;
import csc1304.gr13.retailmanagercsc.utils.HoldProductSellsVariables;
import csc1304.gr13.retailmanagercsc.utils.PostOnlineTransaction;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static csc1304.gr13.retailmanagercsc.database.Stock.TAG;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */
/**
 * A simple {@link Fragment} subclass.
 */

public class ReceiptFrg extends Fragment {

    private FragmentReceiptFrgBinding binding;
    private ArrayList<ProductListModel> products;
    private int totalBill = 0;
    private int discount = 0;
    private int totalPayAmount = 0;
    private int customerPayment = 0;
    private int paymentStatus = 0;
    //private String[] paymentType = new String[]{"Cash", "Card", "Mobile Money", "Pay Later", "Bank"};
    private String[] paymentType = new String[]{"Cash", "Pay Later"};
    private ArrayList<CustomerModel> customers;
    private CustomerModel selectedCustomer;
    private UserDatabaseModel seller;
    private SellsInfo sellsInfo;
    private Stock stock;
    private ProductVariation productVariation;
    private PrintSellInfo printInfo;
    private Customer customerDatabase;
    private CustomerDue customerDue;
    private SoldProductInfo soldProductInfo;

    private String date = "170605";
    private Calendar calendar;
    private View v;
    HashMap<String, String> tranRequest = null;

     String TAG ="csc1304.gr13.retailmanagercsc";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt_frg, container, false);
        products = new ArrayList<>();
        //get all customer details from customer database
        seller = new User(getActivity()).getUserDetails();//this user method will return userDatavaseModelClassObject
        sellsInfo = new SellsInfo(getActivity());
        stock = new Stock(getActivity());
        printInfo = new PrintSellInfo();
        customerDatabase = new Customer(getActivity());
        customerDue = new CustomerDue(getActivity());
        soldProductInfo = new SoldProductInfo(getActivity());
        productVariation = new ProductVariation(getActivity());

        calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");//example date 06-11-17
        date = dateFormat.format(calendar.getTime());
        Log.d(TAG, "onCreateView: -----------" + date);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver
                , new IntentFilter("selectedProduct"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRemoveProductMessageReceiver
                , new IntentFilter("removeProduct"));


        try {
            if (validationCheck(seller.getUserPhone())
                    && validationCheck(seller.getUserEmail())
                    && validationCheck(String.valueOf(sellsInfo.getLastSellItemCode()))) {
                binding.sellerPhoneRTv.setText("Phone : " + seller.getUserPhone());
                binding.sellerEmailRTv.setText("Email : " + seller.getUserEmail());
                //binding.receiptInvoiceTv.setText("u-" + seller.getUserEmployeeId() + "-in-" + (sellsInfo.getLastSellItemCode() + 1));
                binding.receiptInvoiceTv.setText(GenerateUnqueId.getUniqueId("").toUpperCase());

            } else {
                binding.sellerPhoneRTv.setText("Phone : Invalid Seller");
                binding.sellerEmailRTv.setText("Email : Invalid Seller");
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, paymentType);
            binding.receiptPaymentTypeSpinner.setTitle("Select Payment Type");
            binding.receiptPaymentTypeSpinner.setAdapter(adapter);

            binding.receiptDiscountEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (!TextUtils.isEmpty(binding.receiptDiscountEt.getText())) {
                        String temp = binding.receiptDiscountEt.getText().toString().trim();
                        discount = Integer.parseInt(temp);

                        if (discount < 0) {
                            binding.receiptDiscountEt.setError("discount amount can,t be negative");
                            discount = 0;
                        } else {
                            updateData();
                        }
                    }
                    return true;
                }
            });


            updateView();
            binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    Log.e("Radio", "Id Checked:" + checkedId);
                    if (checkedId == binding.radioFullPayment.getId()) {


                        String temp2[] = binding.receiptTotalTv.getText().toString().trim().split(" UGX");
                        if (Integer.parseInt(temp2[0]) < 1) {
                            Toast.makeText(getActivity(), "Select At lest one product", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        totalPayAmount = Integer.parseInt(temp2[0]);
                        discount = Integer.parseInt(binding.receiptDiscountEt.getText().toString().trim());
                        totalBill = totalPayAmount - discount;
                        binding.receiptPayAmountEt.setText(totalBill + "");

                        binding.receiptPayAmountEt.setEnabled(Boolean.FALSE);

                        Log.e("Radio1", "Id Checked:" + checkedId);
                    } else if (checkedId == binding.radioPartial.getId()) {
                        //Snackbar.make(v,"Partial Payment",Snackbar.LENGTH_LONG);
                        Log.e("Radio2", "Id Checked:" + checkedId);
                        binding.receiptPayAmountEt.setText("0");
                        binding.receiptPayAmountEt.setEnabled(Boolean.TRUE);

                    } else if (checkedId == binding.radioPayLater.getId()) {
                        //Snackbar.make(v,"Pay Later",Snackbar.LENGTH_LONG);
                        Log.e("Radio3", "Id Checked:" + checkedId);
                        binding.receiptPayAmountEt.setText("0");
                        binding.receiptPayAmountEt.setEnabled(Boolean.FALSE);
                    }


                }
            });


            binding.receiptSaveReceiptBtn.setOnClickListener(listener);
            Log.d(TAG, "receipt page");


        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getActivity(), "Please Select Valid Data", Toast.LENGTH_SHORT).show();
        }


    }

    private void updateView() {

        customers = new Customer(getActivity()).getAllCustomer();

        final ArrayList<String> suggestionCustomer = new ArrayList<>();
        suggestionCustomer.add("Select Customer");
        suggestionCustomer.add("Create Customer");
        if (customerDatabase.haveAnyData()) {
            if (!customers.isEmpty()) {
                for (CustomerModel customer : customers) {
                    suggestionCustomer.add(customer.getCustomerName());
                }
            }
        }

        ArrayAdapter<String> suggestionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, suggestionCustomer);
        binding.receiptCustomerSpinner.setAdapter(suggestionAdapter);

        binding.receiptCustomerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (suggestionCustomer.get(position).equalsIgnoreCase("Create Customer")) {
                    Intent intent = new Intent(getContext(), AddCustomerActivity.class);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    View.OnClickListener listener = new View.OnClickListener() {


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {

            String temp[] = binding.receiptTotalTv.getText().toString().trim().split(" UGX");

            if (Integer.parseInt(temp[0]) < 1) {
                Toast.makeText(getActivity(), "Select At lest one product", Toast.LENGTH_SHORT).show();

                return;
            }
            if (customerDatabase.haveAnyData()) {
                Log.e("SelectedCustomerIndex", binding.receiptCustomerSpinner.getSelectedItemId() + "");
                if (binding.receiptCustomerSpinner.getSelectedItemId() > 1) {
                    selectedCustomer = customers.get(((int) binding.receiptCustomerSpinner.getSelectedItemId()) - 2);
                } else {
                    selectedCustomer = new CustomerModel();
                }

            } else {
                Snackbar.make(v, "Customer is required", Snackbar.LENGTH_SHORT).show();
                return;
            }

            try {
                customerPayment = Integer.parseInt(binding.receiptPayAmountEt.getText().toString());
                totalPayAmount = Integer.parseInt(temp[0]);
                discount = Integer.parseInt(binding.receiptDiscountEt.getText().toString().trim());

                totalBill = totalPayAmount - discount;

                if (!validationCheck(selectedCustomer.getCustomerName())) {
                    Snackbar.make(v, "Select valid customer", Snackbar.LENGTH_SHORT).show();

                    return;
                } else if (totalPayAmount < 1) {
                    Snackbar.make(v, "please select at lest one product", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(binding.receiptPayAmountEt.getText().toString())) {
                    Snackbar.make(v, "Empty Customer payment ", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(binding.receiptPayAmountEt.getText().toString()) < 1) {
                    Snackbar.make(v, "Empty Customer payment ", Snackbar.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Snackbar.make(v, "Input field can,t be empty", Snackbar.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + e);
                e.printStackTrace();
                return;
            }

//            stockAvailabilityCheck();
//
            if (customerPayment < totalBill) {
                paymentStatus = 1;
            }

            /*boolean status = true;
            SellsDatabaseModel sellDatabase = new SellsDatabaseModel(
                    binding.receiptInvoiceTv.getText().toString(),
                    selectedCustomer.getCustomerCode(),
                    binding.receiptTotalTv.getText().toString(),
                    binding.receiptDiscountEt.getText().toString(),
                    binding.receiptPayAmountEt.getText().toString(),
                    binding.receiptPaymentTypeSpinner.getSelectedItem().toString(),
                    date,
                    String.valueOf(paymentStatus),
                    seller.getUserEmployeeId()
            );

            status = updateSellInfo(sellDatabase);*/


            int due = totalPayAmount - customerPayment - discount;
            String allProducts = "";
            String allProductsQuantity = "";
            String allProductPrice = "";
            for (ProductListModel product : products) {
                allProducts += product.getpName() + "\n";
                allProductsQuantity += product.getpSelectQuantity() + " " + product.getpUnit() + "\n";
                allProductPrice += product.getPriceTotal() + " UGX\n";
            }

            Log.e("SelectedPaymentType", binding.receiptPaymentTypeSpinner.getSelectedItem().toString());


            printInfo = new PrintSellInfo(
                    seller.getUserName(),
                    seller.getUserPhone(),
                    seller.getUserEmail(),
                    selectedCustomer.getCustomerName(),
                    selectedCustomer.getCustomerPhone(),
                    selectedCustomer.getCustomerEmail(),
                    binding.receiptInvoiceTv.getText().toString(),
                    String.valueOf(totalPayAmount),
                    String.valueOf(due),
                    allProducts,
                    allProductsQuantity,
                    allProductPrice,
                    String.valueOf(totalPayAmount),
                    String.valueOf(discount),
                    String.valueOf(totalBill),
                    String.valueOf(customerPayment),
                    String.valueOf(due),
                    binding.receiptPaymentTypeSpinner.getSelectedItem().toString());

            //Holding Static data to be accessed by other activities
            HoldProductSellsVariables.setRequestType("Purchase");
            HoldProductSellsVariables.setBaseUrl(getString(R.string.base_url));
            HoldProductSellsVariables.setStock(stock);
            HoldProductSellsVariables.setProductVariation(productVariation);
            HoldProductSellsVariables.setProducts(products);
            HoldProductSellsVariables.setSelectedCustomer(selectedCustomer);
            HoldProductSellsVariables.setCustomerDatabase(customerDatabase);
            HoldProductSellsVariables.setPrintInfo(printInfo);
            HoldProductSellsVariables.setPaymentStatus(paymentStatus);
            HoldProductSellsVariables.setCustomerDue(customerDue);
            HoldProductSellsVariables.setSellsInfo(sellsInfo);
            HoldProductSellsVariables.setSoldProductInfo(soldProductInfo);

            HoldStaticResponseData.setContext(getContext());

            if (binding.receiptPaymentTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Card")) {


            } else {
                tranRequest = new HashMap<>();
                Log.e("TotalPayment", "" + customerPayment);
                tranRequest.put("amountAuthorized", String.valueOf(customerPayment));
                HoldStaticResponseData.setTranCode("-50");
                HoldStaticResponseData.setTransactionName("PAYMENT");
                HoldStaticResponseData.setTranRequest(tranRequest);

                String terminalId = null, merchantId = null, merchantNameLoc = null, merchantContact = null;

                DbHandler terminalInfo = new DbHandler(getContext());
                try {
                    List<Terminal> terminal = terminalInfo.getAllTerminalDetails();

                    for (Terminal terminal_info : terminal) {
                        terminalId = terminal_info.getTerminalId();
                        merchantId = terminal_info.getMerchantId();
                        merchantNameLoc = terminal_info.getMerchantNameLocation();
                    }


                } catch (Exception ex) {

                }

                DatabaseHandler db = new DatabaseHandler(getActivity());


                Transactions transaction = new Transactions();

                transaction.setTran_type(binding.receiptPaymentTypeSpinner.getSelectedItem().toString());

                transaction.setTerminal_id(terminalId);
                transaction.setMerchant_id(merchantId);
                transaction.setMerchant_location(merchantNameLoc);
                transaction.setAmount_authorsed(Long.parseLong(customerPayment * 100 + ""));
                transaction.setSurcharge(Long.parseLong("0"));

                GetDeviceId getDeviceId = new GetDeviceId(getActivity());
                String uniqueId = null;
                try{
                    uniqueId =getDeviceId.getUniqueId();
                }catch(Exception ex){
                    Log.e("ReceiptFrg",ex.getMessage());
                    uniqueId = "78675675675678";
                }


                transaction.setDevice_id(uniqueId);
                transaction.setTran_currency(Integer.parseInt("0800"));

                Date tranDateFormat = new Date();

                String now = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(tranDateFormat);
                transaction.setTran_date(now);
                HoldStaticResponseData.setTranRequestDate(now);

                GenerateStan getStan = new GenerateStan(getActivity());
                final String stan = String.format("%06d", getStan.getStan());


                transaction.setStan(stan);
                SimpleDateFormat formatter;

                formatter = new SimpleDateFormat("yyyyMMdd");
                Date getInstance = new Date();
                String currentDate = formatter.format(getInstance);

                final String rrn = String.format("%012d", Integer.parseInt(currentDate));
                transaction.setRrn(rrn);

                transaction.setResp_code("00");
                transaction.setResp_desc(paymentType + " SUCCESSFUL");
                transaction.setPan("");
                transaction.setFullMessageResponse(new Gson().toJson(printInfo));
                transaction.setStaffId(UserSession.getEmployeeId());
                transaction.setStructuredData("");
                db.addTransaction(transaction);



                    /*Intent intent = new Intent(getActivity(), MainActivityPrinter.class);
                    intent.putExtra("printData",printInfo);
                    startActivity(intent);*/

                productSellsRequest(v.getContext());//remove soled product form stock

                  /*          updateCustomerInfo();//update customer due info
                            updateDueList();//update due database
                            updateSoldProductInfo();
                            removePreviousData();//remove old data after product sold
                */


            }

            removePreviousData();

        }


    };


    private void removePreviousData() {
        products = new ArrayList<>();
        customers = new Customer(getActivity()).getAllCustomer();//get all customer details from customer database
        seller = new User(getActivity()).getUserDetails();//this user method will return userDatavaseModelClassObject
        sellsInfo = new SellsInfo(getActivity());
        stock = new Stock(getActivity());
        printInfo = new PrintSellInfo();

        totalBill = 0;
        discount = 0;
        totalPayAmount = 0;
        customerPayment = 0;
        paymentStatus = 0;
        try {
            binding.receiptInvoiceTv.setText("u-" + seller.getUserEmployeeId() + "-in-" + (sellsInfo.getLastSellItemCode() + 1));
        } catch (Exception e) {

        }

        sendBroadForClearOldData();
        updateData();
    }

    private void sendBroadForClearOldData() {
        Intent intent = new Intent("clearAll");
        intent.putExtra("flag", true);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }


    private boolean updateSellInfo(SellsDatabaseModel sellsDatabaseModel) {
        return sellsInfo.storeSellDetails(sellsDatabaseModel);
    }

    private void updateSoldProductInfo() {

        /*
        for (ProductListModel product : products){
            soldProductInfo.storeSoldProductInfo(new SoldProductModel(
                    printInfo.getInvoiceTv(),
                    printInfo.getInvoiceTv(),
                    product.getpCode(),
                    product.getpVariation(),
                    product.getpPrice(),
                    product.getpSelectQuantity(),
                    printInfo.getTotalAmountTv(),
                    paymentStatus+""
            ));

        }*/
    }

    private void updateDueList() {

//        private String customerId;
//        private String totalAmount;
//        private String totalPayAmount;
//        private String dueAmount;
//        private String sellsCode;
//        private String payDueDate;
//        private String payDeposit;

        //update due info

        /*boolean status = customerDue.storeSellsDetails(new CustomerDuelDatabaseModel(
                selectedCustomer.getCustomerCode(),
                printInfo.getTotalAmountTv(),
                printInfo.getPayableTv(),
                printInfo.getCurrentDueTv(),
                printInfo.getInvoiceTv(),
                date,
                printInfo.getDepositTv()
        ));*/

       /* if (status) Log.d(TAG, "updateDueList: --------------successful");
        else Log.d(TAG, "updateDueList: --------- failed to store due details");*/
    }

    private void updateCustomerInfo() {

        //customer info update
        boolean status = customerDatabase.updateCustomerDueAmount(selectedCustomer.getCustomerCode(), String.valueOf(getInteger(selectedCustomer.getCustomerDueAmount()) + getInteger(printInfo.getDueTv())));
        if (status) Log.d(TAG, "updateCustomerInfo: --------------new due status updated");
        else
            Log.d(TAG, "updateCustomerInfo:------------- failed to update new customer due status");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void productSellsRequest(Context v) {


        /*PostOnlineTransaction postTransaction = new PostOnlineTransaction(getActivity());
        try {
            postTransaction.updateSellsProductStock(getString(R.string.base_url), stock, productVariation, products, selectedCustomer, customerDatabase, printInfo, paymentStatus, customerDue, sellsInfo, soldProductInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String newStock = "0";

        long sellsId = 0;

        for (ProductListModel product : products) {
            String currentStock = stock.getCurrentTock(product.getpId());
            if(currentStock.equalsIgnoreCase("-1")){
                currentStock = "0";
            }

            newStock = String.valueOf(Integer.parseInt(currentStock)+Integer.parseInt(product.getpSelectQuantity()));

          Boolean stockSave=  stock.updateStock(product.getpId(), newStock);
            if(stockSave){
                ProductVariationModel pvModel = productVariation.getProductVarDetails(product.getpVariation(),product.getpId());

                if(pvModel ==null){
                    newStock = product.getpSelectQuantity();
                }else{
                    newStock = String.valueOf(Integer.parseInt(pvModel.getP_v_Stock())+Integer.parseInt(product.getpSelectQuantity()));
                }

                Boolean pvsave = productVariation.updatePvStock(product.getpVariation(),product.getpId(), newStock);

                if (pvsave) {
                    Log.e("ProductVariation", "Variation Saved");
                } else {
                    Log.e("ProductVariation", "Variation Not Saved");
                }



                //Updating product sells


                //Updating Sells

                int count = 0;
                while (count<1){
                    sellsId = sellsInfo.storeSellDetails2(new SellsDatabaseModel(
                            printInfo.getInvoiceTv(),
                            String.valueOf(selectedCustomer.getId()),
                            printInfo.getTotalAmountTv(),
                            printInfo.getDiscount(),
                            printInfo.getPayableTv(),
                            printInfo.getPaidWithTv(),
                            String.valueOf(new Date()),
                            String.valueOf(paymentStatus),
                            UserSession.getEmployeeId()
                    ));

                    CustomerModel customer = customerDatabase.getCustomerById(selectedCustomer.getId());

                    String dueAmount = String.valueOf(Integer.parseInt(customer.getCustomerDueAmount())+Integer.parseInt(printInfo.getDueTv()));

                    customerDatabase.updateCustomerDueAmount(String.valueOf(selectedCustomer.getId()), dueAmount);

                    if (!dueAmount.equalsIgnoreCase("0")) {
                        boolean status = customerDue.storeSellsDetails(new CustomerDuelDatabaseModel(
                                Integer.parseInt(dueAmount),
                                String.valueOf(selectedCustomer.getId()),
                                printInfo.getTotalAmountTv(),
                                printInfo.getPayableTv(),
                                printInfo.getCurrentDueTv(),
                                printInfo.getInvoiceTv(),
                                dueAmount,
                                printInfo.getDepositTv()
                        ));

                        if (status) {
                            Log.e("Customer Due", "Due Saved Successfully");
                        }
                    }



                    count++;
                }


            }



        if(sellsId != 0){
            soldProductInfo.storeSoldProductInfo(new SoldProductModel(
                    printInfo.getInvoiceTv(),
                    String.valueOf(sellsId),
                    product.getpId(),
                    product.getpVariation(),
                    product.getpPrice(),
                    product.getpSelectQuantity(),
                    product.getPriceTotal(),
                    String.valueOf(paymentStatus)
            ));

        }



        }


        Gson gson = new Gson();

        new DialogUIActivity(v).showDialogAndQuick("Items(s) Sold Successfully", "Items Sold Successfully",gson.toJson(printInfo));


    }

    BroadcastReceiver mRemoveProductMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int temp = intent.getIntExtra("position", -1);
                int tempValue = Integer.parseInt(products.get(temp).getPriceTotal());
                if (tempValue > 0) totalBill -= tempValue;

                products.remove(temp);
                updateData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void updateData() {
        if (discount > 0) {
            totalPayAmount = totalBill - discount;
        }
        binding.receiptTotalTv.setText(String.valueOf(totalBill) + " UGX");
        binding.receiptPayAmountEt.setText(String.valueOf(totalPayAmount));
    }

    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ProductListModel temp = (ProductListModel) intent.getSerializableExtra("product");
            totalBill += Integer.parseInt(temp.getPriceTotal());
            products.add(temp);

            updateData();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(messageReceiver);
    }

    public boolean validationCheck(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int getInteger(String str) {
        str.trim();
        int temp = 0;
        try {
            temp = Integer.parseInt(str);
        } catch (Exception e) {

        }
        return temp;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Activity", "On Resume");
        updateView();
    }


}
