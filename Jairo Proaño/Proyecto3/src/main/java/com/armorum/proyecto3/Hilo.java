/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.armorum.proyecto3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathcrap
 */
public class Hilo implements Runnable{

    Socket clienteServer;
    ServerSocket server;
    
    public Hilo(ServerSocket server){
        this.server = server;
    }
    
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++){
            try {
                System.out.println("Server: Esperando conexion " + i);
                clienteServer = server.accept(); 
                (new Thread(new Server(clienteServer))).start();
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
}
