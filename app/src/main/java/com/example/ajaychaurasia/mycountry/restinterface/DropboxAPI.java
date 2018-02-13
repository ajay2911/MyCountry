package com.example.ajaychaurasia.mycountry.restinterface;

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

    public static final String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/555/";

    public static DataService dataService = null;

    //Creating static retrofit builder object
    public static DataService getService(){
        if(dataService==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
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
