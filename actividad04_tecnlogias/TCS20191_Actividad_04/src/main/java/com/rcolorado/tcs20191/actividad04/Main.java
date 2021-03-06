/*
 * 
 * UNIVERSIDAD VERACRUZANA
 * rcolorado@uv.mx
 * Ejercicio 04: Tecnologías para la construcción de Software
 * Versión 1.0.0
 */
package com.rcolorado.tcs20191.actividad04;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rpimentel
 */
public class Main {

  public static void main(String[] args) throws IOException {

    Scanner scanner = new Scanner(System.in);
    String divisa;
    do {
      System.out.println("********************");
      System.out.println("¿Cuánto vale un Bitcoin en mi divisa?");
      System.out.println("********************");
      System.out.println("Introduce el nombre de tu divisa (MXN, USD, CAD, EUR, ARS, VES):");

      divisa = scanner.next();
      System.out.println("... espere un momento");

      List<DivisaJsonClass> lista = ConsultaBitCoinMarket();

      System.out.println("Se consultó de un total de : " + lista.size() + " divisas");

      boolean encontrado = false;    //Indica si se encontró la divisa solicitada.

      for (int i = 0; i < lista.size(); i++) {
        
        if (lista.get(i).symbol.equals("localbtc" + divisa.toUpperCase())) {
          System.out.println(lista.get(i).currency + " : " + lista.get(i).ask);
          System.out.println("Venta : " + lista.get(i).bid);
          System.out.print(LocalDate.now().getDayOfMonth() + "/");
          System.out.print(LocalDate.now().getMonth() + "/");
          System.out.println(LocalDate.now().getYear());
          
          encontrado = true;
        }
      }
      if (!encontrado) {
        System.out.println("No se pudo encontrar la divisa solicitada.");
      }
    } while (!divisa.equalsIgnoreCase("NINGUNO"));

  }

  private static List<DivisaJsonClass> ConsultaBitCoinMarket() {
     InputStreamReader isr = null;
     TypeToken<List<DivisaJsonClass>> token = null;
     
    try {
        URL url = new URL("http://api.bitcoincharts.com/v1/markets.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        if (conn.getResponseCode() != 200) {
            throw new execpcionFalloHttpConecction();
        }
        
        isr = new InputStreamReader(conn.getInputStream());
        token = new TypeToken<List<DivisaJsonClass>>() {};
      
    } catch (IOException | NullPointerException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    } catch (execpcionFalloHttpConecction e) {
         System.out.println(e.excepcionError());
    }
        
    return  new Gson().fromJson(isr, token.getType());
  }

  /*
     * Clase privada para obtener los datos de las divisas
     *
   */
  class DivisaJsonClass {
    // Precio a la venta

    private double bid;
    private String currency;
    // Precio a la compra
    private double ask;
    private String symbol;
  }
}
