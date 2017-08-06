/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pproyecto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Servidor extends Thread{
    private int puerto;
  DataOutputStream output;
 BufferedInputStream bis;
 BufferedOutputStream bos;

byte[] receivedData;
 int in;
 String file;
    public Servidor(int puerto) {
     this.puerto= puerto;
    }

    Servidor(Socket cliente, Cliente[] ejeCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     public void Ejecucion(){
        Socket cliente;
        PrintWriter salida;
        BufferedReader entrada;
        String mensajeEntrada="";
        String mensaje="";
       
        try {
        
            ServerSocket servidor=new ServerSocket(puerto);
            cliente=new Socket();
            cliente=servidor.accept();
           String nombreCliente = cliente.getInetAddress().getHostAddress();
           System.out.println("Nueva conexion " +  nombreCliente);
           Scanner s = new Scanner(cliente.getInputStream());
            while (s.hasNextLine()) {
               System.out.println(nombreCliente + ": "+ s.nextLine());
           }             cliente.close();
           
            //servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public void TransferenciaArchivo()
   {
       ServerSocket server;
 Socket connection;
 
DataOutputStream output;
 BufferedInputStream bis;
 BufferedOutputStream bos;

byte[] receivedData;
 int in;
 String file;

try{
 //Servidor Socket en el puerto 5000
 server = new ServerSocket( puerto );
 while ( true ) {
 //Aceptar conexiones
 connection = server.accept();
 //Buffer de 1024 bytes
 receivedData = new byte[1024];
 bis = new BufferedInputStream(connection.getInputStream());
 
 DataInputStream dis=new DataInputStream(connection.getInputStream());
 //Recibimos el nombre del fichero
 file = dis.readUTF();
 file = file.substring(file.indexOf('\\')+1,file.length());
 //Para guardar fichero recibido
 bos = new BufferedOutputStream(new FileOutputStream(file));
 
 while ((in = bis.read(receivedData)) != -1){
 bos.write(receivedData,0,in);
 }
 bos.close();
 
 }
 }catch (Exception e ) {
 System.err.println(e);
 }
 }
   
    public void run(){
        Ejecucion();
        // TransferenciaArchivo();
    }
}
