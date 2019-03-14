/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rcolorado.tcs20191.actividad04;

/**
 *
 * @author JET
 */
public class execpcionFalloHttpConecction extends Exception{
    
    /**
    Constructo para crear el error cuando sea necesario
    */
    public execpcionFalloHttpConecction() {
     //es solo un constructor 
    }
    public String excepcionError() {
        return "Respuesta estándar para peticiones incorrectas, no se pudo conectar con"
                + " el archivo";
    }
    
}

