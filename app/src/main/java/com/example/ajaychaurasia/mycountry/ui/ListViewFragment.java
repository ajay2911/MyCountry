package com.example.ajaychaurasia.mycountry.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajaychaurasia.mycountry.MainActivity;
import com.example.ajaychaurasia.mycountry.R;
import com.example.ajaychaurasia.mycountry.adapter.ListDataAdapter;
import com.example.ajaychaurasia.mycountry.pojo.JSONResponseData;
import com.example.ajaychaurasia.mycountry.pojo.RowData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewFragment extends Fragment {

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.error_message)
    TextView errorMessageText;

    private UpdateData updateDataCallback;

    private ListDataAdapter listDataAdapter;

    //Method to initiate ListDataAdapter only once
    private ListDataAdapter getListDataAdapter() {
        if (null == listDataAdapter) {
            listDataAdapter = new ListDataAdapter(getContext(), getJsonResponseData().getRows());
        }
        return listDataAdapter;
    }

    //Method to set Adapter
    private void setAdapter(){
        if(null==recyclerList.getAdapter()){
            recyclerList.setAdapter(getListDataAdapter());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  The CallBack object is initiated with MainActivity instance
        updateDataCallback = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_view_frag, container, false);
        ButterKnife.bind(this, view);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateDataCallback.updateCountryData();
                    }
                }
        );

        //This saves Fragment instance while configuration change
        setRetainInstance(true);

        // Reuse JSONResponseData if its already present before orientation config change
        // Else display Error Screen while the data is being loaded (when Fragment is created for first time)
        if(getJsonResponseData()!=null){
            updateViewWithResponseData(jsonResponseData);
        } else {
            updateViewWithErrorScreen();
        }
        return view;
    }

    /*
    * When response is received this method updates UI with Row Data
    * When no rowData is present, error screen is displayed hiding the RecyclerView
    * Also, JSONResponseData is saved to reuse while configuration change
    * */
    public void updateViewWithResponseData(JSONResponseData jsonResponseData) {
        updateDataCallback.updateActionBarTitle(jsonResponseData.getTitle());
        RowData[] rowDataArray = jsonResponseData.getRows();
        if (null != rowDataArray) {
            //To save the JSONResponseData for reuse in Orientation change
            setJsonResponseData(jsonResponseData);
            setAdapter();
            getListDataAdapter().notifyDataSetChanged();
            recyclerList.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
        } else {
            recyclerList.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.VISIBLE);
            errorMessageText.setText(getContext().getResources().getString(R.string.no_row_recieved));
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    /*
    * When REST Call results in error this method updates UI with error screen
    * */
    public void updateViewWithErrorScreen() {
        recyclerList.setVisibility(View.GONE);
        errorMessageText.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        setJsonResponseData(null); //Nullifying old data if any present
        updateDataCallback.updateActionBarTitle(null);   //Resetting ActionBar title to app name
    }

    private void setJsonResponseData(JSONResponseData jsonResponseData) {
        this.jsonResponseData = jsonResponseData;
    }

    private JSONResponseData getJsonResponseData() {
        return jsonResponseData;
    }

    private JSONResponseData jsonResponseData;

    // Interface to support interaction with Activity
    public interface UpdateData {
        void updateCountryData();
        void updateActionBarTitle(String title);
    }
}
