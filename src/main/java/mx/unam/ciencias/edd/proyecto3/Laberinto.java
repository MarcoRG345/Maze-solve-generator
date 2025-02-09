package mx.unam.ciencias.edd.proyecto3;
import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Lista;

public class Laberinto{

    protected Casilla[][] maze;

    private Random rand;

    public Laberinto(int n, int m){
	maze = new Casilla[n][m];
	rand = new Random();
	generado();
    }

    public Laberinto(Casilla[][] maze){
	this.maze = maze;
	marcaEntrada(maze);
    }

    /**
     * Coloca cada instancia de Casilla en la matriz
     * con la celda correspondiente.
     */
    private void generado(){
	for (int i  = 0; i < maze.length; i++){	    
	    for (int j = 0; j < maze[i].length; j++){
		Casilla casilla = new Casilla();
		maze[i][j] = casilla;
	    }
	}
	dfsMaze();
	generaEntradas();
	generaEntradas();
	marcaEntrada(maze);
    }
    
    /**
     * Tira la puerta actual de la casilla manteniendo la 
     * congruencia con el vecino exitente de la misma. 
     */
    private void tiraPuerta(Casilla casilla, Casilla vecino){
	for (int i = 0; i < maze.length; i++){
	    for (int j = 0; j < maze[i].length; j++ ){
		if (casilla.equals(maze[i][j])){
		    // Norte.
		    if ((i - 1) >= 0 && vecino.equals(maze[i-1][j])){
			maze[i - 1][j].setSur(false);
			maze[i][j].setNorte(false);
		    }
		    // Sur.
		    else if ((i+1) < maze.length && vecino.equals(maze[i+1][j])){
			maze[i + 1][j].setNorte(false);
			maze[i][j].setSur(false);
		    }
		    // Oeste.
		    else  if ((j - 1) >= 0 && vecino.equals(maze[i][j-1])){
			maze[i][j - 1].setEste(false);
			maze[i][j].setOeste(false);
		    }
		    // Este.
		    else  if ((j + 1)<maze[i].length && vecino.equals(maze[i][j+1])){
			maze[i][j + 1].setOeste(false);
			maze[i][j].setEste(false);
		    }
		}
	    }
	}
    }

    /**
     * Immplementa el algoritmo Depth-First-Search (DFS) 
     * para generar un laberinto utilizando el algoritmo auxiliar 
     * backtracking. La implementacion es iterativa
     * apoyada por una stack.
     */
    private void dfsMaze(){
        Pila<Casilla> pila = new Pila<>();
	Lista<Casilla> vecinos = new Lista<>();
	maze[0][0].marcarVisita(true);
	pila.mete(maze[0][0]);
	while (!pila.esVacia()) {
	    Casilla actual = pila.saca();
	    int i = get_i(actual);
	    int j = get_j(actual);
	    vecinos = noVisitados(i, j);
	    if (!vecinos.esVacia()){
		Casilla v = vecino(i, j);
		v.marcarVisita(true);
		pila.mete(actual);
		pila.mete(v);
		tiraPuerta(actual, v);
	    }
	}
    }

      
    public void marcaEntrada(Casilla[][] maze){
	//Lista<Casilla> lista = new Lista<>();
	//Encuentra la entrada parte norte.
	try {
	    for (int j  = 0; j < maze[0].length; j++){
		if (!maze[0][j].getNorte() && !hayVecino(0, j, "NORTE")){
		    maze[0][j].set_esEntrada(true);
		    //lista.agrega(maze[0][j]);
		}
	    }
	}catch (NullPointerException ne) { }
	//Encuentra la entrada parte oeste.
	try{
	    for (int i = 0; i < maze.length; i++){
		if (!maze[i][0].getOeste() && !hayVecino(i, 0, "OESTE")){
		    maze[i][0].set_esEntrada(true);
		    //lista.agrega(maze[i][0]);
		}
	    }
	}catch (NullPointerException ne) {}
	//Encuentra la entrada parte sur.
	try{
	    for (int j = 0; j < maze[maze.length - 1].length; j++){
		if (!maze[maze.length - 1][j].getSur() && !hayVecino(maze.length - 1, j, "SUR")){
		    maze[maze.length - 1][j].set_esEntrada(true);
		    //lista.agrega(maze[maze.length - 1][j]);
		}
	    }
	}catch (NullPointerException ne) { }
	//Encuentra la entrada parte este.
	try{ 
	for (int i = 0; i  < maze.length; i++){
	    if (!maze[i][maze[i].length - 1].getEste() && !hayVecino(i, maze.length - 1, "ESTE")){
		maze[i][maze[i].length - 1].set_esEntrada(true);
		//lista.agrega(maze[i][maze[i].length - 1]);
	    }
	}
	} catch (NullPointerException ne){ }
	//return lista;
    }

    
    /**
     * Verifica que exista un vecino segun la orientacion
     * correspondiente.
     */
    private boolean hayVecino(int i , int j, String orientacion){
	switch (orientacion) {
	case "NORTE":
	    if ((i - 1) < maze.length)
		return false;
	    break;
	case "OESTE":
	    if ((j - 1) < maze[i].length)
		return false;
	    break;
	case "ESTE":
	    if ((j + 1) >= maze[i].length)
		return false;
	    break;
	case "SUR":
	    if ((i + 1) >= maze.length)
		return false;
	    break;
	}
	return true;
    }
    /**
     * Devuelve la lista de vecinos mo visitados de una
     * casilla. El algoritmo implementa uso de excepciones
     * en caso de que el indice sea invalido por cada 
     * punto cardinal en el que se encuentre la casilla.
     * @param i la posicion i de la casilla.
     * @param j la poscion j de la casilla.
     * @return vecinos la lista de vecinos no visitados. 
     */
    private Lista<Casilla> noVisitados(int i, int j){
	Lista<Casilla> vecinos = new Lista<>();
	try{
	    if (maze[i - 1][j].seVisito() == false)
		vecinos.agrega(maze[i - 1][j]);
	}catch (ArrayIndexOutOfBoundsException ae){
	    
	}
	try{
	    if (maze[i][j - 1].seVisito() == false)
		vecinos.agrega(maze[i][j - 1]);
	}catch (ArrayIndexOutOfBoundsException ae){
	    
	}
	try{
	    if (maze[i + 1][j].seVisito() == false)
	    vecinos.agrega(maze[i + 1][j]);
	}catch (ArrayIndexOutOfBoundsException ae){
	    
	}
	try{
	    if (maze[i][j + 1].seVisito() == false)
		vecinos.agrega(maze[i][j + 1]); 
	}catch (ArrayIndexOutOfBoundsException ae){
	   
	}
	return vecinos;
    }
    
    

