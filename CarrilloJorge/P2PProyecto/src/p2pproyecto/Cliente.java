/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pproyecto;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class Cliente extends Thread{
    Socket cliente;
    boolean verificar=false;
    PrintWriter salida;
    BufferedReader entrada;
    String mensaje="",mensajeRecibido;
    DataInputStream input;
     BufferedInputStream bis;
    BufferedOutputStream bos;
    int in;
    byte[] byteArray;
    //Fichero a transferir
 final String filename = "C:\\Users\\Usuario\\Desktop\\1.txt";
    int puerto;
    String HOST;
    String TablaMensaje[][];
    ArrayList<String> ListaConecciones;
    
    public Cliente(String Host,int puerto,ArrayList<String> conecciones,String[][] Info){
        this.HOST=Host;
        this.puerto=puerto;
        this.ListaConecciones=conecciones;
        this.TablaMensaje=Info;
    }

   
    public void Ejecucion(){
        try {
            Scanner leerTeclado = new Scanner(System.in);
            cliente=new Socket(HOST,this.puerto);
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             System.out.println("Cliente conectado"+HOST);
             System.out.println("Ingrese su Mensaje");
            salida=new PrintWriter(cliente.getOutputStream(), true);
            this.verificar=true;
            while (leerTeclado.hasNextLine()) {
             salida.println(leerTeclado.nextLine());
            }
            leerTeclado.close();
            cliente.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    public void Mensaje(String texto)
            {
                salida.println(texto);
            }
   public void TransmitirArchivo()
   {
       try {
            cliente=new Socket(HOST,this.puerto);
            final File localFile = new File( filename );
             bis = new BufferedInputStream(new FileInputStream(localFile));
             bos = new BufferedOutputStream(cliente.getOutputStream());
                 //Enviamos el nombre del fichero
             DataOutputStream dos=new DataOutputStream(cliente.getOutputStream());
             dos.writeUTF(localFile.getName());
             //Enviamos el fichero
             byteArray = new byte[8192];
             while ((in = bis.read(byteArray)) != -1){
             bos.write(byteArray,0,in);
            } bis.close();
             bos.close();
            cliente.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
       public void run(){
            Ejecucion();
           
           //TransmitirArchivo();
    }
}
