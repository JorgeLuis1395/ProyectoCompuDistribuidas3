import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class Main {
    public static ArrayList<String> listaIP = new ArrayList<String>();

    public static int numeroDeConexiones=0;
    public static int aleatorio;
    public static int aux=0;
    public static String miIp;
    public static ArrayList<String> tablaEnrutamiento = new ArrayList<String>();
    public static HashMap<Integer,String> fraseFinal = new HashMap<Integer,String >();
    public static int numeroNodo=3;
    public static int fin=numeroNodo*10 ;
    public static int inicio= fin-10;
    public static ArrayList<String> palabrasEnviar = new ArrayList<String>();

    public static void main(String[] args) throws IOException{



        palabras ordenarPalabras = new palabras(numeroNodo);
        fraseFinal.put(numeroNodo,palabras.listaPalabras.get(numeroNodo));
        ordenarPalabras.palabrasDisponibles.remove(numeroNodo);
        Iterator it = ordenarPalabras.palabrasDisponibles.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            palabrasEnviar.add(e.getKey()+" "+e.getValue());

        }

///////////////comprobar si la frase esta ordenada //////////////
        TimerTask comprobarFrase = new TimerTask() {
            @Override
            public void run() {
                if (fraseFinal.size()==10){
                    System.out.println("La frase esta ordenada: ");
                    Iterator it = fraseFinal.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry e = (Map.Entry)it.next();
                        System.out.print( e.getValue());
                    }

                }
            }
        };
        Timer timerComprobarFrase = new Timer();
        timerComprobarFrase.scheduleAtFixedRate(comprobarFrase, 0, 1000);
        ////////////////////////


        //levantar servicio en el puerto 8888

        MultiThreadedServer server = new MultiThreadedServer(8888);
        new Thread(server).start();
        miIp = InetAddress.getLocalHost().getHostAddress();

        // creacion de los clientes

        leerIP("lista.txt");
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();

        System.out.println("Intentando conectarse a un nodo...");
        while (!Cliente.estado&&!listaIP.isEmpty() ){
            Cliente.espera=false;
            aleatorio =(int) (Math.random() * (listaIP.size()));
            //System.out.println("probando la ip" +aleatorio);
            c1.setIp(listaIP.get(aleatorio));
            Thread tc1 = new Thread(c1);
            tc1.start();
            while (!Cliente.espera) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

       try {
            listaIP.remove(aleatorio);
       }
       catch (Exception e){}



        Cliente.estado = false;
        System.out.println("Intentando conectarse a otro nodo...");
        while (!Cliente.estado && !listaIP.isEmpty()){
            Cliente.espera=false;
            aleatorio =(int) (Math.random() * (listaIP.size()));
            //System.out.println("probando la ip" +aleatorio);
            c2.setIp(listaIP.get(aleatorio));
            Thread tc2 = new Thread(c2);
            tc2.start();
            while (!Cliente.espera) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }



    }
    public static void leerIP(String nombre) throws IOException {
        //en la variable "contenido" se almacenara el texto leido
        String contenido = "";
        int i =0;
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        archivo = new File(nombre);
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);
        String linea;
        while ((linea = br.readLine()) != null) {

            contenido = contenido + linea + "\n";
        }

        contenido = contenido.substring(0,contenido.length()-1);
        if (null != fr) {
            fr.close();
        }
        StringTokenizer tk = new StringTokenizer(contenido, "\n");

        while (tk.hasMoreTokens()){
            listaIP.add(tk.nextToken());
        }


    }
    public static boolean comprobarPalabras (String palabra, int key){
        if (palabras.palabrasObjetivo.containsValue(palabra)){
            System.out.println("se encontro la palabra: "+palabra);
            fraseFinal.put(key,palabra);
            return true;
        }
       else {return false;}
    }
}
