package com.uco.maquinadeturing.viewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uco.maquinadeturing.R;
import com.uco.maquinadeturing.model.MaquinaTuring;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MaquinaTuringActivity extends AppCompatActivity {

    private GridView gridView;
    private GridAdapter adapter;
    EditText edtCadena;
    ArrayList<String> listaCadena, cadenaAuxiliar = new ArrayList<>(), cadenaVacia = new ArrayList<>();
    ArrayList<String> almacenCadena;
    Button btnMas, btnMenos, btnCalcular, btnBorrar;
    Boolean esPrimerCaracter = true;
    TextView txtResultado, txtTamaño;
    private ListView listPasosCadena;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_maquina_turing);

        edtCadena = (EditText) findViewById(R.id.edt_cadena);

        gridView = (GridView) findViewById(R.id.grid_turing);

        txtResultado = (TextView) findViewById(R.id.txt_resultado);
        txtTamaño = (TextView) findViewById(R.id.txt_tamañoLista);

        btnMas = (Button) findViewById(R.id.btn_mas);
        btnMenos = (Button) findViewById(R.id.btn_menos);
        btnCalcular = (Button) findViewById(R.id.btn_calcular);
        btnBorrar = (Button) findViewById(R.id.btn_borrar);

        listPasosCadena = (ListView) findViewById(R.id.listPasosCadena);


        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirCadenaMas();
                calcularGrid();
                adapter = new GridAdapter(getApplicationContext(), listaCadena);
                gridView.setAdapter(adapter);
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirCadenaMenos();
                calcularGrid();
                adapter = new GridAdapter(getApplicationContext(), listaCadena);
                gridView.setAdapter(adapter);
            }
        });
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerTM();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void añadirCadenaMas() {

        String aux = edtCadena.getText().toString();
        String actual;

        if (esPrimerCaracter) {
            esPrimerCaracter = false;
            actual = aux + "0";
        } else {
            actual = aux + ",0";
        }
        edtCadena.setText(actual);


    }

    private void añadirCadenaMenos() {

        try {
            String aux = edtCadena.getText().toString();
            String actual = aux.substring(0, aux.length() - 2);
            edtCadena.setText(actual);


        } catch (Exception e) {
            edtCadena.setText("");
            esPrimerCaracter = true;
        }

    }

    private void calcularGrid() {
        listaCadena = new ArrayList<>();
        cadenaAuxiliar = new ArrayList<>();
        String[] aux;
        String cadenaACalcular = edtCadena.getText().toString();
        aux = cadenaACalcular.split(",");
        for (String item : aux) {
            listaCadena.add(item);
            cadenaAuxiliar.add(item);
        }

    }

    private void borrar() {

        try {
            edtCadena.setText("");
            esPrimerCaracter = true;
            listaCadena.clear();
            cadenaAuxiliar.clear();
            gridView.removeAllViewsInLayout();
            almacenCadena.clear();
            listPasosCadena.setAdapter(new ArrayAdapter<>(MaquinaTuringActivity.this,
                    android.R.layout.simple_list_item_1, almacenCadena));
            btnCalcular.setEnabled(true);
            btnMas.setEnabled(true);
            btnMenos.setEnabled(true);
            txtTamaño.setText("*");
            txtResultado.setText("*");
        }catch (Exception e){
            Toast.makeText(MaquinaTuringActivity.this, "No hay nada que borrar", Toast.LENGTH_SHORT).show();
        }


    }


    private void hilo() throws InterruptedException {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MaquinaTuringActivity.this, "prueba de tiempo", Toast.LENGTH_SHORT).show();
                adapter = new GridAdapter(getApplicationContext(), listaCadena);
                gridView.setAdapter(adapter);
            }
        }, 3000);
    }

    private void obtenerTM() {
        if (edtCadena.getText().toString().equals("")) {
            edtCadena.setError("Por favor ingrese como mínimo un valor");
            Toast.makeText(MaquinaTuringActivity.this, "Error: valor de cadena vacío, Por favor ingrese como mínimo un valor", Toast.LENGTH_SHORT).show();
        } else {
            edtCadena.setError(null);
            almacenCadena = new ArrayList<>();
            String estadoMaquina = "q1";
            int posActual = 0;
            listaCadena.add("ø");
            String[] cabezal = new String[listaCadena.size() + 1];
            String valor;
            cabezal[posActual] = estadoMaquina;
            boolean direccion = true; // true = derecha, false = izquierda.
            while (!(estadoMaquina.equals("qAccepted") || estadoMaquina.equals("qRejected"))) {
                valor = listaCadena.get(posActual);
                if (estadoMaquina.equals("q1")) {
                    listaCadena.set(posActual, "[" + estadoMaquina + "]" + valor);
                    almacenCadena.add(listaCadena.toString());
                    listaCadena.set(posActual, valor);
                }
                switch (estadoMaquina) {
                    case "q1":

                        if (valor.equals("0")) {
                            direccion = true;
                            valor = "ø";
                            estadoMaquina = "q2";

                        } else if (valor.equals("ø")) {
                            direccion = true;
                            estadoMaquina = "qRejected";
                        }
                        break;
                    case "q2":
                        if (valor.equals("0")) {
                            direccion = true;
                            valor = "x";
                            estadoMaquina = "q3";
                        } else if (valor.toUpperCase().equals("X")) {
                            direccion = true;
                        } else if (valor.equals("ø")) {
                            direccion = true;
                            estadoMaquina = "qAccepted";
                        }
                        break;
                    case "q3":
                        if (valor.equals("0")) {
                            direccion = true;
                            estadoMaquina = "q4";
                        } else if (valor.toUpperCase().equals("X")) {
                            direccion = true;
                        } else if (valor.equals("ø")) {
                            direccion = false;
                            estadoMaquina = "q5";
                        }
                        break;
                    case "q4":
                        if (valor.equals("0")) {
                            direccion = true;
                            valor = "x";
                            estadoMaquina = "q3";
                        } else if (valor.toUpperCase().equals("X")) {
                            direccion = true;
                        } else if (valor.equals("ø")) {
                            direccion = true;
                            estadoMaquina = "qRejected";
                        }
                        break;
                    case "q5":
                        if (valor.equals("0")) {
                            direccion = false;
                        } else if (valor.toUpperCase().equals("X")) {
                            direccion = false;
                        } else if (valor.equals("ø")) {
                            direccion = true;
                            estadoMaquina = "q2";
                        }
                        break;
                }
                listaCadena.set(posActual, "[" + estadoMaquina + "]" + valor);
                almacenCadena.add(listaCadena.toString());
                listaCadena.set(posActual, valor);
                cabezal[posActual] = "";
                listaCadena.set(posActual, valor);
                posActual = MaquinaTuring.movimientoCabezal(posActual, direccion);
                cabezal[posActual] = estadoMaquina;
                //hilo();
                adapter = new GridAdapter(getApplicationContext(), listaCadena);
                gridView.setAdapter(adapter);


            }
            listPasosCadena.setAdapter(new ArrayAdapter<>(MaquinaTuringActivity.this,
                    android.R.layout.simple_list_item_1, almacenCadena));

            txtTamaño.setText("Tamaño de la cadena: " + (listaCadena.size() - 1));

            if (estadoMaquina.equals("qAccepted")) {
                Toast.makeText(MaquinaTuringActivity.this, "La cadena " + cadenaAuxiliar + " Fue aceptada!", Toast.LENGTH_LONG).show();
                txtResultado.setText("Estado: Aceptada!");
            } else if (estadoMaquina.equals("qRejected")) {
                Toast.makeText(MaquinaTuringActivity.this, "La cadena " + cadenaAuxiliar + " Fue rechazada!", Toast.LENGTH_LONG).show();
                txtResultado.setText("Estado: Rechazada!");
            }
            btnCalcular.setEnabled(false);
            btnMas.setEnabled(false);
            btnMenos.setEnabled(false);
        }

    }


}


