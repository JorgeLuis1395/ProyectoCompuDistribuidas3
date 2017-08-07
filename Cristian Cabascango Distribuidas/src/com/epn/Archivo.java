/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Usuario
 */
public class Archivo {
    

    
    BufferedReader br = null;
    BufferedWriter bw = null;
    String IP;
    
    public String getIP(){
    String linea="";
        try{
            br = new BufferedReader(new FileReader("ips"));
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
    
    public void setIP(String IP){
        String linea="";
        try{
            //br = new BufferedReader(new FileReader("data/listaIPs"));
            bw = new BufferedWriter(new FileWriter("ips"));
            while(IP!=""){
                bw.append(IP + "\n");
            }
            bw.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
}
