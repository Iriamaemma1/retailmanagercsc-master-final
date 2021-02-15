package csc1304.gr13.retailmanagercsc.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import csc1304.gr13.retailmanagercsc.DialogUIActivity;
import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.UserSession;
import csc1304.gr13.retailmanagercsc.database.Category;
import csc1304.gr13.retailmanagercsc.database.Customer;
import csc1304.gr13.retailmanagercsc.database.CustomerDue;
import csc1304.gr13.retailmanagercsc.database.Product;
import csc1304.gr13.retailmanagercsc.database.ProductVariation;
import csc1304.gr13.retailmanagercsc.database.Purchase;
import csc1304.gr13.retailmanagercsc.database.SellsInfo;
import csc1304.gr13.retailmanagercsc.database.SoldProductInfo;
import csc1304.gr13.retailmanagercsc.database.Stock;
import csc1304.gr13.retailmanagercsc.database.Supplier;
import csc1304.gr13.retailmanagercsc.database.Variation;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddCategoryDbBinding;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddCustomerDbBinding;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddProductDbBinding;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddPurchaseDbBinding;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddSupplierDbBinding;
import csc1304.gr13.retailmanagercsc.databinding.FragmentAddVariationDbBinding;
import csc1304.gr13.retailmanagercsc.databinding.FragmentDueDetailsBinding;
import csc1304.gr13.retailmanagercsc.fragments.DueListFragment;
import csc1304.gr13.retailmanagercsc.modelClass.CategoryModel;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerDuelDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.CustomerModel;
import csc1304.gr13.retailmanagercsc.modelClass.DueDetailsModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductListModel;
import csc1304.gr13.retailmanagercsc.modelClass.ProductVariationModel;
import csc1304.gr13.retailmanagercsc.modelClass.PurchaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SellsDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SoldProductModel;
import csc1304.gr13.retailmanagercsc.modelClass.StockDatabaseModel;
import csc1304.gr13.retailmanagercsc.modelClass.SupplierModel;
import csc1304.gr13.retailmanagercsc.modelClass.VariationModel;
import csc1304.gr13.retailmanagercsc.p25.PrintSellInfo;
import csc1304.gr13.retailmanagercsc.staffId.CurrentLogginSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class PostOnlineTransaction {

    private ProgressDialog progressDialog;
    private Handler dialogHandler;
    private Activity mActivity;

    public PostOnlineTransaction(Activity activity) {

        mActivity = activity;

        dialogHandler = new Handler(mActivity.getMainLooper());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createProduct(final String baseUrl, final String remoteurl, final Product product, final Stock stock, final ProductVariation productVariation, final ProductDatabaseModel productDatabaseModel, final FragmentAddProductDbBinding stockBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Product", false);
            }
        });
        //String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("productName", productDatabaseModel.getProductName());
            postdata.put("productCode", productDatabaseModel.getProductCode());
            postdata.put("productPrice", Integer.parseInt(productDatabaseModel.getProductPrice()));
            postdata.put("sellPrice", Integer.parseInt(productDatabaseModel.getProductSellPrice()));
            postdata.put("unit", productDatabaseModel.getProductUnit());
            postdata.put("brand", productDatabaseModel.getProductBrand());
            postdata.put("size", productDatabaseModel.getProductSize());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));
            postdata.put("category", Integer.parseInt(productDatabaseModel.getProductCategory()));


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        productDatabaseModel.setProductId(parseId);

                                        Long productId = product.storeProductInfo(productDatabaseModel);


                                        if (productId > 0) {

                                            StockDatabaseModel stockDatabaseModel = new StockDatabaseModel("" + productId, productDatabaseModel.getProductCategory(), "0", UserSession.getEmployeeId());
                                            try {
                                                createProductStock(baseUrl, stock, productVariation, stockDatabaseModel, stockBinding);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            //Long productVariationId = productVariation.storeProductVariationInfo(new ProductVariationModel(productId,))
                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createProductStock(final String baseUrl, final Stock stock, final ProductVariation productVariation, final StockDatabaseModel stockDatabaseModel, final FragmentAddProductDbBinding stockBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Updating Product Stock", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/create/stock";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("totalstock", Integer.parseInt(stockDatabaseModel.getProductQuantity()));
            postdata.put("productfor", stockDatabaseModel.getProductFor());
            postdata.put("productid", Integer.parseInt(stockDatabaseModel.getProductId()));
            postdata.put("productCategory", Integer.parseInt(stockDatabaseModel.getProductType()));
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        stockDatabaseModel.setStockId_(parseId);


                                        Boolean save = stock.storeStock(stockDatabaseModel);

                                        if (save) {

                                            try {
                                                createProductVariation(baseUrl, stock, productVariation, stockDatabaseModel, stockBinding);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                        }


                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createProductVariation(String baseUrl, final Stock stock, final ProductVariation productVariation, final StockDatabaseModel stockDatabaseModel, final FragmentAddProductDbBinding stockBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Product Variations", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/create/productvariation";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (Map.Entry entry : HoldStaticVariationData.getVariationData().entrySet()) {
                Log.e(entry.getKey().toString(), entry.getValue().toString());

                if (count == 0) {
                    sb.append(entry.getValue().toString());
                } else {
                    sb.append("," + entry.getValue().toString());
                }
                count++;
            }
            postdata.put("productid", Integer.parseInt(stockDatabaseModel.getProductId()));
            postdata.put("variationid", sb);
            postdata.put("variationStock", 0);
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Long productVariationId = -1L;
                                        for (Map.Entry entry : HoldStaticVariationData.getVariationData().entrySet()) {
                                            Log.e(entry.getKey().toString(), entry.getValue().toString());
                                            productVariationId = productVariation.storeProductVariationInfo(new ProductVariationModel(entry.getValue().toString(), stockDatabaseModel.getProductId() + "", "0"));
                                            Log.e("ProductVariationId", productVariationId + "");
                                        }

                                        if (productVariationId > 0) {
                                            new DialogUIActivity(mActivity).showDialog("Add Product", "Item has been saved successfully");
                                            clearForm((ViewGroup) stockBinding.addProductLayout);

                                        } else {
                                            new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                        }


                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createCustomer(String remoteurl, final Customer customer, final CustomerModel customerModel, final FragmentAddCustomerDbBinding customerBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Customer", false);
            }
        });
        //String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("customerName", customerModel.getCustomerName());
            postdata.put("customerCode", customerModel.getCustomerCode());
            postdata.put("customerType", customerModel.getCustomerType());
            postdata.put("gender", customerModel.getCustomerGender());
            postdata.put("phone", customerModel.getCustomerPhone());
            postdata.put("email", customerModel.getCustomerEmail());
            postdata.put("address", customerModel.getCustomerAddress());
            postdata.put("dueAmount", customerModel.getCustomerDueAmount());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        customerModel.setId(parseId);

                                        Boolean save = customer.createNewCustomer(customerModel);

                                        if (save) {
                                            clearForm(customerBinding.addCustomerLayout);
                                            new DialogUIActivity(mActivity).showDialog("Add Customer", "Customer has been saved successfully");
                                        } else {
                                            new DialogUIActivity(mActivity).showDialog("Add Customer", "Customer failed to be saved");
                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Customer", "Customer failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Customer", "Customer failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createVariation(String remoteurl, final Variation variation, final VariationModel variationModel, final FragmentAddVariationDbBinding addVariationDbBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Variation", false);
            }
        });
        //String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("variationName", variationModel.getVariation_name());
            postdata.put("description", variationModel.getVariation_description());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        variationModel.setId(parseId);

                                        Long saveVariation = variation.CreateVariation(variationModel);
                                        Log.e("VariationId", saveVariation + "");
                                        if (saveVariation > 0) {

                                            new DialogUIActivity(mActivity).showDialog("Add Variation", "Variable " + addVariationDbBinding.varNameEditText.getText().toString() + " has been saved successfully");
                                            addVariationDbBinding.varNameEditText.setText("");
                                            addVariationDbBinding.varDescEditText.setText("");

                                            Toast.makeText(mActivity, "New Variation details created", Toast.LENGTH_LONG);

                                        } else {
                                            Toast.makeText(mActivity, "New Variation details creation failed", Toast.LENGTH_LONG);
                                            new DialogUIActivity(mActivity).showDialog("Add Variation", "Variation " + addVariationDbBinding.varNameEditText.getText().toString() + " failed to be saved,\n Please try again");

                                            //categoryBinding.catNameEditText.setText("");
                                            //categoryBinding.catDescEditText.setText("");
                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mActivity, "New Variation details creation failed", Toast.LENGTH_LONG);
                                        new DialogUIActivity(mActivity).showDialog("Add Variation", "Variation " + addVariationDbBinding.varNameEditText.getText().toString() + " failed to be saved,\n Please try again");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mActivity, "New Variation details creation failed", Toast.LENGTH_LONG);
                                    new DialogUIActivity(mActivity).showDialog("Add Variation", "Variation " + addVariationDbBinding.varNameEditText.getText().toString() + " failed to be saved,\n Please try again");

                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createSupplier(String remoteurl, final Supplier supplier, final SupplierModel supplierModel, final FragmentAddSupplierDbBinding supplierDbBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Supplier", false);
            }
        });
        // String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("supplierCompanyName", supplierModel.getSupplier_company_name());
            postdata.put("supplierName", supplierModel.getSupplier_name());
            postdata.put("supplierPhoneNumber", supplierModel.getSupplier_phone_number());
            postdata.put("supplierAddress", supplierModel.getSupplier_address());
            postdata.put("supplierBankName", supplierModel.getSupplier_bank_name());
            postdata.put("supplierBankAccount", supplierModel.getSupplier_bank_account());
            postdata.put("supplierEmail", supplierModel.getSupplier_email());
            postdata.put("supplierWebsite", supplierModel.getSupplier_website());
            postdata.put("supplierDescription", supplierModel.getSupplier_description());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        supplierModel.setId(parseId);

                                        Boolean save = supplier.CreateNewSupplier(supplierModel);


                                        if (save) {
                                            clearForm((ViewGroup) supplierDbBinding.addSupplierLayout);
                                            new DialogUIActivity(mActivity).showDialog("Add Supplier", "Supplier has been saved successfully");
                                        } else {
                                            new DialogUIActivity(mActivity).showDialog("Add Supplier", "Supplier failed to be saved");
                                        }


                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Supplier", "Supplier failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Supplier", "Supplier failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createCategory(String remoteurl, final Category category, final CategoryModel categoryModel, final FragmentAddCategoryDbBinding categoryBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Category", false);
            }
        });
        // String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("categoryName", categoryModel.getCategory_name());
            postdata.put("description", categoryModel.getCategory_description());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        categoryModel.setId(parseId);

                                        Boolean saveCategory = category.createNewCategory(categoryModel);

                                        if (saveCategory) {

                                            new DialogUIActivity(mActivity).showDialog("Add Category", "Stock category " + categoryBinding.catNameEditText.getText().toString() + " has been saved successfully");
                                            categoryBinding.catNameEditText.setText("");
                                            categoryBinding.catDescEditText.setText("");

                                            Toast.makeText(mActivity, "New Category details inserted", Toast.LENGTH_LONG);


                                        } else {
                                            Toast.makeText(mActivity, "New Category details insert failed", Toast.LENGTH_LONG);
                                            new DialogUIActivity(mActivity).showDialog("Add Category", "Stock category " + categoryBinding.catNameEditText.getText().toString() + " failed to be saved,\n Please try again");

                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mActivity, "New Category details insert failed", Toast.LENGTH_LONG);
                                        new DialogUIActivity(mActivity).showDialog("Add Category", "Stock category " + categoryBinding.catNameEditText.getText().toString() + " failed to be saved,\n Please try again");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mActivity, "New Category details insert failed", Toast.LENGTH_LONG);
                                    new DialogUIActivity(mActivity).showDialog("Add Category", "Stock category " + categoryBinding.catNameEditText.getText().toString() + " failed to be saved,\n Please try again");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createPurchase(final String baseUrl, final String remoteurl, final Purchase purchase, final Stock stock, final ProductVariation productVariation, final PurchaseModel purchaseModel, final String productCategory, final FragmentAddPurchaseDbBinding purchaseBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Saving Product", false);
            }
        });
        //String remoteurl = getString(R.string.base_url) + loginUrl;
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("purchaseDate", purchaseModel.getPurchase_date());
            postdata.put("purchaseQuantity", purchaseModel.purchase_product_quantity);
            postdata.put("purchaseProductPrice", Integer.parseInt(purchaseModel.purchase_product_price));
            postdata.put("purchaseAmount", Integer.parseInt(purchaseModel.purchase_amount));
            postdata.put("purchasePayment", Integer.parseInt(purchaseModel.purchase_payment));
            postdata.put("purchaseBalance", Integer.parseInt(purchaseModel.purchase_balance));
            postdata.put("purchaseDescription", purchaseModel.getPurchase_description());
            postdata.put("createdBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));
            postdata.put("productid", Integer.parseInt(purchaseModel.product_id));
            postdata.put("productVariation", Integer.parseInt(purchaseModel.product_variation_id));
            postdata.put("suppplierid", Integer.parseInt(purchaseModel.supplier_id));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        purchaseModel.setId(parseId);
                                        Long purchaseId = purchase.CreateNewPurchase(purchaseModel);

                                        if (purchaseId > 0) {

                                            Log.e("Stock", "Exists");
                                            StockDatabaseModel stockDatabaseModel;

                                            stockDatabaseModel = new StockDatabaseModel();
                                            stockDatabaseModel.setProductId(purchaseModel.getProduct_id());
                                            stockDatabaseModel.setProductQuantity(String.valueOf(purchaseModel.getPurchase_product_quantity()));

                                            try {
                                                updateProductStock(baseUrl, stock, productVariation, stockDatabaseModel, purchaseModel, purchaseBinding);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }


                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateProductStock(final String baseUrl, final Stock stock, final ProductVariation productVariation, final StockDatabaseModel stockDatabaseModel, final PurchaseModel purchaseModel, final FragmentAddPurchaseDbBinding purchaseBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Updating Product Stock", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/update/stock";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {


            if (stock.ifStockExists(purchaseModel.getProduct_id())) {
                postdata.put("totalstock", Integer.parseInt(stockDatabaseModel.getProductQuantity()));
                postdata.put("productid", Integer.parseInt(stockDatabaseModel.getProductId()));
                postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

            } else {
                Log.e("Stock", "Not Exists");
                postdata.put("totalstock", Integer.parseInt(stockDatabaseModel.getProductQuantity()));
                postdata.put("productfor", stockDatabaseModel.getProductFor());
                postdata.put("productid", Integer.parseInt(stockDatabaseModel.getProductId()));
                postdata.put("productCategory", Integer.parseInt(stockDatabaseModel.getProductFor()));
                postdata.put("createdBy", UserSession.getEmployeeId());
                postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();
                            final String status = JsonResponse.get("exists").getAsString();
                            final String newStock = JsonResponse.get("newStock").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Boolean save = false;
                                        if (status.equalsIgnoreCase("1")) {
                                            save = stock.updateStock(stockDatabaseModel.getProductId(), newStock);
                                        } else {
                                            save = stock.storeStock(new StockDatabaseModel("" + purchaseModel.getProduct_id(), stockDatabaseModel.getProductFor(), purchaseModel.purchase_product_quantity, UserSession.getEmployeeId()));
                                        }

                                        if (save) {
                                            try {
                                                updateProductVariation(baseUrl, purchaseModel, productVariation, stockDatabaseModel, purchaseBinding);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            new DialogUIActivity(mActivity).showDialog("Add Purchase", "Purchase failed to be saved");
                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateProductVariation(String baseUrl, final PurchaseModel purchaseModel, final ProductVariation productVariation, final StockDatabaseModel stockDatabaseModel, final FragmentAddPurchaseDbBinding purchaseBinding) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Updating Product Variation Stock", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/update/productvariation";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (Map.Entry entry : HoldStaticVariationData.getVariationData().entrySet()) {
                Log.e(entry.getKey().toString(), entry.getValue().toString());

                if (count == 0) {
                    sb.append(entry.getValue().toString());
                } else {
                    sb.append("," + entry.getValue().toString());
                }
                count++;
            }

            postdata.put("variationid", Integer.parseInt(purchaseModel.getProduct_variation_id()));
            postdata.put("productid", Integer.parseInt(purchaseModel.getProduct_id()));
            postdata.put("variationStock", Integer.parseInt(stockDatabaseModel.getProductQuantity()));
            postdata.put("dateUpdate", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();
                            final String status = JsonResponse.get("exists").getAsString();
                            final String newStock = JsonResponse.get("newStock").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        Boolean save_pv_stock = productVariation.updatePvStock(purchaseModel.getProduct_variation_id(), purchaseModel.getProduct_id(), newStock);

                                        if (save_pv_stock) {
                                            Log.e("PVStock", "Updated Successfully");
                                            new DialogUIActivity(mActivity).showDialog("Add Purchase", "Purchase has been saved successfully");
                                            clearForm((ViewGroup) purchaseBinding.addPurchaseLayout);
                                        } else {
                                            Log.e("PVStock", "Failed to updated");
                                            new DialogUIActivity(mActivity).showDialog("Add Purchase", "Purchase failed to be saved");
                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Purchase", "Purchase failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }




    /*Sells Updates*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateSellsProductStock(final String baseUrl, final Stock stock, final ProductVariation productVariation, final ArrayList<ProductListModel> products, final CustomerModel selectedCustomer, final Customer customerDatabase, final PrintSellInfo printInfo, final int paymentStatus, final CustomerDue customerDue, final SellsInfo sellsInfo, final SoldProductInfo soldProductInfo) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Updating Product Sells", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/update/productsells";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();
        final Gson gson = new Gson();
        try {
            Log.e("Sellls", "Update Product Sellls");


            postdata.put("createdBy", UserSession.getEmployeeId());


            String sellproductslist = gson.toJson(products);
            postdata.put("soldproducts", sellproductslist);
            postdata.put("customer", gson.toJson(selectedCustomer));
            postdata.put("printinfo", gson.toJson(printInfo));
            postdata.put("paymentstatus", paymentStatus);
            postdata.put("updatedBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();

                        int maxLogSize = 1000;
                        for (int i = 0; i <= mMessage.length() / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i + 1) * maxLogSize;
                            end = end > mMessage.length() ? mMessage.length() : end;
                            Log.e("InventoryManager", mMessage.substring(start, end));
                        }


                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            final JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();
                            //final String totalAvailableStock = JsonResponse.get("totalavailablestock").getAsString();
                            //final String newVariationStock = JsonResponse.get("variationstock").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] stockUpdateResponse = JsonResponse.get("stockUpdate").getAsString().split("=");
                                        for (String stockupdate : stockUpdateResponse) {
                                            JsonObject stockUpdateObject = new Gson().fromJson(stockupdate, JsonObject.class);

                                            Log.e("NewStock", stockUpdateObject.get("newStock").getAsString());
                                            Log.e("id", stockUpdateObject.get("id").getAsString());
                                            Log.e("ProductId", stockUpdateObject.get("productId").getAsString());

                                            stock.updateStock(stockUpdateObject.get("productId").getAsString(), stockUpdateObject.get("newStock").getAsString());

                                        }


                                        String[] variationUpdateResponse = JsonResponse.get("variationUpdate").getAsString().split("=");
                                        for (String variationupdate : variationUpdateResponse) {
                                            JsonObject variationUpdateObject = new Gson().fromJson(variationupdate, JsonObject.class);

                                            Log.e("Variation-NewStock", variationUpdateObject.get("newStock").getAsString());
                                            Log.e("Variation-id", variationUpdateObject.get("id").getAsString());
                                            Log.e("ProductId", variationUpdateObject.get("productId").getAsString());

                                            Boolean save = productVariation.updatePvStock(variationUpdateObject.get("id").getAsString(), variationUpdateObject.get("productId").getAsString(), variationUpdateObject.get("newStock").getAsString());

                                            if (save) {
                                                Log.e("ProductVariation", "Variation Saved");
                                            } else {
                                                Log.e("ProductVariation", "Variation Not Saved");
                                            }

                                        }

                                        String customerUpdateResponse = JsonResponse.get("customerUpdate").getAsString();

                                        JsonObject customerUpdateObject = new Gson().fromJson(customerUpdateResponse, JsonObject.class);

                                        Log.e("Customer-dueAmount", customerUpdateObject.get("dueAmount").getAsString());
                                        Log.e("Customer-id", customerUpdateObject.get("id").getAsString());
                                        Log.e("dueInfoId-id", customerUpdateObject.get("dueInfo").getAsString());
                                        Log.e("due-Date", customerUpdateObject.get("dueDate").getAsString());

                                        customerDatabase.updateCustomerDueAmount(customerUpdateObject.get("id").getAsString(), customerUpdateObject.get("dueAmount").getAsString());

                                        if (!customerUpdateObject.get("dueInfo").getAsString().equalsIgnoreCase("-1")) {
                                            boolean status = customerDue.storeSellsDetails(new CustomerDuelDatabaseModel(
                                                    Integer.parseInt(customerUpdateObject.get("dueInfo").getAsString()),
                                                    customerUpdateObject.get("id").getAsString(),
                                                    printInfo.getTotalAmountTv(),
                                                    printInfo.getPayableTv(),
                                                    printInfo.getCurrentDueTv(),
                                                    printInfo.getInvoiceTv(),
                                                    customerUpdateObject.get("dueDate").getAsString(),
                                                    printInfo.getDepositTv()
                                            ));

                                            if (status) {
                                                Log.e("Customer Due", "Due Saved Successfully");
                                            }
                                        }

                                        //Updating Sells

                                        String[] productSellsUpdateResponse = JsonResponse.get("productSellUpdate").getAsString().split("=");

                                        for(String pSellResponse: productSellsUpdateResponse){

                                            JsonObject productSellsUpdateObject = new Gson().fromJson(pSellResponse, JsonObject.class);

                                            String productSells = productSellsUpdateObject.get("productSells").getAsString();

                                            JsonObject productSellsObject = new Gson().fromJson(productSells, JsonObject.class);

                                            Log.e("Sells-Id", productSellsObject.get("sellsid").getAsString());
                                            Log.e("SellsCode", productSellsObject.get("sellsCode").getAsString());
                                            Log.e("totalAmount", productSellsObject.get("totalAmount").getAsString());

                                            String sellsCustomerId = new Gson().fromJson(productSellsObject.get("customerid").toString(), JsonObject.class).get("customerid").getAsString();

                                            sellsInfo.storeSellDetails(new SellsDatabaseModel(
                                                    productSellsObject.get("sellsCode").getAsString(),
                                                    sellsCustomerId,
                                                    productSellsObject.get("totalAmount").getAsString(),
                                                    productSellsObject.get("discount").getAsString(),
                                                    productSellsObject.get("payAmount").getAsString(),
                                                    productSellsObject.get("paymentType").getAsString(),
                                                    productSellsObject.get("sellsDate").getAsString(),
                                                    productSellsObject.get("paymentStatus").getAsString(),
                                                    productSellsObject.get("sellBy").getAsString()
                                            ));

                                        }


                                        String[] productSellsInfoUpdateResponse = JsonResponse.get("productSellInfoUpdate").getAsString().split("=");

                                        for(String pSellInfo: productSellsInfoUpdateResponse){

                                            JsonObject productSellsInfoUpdateObject = new Gson().fromJson(pSellInfo, JsonObject.class);

                                            String productSellsInfo = productSellsInfoUpdateObject.get("sellsProductInfo").getAsString();

                                            JsonObject productSellsInfoObject = new Gson().fromJson(productSellsInfo, JsonObject.class);

                                            Log.e("Sells-Info-Id", productSellsInfoObject.get("sellproductinfoid").toString());
                                            Log.e("productPrice", productSellsInfoObject.get("productPrice").getAsString());
                                            Log.e("quantity", productSellsInfoObject.get("quantity").toString());
                                            Log.e("totalPrice", productSellsInfoObject.get("totalPrice").toString());
                                            Log.e("pendingStatus", productSellsInfoObject.get("pendingStatus").toString());

                                            String productId = new Gson().fromJson(productSellsInfoObject.get("productid").toString(), JsonObject.class).get("productid").getAsString();
                                            String variationId = new Gson().fromJson(productSellsInfoObject.get("productVariation").toString(), JsonObject.class).get("variationid").getAsString();
                                            String sellsId = new Gson().fromJson(productSellsInfoObject.get("sellsid").toString(), JsonObject.class).get("sellsid").getAsString();
                                            Log.e("ProductId", productId);
                                            Log.e("variationId", variationId);
                                            Log.e("sellsId", sellsId);


                                            soldProductInfo.storeSoldProductInfo(new SoldProductModel(
                                                    Integer.parseInt(productSellsInfoObject.get("sellproductinfoid").toString()),
                                                    printInfo.getInvoiceTv(),
                                                    sellsId,
                                                    productId,
                                                    variationId,
                                                    productSellsInfoObject.get("productPrice").toString(),
                                                    productSellsInfoObject.get("quantity").toString(),
                                                    productSellsInfoObject.get("totalPrice").toString(),
                                                    productSellsInfoObject.get("pendingStatus").toString()
                                            ));


                                        }


                                        new DialogUIActivity(mActivity).showDialogAndQuick("Items(s) Sold Successfully", "Items Sold Successfully",gson.toJson(printInfo));
                                    }
                                });

                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Sell Product", "Item(s) sell failed");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Sell Product", "Item sell failed");
                                }
                            });
                        }

                    } else {
                        dialogHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                new DialogUIActivity(mActivity).showDialog("Sell Product", "Item sell failed");
                            }
                        });
                    }

                } else {
                    formatFailureResponse();
                }
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateSellsProductVariation(String baseUrl, final PurchaseModel purchaseModel, final ProductVariation productVariation, final StockDatabaseModel stockDatabaseModel) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Updating Product Variation Stock", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/update/productvariation";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (Map.Entry entry : HoldStaticVariationData.getVariationData().entrySet()) {
                Log.e(entry.getKey().toString(), entry.getValue().toString());

                if (count == 0) {
                    sb.append(entry.getValue().toString());
                } else {
                    sb.append("," + entry.getValue().toString());
                }
                count++;
            }

            postdata.put("variationid", Integer.parseInt(purchaseModel.getProduct_variation_id()));
            postdata.put("productid", Integer.parseInt(purchaseModel.getProduct_id()));
            postdata.put("variationStock", Integer.parseInt(stockDatabaseModel.getProductQuantity()));
            postdata.put("updatedBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String id = JsonResponse.get("id").getAsString();
                            final String status = JsonResponse.get("exists").getAsString();
                            final String newStock = JsonResponse.get("newStock").getAsString();

                            final Integer parseId = Integer.parseInt(id);
                            if (parseId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        Boolean save_pv_stock = productVariation.updatePvStock(purchaseModel.getProduct_variation_id(), purchaseModel.getProduct_id(), newStock);

                                        if (save_pv_stock) {
                                            Log.e("PVStock", "Updated Successfully");
                                            new DialogUIActivity(mActivity).showDialog("Add Purchase", "Stock successfully Updated");
                                        } else {
                                            Log.e("PVStock", "Failed to updated");
                                            new DialogUIActivity(mActivity).showDialog("Add Purchase", "Stock Update Failed");
                                        }

                                    }
                                });
                            } else {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new DialogUIActivity(mActivity).showDialog("Add Purchase", "Purchase failed to be saved");
                                    }
                                });
                            }

                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Add Product", "Item failed to be saved");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateSellsDueInfo(String baseUrl, final FragmentDueDetailsBinding binding, final DueDetailsModel dueDetails, final SellsInfo sellsInfo, final CustomerDue customerDue, final Customer customerDatabase, final String date, final int newPaid, final String paidWith) throws IOException {

        dialogHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog("Updating Due Info ...", false);
            }
        });
        String remoteurl = baseUrl + "/inventory/update/sellsdueinfo";
        Log.d("RemoteKeyUrl", remoteurl);
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("interswitchug.com", sslSession);
                        /*if(s.equals("interswitch.io")){
                            return true;
                        }else{
                            return false;
                        }*/


                    }
                })
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();

        try {

            postdata.put("dueid", dueDetails.getDueid());
            postdata.put("amountpaid", newPaid);
            postdata.put("customerid",dueDetails.getCustomerCode());
            postdata.put("sellsCode", dueDetails.getSellCode());
            postdata.put("paymentType", paidWith);
            postdata.put("updatedBy", UserSession.getEmployeeId());
            postdata.put("institutionid", Integer.parseInt(CurrentLogginSession.getInstitutionId()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RequestJson", postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(remoteurl)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                formatFailureResponse();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("NetworkResponse", response.toString());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        String mMessage = response.body().string();
                        Log.e("PostilionResponse", mMessage);

                        progressDialog.dismiss();

                        if (mMessage.length() != 0) {
                            final JsonObject JsonResponse = new Gson().fromJson(mMessage, JsonObject.class);
                            final String sellId = JsonResponse.get("sellId").getAsString();
                            final String dueId = JsonResponse.get("dueId").getAsString();
                            final String customerId = JsonResponse.get("customerId").getAsString();

                            final Integer parseSellId = Integer.parseInt(sellId);
                            final Integer parseDueId = Integer.parseInt(dueId);
                            final Integer parseCustomerId = Integer.parseInt(customerId);
                            final int[] dueAmount = {0};

                            if (parseDueId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        dueAmount[0] = Integer.parseInt(JsonResponse.get("dueAmount").getAsString());
                                        int dueDepositAmout = Integer.parseInt(JsonResponse.get("dueDepositAmout").getAsString());

                                        binding.newPayDateTv.setText(date);
                                        binding.newPaidTv.setText(String.valueOf(newPaid));

                                        NumberFormat formatBal = new DecimalFormat("#,###");;
                                        formatBal.setMaximumFractionDigits(0);

                                        binding.presentDueTv.setText(formatBal.format(Double.parseDouble(String.valueOf(dueAmount[0]))));

                                        if (dueAmount[0] == 0){
                                            customerDue.deleteDue(String.valueOf(parseDueId));
                                        }else {
                                            customerDue.updateDueDetails(String.valueOf(dueAmount[0]),String.valueOf(dueDepositAmout),String.valueOf(parseDueId));
                                        }

                                    }
                                });
                            }
                            if (parseSellId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        int sellTotalPayment = Integer.parseInt(JsonResponse.get("sellTotalPayment").getAsString());
                                        NumberFormat formatBal = new DecimalFormat("#,###");;
                                        formatBal.setMaximumFractionDigits(0);

                                        boolean status;
                                        if (dueAmount[0] == 0){
                                            status = sellsInfo.updateSellDetails(dueDetails.getSellCode(),String.valueOf(sellTotalPayment),paidWith,"0");
                                        }else {
                                            status = sellsInfo.updateSellDetails(dueDetails.getSellCode(),String.valueOf(sellTotalPayment),paidWith,"1");
                                        }

                                        if (status){
                                            Log.d(DueListFragment.TAG, "updateDueDatabase: success");
                                        }else {
                                            Log.d(DueListFragment.TAG, "updateDueDatabase: faield");

                                        }
                                    }
                                });
                            }
                            if (parseCustomerId > 0) {
                                dialogHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int customerDueAmount = Integer.parseInt(JsonResponse.get("customerDueAmount").getAsString());
                                        customerDatabase.updateCustomerDueAmount(dueDetails.getCustomerCode(), String.valueOf(customerDueAmount));

                                    }
                                });
                            }



                        } else {
                            dialogHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new DialogUIActivity(mActivity).showDialog("Updating Due Info", "Failed to update due info");
                                }
                            });
                        }


                    } else {
                        formatFailureResponse();
                    }
                } else {
                    response.close();
                    formatFailureResponse();
                }
            }
        });

    }


    private void progressDialog(String msg, boolean cancelable) {

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }


    private void respDialog(String title, String msg, final String originalmsg, final boolean isSuccessful, boolean cancelable) {
        new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(cancelable)
                .show();
    }


    public void formatFailureResponse() {
        progressDialog.dismiss();
        final String msg = "Posting transaction failed\nReason: Bad or No network";
        this.dialogHandler.post(new Runnable() {
            public void run() {
                //Instanitiating Alert Dialog
                respDialog("Transaction response", msg, null, false, true);
            }
        });
    }


    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }


}
