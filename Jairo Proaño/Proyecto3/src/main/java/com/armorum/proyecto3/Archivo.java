/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.armorum.proyecto3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathcrap
 */
public class Archivo {
    
    public void eliminarListaIpsConocidas(){
        String pathFile = "src/main/resources/ips/conocidas.txt";
        File archivo = new File(pathFile);
        archivo.delete();
        
        String pathFileSave = "src/main/resources/ips/palabrasGuardadas.txt";
        File archivoGuardadas = new File(pathFileSave);
        archivoGuardadas.delete();
        
        
    }
    
    public void agregarListaIpsConocidas(String ip){
        try {
            FileWriter fileWritter;
            PrintWriter pw;
            String pathFile = "src/main/resources/ips/conocidas.txt";
            
            fileWritter = new FileWriter(pathFile, true);
            pw = new PrintWriter(fileWritter);
            
            pw.println(ip);
            fileWritter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String[] leerArchivo(){
        
        File archivo;
        FileReader fileReader;
        BufferedReader br;
        String pathFile = "src/main/resources/archivo/Lipsum.txt";
        String[] palabras = new String[10];
        try{
            
                archivo = new File(pathFile);
                fileReader = new FileReader(archivo);
                br = new BufferedReader(fileReader);
                String linea;
                int numeroLinea = 0;
                while((linea=br.readLine()) != null){
                    palabras[numeroLinea] = linea;
                    numeroLinea++;  
                    }       
                
                fileReader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return palabras;
       
    }
    
    public void guardarPalabra(String palabra){
        try {
            FileWriter fileWritter;
            PrintWriter pw;
            String pathFile = "src/main/resources/ips/palabrasGuardadas.txt";
            
            fileWritter = new FileWriter(pathFile, true);
            pw = new PrintWriter(fileWritter);
            
            pw.println(palabra);
            fileWritter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] obtenerListaIps(String path){
        File archivo;
        FileReader fileReader;
        BufferedReader br;
        String pathFile = path;
        String[] listaIps = null;
        try{
             int contador = 0;
            while(contador < 2){
                archivo = new File(pathFile);
                fileReader = new FileReader(archivo);
                br = new BufferedReader(fileReader);
                String linea;
                int numeroLineas = 0;
                int numeroLinea = 0;
                while((linea=br.readLine()) != null){
                    if(contador == 0){
                        numeroLineas++;
                    }else {
                        if(listaIps != null){
                            listaIps[numeroLinea] = linea;
                            numeroLinea++;
                        }
                        
                    }       
                }
                fileReader.close();
                if(numeroLineas > 0 && contador == 0){
                    listaIps = new String[numeroLineas];
                }
                contador++;
            }
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaIps;
    }
    
}
