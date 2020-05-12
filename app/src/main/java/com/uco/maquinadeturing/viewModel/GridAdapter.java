package com.uco.maquinadeturing.viewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uco.maquinadeturing.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> cadena;

    public GridAdapter(Context context, ArrayList<String> cadena) {
        this.context = context;
        this.cadena = cadena;
    }

    @Override
    public int getCount() {
        return cadena.size();
    }

    @Override
    public Object getItem(int position) {
        return cadena.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grid, null);
        }
        TextView txtItem = (TextView) convertView.findViewById(R.id.txt_item_grid);
        txtItem.setText(cadena.get(position));
        return convertView;
    }
}
