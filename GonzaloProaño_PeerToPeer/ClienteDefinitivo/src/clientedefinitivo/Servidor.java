package clientedefinitivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author DaViD
 */
public class Servidor extends Thread {

    private DataOutputStream salidaDatos;
    private InputStreamReader inputStream;
    private Socket miServicio;
    private BufferedReader entrada;
    private static int numThreads = 1;

    public Servidor(Socket socket) throws IOException {

        this.miServicio = socket;
        numThreads++;

    }

    public void grabarArchivo(String datoI, String nombre) {
        try {
            String direccion1 = "C:\\Users\\DaViD\\Desktop\\epn\\SEMESTRE POLI\\Sexto Semestre\\computacion distribuida\\SERVER_HIGH_ENABLE\\" + nombre;

            File abrir = new File(direccion1);
            FileWriter w = new FileWriter(abrir);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);

            String aux = "\r" + datoI + "\r\n";

            wr.append(aux);

            wr.close();
            bw.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "La direccion del archivo esta incorrecta");
        }

    }

    public void enviarCliente(String Respuesta) {
        try {

            this.salidaDatos.writeUTF(Respuesta+"\r\n");
            System.out.println("\nSend to CLIENT :" + Respuesta);

        } catch (IOException ex) {
            System.out.println("Error al enviar msg al cliente");
        }
    }
    
    @Override
    public void run() {
        String cadena0, cadena2;
        String cadena="";

        try {
            inputStream = new InputStreamReader(miServicio.getInputStream());
            entrada = new BufferedReader(inputStream);
            salidaDatos = new DataOutputStream(miServicio.getOutputStream());
            String msgCliente = "";
            
            try{
          String direccion="C:\\Users\\DaViD\\Desktop\\epn\\SEMESTRE POLI\\Sexto Semestre\\computacion distribuida\\SERVER_HIGH_ENABLE\\Lipsum.txt";
			FileReader abrir=new FileReader(direccion);
			BufferedReader br = new BufferedReader(abrir);
			
                        
                        while((cadena=br.readLine())!=null)     {
                             cadena=cadena.trim();
                             System.out.println(cadena);
                            enviarCliente(cadena);
                            //System.err.println(cadena);
                        }   
                   abrir.close();    
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "hay un error"+ex);
		}
            
            String aux="";  
            while (true) {

                msgCliente = entrada.readLine().trim();

                System.out.println("RECIBE SERVER from" + miServicio.getInetAddress() +":" + msgCliente);

                Pattern pat = Pattern.compile("\\d+\\s\\w*");
                Matcher mat = pat.matcher(msgCliente.trim());
                if (mat.matches()) {
                    StringTokenizer tokens = new StringTokenizer(msgCliente," ");
                    
                    cadena0 = tokens.nextToken();
                    cadena2 = tokens.nextToken();
                    int valor=Integer.parseInt(cadena0) % 2 ;
                    
                    if (valor== 0) {
                        try {
                            String direccion1 = "C:\\Users\\DaViD\\Desktop\\epn\\SEMESTRE POLI\\Sexto Semestre\\computacion distribuida\\SERVER_HIGH_ENABLE\\ArchivoF.txt";

                            File abrir = new File(direccion1);
                            FileWriter w = new FileWriter(abrir);
                            BufferedWriter bw = new BufferedWriter(w);
                            PrintWriter wr = new PrintWriter(bw);

                                                           
                            aux+= "\r"+cadena0 + " " + cadena2 + "\r\n";
                            wr.append(aux);

                            wr.close();
                            bw.close();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "La direccion del archivo esta incorrecta");
                        }
                    } else {
                        enviarCliente(cadena0 + " " + cadena2);

                    }

                }else
                {
                   // enviarCliente("Envia las palabras");
                }
            }

        } catch (Exception e) {
            System.err.println("DESCONECTADO: " + miServicio + "  Hora: " + new Date()+"\n"+e);
            numThreads--;

        }
    }
}
