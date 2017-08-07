/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.armorum.proyecto3;


import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/**
 *
 * @author mathcrap
 */
public class Main {
    
    public void ejecutarNodo() throws IOException{
        ServerSocket server;

        
        int PORT = 8888;
        
        boolean cliente1Conectado = false;
        boolean cliente2Conectado = false;
        
        Socket[] cliente =  new Socket[2];
        
        Archivo archivo = new Archivo();
        archivo.eliminarListaIpsConocidas();
        try{
            server = new ServerSocket(PORT, 5, InetAddress.getLocalHost());
            System.out.println("Servidor levantado en puerto: " + PORT);
            (new Thread(new Hilo(server))).start(); // Siempre esta esperando conexiones
        } catch(BindException error){
            System.err.println("Puerto " + PORT + " para servidor ya usada");
            return;
        }
  
        String[] listaIps = archivo.obtenerListaIps("src/main/resources/ips/ips.txt");
        
        if(listaIps == null){
            System.err.println("No hay ips a donde conectarse");
            return;
        }
        int ipUsada = 0;
        
        System.out.println("Cliente 1: Se inicia la busqueda de servidor");
        for (String ip : listaIps) {
            try{
            System.out.println("Cliente 1: Esperando por ip: " + ip);
            cliente[0] = new Socket(ip, PORT);
            System.out.println("Cliente 1: " + cliente[0].getInetAddress()
                .toString() + " conectado");
                cliente1Conectado = true;
                break;
            } catch(NoRouteToHostException error){
                System.err.println("Conexion fallida\n Siguiente ip");
                ipUsada++;
            } catch (ConnectException error){
                System.err.println("Excepcion de tiempo\n Siguiente ip");
                ipUsada++;
            } catch (SocketException error){
                System.err.println("Ip no valida");
                return;
            }
        }
        String[] listaIps2 = new String[listaIps.length -1]; 
        if(ipUsada < listaIps.length){
            int k =0;
            for(int i =0; i<listaIps.length; i++){
                if(i != ipUsada){
                   listaIps2[k] = listaIps[i];
                   k++;
                }  
            }  
        }
        
        if(cliente1Conectado){
            (new Thread(new Cliente(cliente[0], 1))).start();
            
            for (String ip : listaIps2) {
                try{
                System.out.println("Cliente 2: Esperando por ip: " + ip);
                cliente[1] = new Socket(ip, PORT);
                System.out.println("Cliente 2: " + cliente[1].getInetAddress()
                    .toString() + " conectado");
                    cliente2Conectado = true;
                    break;
                } catch(NoRouteToHostException error){
                    System.out.println("Conexion fallida\n Siguiente ip");
                } catch (ConnectException error){
                    System.out.println("Excepcion de tiempo\n Siguiente ip");
                } catch (SocketException error){
                    System.err.println("Ip no valida");
                    return;
                }
            }
            if(cliente2Conectado){
                (new Thread(new Cliente(cliente[1], 2))).start();
            }
        }
    }
    
    public static void main(String args[]) throws IOException{
        
        Main app = new Main();
        app.ejecutarNodo();
        
    }
    
}
