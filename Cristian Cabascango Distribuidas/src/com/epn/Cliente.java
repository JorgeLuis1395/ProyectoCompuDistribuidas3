/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Cliente extends Thread{
    

    Socket cliente;
    int puerto = 8888;
    String ips[]={"172.29.88.206","172.29.9.130","172.29.17.206","172.29.12.52","172.29.5.254"};
    BufferedReader entrada, teclado;
    PrintStream salida;
    Boolean bandera = true;
    DataOutputStream salida1;
    public void inicClient ()
    {
        while(bandera==true&&i<5)
        try{
            cliente = new Socket(ips[i],puerto);
            //Thread cliente1 []= new Thread[3];
            
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            teclado = new BufferedReader(new InputStreamReader(System.in));
            String tec = teclado.readLine();
            salida = new PrintStream(cliente.getOutputStream());
            salida.println("1 lorem");
            salida1 = new DataOutputStream(cliente.getOutputStream());
            salida1.writeUTF("1 lorem");
            salida1.writeUTF("2 lipsum");
            String msg = entrada.readLine();
            System.out.println(msg);
            bandera = false;
            entrada.close();
            salida.close();
            teclado.close();
            cliente.close();
        }
        catch (Exception e) {
            System.out.println("");
            bandera=true;
            i++;
        } 
    }
            
    public void run ()
    {
        inicClient();
    }
    
    
    private String direccion; //
    private int port; //
    private String nombre;
    Boolean flag = true;
    int i=0;
    public void Inic(){
        while (flag==true&&i<5)
        {
        try {
            
            String mensagem, retorno;  //Vai guardar as mensagens enviadas para o outro lado
            Scanner lerTeclado = new Scanner(System.in);
            String ips[]={"172.29.12.52","172.29.88.184","172.29.12.52","172.29.31.46"};
            //System.out.println("nada");
            Boolean flag = true;
            Socket cliente = new Socket(ips[i], port);
            flag=false;
            System.out.println("Cliente conectado exitosamente!"+ips[i]);
            PrintStream salida = new PrintStream(cliente.getOutputStream());
            while (lerTeclado.hasNextLine()) {
                salida.println(nombre + ": " + lerTeclado.nextLine());
            }
            salida.close();
            lerTeclado.close();
            cliente.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar");
            flag=true;
            i++;
        } 
        }
    }
    
    public void setEnrutamiento(int portap, String n){
       
        port = portap;
        nombre = n; 
    }
    
    
}
