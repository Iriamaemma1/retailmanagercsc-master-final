package csc1304.gr13.retailmanagercsc.activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestToPay extends gettoken {

    private static gettoken gt;
    public static String uniqueID = UUID.randomUUID().toString();

    public static String rtp() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"amount\": \"5.0\",\n  \"currency\": \"EUR\",\n  \"externalId\": \"45678910\",\n  \"payer\": {\n    \"partyIdType\": \"MSISDN\",\n    \"partyId\": \"0773455643\"\n  },\n  \"payerMessage\": \"Send it\",\n  \"payeeNote\": \"Just like that\"\n}");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + gt.thetoken())
                .addHeader("Ocp-Apim-Subscription-Key", "35c428654c1e4922bed2a7d828030c64")
                .addHeader("X-Reference-Id", uniqueID)
                .addHeader("X-Target-Environment", "sandbox")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String resp2 = response.body().string(); //get response body


        JSONObject object = new JSONObject(resp2); //creating a response to a JSON object
        //String token = object.getString("access_token");
        //String token_type = object.getString("token_type");
        //String expires_in = object.getString("expires_in");

        String status2 = String.valueOf(response.code());
        //System.out.println("-------Token is--------" +token+ "--------");
        //System.out.println("--------Token type:--------" +token_type+ "--------");
        //System.out.println("--------Expires in:--------" +expires_in+ "--------");
        System.out.println("--------Status 2:--------" +status2+ "--------");
        System.out.println("--------UUID:--------" +uniqueID+ "--------");

        return status2;
    }

}
