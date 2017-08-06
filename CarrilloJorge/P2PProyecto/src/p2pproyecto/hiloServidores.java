package p2pproyecto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class hiloServidores extends Thread {
    private Cliente[] ejeCliente;
    public hiloServidores(Cliente[] cliente){
        this.ejeCliente=cliente;
    }
    public void hiloServidores(){
        try {
            final int puerto=8888;
            ServerSocket servidor=new ServerSocket(puerto);
            for(int i=0;i<3;i++){
                Socket cliente=new Socket();
                cliente=servidor.accept();
                Servidor servidor2;
                servidor2 = new Servidor(cliente,ejeCliente);
                servidor2.start();
            }
            servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        hiloServidores();
    }
}
