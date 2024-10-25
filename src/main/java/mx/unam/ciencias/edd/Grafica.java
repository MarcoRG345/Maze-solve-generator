package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
	    iterador = vertices.iterator();
	    
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    if (iterador.hasNext())
		return true;
	    return false;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
	    if (hasNext())
		return iterador.next().elemento;
	    throw new NoSuchElementException();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La distancia del vértice. */
        private double distancia;
        /* El índice del vértice. */
        private int indice;
        /* La lista de vecinos del vértice. */
        private Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
	    this.elemento = elemento;
	    color = Color.NINGUNO;
	    vecinos = new Lista<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
	    return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
	    return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
	    return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
	    return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
	    this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
	    return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
	    if (distancia - vertice.distancia > 0.0)
		return 1;
	    else if (distancia - vertice.distancia < 0.0)
		return -1;
	    return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
	    this.vecino = vecino;
	    this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
	    return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
	    return vecino.vecinos.getElementos();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
	    return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
	    return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino<T> {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica<T>.Vertice v, Grafica<T>.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
	vertices = new Lista<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código
	return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código
	return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if (elemento == null || contiene(elemento))
	    throw new IllegalArgumentException();
	Vertice vertice = new Vertice(elemento);
	vertices.agrega(vertice);
    }
    
    /**
     * Encuentra el elemento en la lista de vertices de la grafica
     * y devuelve el vertice indicado como instacia de {@link#Vertice}.
     * @param elemento el elemento a buscar en la lista de vertices.
     * @return el vertice de la grafica.
     */
    private Vertice busca(T elemento){
	for (Vertice vertice : vertices){
	    if (vertice.elemento.equals(elemento))
		return vertice;
	}
	return null;
    }

    

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
	if (!contiene(a) || !contiene(b))
	    throw new NoSuchElementException();
	else if (a.equals(b) || sonVecinos(a, b))
	    throw new IllegalArgumentException();
        Vertice vertex_a = busca(a);
	Vertice vertex_b = busca(b);
	Vecino u = new Vecino(vertex_a, 1.0);
	Vecino v = new Vecino(vertex_b, 1.0);
	vertex_a.vecinos.agrega(v);
	vertex_b.vecinos.agrega(u);
	aristas++;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
	if (!contiene(a) || !contiene(b))
	    throw new NoSuchElementException();
	if (a.equals(b) || sonVecinos(a, b) || peso < 0)
	   throw new IllegalArgumentException();
	Vertice vertex_a = busca(a);
	Vertice vertex_b = busca(b);
	Vecino u = new Vecino(vertex_a, peso);
	Vecino v = new Vecino(vertex_b, peso);
	vertex_a.vecinos.agrega(v);
	vertex_b.vecinos.agrega(u);
	aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
	if (!contiene(a) || !contiene(b))
	    throw new NoSuchElementException();
	if (a.equals(b)  || !sonVecinos(a, b))
	    throw new IllegalArgumentException();
	Vertice vertex_a = busca(a);
	Vertice vertex_b = busca(b);
	Vecino u = buscaVecino(vertex_a, vertex_b);
	Vecino w = buscaVecino(vertex_b, vertex_a);
	vertex_b.vecinos.elimina(u);
	vertex_a.vecinos.elimina(w);
	aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	for (Vertice vertice : vertices){
	    if (vertice.elemento.equals(elemento))
		return true;
	}
	return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	if (!contiene(elemento))
	    throw new NoSuchElementException();
        Vertice vertex = busca(elemento);
	for (Vecino u : vertex.vecinos){
	    desconecta(vertex.elemento, u.vecino.elemento);
	}
	vertices.elimina(vertex);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
	if (a.equals(b) || !contiene(a) || !contiene(b))
	    throw new NoSuchElementException();
        
	int cont = 0;
	Vertice u = busca(a);
	Vertice v = busca(b);
	//vecinos de b.
	for (Vecino w : v.vecinos){
	    if (w.vecino.elemento.equals(a))
		cont++;
	}
	//vecinos de a.
	for (Vecino w : u.vecinos){
	    if (w.vecino.elemento.equals(b))
		cont++;
	}
	if (cont == 2)
	    return true;
	return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
	if (!sonVecinos(a, b))
	    throw new IllegalArgumentException();
	Vertice vertex_a = busca(a);
	Vertice vertex_b = busca(b);
	Vecino u = buscaVecino(vertex_a, vertex_b);
	return u.peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
	if (!contiene(a) || !contiene(b))
	    throw new NoSuchElementException();
	if (!sonVecinos(a, b) || peso < 0)
	    throw new IllegalArgumentException();
	Vertice vertex_a = busca(a);
	Vertice vertex_b = busca(b);
	Vecino u = buscaVecino(vertex_a, vertex_b);
	u.peso = peso;
	Vecino w = buscaVecino(vertex_b, vertex_a);
	w.peso = peso;
    }

    /**
     * Regresa el vertice vecino w de vertice como instancia de 
     * {@link#Vecino}.
     * @param w el vertice vecino de vertice.
     * @param vertice el vertice en cuestion.
     * @return u el vecino. <code>null<code> si no lo encuentra.
     */
    private Vecino buscaVecino(Vertice w, Vertice vertice){
	for (Vecino u : vertice.vecinos){
	    if (u.vecino.elemento.equals(w.elemento))
		return u;
	}
	return null;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
	if (!contiene(elemento))
	    throw new NoSuchElementException();
	return busca(elemento);
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
	if (vertice == null || vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class)
	    throw new IllegalArgumentException();
	
	if (vertice.getClass() == Vertice.class){
	    Vertice v = (Vertice) vertice;
	    v.color = color;
	}

	if (vertice.getClass() == Vecino.class){
	    Vecino v = (Vecino) vertice;
	    v.vecino.color = color;
	}
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
	int cont = 0;
	Cola<Vertice> cola = new Cola<Vertice>();
	for (Vertice vertice : vertices){
	    vertice.color = Color.NINGUNO;
	}
	Vertice v = vertices.get(0);
	v.color = Color.ROJO;
	cola.mete(v);
        while (!cola.esVacia()) {
	    v = cola.saca();
	    for (Vecino u : v.vecinos){
		if (u.vecino.color != Color.ROJO){
		    u.vecino.color = Color.ROJO;
		    cola.mete(u.vecino);
		}
	    } 
	}
	for (Vertice vertice : vertices){
	    if (vertice.color != Color.ROJO)
		return false;
	}
	return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	for (Vertice vertice : vertices)
	    accion.actua(vertice);
    }

    /**
     * Realiza el recorrido ya sea BFS o DFS dependiendo de la opcion. Como puede
     * ser tanto pila como cola recibimos una instancia de {@link#MeteSaca<T>}.
     * @param opcion recibe la estructura que va realizar BFS o DFS.
     * @param accion recibe la accion que se va actuar en relacion al vertice.
     */
    private void bfsOrDfs(Vertice v, MeteSaca<Vertice> opcion, AccionVerticeGrafica<T> accion){
	for (Vertice vertice : vertices){
	    vertice.color = Color.ROJO;
	}
	v.color = Color.NEGRO;
	opcion.mete(v);
	while (!opcion.esVacia()){
	    v = opcion.saca();
	    accion.actua(v);
	    for (Vecino u : v.vecinos){
		if (u.vecino.color != Color.NEGRO){
		    u.vecino.color = Color.NEGRO;
		    opcion.mete(u.vecino);
		}
	    }
	}
    }
    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	if (!contiene(elemento))
	    throw new NoSuchElementException();
	Cola<Vertice> cola = new Cola<Vertice>();
	Vertice w = busca(elemento);
	bfsOrDfs(w, cola, accion);
	paraCadaVertice((v) -> setColor(v, Color.NINGUNO));
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	if (!contiene(elemento))
	    throw new NoSuchElementException();
        Vertice w = busca(elemento);
	Pila<Vertice> pila = new Pila<Vertice>();
	bfsOrDfs(w, pila, accion);
	paraCadaVertice((v) -> setColor(v, Color.NINGUNO));
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
	return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
	aristas = 0;
	vertices.limpia();
    }
    
    /**
     * Recibe dos vertices adyacentes y verifica que no este la simetria
     * de los vertices, es decir, para toda (u,v) en E(G) no debe ocurrir
     * que (v,u) exista en E(G), ya que es un propiedad conjuntista. El
     * alogoritmo trabaja con una estructura auxiliar de arreglo, una lista
     * nos hubiera aumentado mas la complejidad de lo terrible que es cuando 
     * el metodo toString() manda a llamar al algoritmo.
     * @param vertice el vertice en cuestion.
     * @param vecino el vecino del vertice.
     * @param a el arreglo en cuestion.
     */
    private void aristaRepetida(Vertice vertice, Vecino v, String[] a){
	String cadena = "("+v.vecino.elemento+", "+vertice.elemento+")";
        for (int i = 0; i < a.length; i++){
	    if (a[i] != null && a[i].equals(cadena))
		return;
	}
	for (int i = 0; i < a.length; i++){
	    if (a[i] == null){
		a[i] = "("+vertice.elemento+", "+v.vecino.elemento+")";
		break;
	    }
	}
    }
    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.
	String cadena = "{";
	String registro = "";
	for (Vertice vertice : vertices){
	    cadena += vertice.elemento + ", ";
	}
	cadena += "}, {";
	int index = 0;
	Lista<Vertice> registros = new Lista<>();
	String[] a = new String[(vertices.getLongitud()*(vertices.getLongitud() - 1))/2];
        for (Vertice vertice : vertices){
	    for (Vecino vecino : vertice.vecinos){
		aristaRepetida(vertice, vecino, a);
	    }
	}
	for (int i = 0; i < a.length; i++){
	    if (a[i] != null)
		cadena += a[i] + ", ";
	}
	cadena += "}";
	return cadena;
    }
    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
	if (getElementos() != grafica.getElementos() || getAristas() != grafica.getAristas())
	    return false;
	paraCadaVertice(v -> grafica.setColor(v, Color.NINGUNO));
	paraCadaVertice(v -> setColor(v, Color.NINGUNO));
	
	for (Vertice v : vertices){
	    for (Vertice u : grafica.vertices){
		if (v.elemento.equals(u.elemento)){
		    v.color = Color.ROJO;
		    u.color = Color.ROJO;
		}
	    }
	}

	for (Vertice v : vertices)
	    if (v.color != Color.ROJO)
		return false;

	for (Vertice w : grafica.vertices)
	    if (w.color != Color.ROJO)
		return false;

	Cola<Vertice> cola = new Cola<>();
	Vertice vertex = vertices.getPrimero();
	vertex.color = Color.NEGRO;
	cola.mete(vertex);
	Cola<Vertice> q = new Cola<>();
	vertex = grafica.vertices.getPrimero();
	vertex.color = Color.NEGRO;
	q.mete(vertex);
	
	while (!cola.esVacia() && !q.esVacia()){
	    Vertice v1 = cola.saca();
	    for (Vecino w : v1.vecinos){
		if (w.vecino.color != Color.NEGRO){
		    w.vecino.color = Color.NEGRO;
		    cola.mete(w.vecino);
		}
	    }
	    Vertice v2  = q.saca();
	    for (Vecino u : v2.vecinos){
		if (u.vecino.color != Color.NEGRO){
		    u.vecino.color = Color.NEGRO;
		    q.mete(u.vecino);
		}
	    }
	    if (v1.elemento.equals(v2.elemento))
		if (!cola.equals(q))
		    return false;
	    
	}
	return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Recontruye la trayectoria a partir del vertice destino, repitiendo hasta que 
     * èste sea igual al vertice origen. Para cada vecino del vertice origen, si su
     * distancia es igual al destino menos 1, lo agregamos a la lista.
     * @param v_origen el vertice origen.
     * @param u_destino el vertice destino.
     * @return tray la trayectoria minima construida.
     */
    private Lista<VerticeGrafica<T>> getTray(Vertice v_origen, Vertice u_destino){
	Lista<VerticeGrafica<T>> tray = new Lista<>();
	tray.agrega(u_destino);
	
	if (u_destino.distancia == Double.MAX_VALUE){
	    tray.limpia();
	    return tray;
	}

	while (u_destino != v_origen){
	    for (Vecino w : u_destino.vecinos){
		if (w.vecino.distancia == (u_destino.distancia - 1)){
		    tray.agrega(w.vecino);
		    u_destino = w.vecino;
		}
	    }
	}
	
	return tray.reversa();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
	if (!contiene(origen) || !contiene(destino))
	    throw new NoSuchElementException();
	
	Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
	Vertice u = busca(origen);
	Vertice w = busca(destino);
	
	if (origen.equals(destino)){	    
	    trayectoria.agrega(u);
	    return trayectoria;
	}
	
	Cola<Vertice> cola = new Cola<>();
	for (Vertice v : vertices){
	    v.distancia = Double.MAX_VALUE;
	}
	u.distancia = 0.0;
	cola.mete(u);
	
	while (!cola.esVacia()){
	    Vertice vertice = cola.saca();
	    for (Vecino n : vertice.vecinos){
		if (n.vecino.distancia == Double.MAX_VALUE){
		    n.vecino.distancia = vertice.distancia + 1;
		    cola.mete(n.vecino);
		}
	    }
	}
	
	return getTray(u, w);
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
	if (!contiene(origen) || !contiene(destino))
	    throw new NoSuchElementException();
	
	Vertice u = busca(origen);
	Vertice v = busca(destino);
	MonticuloDijkstra<Vertice> monticulo = null;
	    
	for (Vertice vertice : vertices){
	    vertice.distancia = Double.MAX_VALUE;
	}
	u.distancia = 0.0;
	int operacion = ((vertices.getElementos()*(vertices.getElementos() - 1))/2) - vertices.getElementos();
	
	if (aristas > operacion){
	    monticulo = new MonticuloArreglo<Vertice>(vertices); 
	}else {
	    monticulo = new MonticuloMinimo<Vertice>(vertices);
	}

	while (!monticulo.esVacia()){
	    Vertice raiz = monticulo.elimina();
	    for (Vecino w : raiz.vecinos){
		if (w.vecino.distancia > raiz.distancia + w.peso){
		    w.vecino.distancia = raiz.distancia + w.peso;
		    monticulo.reordena(w.vecino);
		}
	    }
	}
        
	return getTray(u, v);
    }
}
