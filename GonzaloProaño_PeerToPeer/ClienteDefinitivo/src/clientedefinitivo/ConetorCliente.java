package clientedefinitivo;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ConetorCliente extends Thread {

    private Socket sokett;
    private InputStreamReader entradaSocket;
    private InputStream inputStream;
    private DataInputStream input;
    private DataOutputStream salida;
    private BufferedReader entrada;

    public ConetorCliente(String ip, int puerto) {
        try {

            sokett = new Socket(ip, puerto);

            entradaSocket = new InputStreamReader(sokett.getInputStream());
            entrada = new BufferedReader(entradaSocket);
            inputStream = sokett.getInputStream();
            input = new DataInputStream(inputStream);

            salida = new DataOutputStream(sokett.getOutputStream());

        } catch (Exception e) {
            System.out.println("SERVER CAIDO 1" + e);
        }

    }

    @Override
    public void run() {

        String cadena1,cadena2;

        while (true) {
            try {

                cadena1 = entrada.readLine();
                System.out.println("\nRecibe cliente:" + cadena1);
                Interfaz.converTA.append("\nRecibe cliente:" + cadena1);

                Pattern pat = Pattern.compile(".*\\d.*");
                Matcher mat = pat.matcher(cadena1);

                if (mat.matches()) {
                    StringTokenizer tokens = new StringTokenizer(cadena1, " ");
                    tokens.nextToken();
                    cadena2 = tokens.nextToken();

                } else {

                }

            } catch (IOException e) {
                System.out.println("UN NODO SE HA DESCONECTADO" + e);
                break;
            }
        }

    }

    public void EnviaServidor(String cadena) {
        try {
            this.salida.writeUTF(cadena + "\n");
            Interfaz.converTA.append("\nSend to server:" + cadena);

        } catch (IOException e) {
            System.out.println("SERVER CAIDO 2" + e);
        }
    }
}
