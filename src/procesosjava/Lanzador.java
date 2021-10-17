package procesosjava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class Lanzador {

    /**
     * *** POR FAVOR INDIQUE EN PATH LA RUTA HACIA LA CARPETA CLASSES ***
     */
    static String PATH = "D:\\OpenBeansProjects\\Procesos_Competidores\\build\\classes";
    static String COMANDO = "procesosjava.RegistrarES"; //Clase del Proceso a ejecutar
    static String COMANDO2 = "procesosjava.LeerClientes"; //Clase del Proceso a ejecutar

    public static void main(String[] args) {

        try {

            File fichero = new File(Lanzador.PATH + "\\Clientes.txt");
            if (fichero.delete()) {
            }

            if (args.length != 2) {
                System.out.println("Error en el número de parámetros recibidos");
            }

            System.out.println("*****************************************************************************");
            System.out.println("***********************    PROCESOS COMPETIDORES   **************************");
            System.out.println("*****************************************************************************");

            int numero = 0, cuentaEntradas = 0, cuentaSalidas = 0;
            //recibimos el argumento y lo convertimos a entero
            int repite = Integer.parseInt(args[0]);
            int segundos = Integer.parseInt(args[1]);

            System.out.println("Ejecutando el Proceso Registrar Clientes...\n");
            pausa(2);

            for (int i = 1; i <= repite; i++) {
                numero = generarNumero();

                if (numero == 1) {
                    cuentaEntradas++;
                } else {
                    cuentaSalidas++;
                }

                if (cuentaSalidas > cuentaEntradas) {
                    numero = 1;
                    cuentaSalidas--;
                    cuentaEntradas++;
                }

                lanzadorProcesoRegistrar(numero);
            }

            System.out.println("entradas: " + cuentaEntradas + "\nsalidas: " + cuentaSalidas);
            System.out.println("El proceso Registrar ha finalido");

            //Ejecutamos el Proceso2
            System.out.println("Ejecutando el Proceso Leer Clientes...\n");
            while (true) {
                lanzadorProcesoLeer(segundos);
            }

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error :" + ex.getMessage());
        }

    }

    private static int generarNumero() {
        Random r = new Random();
        int valor = r.nextInt(2) + 1;

        if (valor == 2) {
            return -1;
        } else {
            return 1;
        }
    }

    private static void lanzadorProcesoRegistrar(int numero) {

        File path = new File(PATH);

        try {
            ProcessBuilder processRegistrar;
            processRegistrar = new ProcessBuilder("java", COMANDO, numero + "");
            processRegistrar.directory(path);
            processRegistrar.redirectError(new File("erroresRegistrar.txt"));
            Process process = processRegistrar.start();
            process.waitFor();
            //enviarDatos(process, numero + "");

        } catch (IOException | InterruptedException ex) {
            System.err.println("Error " + ex.getMessage());
        }

    }

    private static void lanzadorProcesoLeer(int segundos) {

        File path = new File(PATH);

        try {
            ProcessBuilder processRegistrar;
            processRegistrar = new ProcessBuilder("java", COMANDO2, segundos + "");
            processRegistrar.directory(path);
            processRegistrar.redirectError(new File("erroresLeer.txt"));
            Process process = processRegistrar.start();
            process.waitFor();
            recibirDatos(process);

        } catch (IOException | InterruptedException ex) {
            System.err.println("Error " + ex.getMessage());
        }

    }

    private static void pausa(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void recibirDatos(Process process) {
        InputStream is = process.getInputStream();
        int letra = 0;
        try {
            while ((letra = is.read()) != -1) {
                System.out.print((char) letra);
            }
        } catch (IOException ex) {
            System.err.println("Error " + ex.getMessage());
        }
    }

    //Este método no se usa para estos Procesos, pero es importante tenerlos declarados por si se usan
    private static void enviarDatos(Process process, String datos) {
        OutputStream os = process.getOutputStream();
        OutputStreamWriter ow = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(ow);
        try {
            bw.write(datos);
            bw.close();

        } catch (IOException ex) {
            System.err.println("Error " + ex.getMessage());
        }
    }

}
