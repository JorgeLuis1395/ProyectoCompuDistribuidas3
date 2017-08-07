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


public class SocketServidor implements Runnable {
   private int port;
   Socket cliente;
   
   public void run(){
       try {
           ServerSocket servidor = new ServerSocket(port);
           cliente = servidor.accept();
           String hostCliente = cliente.getInetAddress().getHostAddress();
           System.out.println("Conexion con cliente: " +  hostCliente);
           Scanner s = new Scanner(cliente.getInputStream());
           while (s.hasNextLine()) {
               String [] host = s.nextLine().split(":");
               if(host[0].equals(cliente.getInetAddress().getHostAddress()))
                    System.out.println("localhost" + s.next());
               else
                    System.out.println(hostCliente + ": "+ s.nextLine());
           }
           s.close();
           servidor.close();
           cliente.close();
           
       /*} catch (Exception e) {
           e.printStackTrace();*/
       } catch(IOException e){
           System.out.println("Servidor desconectado: " + cliente.getInetAddress().getHostAddress());
       }
      
   }
   
    public int getPort() {
        return port;
    }
   
   public void setPuerto(int port){
       this.port = port;
   }
}