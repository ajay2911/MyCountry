
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

    private ListViewFragment listViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding the ListView Fragment to MainActivity
        //This loads data for the first time on activity creation
        //On Orientation change, fragment's old instance is retained
        if (findViewById(R.id.fragment_container) != null) {
            listViewFragment = (ListViewFragment) getSupportFragmentManager().findFragmentByTag("ListFragment");
            if (listViewFragment == null) {
                initiateFragment();
                fetchListData();
            }
        }
    }

    @Override
    public void updateListData() {
        fetchListData();
    }

    @Override
    public void updateTitle(String title) {
        if (null != title) {
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    // This methods creates a new instance of ListViewFragment
    private void initiateFragment() {
        listViewFragment = new ListViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, listViewFragment, "ListFragment")
                .commit();
    }

    /*
    * Method to trigger REST Api call and receive JSON Data
    * Received data is passed to ListViewFragment for UI rendering
    * */
    private void fetchListData() {
        final Call<JSONResponseData> responseData = DropboxAPI.getService(MainActivity.this).getFactsData();
        responseData.enqueue(new Callback<JSONResponseData>() {
            @Override
            public void onResponse(Call<JSONResponseData> call, Response<JSONResponseData> response) {
                JSONResponseData restResponse = response.body();
                listViewFragment.updateViewWithResponse(restResponse);
            }

            @Override
            public void onFailure(Call<JSONResponseData> call, Throwable t) {
                listViewFragment.updateViewWithError();
            }
        });
    }
}
