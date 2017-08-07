/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epn;

import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Main extends Thread{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
         try { 
            Servidor sv   = new Servidor(); // Instanciando  servidor
            Cliente sc = new Cliente();
            
            //Instanciamos o processo cliente
           Thread processoSV = new Thread(sv);
           Thread processoSC = new Thread(sc);
            Thread processoSV1 = new Thread(sv);
           //Thread processoSV[] = new Thread[1];
          // sv[1] = new Servidor();
          // sv[1].start();();
          //sv[2] = new Servidor();
          processoSV.start();
          sc.setEnrutamiento(8888, "");
             //System.out.println("kasjka");
          processoSC.start();
             //System.out.println("kasjka");
          // processoSV1.start();
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
              
    }
    
}
