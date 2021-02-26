package csc1304.gr13.retailmanagercsc.activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class gettoken extends MoMoPayment {

    public static String thetoken() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/token/")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", "35c428654c1e4922bed2a7d828030c64")
                .addHeader("Authorization", "Basic NzM5ZGE5ODEtNjY1OS00ZmEyLTlmNzAtZmE0MmNhNTgwYzg1OmRkM2ZiNTE5MmE1MjQ0ZWRiZGE0Y2RiNTkyN2FhZjYz")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String resp = response.body().string(); //get response body


        JSONObject object = new JSONObject(resp); //creating a response to a JSON object
        String token = object.getString("access_token");
        String token_type = object.getString("token_type");
        String expires_in = object.getString("expires_in");

        String status = String.valueOf(response.code());
        System.out.println("-------Token is--------" +token+ "--------");
        System.out.println("--------Token type:--------" +token_type+ "--------");
        System.out.println("--------Expires in:--------" +expires_in+ "--------");
        System.out.println("--------Status:--------" +status+ "--------");

        return token;
    }

}
