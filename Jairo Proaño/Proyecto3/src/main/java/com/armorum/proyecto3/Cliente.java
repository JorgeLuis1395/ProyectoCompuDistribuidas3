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
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathcrap
 */
public class Cliente implements Runnable {

    Socket socket;
    BufferedReader input;
    PrintStream output;
    Scanner lerTeclado;
    int id;
    boolean redireccion = false;
    String palabras;

    public Cliente(Socket socket, int id) {
        this.socket = socket;
        this.lerTeclado = new Scanner(System.in);
        this.id = id;
        this.redireccion = false;
    }
    
    public Cliente(Socket socket, boolean redireccion, String palabras){
        this.redireccion = redireccion;
        this.socket = socket;
        this.palabras = palabras;
    }

    @Override
    public void run() {
        try {
            if(this.redireccion){
                this.id = 0;
            }
            System.out.println("Cliente " + this.id + ": Se empezara la comunicacion");

            Archivo archivo = new Archivo();

            DataInputStream is = null;
            DataOutputStream os = null;
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());

            PrintStream salida = new PrintStream(socket.getOutputStream());

            String[] palabras = archivo.leerArchivo();
            if(this.redireccion){
                palabras = this.palabras.split(":");
            }
            
            for (String palabra : palabras) {
                salida.println(palabra);
                System.out.println("Cliente "+ this.id+": (Mensaje enviado): "
                        + palabra );
            }
            
            if(!this.redireccion){
                archivo.agregarListaIpsConocidas(socket.getInetAddress().getHostAddress());
            }
            
            System.out.println("Esperando respuesta");
            Scanner in2 = new Scanner(socket.getInputStream());
            while(in2.hasNextLine()){
                System.out.println("Cliente "+ this.id+": (Repuesta recibida desde "+
                    socket.getInetAddress() + " ): " + in2.nextLine());
            }

            socket.close();
            System.out.println("Conexion cerrada");

        } catch (NullPointerException error) {
            System.err.println("Socket Cliente " + this.id + " desconectado");
        } catch (SocketException error) {
            System.err.println("Cliente " + this.id + ": Conexion perdida");
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
