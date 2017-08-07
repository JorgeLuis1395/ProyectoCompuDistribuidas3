/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.armorum.proyecto3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathcrap
 */
public class Server implements Runnable{
    Socket clienteServer;
    BufferedReader input;
    PrintStream output;
    Scanner lerTeclado;
    Archivo archivo;
    int PORT;
    
    public Server(Socket cliente){
        this.clienteServer = cliente;
    }
    @Override
    public void run() {
        System.out.println("Server: Conexion aceptada: " + clienteServer.getInetAddress().toString());
        archivo = new Archivo();
        String palabrasParaRedirigir = "";
        try {
            DataInputStream is = null;
            DataOutputStream os = null;
            is = new DataInputStream(clienteServer.getInputStream());
            os = new DataOutputStream(clienteServer.getOutputStream());
            
            System.out.println("Server: (" + clienteServer.getInetAddress().getHostAddress() + 
                            ") Esperando mensajes: ");
            Scanner entradas = new Scanner(clienteServer.getInputStream());
            
            while(entradas.hasNextLine()){
                String linea = entradas.nextLine();
                System.out.println("Server: (Mensaje recibido desde "+
                clienteServer.getInetAddress() + " ) : " + linea);
                
                
                String[] palabra = linea.split(" ");

                if(this.mesajeDirgidoAMi(palabra[0])){
                    archivo.guardarPalabra(palabra[0] + " " + palabra[1]);
                    System.out.println("Server: (" + clienteServer.getInetAddress().getHostAddress() + 
                                    ") Palabra guardada: " + palabra[1]);
                } else {
                    palabrasParaRedirigir += palabra[1] + ":";
                }
                
                if(!palabrasParaRedirigir.equals("")){
                    this.redirigirMensaje(palabrasParaRedirigir,
                            this.clienteServer.getInetAddress().getHostAddress());
                    System.out.println("Server: (" + clienteServer.getInetAddress().getHostAddress() + 
                                        ") Palabras redirigidas: " +
                                        palabrasParaRedirigir);
                }
                
            }
            
            clienteServer.close();
        } catch(SocketTimeoutException error){ 
            System.out.println("Server: No llego mensaje");
            if(!palabrasParaRedirigir.equals("")){
                this.redirigirMensaje(palabrasParaRedirigir,
                            this.clienteServer.getInetAddress().getHostAddress());
                    System.out.println("Server: (" + clienteServer.getInetAddress().getHostAddress() + 
                                        ") Palabras redirigidas: " +
                                        palabrasParaRedirigir);
            }
        } catch(SocketException error){
            System.err.println("Server: Conexion perdida");
        }
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    public boolean mesajeDirgidoAMi(String mensaje){
        int identificadorPalabra;
        try{
            identificadorPalabra =  Integer.parseInt(mensaje);
        } catch(Exception error){
            return false;
        }
        
        return ((identificadorPalabra)%6 == 0);
    }
    
    
    
    public void enviarRespuesta(String linea){
        try {
            PrintStream salida = new PrintStream(clienteServer.getOutputStream());
            salida.println(linea);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean ipEstaEnRutas(String mensaje){
        
        return false;
    }
    
    public void redirigirMensaje(String mensaje, String ipOrigen){
        String[] listaIps = archivo.obtenerListaIps("src/main/resources/ips/conocidas.txt");
        Socket socketRedireccion = null;
        boolean clienteConectado = false;
        if(listaIps == null){
            System.err.println("No hay ips a donde conectarse");
            return;
        }
        int ipUsada = 0;
        
        System.out.println("Redirigiendo: Se inicia la busqueda de servidor");
        for (String ip : listaIps) {
            if(ip.equals(ipOrigen)){
                continue;
            }
            try{
                System.out.println("Cliente 0: Esperando por ip: " + ip);
                socketRedireccion = new Socket(ip, PORT);
                System.out.println("Cliente 0: " + socketRedireccion.getInetAddress()
                    .toString() + " conectado");
                System.out.println("Mensaje redirigido");
                clienteConectado = true;
                break;
            } catch(NoRouteToHostException error){
                System.err.println("Redireccion: Conexion fallida\n Siguiente ip");
                ipUsada++;
            } catch (ConnectException error){
                System.err.println("Redireccion: Excepcion de tiempo\n Siguiente ip");
                ipUsada++;
            } catch (SocketException error){
                System.err.println("Redireccion: Ip no valida");
                return;
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(clienteConectado){
            (new Thread(new Cliente(socketRedireccion, clienteConectado, mensaje))).start();
        }
        
    }
    
    
}
