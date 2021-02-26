package csc1304.gr13.retailmanagercsc.activity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckRequestToPay extends RequestToPay {
    gettoken gt;
    RequestToPay rtp;

    public static Response checkPayment() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"amount\": \"5.0\",\n  \"currency\": \"EUR\",\n  \"externalId\": \"45678910\",\n  \"payer\": {\n    \"partyIdType\": \"MSISDN\",\n    \"partyId\": \"0773455643\"\n  },\n  \"payerMessage\": \"Send it\",\n  \"payeeNote\": \"Just like that\"\n}");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay")
                .method("POST", body)
                .addHeader("X-Reference-Id", "a0959b07-dc4a-41ff-9dc7-82d7f775ff72")
                .addHeader("X-Target-Environment", "sandbox")
                .addHeader("Ocp-Apim-Subscription-Key", "35c428654c1e4922bed2a7d828030c64")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjczOWRhOTgxLTY2NTktNGZhMi05ZjcwLWZhNDJjYTU4MGM4NSIsImV4cGlyZXMiOiIyMDIxLTAyLTIzVDE2OjA5OjIzLjIzMCIsInNlc3Npb25JZCI6IjViOGFlMWZkLTYzNzItNDdjZi04Y2IwLTgxNDUyY2RmOWZkZSJ9.WBgS6uEauQGNjqZcFBLUvDSQVi-id5zsrjmEAeZCgdodqS-LWkmhXOYqTD-UQYjKDoiMGQXcpzBndH11lQnz_bF0bytoNk8uOU1kmyHtRl12UVZiWQ53GiZlI_T_l2y1dr-Bzn-kT-L6pgSGkR4940I0popHSHZF_kMogQv3XKb6ws6Utixs2aFOn7RNXSsVVkMv5f0or0ccb0GMwfuc1H5PiseDpqrxaa434VrK8msbQrigCMFmVQVeufVcAKA9bs02Mt7RyuqEVUhHp72gak0Vxx5KnaWPW04koydS_Mddfy2EqBk9ZsHyzkfpJE5C1Ow3i0cG-Ng2OPX-KzBwWQ")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
