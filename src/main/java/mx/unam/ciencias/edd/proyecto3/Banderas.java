package mx.unam.ciencias.edd.proyecto3;
import java.util.Random;
import java.lang.NumberFormatException;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Clase para poder verificar las banderas permitidas.
 */
public class Banderas{
    
    /* Evita instancias. */
    private Banderas() { }

    /**
     * Revisa la bandera -g para autorizar la generacion de 
     * un laberinto nuevo.
     */
    public static boolean param_g(String[] args){
	for (int i = 0; i < args.length; i++)
	    if (args[i].equals("-g"))
		return true;
	return false;
    }

    /**
     * Regresa el valor entero positivo correspondiente al encontrar `-w` como bandera,
     * es decir, las filas del laberinto.
     *
     * @param args los argumentos de línea de comandos.
     * @return el valor entero positivo de las filas si se proporciona, de lo contrario `-1`.
     */
    public static int param_w(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-w")) {
                    int N = Integer.parseInt(args[i + 1]);
                    if (N >= 2) {
                        return N;
                    } else {
                        System.err.println("Asegúrate de que el número sea un entero positivo mayor o igual a 2 para -w.");
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ae) {
            System.err.println("Asegúrate de que haya un valor numérico después del argumento \"-w\".");
        } catch (NumberFormatException nfe) {
            System.err.println("Asegúrate de que el argumento después de \"-w\" sea un valor numérico.");
        }
        return -1;
    }

    /**
     * Regresa el valor entero positivo correspondiente al encontrar `-h` como bandera,
     * es decir, las columnas del laberinto.
     *
     * @param args los argumentos de línea de comandos.
     * @return el valor entero positivo de las columnas si se proporciona, de lo contrario `-1`.
     */
    public static int param_h(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-h")) {
                    int N = Integer.parseInt(args[i + 1]);
                    if (N >= 2) {
                        return N;
                    } else {
                        System.err.println("Asegúrate de que el número sea un entero positivo mayor o igual a 2 para -h.");
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ae) {
            System.err.println("Asegúrate de que haya un valor numérico después del argumento \"-h\".");
        } catch (NumberFormatException nfe) {
            System.err.println("Asegúrate de que el argumento después de \"-h\" sea un valor numérico.");
        }
        return -1;
    }
}