    private Casilla vecino(int i, int j){
	Lista<Casilla> lista = noVisitados(i, j);
	int random = rand.nextInt(lista.getLongitud());
	return lista.get(random);
    }

    /**
     * Regresa el laberinto.
     * @return maze el laberinto en cuestion.
     */
    public Casilla[][] get(){
	return maze;
    }

    /**
     * Genera entradas aleatorias. Como se llama dos veces
     * el metodo, en caso de caer con una entrada simplemente 
     * verificamos la existencia de alguno de los vecinos.
     */
    private void generaEntradas(){
	int random = rand.nextInt(4) + 1;
	int r = 0;
	switch (random){
	case 1:
	    // Parte norte.
	    r = rand.nextInt(maze[0].length - 1);
	    if (maze[0][r].getEsEntrada()){
		try{
		    maze[0][r + 1].setNorte(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) {}
		try{
		    maze[0][r - 1].setNorte(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) { }
	    }
	    maze[0][r].setNorte(false);
	    break;
	case 2:
	    // Parte Sur.
	    r = rand.nextInt(maze[0].length - 1);
	    if (maze[maze.length - 1][r].getEsEntrada()){
		try{
		    maze[maze.length][r + 1].setSur(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) {}
		try{
		    maze[maze.length][r - 1].setSur(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) { }
	    }
	    maze[maze.length - 1][r].setSur(false);
	    break;
	case 3:
	    // Parte Este.
	    r = rand.nextInt(maze.length - 1);
	    if (maze[r][maze[r].length - 1].getEsEntrada()){
		try{
		    maze[r + 1][maze[r].length - 1].setEste(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) {}
		try{
		    maze[r - 1][maze[r].length - 1].setEste(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) { }
	    }
	    maze[r][maze[r].length - 1].setEste(false);
	    break;
	case 4:
	    //Parte Oeste.
	    r = rand.nextInt(maze[0].length - 1);
	    if (maze[r][0].getEsEntrada()){
		try{
		    maze[r][0].setOeste(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) {}
		try{
		    maze[r][0].setOeste(false);
		    return;
		}catch (ArrayIndexOutOfBoundsException as) { }
	    }
	    maze[r][0].setOeste(false);
	    break;
	}
    }


    /** @return filas.*/
    public int getFilas() {
	int cont = 0;
	for (int i = 0; i < maze.length; i++){
	    cont++;
	}
	return cont;
    }
    
    /** @return columnas.*/
    public int getCols() {
	int cont = 0;
	for (int i = 0; i < maze[0].length; i++){
	    cont++;
	}
	return cont;
    }
    
    /**
     * Regresa el numero de elementos del laberinto.
     * @return cont el contador de elementos.
     */
    public int getElementos(){
	int cont = 0;
	for (int i = 0; i < maze.length; i++){
	    for (int j = 0; j < maze[i].length; j++){
		cont++;
	    }
	}
	return cont;
    }
    
    /**
     * Regresa la coordenada i de la matriz en cuestion.
     * @param casilla la casilla arbitraria.
     * @return i el indice i donde se encuentra.
     */
    public int get_i(Casilla casilla){
	for (int i = 0; i < maze.length; i++){
	    for (int j = 0; j < maze[i].length; j++){
		if (casilla.equals(maze[i][j]))
		    return i;
	    }
	}
	return -1;
    }

    /**
     * Regresa la coordenada j de la matriz en cuestion.
     * @param casilla la casilla arbitraria.
     * @return j el indice j donde se encuentra.
     */
    public int get_j(Casilla casilla){
	for (int i = 0; i < maze.length; i++){
	    for (int j = 0; j < maze[i].length; j++){
		if (casilla.equals(maze[i][j]))
		    return j;
	    }
	}
	return -1;
    }

    
   
    
    @Override public String toString(){
	StringBuilder str  = new StringBuilder();
	for (int i = 0; i < maze.length; i++){
	    for (int j = 0; j < maze[i].length; j++){
		Casilla actual = maze[i][j];
		
		if (actual.getOeste()){
		    str.append("|");
		}else{
		    str.append(" ");
		}
		if (actual.getSur()){
		    str.append("__");
		}else{
		    str.append("  ");
		}
		if (actual.getEste()){
		    str.append("|");
		}else{
		    str.append(" ");
		}
		
	    }
		
	    str.append("\n");
	}
	return str.toString();
    }
    
}
