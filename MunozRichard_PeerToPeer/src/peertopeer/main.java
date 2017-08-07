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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
*
* @author Aspire
*/
public class main {
    public static int puertoServidor = 8888;

    public static void main(String[] args) {
       
       try { 
           Scanner scannerIn = new Scanner(System.in);
           //Scanner scannerInInt = new Scanner(System.in);
           Archivo archivoServ = new Archivo();
           int cont=0;
           HashMap<String,String> map = new HashMap<>();
           String linea="";
//           while(cont<10){
//               linea = archivoServ.getPalabra("data/Lipsum.txt");
//               String [] line = linea.split(":");
//               map.put(line[0], line[1]);
//               cont++;
//           }
           
           Scanner s = new Scanner(new File("data/Lipsum.txt"));
ArrayList<String> list = new ArrayList<String>();
while (s.hasNext()){
    list.add(s.next());
}
s.close();
           String ipServidor;
           //String ipServidor=ipArchivoServ.getIP();
           //String ipServidor=ipArchivoServ.getIPEspecifica()
           String nombre;
           
           //System.out.println(ipServidor);
           
           //List<SocketCliente> clienteList = new ArrayList<SocketCliente>(); 
           //List<SocketServidor> servidorList  = new ArrayList<SocketServidor>(); 
           SocketCliente cliente= new SocketCliente(); 
           SocketServidor servidor  = new SocketServidor(); 

           Thread hiloCliente = new Thread(cliente); 
           Thread hiloServidor = new Thread(servidor); 

           servidor.setPuerto(puertoServidor);    
           hiloServidor.start();
           System.out.println("IP servidor destino: ");
           
           ipServidor = scannerIn.nextLine();
           
           System.out.println("Ingrese nombre");
           nombre = scannerIn.nextLine();
           cliente.setEnrutamiento(puertoServidor, ipServidor, nombre);
           hiloCliente.start();
           
       } catch (Exception e) {
           e.printStackTrace();
       }
      
   
   }
}