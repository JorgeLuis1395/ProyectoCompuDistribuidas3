import java.io.*;
import java.util.HashMap;

public class palabras {
    public int getNumeroNodo() {
        return numeroNodo;
    }

    public void setNumeroNodo(int numeroNodo) {
        this.numeroNodo = numeroNodo;
    }

    private int numeroNodo;
    public static HashMap<Integer,String> listaPalabras = new HashMap<Integer,String >();
    public static HashMap<Integer,String> palabrasObjetivo = new HashMap<Integer,String >();
    public static HashMap<Integer,String> palabrasDisponibles = new HashMap<Integer,String >();

    public palabras(int numeroNodo) throws IOException {
        this.numeroNodo= numeroNodo;

        int i=0;
        // leer archivo generado por ipscanner y guardarlo en un arraylist
        FileInputStream fstream = new FileInputStream("prueba.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null)   {
           String[] separar = strLine.split(" ");
           listaPalabras.put(Integer.valueOf(separar[0]),separar[1]);
        }
        br.close();
        fraseObjetivo(numeroNodo);
        palabrasQTocaron(numeroNodo);



    }

    public static void fraseObjetivo(int numeroNodo){
        int inicio, fin ;
        fin = 10*numeroNodo;
        inicio=fin -10;
        for(int i = inicio;i<fin;i++){
            palabrasObjetivo.put(i,listaPalabras.get(i));

        }

    }
    public static void palabrasQTocaron(int numeroNodo){
        int aux= numeroNodo;
        palabrasDisponibles.put(numeroNodo,listaPalabras.get(numeroNodo));
        for (int i =0;i<9;i++){
            aux=aux+7;
            palabrasDisponibles.put(aux,listaPalabras.get(aux));
          //  System.out.println(aux);
        }
    }
}
