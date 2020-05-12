package com.uco.maquinadeturing.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uco.maquinadeturing.R;
import com.uco.maquinadeturing.viewModel.MaquinaTuringActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInciar = (Button) findViewById(R.id.btnIniciar);

        btnInciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MaquinaTuringActivity.class);
                startActivity(intent);
            }
        });


    }

}
