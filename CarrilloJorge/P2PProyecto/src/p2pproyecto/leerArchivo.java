/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pproyecto;

import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Usuario
 */
public class leerArchivo {
    private String archivoSalida="DireccionesIp.txt";
    List<leerIp> ip = new ArrayList<leerIp>();
public void leerArchivoIP() throws IOException  {
     
        try {
         
        CsvReader usuarios_import = new CsvReader("C:\\Users\\Usuario\\Documents\\ip.csv");
        usuarios_import.readHeaders();
         
        while (usuarios_import.readRecord())
        {
            String ipsactivas = usuarios_import.get(0);
            ip.add(new leerIp(ipsactivas));    
        }
         
        usuarios_import.close();
         
        for(leerIp us : ip){
         
            System.out.println(us.getIp());
        }
         
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/* public String leerpalabras() throws FileNotFoundException, IOException{
        File datos=new File("C:\\Users\\Usuario\\Desktop\\Lipsum.txt");
        FileReader fr=new FileReader(datos);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        String nextline="";
        try {
            
        while((line=br.readLine())!=null){
             
            nextline+=line;
           hashinicial=new HashMap<Integer,String>();
               String [] arreglo = nextline.split("\\s");
               Set<Map.Entry<Integer, String>> numero = hashinicial.entrySet();
		Iterator<Map.Entry< Integer,String>> frecuencia = numero.iterator();
		while ( frecuencia.hasNext() ) {
			Map.Entry< Integer,String> item = frecuencia.next();
			System.out.println ( "Llave: "+item.getKey() + " Palabra: " + item.getValue() );
		}
         } }
      catch(IOException e){
      }finally{}
      
        return nextline;
    }*/
public void EscrituraArchivoSalida(){
       FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(archivoSalida);
            pw = new PrintWriter(fichero);
            for(leerIp us : ip){
            pw.println(us.getIp());
            System.out.println(us.getIp());
        } 
        }    catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
   }
}
