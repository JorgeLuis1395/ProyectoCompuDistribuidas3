package proyectofinal3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class ParteServidor extends Thread{
    private Socket cliente;
    private ParteCliente[] ejeucionCliente;
    public ParteServidor(Socket cli,ParteCliente[] ejeCli){
        this.cliente=cli;
        this.ejeucionCliente=ejeCli;
    }
    
    public void Ejecucion(){
        int con=0;
        PrintWriter salida;
        BufferedReader entrada;
        String mensajeEntrada="",palabra="";
        String mensaje="";
        try {
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida=new PrintWriter(cliente.getOutputStream(), true);
            while((mensajeEntrada=entrada.readLine())!=null){
                if(con<1)
                    salida.print("hola");
                con++;
                System.out.println("Recibi el mensaje : "+cliente.getInetAddress().getHostAddress()+" "+mensajeEntrada);
                if(!(mensaje=JOptionPane.showInputDialog("Ingrese el texto hacia el Nodo "+cliente.getInetAddress().getHostAddress())).equals(""))
                    salida.println(mensaje);
                /*StringTokenizer trozos=new StringTokenizer(mensajeEntrada," ");
                while(trozos.hasMoreTokens()){
                    palabra=trozos.nextToken();
                    if(Integer.parseInt(palabra)>28)
                        for(int i=0;i<ejeucionCliente.length;i++)
                            try{
                                ejeucionCliente[i].ReenviarSMS(mensajeEntrada);
                                System.out.println("reenvie el sms"+mensajeEntrada);
                                }catch(NullPointerException ex){}
                    else
                        System.out.println("Recibi el mensaje : "+cliente.getInetAddress().getHostAddress()+" "+mensajeEntrada);
                    break;
                }*/
//servidor.close();
            }
        } catch (IOException ex) {
            //Logger.getLogger(ParteServidor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Servidor suspendido: "+this.cliente.getInetAddress().getHostAddress());
        }
    }
    
    public void run(){
        System.out.println("Nodo conectado: "+this.cliente.getInetAddress().getHostAddress());
        Ejecucion();
    }
}
