/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Aspire
 */
public class Archivo {
    
    BufferedReader br = null;
    BufferedWriter bw = null;
    String IP;
    
    public String getIP(){
        String linea="";
        
        try{
            br = new BufferedReader(new FileReader("data/listaIPs"));
            //bw = new BufferedWriter(new FileWriter("data/listaIPs"));
            while((linea=br.readLine())!=null){
                linea = br.readLine();
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return IP=linea;
    }
    
    public String getIPEspecifica(int linea){
        
        String lineaEspecifica="";
    
        try{
            lineaEspecifica = Files.readAllLines(Paths.get("data/listaIPs")).get(linea);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return lineaEspecifica;
    }
    
    public void setIP(String IP){
        String linea="";
        try{
            //br = new BufferedReader(new FileReader("data/listaIPs"));
            bw = new BufferedWriter(new FileWriter("data/listaIPs"));
            while(IP!=""){
                bw.append(IP + "\n");
            }
            bw.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }   
    }
    
    public String getPalabra(String archivo){
        String linea="";
        
        try{
            br = new BufferedReader(new FileReader(archivo));
            //bw = new BufferedWriter(new FileWriter("data/listaIPs"));
            while((linea=br.readLine())!=null){
                linea = br.readLine();
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return IP=linea;
    }
        
}
