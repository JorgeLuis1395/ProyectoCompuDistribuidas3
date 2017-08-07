/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

/**
 *
 * @author Aspire
 */
import java.io.*; 
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Aspire
 */
public class SocketCliente implements Runnable{
    
    private String direccion; 
    private int port; 
    private String nombre;
    
    public void run(){
        try {
            //String mensage, retorno;  
            Scanner scan = new Scanner(System.in);
            Socket cliente = new Socket(direccion, port);
            System.out.println("Cliente conectado exitosamente!");
            PrintStream out = new PrintStream(cliente.getOutputStream());
            while (scan.hasNextLine()) {
                //out.println(nombre + ": " + scan.nextLine());
                out.println(scan.nextLine());
            }
            out.close();//cierra flujo salida
            scan.close();//cierra flujo entrada
            cliente.close();//cierra puerto cliente
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Servidor desconectado: " + e.getMessage());
        } 
    }
    
    public void setEnrutamiento(int port, String direccion, String nombre){
        this.direccion = direccion;
        this.port = port;
        this.nombre = nombre; 
    }
}