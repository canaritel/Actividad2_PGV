package procesosjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LeerClientes {

    synchronized public static void main(String[] args) {

        try {
            if (args.length != 1) {
                System.out.println("Error en el número de parámetros recibidos");
            }

            int segundos = Integer.parseInt(args[0]);
            //int segundos = 3;
            int total = 0, totalEntradas = 0, totalSalidas = 0;

            String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

            File fichero = new File(Lanzador.PATH + "\\Clientes.txt");
            FileReader fr;

            //while (true) {
            fr = new FileReader(fichero);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                if (Integer.parseInt(line) == 1) {
                    totalEntradas++;
                } else {
                    totalSalidas++;
                }
            }

            timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            total = totalEntradas - totalSalidas;
            System.out.println(timeStamp + " " + "Hay " + total + " clientes");

            br.close();
            pausa(segundos);

        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void pausa(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
