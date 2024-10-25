package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Grafica;
import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.Cola;

/**
 * Clase para establecer toda la logica del laberinto para solucionar.
 * <p>
 * Como solo ocupamos tareas especificas de solucion e implementar 
 * el algoritmo TrayectoriaMinima para encontrar la solucion
 * se hace uso de metodos estaticos, basicamente {@link#Soluciona} 
 * no tendra comportamiento para instanciar objetos.
 * </p>
 */
public class Soluciona{

    /* Crea una grafica de tipo Casilla*/
    private static Grafica<Casilla> roomGraph = new Grafica<>();

    /* Evita instancias de la clase. */
    private Soluciona() { }

    /**
     * Devuelve la lista de vecinos de la casilla actual en la matriz.
     * @param i el indice i.
     * @param j el indice j.
     * @param arr la matriz.
     * @return vecinos la lista de vecinos.
     */
    private static Lista<Casilla> vecindad(int i, int j, Casilla[][] arr){
	Lista<Casilla> vecinos = new Lista<>();
	try{
	    if (!arr[i][j].getNorte())
		vecinos.agrega(arr[i - 1][j]);
	}catch (ArrayIndexOutOfBoundsException ae){
	    
	}
	try{
	    if (!arr[i][j].getOeste())
		vecinos.agrega(arr[i][j - 1]);
	}catch (ArrayIndexOutOfBoundsException ae){
	    
	}
	try{
	    if (!arr[i][j].getSur())
		vecinos.agrega(arr[i + 1][j]);
	}catch (ArrayIndexOutOfBoundsException ae){
	    
	}
	try{
	    if (!arr[i][j].getEste())
		vecinos.agrega(arr[i][j + 1]); 
	}catch (ArrayIndexOutOfBoundsException ae){
	   
	}
	return vecinos;
    }

    /**
     * Convierte la matriz que representa el laberinto en una grafica.
     * @return grafica la grafica correspondiente a la matriz.
     */
    private static Grafica<Casilla> toGraph(Casilla[][] arr){
	Grafica<Casilla> grafica = new Grafica<>();
	for (int i = 0; i < arr.length; i++){
	    for (int j = 0; j < arr[i].length; j++){
	        if (!grafica.contiene(arr[i][j])) {
		    grafica.agrega(arr[i][j]);
		}
		for (Casilla c : vecindad(i, j, arr)) {
		    if (!grafica.contiene(c)) {
			grafica.agrega(c);
		    }
		    if (!grafica.sonVecinos(arr[i][j], c)) {
			grafica.conecta(arr[i][j], c);
			grafica.setPeso(arr[i][j], c, 1 + (arr[i][j].getPuntaje() + c.getPuntaje()));
		    }
		}
	    }
	}
	return grafica;
    }
    
     /**
     * Verifica que exista un vecino segun la orientacion
     * correspondiente.
     */
    private static boolean hayVecino(int i , int j, String orientacion, Casilla[][] arr){
	switch (orientacion) {
	case "NORTE":
	    if ((i - 1) < arr.length)
		return false;
	    break;
	case "OESTE":
	    if ((j - 1) < arr[i].length)
		return false;
	    break;
	case "ESTE":
	    if ((j + 1) >= arr[i].length)
		return false;
	    break;
	case "SUR":
	    if ((i + 1) >= arr.length)
		return false;
	    break;
	}
	return true;
    }

