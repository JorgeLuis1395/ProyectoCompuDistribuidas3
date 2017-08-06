/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pproyecto;

import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public final class P2PProyecto {
 String ip="";
 ArrayList<String> ipconectarme=new ArrayList<>();
Cliente cliente[]=new Cliente[3];
 int contadorCliente=0;
 leerArchivo archivo=new leerArchivo();
 int puerto=8888;
 String informacion[][]=new String[7][2];
 public P2PProyecto() throws IOException {
   
    try {
         leerArchivoIP();
         hiloServidores EsperandoConecciones=new hiloServidores(cliente);
            EsperandoConecciones.start();
            while(true){
                leerArchivoIP();
                sleep(60000);
                
            }
        } catch (IOException ex) {
            System.out.println("hola");
        } catch (InterruptedException ex) {
            System.out.println("hola1");
        }
    } 
public void ConectarServidor(Cliente cliente,String ip,int puerto){
          
        cliente=new Cliente(ip,puerto,ipconectarme,informacion);
        cliente.start();
        
    }
    
public void leerArchivoIP() throws IOException  {
        String cadena;
        String palabra;
        int contador;
        int puerto=8888;
        FileReader archivoLeido = new FileReader("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\P2PProyecto\\DireccionesIp.txt");
        BufferedReader bufferArchivo = new BufferedReader(archivoLeido);
        cadena = bufferArchivo.readLine();
        while((cadena = bufferArchivo.readLine())!=null && ipconectarme.size()<3)
                if(!cadena.equals(ip) && !ipconectarme.contains(cadena)){
                    ConectarServidor(cliente[ipconectarme.size()],cadena,puerto);
            }
        bufferArchivo.close();   
        }
         
   
    public static void main(String[] args) throws IOException {
        
        new P2PProyecto();
    }
    
}
