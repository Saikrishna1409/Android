package com.example.employee_crud;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<Pair<String,String>> {
    private Context context;
    private List<Pair<String,String>> departmentList;

    public CustomSpinnerAdapter(Context context,List<Pair<String, String>> departmentList) {
        super(context, android.R.layout.simple_spinner_item, departmentList);
        this.context = context;
        this.departmentList = departmentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createColoredView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createColoredView(position, convertView, parent);
    }

    private View createColoredView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(departmentList.get(position).first);
        textView.setTextColor(Color.parseColor(departmentList.get(position).second));

        return convertView;
    }
}
