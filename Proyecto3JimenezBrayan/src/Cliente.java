import java.io.*;
import java.net.Socket;
import java.util.*;

public class Cliente implements Runnable {
    protected Socket cliente=null;
public static Boolean estado = false;
public  static Boolean espera = false;
public static   Boolean heartBeat = true;
public static  int aux = 0;
    private int idThread;
    private String ip;
    private int puerto=8888;

    public int getIdThread() {
        return idThread;
    }

    public void setIdThread(int idThread) {
        this.idThread = idThread;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }



    public void run () {
        try {
            if (Main.numeroDeConexiones < 3) {


            Socket cliente = new Socket(ip,puerto);

            System.out.println("Conectado a el nodo:" + ip);
            Main.numeroDeConexiones = Main.numeroDeConexiones + 1;
            Scanner lerTeclado = new Scanner(System.in);

            TimerTask timerTask = new TimerTask() {
                public void run() {
                    try {
                        DataOutputStream os = null;
                        os = new DataOutputStream(cliente.getOutputStream());
                        os.writeUTF("");
                    } catch (IOException e) {
                        heartBeat = false;
                        if (aux == 0) {
                            System.out.println("El nodo " + ip + " se desconecto");
                            aux++;
                        }


                    }
                }
            };


            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 1000, 3000);
            //////////////////////////////////////////////


            PrintStream salida = new PrintStream(cliente.getOutputStream());
            String ipPub = (new java.net.Socket("www.google.com", 80)).getLocalAddress().getHostAddress();

            //////////tarea para reenviar mensaje /////////
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
                ////////////////////////////
                Scanner s = new Scanner(cliente.getInputStream());


            while (s.hasNextLine()) {

                salida.println("cleint"+s.nextLine());

            }
            salida.close();

            cliente.close();
        }else {System.out.println("Numero de conexiones maximo alcanzado");}

        }
        catch(Exception e){
               // System.out.println("No se pudo concetar");
                estado = false;
                espera = true;

            }

        }




}
