package com.example.ajaychaurasia.mycountry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajaychaurasia.mycountry.pojo.JSONResponseData;
import com.example.ajaychaurasia.mycountry.restinterface.DropboxAPI;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchListData();
    }

    private void fetchListData() {

        Call<JSONResponseData> responseData = DropboxAPI.getService().getFactsData();
        responseData.enqueue(new Callback<JSONResponseData>() {
            @Override
            public void onResponse(Call<JSONResponseData> call, Response<JSONResponseData> response) {
                JSONResponseData restResponse = response.body();
                Log.d(TAG+".fetchListData()","Response received with title as: "+restResponse.getTitle());
            }

            @Override
            public void onFailure(Call<JSONResponseData> call, Throwable t) {
                Log.d(TAG+".fetchListData()","Error occurred: "+t.getMessage());
            }
        });
    }
}
