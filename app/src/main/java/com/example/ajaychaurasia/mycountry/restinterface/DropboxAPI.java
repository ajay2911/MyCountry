package com.example.ajaychaurasia.mycountry.restinterface;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.ajaychaurasia.mycountry.MainActivity;
import com.example.ajaychaurasia.mycountry.R;
import com.example.ajaychaurasia.mycountry.pojo.JSONResponseData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Ajay Chaurasia on 13-Feb-18.
 * Used to create and send REST services
 */

public class DropboxAPI {

    public static DataService dataService = null;

    //Creating static retrofit builder object
    public static DataService getService(Context context){
        if(dataService==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.dropboxURL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            dataService = retrofit.create(DataService.class);
        }
        return dataService;
    }

    /*
    * Interface for HTTP API
    * */
    public interface DataService {
        @GET("facts.json")
        Call<JSONResponseData> getFactsData();
    }
}
