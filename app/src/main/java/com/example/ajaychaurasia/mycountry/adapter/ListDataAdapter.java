package com.example.ajaychaurasia.mycountry.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajaychaurasia.mycountry.R;
import com.example.ajaychaurasia.mycountry.pojo.RowData;
import com.squareup.picasso.Picasso;

/**
 * Adapter to render data on RecyclerView
 */

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ListDataViewHolder> {

    private final Context context;
    private final RowData rowData[];

    public ListDataAdapter(Context context, RowData[] rowData) {
        this.context = context;
        this.rowData = rowData;
    }

    @Override
    public ListDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ListDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListDataViewHolder holder, int position) {
        RowData singleRowData = rowData[position];
        holder.itemTitle.setText(singleRowData.getTitle());
        holder.description.setText(singleRowData.getDescription());

        //This is Picasso Builder which renders images from URL and if it fails the error is displayed as logs
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context)
                .load(singleRowData.getImageHref())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imageContainer);
    }

    @Override
    public int getItemCount() {
        return rowData.length;
    }

    /*
    * Inner ViewHolder class to hold data for each item in RecyclerView
    * */
    public class ListDataViewHolder extends RecyclerView.ViewHolder {

        final TextView itemTitle, description;
        final ImageView imageContainer;

        public ListDataViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageContainer = itemView.findViewById(R.id.imageContainer);
        }
    }
}
