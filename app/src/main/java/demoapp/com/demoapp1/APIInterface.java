package demoapp.com.demoapp1;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


interface APIInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<JsonObject> doGetUserList();


}
