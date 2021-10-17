package procesosjava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrarES {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.out.println("Error en el número de parámetros recibidos");
            }

            String datos = args[0];
            //String datos = 1 + "";

            File fichero = new File(Lanzador.PATH + "\\Clientes.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true));

            bw.write(datos);
            bw.newLine();
            bw.close();

        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
