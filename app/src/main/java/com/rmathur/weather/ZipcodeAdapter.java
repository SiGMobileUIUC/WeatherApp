package com.rmathur.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rohanmathur on 11/13/16.
 */

public class ZipcodeAdapter extends BaseAdapter {

    private List<Location> locations;
    private LayoutInflater inflater;
    private Context context;

    public ZipcodeAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.locations = locationList;
    }

    @Override
    public int getCount() {
        if(locations != null) {
            return locations.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_zip_row, null);
        }

        TextView zipCode = (TextView) convertView.findViewById(R.id.zip_code);
        zipCode.setText(locations.get(position).getZipCode());
        return convertView;
    }
}
