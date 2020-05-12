package com.uco.maquinadeturing.viewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uco.maquinadeturing.R;

import org.w3c.dom.Text;

public class ListAdapter extends BaseAdapter {

    public ListAdapter(Context contexto, String[][] datos, int imagen) {
        this.contexto = contexto;
        this.datos = datos;
        this.imagen = imagen;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    private static LayoutInflater inflater = null;

    Context contexto;
    String[][] datos;
    int imagen;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        ImageView imageView = (ImageView) vista.findViewById(R.id.img_cabezal);
        TextView txtValor = (TextView) vista.findViewById(R.id.txt_valor);
        imageView.setTag(position);
        txtValor.setText(datos[position][1]);

        return vista;

    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
