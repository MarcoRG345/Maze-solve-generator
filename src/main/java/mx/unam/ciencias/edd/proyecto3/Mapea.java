package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;


/**
 * Clase para mapear valores binarios con su respectivo numero entero.
 * Las combinaciones posibles para cada casilla del laberinto son del
 * 1 - 14, o bien, 0001 - 1110 en binaio. Java no permite implementar
 * funciones, por lo que para darle la vuelta implementaremos metodos
 * estaticos.
 */
public class Mapea{
    
    /* Crea un diccionario con llave binaria tipo String y su valor entero. */
    private static  Diccionario<String, Integer> dicc;

    /* Evita instancias. */
    private Mapea() { }

    /**
     * "Funcion" que obtiene todas las posibles combinaciones binarias del
     * 0001 - 1110, nos apoyamos del metodo toBinaryString de la clase Integer
     * para obtener su representacion binaria en cadena y lo asosciamos con el
     * valor entero para posteriormente agregarlo al diccionario.
     * @return dicc el diccionario con las combinaciones.
     */
    public static Diccionario<String, Integer> combinaciones(){
	dicc = new Diccionario<>();
	for (int i = 1; i <= 14; i++){
	    String bin = Integer.toBinaryString(i);
	    while (bin.length() < 4){
		bin = "0" + bin;
	    }
	    
	    dicc.agrega(bin, i);
	}
	
	return dicc;
    }
}