    /**
     * Implementa el algoritmo TrayectoriaMinima para resolver el
     * laberinto. La implementacion es respecto a la grafica obtenida
     * ademas se cuidan posibles excepciones para cualquier caso
     * extraordinario.
     * @param maze el laberinto.
     * @return lista regresa la trayectoria minima encontrada del laberinto.
     */
    public static Lista<Casilla> solucion(Laberinto maze){
	Casilla[][] arr = maze.get();
	Lista<Casilla> lista = new Lista<>();
	//Encuentra la entrada parte norte.
	try {
	    for (int j  = 0; j < arr[0].length; j++){
		if (!arr[0][j].getNorte() && !hayVecino(0, j, "NORTE",arr)){
		    arr[0][j].set_esEntrada(true);
		    lista.agrega(arr[0][j]);
		}
	    }
	}catch (NullPointerException ne) { }
	//Encuentra la entrada parte oeste.
	try{
	    for (int i = 0; i < arr.length; i++){
		if (!arr[i][0].getOeste() && !hayVecino(i, 0, "OESTE", arr)){
		    arr[i][0].set_esEntrada(true);
		    lista.agrega(arr[i][0]);
		}
	    }
	}catch (NullPointerException ne) {}
	//Encuentra la entrada parte sur.
	try{
	    for (int j = 0; j < arr[arr.length - 1].length; j++){
		if (!arr[arr.length - 1][j].getSur() && !hayVecino(arr.length - 1, j, "SUR",arr)){
		    arr[arr.length - 1][j].set_esEntrada(true);
		    lista.agrega(arr[arr.length - 1][j]);
		}
	    }
	}catch (NullPointerException ne) { }
	//Encuentra la entrada parte este.
	try{ 
	for (int i = 0; i  < arr.length; i++){
	    if (!arr[i][arr[i].length - 1].getEste() && !hayVecino(i, arr.length - 1, "ESTE",arr)){
		arr[i][arr[i].length - 1].set_esEntrada(true);
		lista.agrega(arr[i][arr[i].length - 1]);
	    }
	}
	} catch (NullPointerException ne){ }
	Casilla in = null;
	Casilla out = null;
	try{
	    in = lista.getPrimero();
	    lista.eliminaPrimero();
	    out = lista.getPrimero();
	}catch (NoSuchElementException ne){
	    System.err.println("¡Ups! el laberinto no tiene solucion, intenta generar otro o ingresa uno que lo posea. ");
	    System.exit(1);
	}
	lista.eliminaPrimero();
	
	Lista<VerticeGrafica<Casilla>> tray = toGraph(arr).trayectoriaMinima(in, out);
	lista.limpia();
	for (VerticeGrafica<Casilla> vC : tray){
	    Casilla c = vC.get();
	    for (int i = 0; i < arr.length; i++){
		for (int j = 0; j < arr[i].length; j++){
		    if (arr[i][j].equals(c)){
		        lista.agrega(arr[i][j]);
		    }
			
		}
	    }
	}
	trazaTray(lista, arr);
	return lista;
    }

    /**
     * Verifica que cada vecino sea el mismo en la matriz.
     * @return <code>true</code> en caso que sean vecinos
     * respecto a la matriz, falso en caso contrario.
     */
    private static boolean verifica(Casilla actual, Casilla vecina, Casilla[][] arr){
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[i].length; j++) {
	        if (arr[i][j].equals(actual)){
		    try{
			if (arr[i - 1][j].equals(vecina))
			    return true;
		    }catch (ArrayIndexOutOfBoundsException ae) { }
		    try{
			if (arr[i + 1][j].equals(vecina))
			    return true;
		    }catch (ArrayIndexOutOfBoundsException ae) { }
		    try{
			if (arr[i][j - 1].equals(vecina))
			    return true;
		    }catch (ArrayIndexOutOfBoundsException ae) { }
		    try{
			if (arr[i][j + 1].equals(vecina))
			    return true;
		    }catch (ArrayIndexOutOfBoundsException ae) { }
		}
	    }
	}
	return false;
    }
    
    /**
     * Encuentra y devuelve la subgrafica inducida que traza la trayectoria minima en el
     * laberinto.
     * @param lista la trayectoria minima.
     * @param arr la matriz.
     * @return la subgrafica inducida por la grafica del laberinto.
     */
    public static Grafica<Casilla> trazaTray(Lista<Casilla> lista, Casilla[][] arr){
	Grafica<Casilla> trazado = new Grafica<>();

	for (int i = 0; i < lista.getLongitud(); i++) {
	    Casilla casilla1 = lista.get(i);
	    if (!trazado.contiene(casilla1)) {
		trazado.agrega(casilla1);
	    }
	    
	    for (int j = i + 1; j < lista.getLongitud(); j++) {
		Casilla casilla2 = lista.get(j);
		if (verifica(casilla1, casilla2, arr) && !trazado.contiene(casilla2)) {
		    trazado.agrega(casilla2);
		}
	        
		if (verifica(casilla1, casilla2, arr)) {
		    trazado.conecta(casilla1, casilla2);
		}
	    }
	}
	return trazado;
    }
}
