package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Lista;
import java.io.IOException;

/**
 * Clase principal.
 * <p>
 * Generador y resolucion de laberintos implementando el
 * algoritmo TrayectoriaMinima. El programa es capaz de 
 * generar y resolver laberintos validos, entiendase por
 * validos aquellos que tiene solucion.
 * </p>
 * @author Marco Antonio Raya Garcia
 */
public class Proyecto3{
    
    /**
     * Metodo principal
     * @args los argumentos en consola.
     */
    public static void main(String[] args){

	if (args.length == 0 && System.in != null && !inputDataFound()){
	    System.out.println("Uso->\nOutput: java -jar target/proyecto3.jar -g -w X -h Y\nInput: cat archivo.mze | java -jar target/proyecto3.jar");
	}
	
        if (System.in != null && inputDataFound()){
	    Casilla[][] arr = Lectura.decodificar(Lectura.readBytes());
	    Laberinto maze = new Laberinto(arr);
	    int filas = maze.get().length;
	    int cols = maze.get()[0].length;
	    GraficaLaberinto graficadora = new GraficaLaberinto(filas, cols, maze);
	    System.out.println(graficadora.toString());
	}
	if (Banderas.param_g(args)){
	    int filas = Banderas.param_w(args);
	    int cols = Banderas.param_h(args);
	    if (filas > 0 && cols > 0){
		Laberinto maze = new Laberinto(filas, cols);
		Lectura.codificar(maze);
	    }
	}
    }

    /**
     * Checa los datos ingresados por entrada estandar.
     * @return verdadero si hay datos disponibles, falso
     * en caso contrario cubriendo la excepcion.
     */
    private static boolean inputDataFound() {
        try {
            return (System.in.available() > 0);
        } catch (IOException e) {
            return false;
        }
    }
    
}
