package proyectofinal3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerarServidores extends Thread {
    private ParteCliente[] ejeCliente;
    public GenerarServidores(ParteCliente[] cliente){
        this.ejeCliente=cliente;
    }
    private void GenerarServidores(){
        try {
            final int puerto=8888;
            ServerSocket servidor=new ServerSocket(puerto);
            for(int i=0;i<3;i++){
                Socket cliente=new Socket();
                cliente=servidor.accept();
                ParteServidor server=new ParteServidor(cliente,ejeCliente);
                server.start();
            }
            servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(ParteServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        GenerarServidores();
    }
}
