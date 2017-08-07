package proyectofinal3;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProyectoFinal3 {
    private String IP="";
    private ArrayList<String> ListaConecciones=new ArrayList<>();
    private ParteCliente cliente[]=new ParteCliente[3];
    private int contadorCliente=0;
    private String Informacion[][]=new String[7][2];

    public ProyectoFinal3(){
        try {
            LecturaArchivoInf("Informacion.txt");
            //VerNodosRed();
            System.out.println("Nodo: "+IP);
            GenerarServidores EsperandoConecciones=new GenerarServidores(cliente);
            EsperandoConecciones.start();
            while(true){
                LecturaArchivoNodos("ipes.txt");
                sleep(60000);
                //VerNodosRed();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProyectoFinal3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProyectoFinal3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Conectarme(ParteCliente cliente,String IP,int puerto){
        cliente=new ParteCliente(IP,puerto,ListaConecciones,Informacion);
        cliente.start();
    }
    
    public void LecturaArchivoInf(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        int contador=0;
        FileReader archivoLeido = new FileReader(archivo);
        BufferedReader bufferArchivo = new BufferedReader(archivoLeido);
        cadena = bufferArchivo.readLine();
        while((cadena = bufferArchivo.readLine())!=null){
            if(contador<7){
                StringTokenizer trozos=new StringTokenizer(cadena," ");
                while(trozos.hasMoreTokens()){
                    Informacion[contador][0]=trozos.nextToken();
                    Informacion[contador][1]=trozos.nextToken();
                }
                contador++;
            }   
        }
        bufferArchivo.close();
    }
    
    public void LecturaArchivoNodos(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        String palabra;
        int contador;
        int puerto=8888;
        FileReader archivoLeido = new FileReader(archivo);
        BufferedReader bufferArchivo = new BufferedReader(archivoLeido);
        cadena = bufferArchivo.readLine();
        while((cadena = bufferArchivo.readLine())!=null && ListaConecciones.size()<3)
                if(!cadena.equals(IP) && !ListaConecciones.contains(cadena)){
                    Conectarme(cliente[ListaConecciones.size()],cadena,puerto);
            }
        bufferArchivo.close();
    }
    
    private void VerNodosRed(){
        try {
            ArrayList<String> listaNodos=new ArrayList<>(); 
            String salida = null;
            int contador=0;
            File fichero = new File("ipes.txt");
            fichero.delete();
            Process process = Runtime.getRuntime().exec("arp -a");
            InputStreamReader entrada = new InputStreamReader(process.getInputStream());
            BufferedReader stdInput = new BufferedReader(entrada);
            if((salida=stdInput.readLine()) != null){
                System.out.println("Comando ejecutado Correctamente");
                while ((salida=stdInput.readLine()) != null){
                    String[] ArrayComando = salida.split(" ");
                        if(contador==0)
                            IP=ArrayComando[1];
                        else
                            if(contador!=1)
                                listaNodos.add(ArrayComando[2]);
                        contador++;
                }
                GuardarArchivo(listaNodos);
            }else
                System.out.println("No se a producido ninguna salida");
        } catch (IOException ioe) {
            System.out.println (ioe);
        }
    }
    
    private void GuardarArchivo(ArrayList<String> lista) throws IOException{
        BufferedWriter out = null;   
        try {   
            out = new BufferedWriter(new FileWriter("ipes.txt", true));
            for(int i=0;i<lista.size();i++){
                out.write(lista.get(i));
                out.newLine();
            }
            } catch (IOException e) {   
            // error processing code   
            } finally {   
                    if (out != null) {   
                out.close();   
            }      
        }
    }
    
    public static void main(String[] args) {
        new ProyectoFinal3();
    }
}
