package com.example.tycohanx.emsv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.tycohanx.emsv2.R;
import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter {
    private ArrayList<String> catList;
    private Context context;
    private String item;

    static class ViewHolder {
        TextView dropdownTV;

        ViewHolder() {
        }
    }

    public SpinnerAdapter(Context context, ArrayList<String> catList) {
        super(context, R.layout.dropdown_layout, catList);
        this.catList = catList;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.dropdown_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dropdownTV = (TextView) view.findViewById(R.id.dropdownTV);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        this.item = (String) this.catList.get(position);
        viewHolder.dropdownTV.setText(this.item);
        return view;
    }
}
