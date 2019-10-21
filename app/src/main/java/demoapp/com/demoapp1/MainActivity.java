package demoapp.com.demoapp1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    
    @BindView(R.id.activity_main_recyclerview)
    RecyclerView mRecyclerView;

    InfoRecyclerViewAdapter infoRecyclerViewAdapter;
    APIInterface apiInterface;
    ArrayList<InfoModel> infoData;
    PrefManager pref;
    Context ctx;
    String infoDataStr;
    String appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ctx = this;
        
        apiInterface = APIClient.getClient().create(APIInterface.class);
        infoData = new ArrayList<>();
        pref = new PrefManager(this);
        appTitle = pref.getTitle();
        if(appTitle != null){
            setTitle(appTitle);
        }

        infoDataStr = pref.getArrayInfor();

        if(infoDataStr != null){
            JSONArray recharegJson = null;
            try {
                recharegJson = new JSONArray(infoDataStr);
                for (int i = 0; i < recharegJson.length(); i++) {

                    InfoModel userModel = new InfoModel();
                    JSONObject dataobj = recharegJson.getJSONObject(i);

                    userModel.setTitle(dataobj.getString("title"));
                    userModel.setDescription(dataobj.getString("description"));
                    userModel.setImageHref(dataobj.getString("imageHref"));
                    infoData.add(userModel);
                }

                setupAdapter(infoData);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else {
            if(isNetworkAvailable(ctx)){
                getData();
            }else{
                Toast.makeText(ctx, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }

        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(isNetworkAvailable(ctx)){
                            getData();
                        }else{
                            if(infoDataStr != null){
                                JSONArray recharegJson = null;
                                try {
                                    recharegJson = new JSONArray(infoDataStr);
                                    for (int i = 0; i < recharegJson.length(); i++) {

                                        InfoModel userModel = new InfoModel();
                                        JSONObject dataobj = recharegJson.getJSONObject(i);

                                        userModel.setTitle(dataobj.getString("title"));
                                        userModel.setDescription(dataobj.getString("description"));
                                        userModel.setImageHref(dataobj.getString("imageHref"));
                                        infoData.add(userModel);
                                    }

                                    setupAdapter(infoData);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
    }

    private void getData(){
        Call<JsonObject> call = apiInterface.doGetUserList();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            JsonObject responseTitle = response.body();
                            String title = responseTitle.get("title").toString();
                            appTitle = title.replace("\"", "");
                            pref.setTitle(appTitle);
                            setTitle(appTitle);

                            String data = responseTitle.get("rows").toString();
                            JSONArray dataArray = new JSONArray(data);
                            ArrayList<InfoModel> infoDataList = new ArrayList<>();

                            for (int i = 0; i < dataArray.length(); i++) {

                                InfoModel userModel = new InfoModel();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                userModel.setTitle(dataobj.getString("title"));
                                userModel.setDescription(dataobj.getString("description"));
                                userModel.setImageHref(dataobj.getString("imageHref"));
                                infoDataList.add(userModel);
                            }

                            pref.setArrayInfor(dataArray.toString());
                            setupAdapter(infoDataList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void setupAdapter(ArrayList<InfoModel> userList) {
        infoRecyclerViewAdapter = new InfoRecyclerViewAdapter(this,userList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(infoRecyclerViewAdapter);
    }

    // to check internet connection
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null
                && activeNetworkInfo.isConnectedOrConnecting();
    }


}
