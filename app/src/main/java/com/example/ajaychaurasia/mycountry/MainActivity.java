package com.example.ajaychaurasia.mycountry;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajaychaurasia.mycountry.adapter.ListDataAdapter;
import com.example.ajaychaurasia.mycountry.pojo.JSONResponseData;
import com.example.ajaychaurasia.mycountry.restinterface.DropboxAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateData();
                    }
                }
        );

        fetchListData();
    }

    private void updateData(){
        fetchListData();
        swipeRefreshLayout.setRefreshing(false);
    }

    /*
    * Method to trigger REST Api call and receive JSON Data
    * Recieved data is passed to Adapter for UI rendering
    * */
    private void fetchListData() {
        final Call<JSONResponseData> responseData = DropboxAPI.getService().getFactsData();
        responseData.enqueue(new Callback<JSONResponseData>() {
            @Override
            public void onResponse(Call<JSONResponseData> call, Response<JSONResponseData> response) {
                JSONResponseData restResponse = response.body();
                recyclerList.setAdapter(new ListDataAdapter(MainActivity.this,restResponse.getRows()));
                Log.d(TAG+".fetchListData()","Response received with title as: "+restResponse.getTitle());
            }

            @Override
            public void onFailure(Call<JSONResponseData> call, Throwable t) {
                recyclerList.setVisibility(View.GONE);
                Log.d(TAG+".fetchListData()","Error occurred: "+t.getMessage());
            }
        });
    }
}
