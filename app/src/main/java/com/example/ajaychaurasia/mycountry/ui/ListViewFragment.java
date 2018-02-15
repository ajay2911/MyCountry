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
                        updateDataCallback.updateListData();
                    }
                }
        );

        //This saves Fragment instance while configuration change
        setRetainInstance(true);

        //Reuse JSONResponseData if its already present before orientation config change
        if(getJsonResponseData()!=null){
            updateViewWithResponse(jsonResponseData);
        } else {
            updateViewWithError();
        }
        return view;
    }

    /*
    * When response is received this method updates UI with Row Data
    * */
    public void updateViewWithResponse(JSONResponseData jsonResponseData) {
        updateDataCallback.updateTitle(jsonResponseData.getTitle());
        RowData[] rowDataArray = jsonResponseData.getRows();
        if (null != rowDataArray) {
            recyclerList.setAdapter(new ListDataAdapter(getContext(), rowDataArray));
            recyclerList.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
            //To save the JSONResponseData for reuse in Orientation change
            setJsonResponseData(jsonResponseData);
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
    public void updateViewWithError() {
        recyclerList.setVisibility(View.GONE);
        errorMessageText.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        setJsonResponseData(null);
        updateDataCallback.updateTitle(null);
    }

    public void setJsonResponseData(JSONResponseData jsonResponseData) {
        this.jsonResponseData = jsonResponseData;
    }

    public JSONResponseData getJsonResponseData() {
        return jsonResponseData;
    }

    private JSONResponseData jsonResponseData;

    // Interface to support UICallback and interaction
    public interface UpdateData {
        void updateListData();
        void updateTitle(String title);
    }
}
