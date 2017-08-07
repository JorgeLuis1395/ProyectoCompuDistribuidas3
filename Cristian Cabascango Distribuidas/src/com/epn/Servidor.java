/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epn;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Servidor extends Thread {
    

   int port = 8888;
   ServerSocket ss ;
   Socket socket;
   DataOutputStream salida;
   String mensaje;
   
   public void run (){
       
       BufferedReader entrada;
       try{
           for(int i=0;i<3;i++)
           {
           ss= new ServerSocket(port);
           socket= new Socket();
           socket= ss.accept();
           System.out.println("Cliente conectado"+socket.getInetAddress().getHostAddress());
           entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           salida = new DataOutputStream(socket.getOutputStream());
           
           salida.writeUTF("1 lorem");
           salida.writeUTF("2 lipsum");
           salida.writeUTF("54 sed");
           
           mensaje = entrada.readLine();
           salida = new DataOutputStream(socket.getOutputStream());
           System.out.println(mensaje);
           
           salida.writeUTF("mensaje recibido");
           socket.close();
           }
       }
       catch (Exception e)
       {
           System.out.println("ERRO: " + e.getMessage());
       }
           
   }
   /*public void run(){
       //  Ejecucion();
         incServer();
    }*/
    
    
}
