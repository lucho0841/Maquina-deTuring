package com.uco.maquinadeturing.model;

import java.util.ArrayList;

public class MaquinaTuring {

    /*String estado;
    String variable;
    boolean direccion;
    ArrayList<String> copiaCadena = new ArrayList<>();

    public MaquinaTuring(String estado, String variable, boolean direccion) {
        this.estado = estado;
        this.variable = variable;
        this.direccion = direccion;
    }*/
    private MaquinaTuring (){
        super();
    }

    public static int movimientoCabezal(int posicionActual, boolean direccion){
        int posFinal = 0;
        if(direccion == true){
            posFinal = posicionActual + 1;
        }
        else if (direccion ==false){
            posFinal = posicionActual - 1;
        }
        return posFinal;
    }


}
