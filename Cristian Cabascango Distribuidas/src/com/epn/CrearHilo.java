/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Usuario
 */
public class CrearHilo extends Thread{
    int port = 8888;
   ServerSocket ss ;
   Socket socket;
   DataOutputStream salida;
   String mensaje;
   
   public void run (){
       
       BufferedReader entrada;
       try{
           ss= new ServerSocket(port);
           socket= new Socket();
           socket= ss.accept();
           System.out.println("CLiente conectado"+socket.getInetAddress().getHostAddress());
           entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           salida = new DataOutputStream(socket.getOutputStream());
           
           salida.writeUTF("Conexion exitosa");
           mensaje = entrada.readLine();
           salida = new DataOutputStream(socket.getOutputStream());
           System.out.println(mensaje);
           
           salida.writeUTF("mensaje recibido");
           //socket.close();
    }
       catch (Exception e)
       {
           System.out.println("ERRO: " + e.getMessage());
       }
   }
}
