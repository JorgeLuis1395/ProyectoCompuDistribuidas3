import java.io.*;
import java.util.ArrayList;


public class ipScanner {
    public static ArrayList<String > ipRed = new ArrayList<String>();
    public static String[] aux;

    public  ipScanner() throws IOException {
        // leer archivo generado por ipscanner y guardarlo en un arraylist
        FileInputStream fstream = new FileInputStream("list.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null)   {
            ipRed.add(strLine);
        }
        br.close();
        //metodo para extraer solo las direcciones ip del arraylist y escribirlas en un archivo
        extraerIP(ipRed);

    }

    public static ArrayList extraerIP(ArrayList<String> ipRed) throws IOException {
        for (int i =0;i<ipRed.size();i++) {
            if(!ipRed.get(i).contains("ip")){
                ipRed.remove(i);
            }}
        ipRed.remove(0);
        ipRed.remove(ipRed.size()-1);

        for (int i=0;i<ipRed.size();i++){
            aux= ipRed.get(i).split("\"");
            File archivo = new File("prueba.txt");
            if (i==0){
                archivo.delete();
            }
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo,true));
            bw.write(aux[5]+"\n");
            bw.close();
        }

        return ipRed;
    }
}
