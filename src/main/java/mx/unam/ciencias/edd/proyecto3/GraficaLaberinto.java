package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Diccionario;
import java.lang.ArrayIndexOutOfBoundsException;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Grafica;
/**
 * Clase para graficar el laberinto en el formato
 * SVG. Cada GraficaLaberinto es un Dibujo, por lo que
 * podemos implementar la interfaz Dibujo.
 */

public class GraficaLaberinto implements Dibujo{
    /* Altura. */
    private int height;
    /* Ancho. */
    private int width;
    /* Laberinto a graficar. */
    private Laberinto maze;
    /* Construye una cadena de lenguaje SVG*/
    private final StringBuilder str;
    /* Una lista que indica las casillas solucion del laberinto. */
    private Lista<Casilla> rastrea;
    /* Grafica para trazar la trayectoriaminima del laberinto. */
    private Grafica<Casilla> trazandoTrayMinima;
    
    /**Constructor con parametros. Recibe las dimensiones
     * de la matriz.
     * @param width el ancho de la matriz.
     * @param height el alto de la matriz.
     */
    public GraficaLaberinto(int width, int height, Laberinto maze){
	this.width = width;
	this.height = height;
	this.maze = maze;
	str = new StringBuilder();
	rastrea = Soluciona.solucion(this.maze);
	trazandoTrayMinima = Soluciona.trazaTray(rastrea, this.maze.get());
    }
    /* Constante numerica para posicionar el grafico respecto al eje X. */
    private static final double MAX_CEN_X = 0.5;
    /* Constante numerica para posicionar el grafico respecto al eje Y. */
    private static final double MAX_CEN_Y = 0.6;

    /**
     * Grafica el laberinto a partir de su matriz. Cada instancia de 
     * {@link#GraficaLaberinto} guarda una coordenada (x, y) ubicada
     * en la esquina inferior izquierda del grafico SVG, recibe 
     * la dimension uniforme de las casillas y envuelve a cada instancia
     * de {@link#Casilla}. 
     * @return lista la lista de todas las casillas envueltas en instancias de
     * {@link#Casilla}. 
     */
    private Lista<GraficaCasilla> graficaMaze(){
        Casilla[][] arr = maze.get();
		int num = maze.getElementos()/(arr.length*arr[0].length);
		Lista<GraficaCasilla> lista = new Lista<>();
		int x = 1;
		int y = num + 1;
		for (int i = 0; i < arr.length; i++){
		    for (int j = 0; j < arr[i].length; j++){
				GraficaCasilla re = new GraficaCasilla(arr[i][j], x, y, num);
				if (rastrea.contiene(re.get())){
				    re.setRastreador(true);
				}
				lista.agrega(re);
				x += num; 
		    }
		    x = 1;
		    y += num;
		}
	
		return lista;
   	 }
    
    /**
     * Grafica la solucion utilizando una instancia de {@link#Grafica} para trazarla.
     * Primero filtramos en <code>lista</code> las que contengan el rastreador correspondiente 
     * activado. Luego verificamos su contencion en la grafica y si es un vecino valido para 
     * trazar la linea.
     */
    public void graficaSolucion(){
	Lista<GraficaCasilla> soluciones = graficaMaze();
	Lista<GraficaCasilla> lis = new Lista<>();
	for (GraficaCasilla c : soluciones){
	    if (c.getRastreador()){
		lis.agrega(c);
	    }
	}
	for (int i = 0; i < lis.getLongitud(); i++){
	    for (int j = i + 1; j < lis.getLongitud(); j++ ){		
		if (trazandoTrayMinima.contiene(lis.get(i).get()) && trazandoTrayMinima.contiene(lis.get(j).get())){
		    if (trazandoTrayMinima.sonVecinos(lis.get(i).get(), lis.get(j).get()) && permitida(lis.get(i).get(), lis.get(j).get())){
			str.append(lineas(lis.get(i).getCoordX() + MAX_CEN_X, lis.get(j).getCoordX() + MAX_CEN_X, lis.get(i).getCoordY() - MAX_CEN_Y, lis.get(j).getCoordY() - MAX_CEN_Y));
		    }
		}
	    }
	}

	
	colocaCasillas(soluciones);
    }


    /**
     * Regresa la verificacion de las vecindades, es decir, si no hay puertas
     * que obstruyan el paso.
     * @param actual la casilla actual.
     * @param vecina la casilla vecina.
     * @return <code>true</code> si esta perimitida, falso caso contrario.
     */
    private boolean permitida(Casilla actual, Casilla vecina){
	Casilla[][] arr = this.maze.get();
	for (int i = 0; i < arr.length; i++){
	    for (int j = 0; j < arr[i].length; j++){
			if (arr[i][j].equals(actual)){
			    try{
				if (arr[i - 1][j].equals(vecina) && !arr[i][j].getNorte() && !arr[i - 1][j].getSur()){
				    return true;
				}
				
			    }catch (ArrayIndexOutOfBoundsException ae) { }
			    try{
					if (arr[i + 1][j].equals(vecina) && !arr[i][j].getSur() && !arr[i + 1][j].getNorte()){
					    return true;
				}
			    }catch (ArrayIndexOutOfBoundsException ae) { }
			    try{
					if (arr[i][j - 1].equals(vecina) && !arr[i][j].getOeste() && !arr[i][j - 1].getEste()){
					    return true;
				}
				    }catch (ArrayIndexOutOfBoundsException ae) { }
			    try{
					if (arr[i][j + 1].equals(vecina) && !arr[i][j].getEste() && !arr[i][j + 1].getOeste()){
				    return true;
				}
			
			    }catch (ArrayIndexOutOfBoundsException ae) { }
			}
	    }
	}
	return false;
    }
    /**
     * Coloca las casillas una vez que se ha generado el laberinto.
     * El metodo almacena la represntacion en cadena de cada instancia
     * {@link#GraficaCasilla}, a gusto de cada quien, pintamos el circulo
     * inicio y final de distintos colores, para lograr esto utilizamos 
     * los metodos <code>contains</code> y <code>replaceFirst</code> de
     * la clase {@link#String}.
     * @param graficadora la lista de casillas para graficar. 
     */
    private void colocaCasillas(Lista<GraficaCasilla> graficadora){
	boolean foundCircle = false;
	for (GraficaCasilla c : graficadora){
	    if (c.toString().contains(" <circle") && !foundCircle){
		str.append(c.toString().replaceFirst("fill= \"red\"","fill= \"purple\""));
		foundCircle = true;
	    }else{
		str.append(c.toString());
	    }
	}
    }
    
    
    
    /**
     * Regresa la representacion en cadena de cualquier instancia {@link#GraficaLaberinto}
     * @return str.toString() la representacion en cadena en formato en SVG.
     */
    @Override public String toString(){
	int alto = maze.get().length + 3;
	int ancho = maze.get()[0].length + 3;
	str.append("<svg height= \""+(alto)+"\" width= \""+(ancho)+"\">");
	graficaSolucion();
	str.append("\n</svg>");
	return str.toString();
    }

    
}
