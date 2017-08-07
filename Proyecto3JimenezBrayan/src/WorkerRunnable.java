
import com.sun.deploy.util.SyncAccess;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Timer;


public class WorkerRunnable implements Runnable{

    public static Boolean flagEnviar = false;

    public static ArrayList<String> fraseFinal = new ArrayList<String>();




    protected Socket clientSocket = null;
    protected String serverText   = null;
    public static ArrayList<String > palabraTemporal = new ArrayList<String>();


    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            if (Main.numeroDeConexiones < 3) {

            Socket conex = new Socket(clientSocket.getInetAddress().toString().substring(1), 8888);
            Main.listaIP.remove(clientSocket.getInetAddress().toString().substring(1));
            long time = System.currentTimeMillis();
            System.out.println("Conectado con el nodo: " + clientSocket.getInetAddress().toString().substring(1));
            Main.numeroDeConexiones = Main.numeroDeConexiones + 1;

            Scanner s = new Scanner(clientSocket.getInputStream());
            String msn;
            PrintStream salida = new PrintStream(conex.getOutputStream());
           // salida.println("hola , soy brayan");
            ///////////////////////////////////////
                TimerTask reenviarMensaje = new TimerTask() {
                    @Override
                    public void run() {
                        while (!Main.palabrasEnviar.isEmpty()){
                            for (int i=0;i<Main.palabrasEnviar.size();i++){
                                salida.println(Main.palabrasEnviar.get(i));
                            }
                            Main.palabrasEnviar.clear();
                        }
                    }
                };
                Timer timerReenvioMsj = new Timer();
                timerReenvioMsj.scheduleAtFixedRate(reenviarMensaje, 0, 1500);
                /////////////////////////
            while (s.hasNextLine()) {
                try {
                String [] palabrasRecibidas = s.nextLine().split(" ");
               if (Integer.valueOf(palabrasRecibidas[0])<=Main.fin&&Integer.valueOf(palabrasRecibidas[0])>Main.inicio){
                   System.out.println("Se encontro la palabra: " + palabrasRecibidas[1]);
                   Main.fraseFinal.put(Integer.valueOf(palabrasRecibidas[0]),palabrasRecibidas[1] );
                   System.out.print("\nla frase hasta el momento es : ");
                   Iterator it = Main.fraseFinal.entrySet().iterator();
                   while (it.hasNext()) {
                       Map.Entry e = (Map.Entry)it.next();
                       System.out.print( e.getValue()+" ");
                   }
                   System.out.println("\n");
               }
               else {
                   System.out.println("Se reenvio el mensaje: "+palabrasRecibidas[0]+" "+ palabrasRecibidas[1]);
                   Main.palabrasEnviar.add(palabrasRecibidas[0]+" "+palabrasRecibidas[1]);
                   flagEnviar=true;

               }
                }
                catch (Exception e){
                    System.out.println(s.nextLine());
                  //  msn= JOptionPane.showInputDialog("Mensaje a responder:");
                    salida.println("recibido");
                }


            }
            salida.close();
            s.close();

            System.out.println("Se desconecto el usuario: " + clientSocket.getInetAddress().toString().substring(1));
        }else {System.out.println("Alcanzado el numero de conexiones maximo");}

        }

        catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }



    public static boolean estaCompleto (){
        if (fraseFinal.size()==9){
            return true;
        }
        else{
            return false;
        }
    }
    public void imprimir (ArrayList lista){
        for (int i =0;i<lista.size();i++){
            System.out.print(lista.get(i)+" ");
        }
    }

}