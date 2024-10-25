package mx.unam.ciencias.edd.proyecto3;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.MeteSaca;
import java.io.PrintStream;


/**
 * Clase de Lectura con metodos que fungiran coomo funciones. 
 * <p>
 * Para esto implementaremos metodos estaticos que seran invocados para 
 * realizar tareas especificas con la codificacion y decodificacion
 * de bytes. La clase, en particular, sus metodos trabajan con 
 * herramientas de bajo nivel nivel (lectura binaria, AND OR de bits,
 * escritura de bytes).
 * </p> 
 */

public class Lectura{

    /**
     * Clase interna privada. Solo vive dentro de la clase {@link#Lectura}
     * para manejar una excepcion particular.
     */
    private static class NoMAZEformat extends Exception{

	/* Constructor por omision. */
	public NoMAZEformat(){ }
        
	
    }

    /* Evita instancias de la clase.*/
    private Lectura(){ }

    /**
     * "Funcion" que hace lecturas de bytes dado un archivo .mze
     * por entrada estandar. Creamos un objeto de tipo
     * {@link#BufferedInputStream} para leer bytes crudos. La funcion
     * almacena cada byte del archivo en una lista auxiliar <code>Integer</code>
     * @return lista la lista de cada digito.
     */
    public static Lista<Integer> readBytes(){
	Lista<Integer> lista = new Lista<>();
	try{
	    BufferedInputStream in = new BufferedInputStream(System.in);
	    int i = 0;
	    i = in.read();
	    lista.agrega(i);
	    while (i != -1){
		i = in.read();
		lista.agrega(i);
	    }
	    in.close();
	}catch (IOException ioe) {
	    System.out.println("El archivo fue invalido al querer leer los bytes. ");
	}
	int menosUno = -1;
	lista.elimina(menosUno);
	return lista;
    }

    /**
     * Codifica en bytes un laberinto generado en un archivo .mze valido.
     * @param maze recibe el laberinto para codificar.
     */
    public static void codificar(Laberinto maze){
	Casilla[][] matriz = maze.get();
	Lista<Integer> contenido = new Lista<>();
	for (int i = 0; i < matriz.length; i++){
	    for (int j = 0; j < matriz[i].length; j++){
		contenido.agrega(matriz[i][j].getByte());
	    }
	}
	try{
	    int m = 0x4d, a = 0x41, z= 0x5a, e = 0x45;
	    int filas = maze.getFilas();
	    int cols = maze.getCols();
	    PrintStream ps = new PrintStream(System.out);
	    ps.write(m);
	    ps.write(a);
	    ps.write(z);
	    ps.write(e);
	    ps.write(filas);
	    ps.write(cols);
	    for (Integer b : contenido) {
		ps.write(b);
	    }
	    ps.close();
	}catch (Exception e){
	    System.out.println("No se puede escribir en el archivo. ");
	}
	
    }


   

    /**
     * Decodifica los bytes de una lista. La lista recibida es de tipo <code>Integer</code>
     * por lo que no sera necesario trabajar con la mascara 0xFF para quedarnos con un 
     * entero positivo. 
     * <p> 
     * Siguiendo con el formato de archivos .mze, verificamos que los
     * primeros 4 bytes correspondan al ASCII M-A-Z-E de cada caracter, posteriormente 
     * eliminamos de forma constante cada elemento de lista, los dos siguientes bytes
     * corresponden a las dimensiones del laberinto. Los siguientes son los cuartos
     * </p>.
     * @param codificacion la lista de bytes a decodificar.
     */
    public static Casilla[][] decodificar(Lista<Integer> codificacion) {
	int[] str = new int[4];
	int k = 0;
	for (int i = 0; i < 4; i++){
	    str[k] = codificacion.eliminaPrimero();
	    k++;
	}
	
	int filas = codificacion.getPrimero();
	codificacion.eliminaPrimero();
	int col = codificacion.getPrimero();
	// Recuperamos de forma segura el numero que se escribio.
	int recuperaNum1 = filas & 0xFF;
	int recuperaNum2 = col & 0xFF;
	if ((recuperaNum1 & 0x80) != 0)
	    recuperaNum1 |=  0xFFFFFF00;
	if ((recuperaNum2 & 0x80) != 0)
	    recuperaNum2 |= 0xFFFFFF00;
	try{
	    if (str[0] != 77 || str[1] != 65 || str[2] != 90 || str[3] != 69)
		throw new NoMAZEformat();
	    if (recuperaNum1 < 2 || recuperaNum2 < 2)
		throw new NoMAZEformat();
        }catch (NoMAZEformat noMaze){
	    System.err.println("El formato MAZE debe cumplir:\nEl numero de columnas y filas sea mayor o igual a 2.\nTener codificado en ASCII M-A-Z-E. ");
	    System.exit(1);
	}
	codificacion.eliminaPrimero();
	Casilla[][] maze = new Casilla[filas][col];
	Cola<Casilla> auxiliar = new Cola<>();
	int actual = 0;
	String bin = "";
	for (Integer en : codificacion){
	    // Nos quedamos con los 4 bits menos significativos.
	    if (en > 16){
		actual = en & 0x0F;
	    }else {
		actual = en;
	    }
	    bin = Integer.toBinaryString(actual);
	    Casilla cuarto = new Casilla(bin);
	    auxiliar.mete(cuarto);
	}
	for (int i = 0; i < maze.length; i++){
	    for (int j = 0; j < maze[i].length; j++){
		if (!auxiliar.esVacia()){
		    maze[i][j] = auxiliar.saca();
		}
	    }
	}
	return maze;
    }
}
