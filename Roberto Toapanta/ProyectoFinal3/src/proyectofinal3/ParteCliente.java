package proyectofinal3;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class ParteCliente extends Thread{
    public Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String mensaje="",mensajeRecibido;
    private String TablaMensaje[][];
    private boolean verificar=false;
    int puerto;
    private ArrayList<String> ListaConecciones;
    private String HOST;
    public ParteCliente(String Host,int puerto, ArrayList<String> ListaConec,String[][] Info){
        this.HOST=Host;
        this.puerto=puerto;
        this.ListaConecciones=ListaConec;
        this.TablaMensaje=Info;
    }
    public void Ejecucion(){
        try {
            cliente=new Socket(HOST,this.puerto);
            System.out.println("Conectado con nodo: "+HOST);
            ListaConecciones.add(HOST);
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida=new PrintWriter(cliente.getOutputStream(), true);
            this.verificar=true;
            if(!(mensaje=JOptionPane.showInputDialog("Ingrese el texto hacia el Nodo "+HOST)).equals(""))
                salida.println(mensaje);
            /*for(int i=0;i<TablaMensaje.length;i++){
                mensaje=TablaMensaje[i][0]+" "+TablaMensaje[i][1];
                if(Integer.parseInt(TablaMensaje[i][0])>7)
                    salida.println(mensaje);
            }/*while(!(mensaje=JOptionPane.showInputDialog("Ingrese el texto hacia el Nodo "+HOST)).equals("")){
                salida.println(mensaje);
                mensaje="";
            }*/
            
        } catch (IOException ex){
            //Logger.getLogger(ParteCliente.class.getName()).log(Level.SEVERE, null, ex)
            //System.out.println("no valio "+puerto+" "+HOST);
            //puerto++;
            //if(puerto<4318)
                //Ejecucion();
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(ParteCliente.class.getName()).log(Level.SEVERE, null, ex);
                //ListaConecciones.remove(HOST);
        }
    }
    
    public void ReenviarSMS(String mensaje){
        salida.println(mensaje);
    }
    
    public void run(){
        Ejecucion();
    }
}
