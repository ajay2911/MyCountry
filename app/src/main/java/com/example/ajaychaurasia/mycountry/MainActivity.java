package com.example.ajaychaurasia.mycountry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ajaychaurasia.mycountry.pojo.JSONResponseData;
import com.example.ajaychaurasia.mycountry.restinterface.DropboxAPI;
import com.example.ajaychaurasia.mycountry.ui.ListViewFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ListViewFragment.UpdateData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding the ListView Fragment to MainActivity
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new ListViewFragment(), "ListFragment")
                    .commit();
        }
    }

    @Override
    public void updateListData() {
        fetchListData();
    }

    /*
    * Method to trigger REST Api call and receive JSON Data
    * Received data is passed to Adapter for UI rendering
    * */
    private void fetchListData() {
        final Call<JSONResponseData> responseData = DropboxAPI.getService(MainActivity.this).getFactsData();
        final ListViewFragment listViewFragment = (ListViewFragment) getSupportFragmentManager().findFragmentByTag("ListFragment");

        responseData.enqueue(new Callback<JSONResponseData>() {
            @Override
            public void onResponse(Call<JSONResponseData> call, Response<JSONResponseData> response) {
                JSONResponseData restResponse = response.body();
                if (null != restResponse.getTitle()) {
                    getSupportActionBar().setTitle(restResponse.getTitle());
                }
                listViewFragment.updateViewWithResponse(restResponse);
            }

            @Override
            public void onFailure(Call<JSONResponseData> call, Throwable t) {
                listViewFragment.updateViewWithError();
            }
        });
    }
}
