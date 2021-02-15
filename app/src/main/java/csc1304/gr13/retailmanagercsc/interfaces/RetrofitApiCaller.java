package csc1304.gr13.retailmanagercsc.interfaces;

import com.google.gson.JsonObject;

import csc1304.gr13.retailmanagercsc.modelClass.CustomerOnlineDataModel;
import csc1304.gr13.retailmanagercsc.modelClass.RetrofitResponse;
import csc1304.gr13.retailmanagercsc.modelClass.StockOnlineModel;
import csc1304.gr13.retailmanagercsc.modelClass.UserLogin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by CS1304 on 8/02/2021.
 */

public interface RetrofitApiCaller {

    @FormUrlEncoded
    @POST("posapi/public/access")
    Call<UserLogin> userLogin(@Field("username") String userName, @Field("password") String password);

    @GET()
    Call<ArrayList<StockOnlineModel>> getStocks(@Url String url);

    @GET()
    Call<CustomerOnlineDataModel> getCustomersInfo(@Url String url);

    @POST()
    Call<RetrofitResponse> sendData(@Body JsonObject data, @Url String url);

}
