package com.rmathur.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rohanmathur on 11/6/16.
 */

public class ZipListAdapter extends RecyclerView.Adapter<ZipListAdapter.CustomViewHolder> {
    private List<Location> locationList;
    private Context mContext;

    public ZipListAdapter(Context context, List<Location> newLocationList) {
        this.locationList = newLocationList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_zip_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Location location = locationList.get(i);

        //Setting text view title
        customViewHolder.textView.setText(location.getZipCode());
    }

    @Override
    public int getItemCount() {
        if(locationList == null) {
            return 0;
        } else {
            return locationList.size();
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.zip_code);
        }
    }
}