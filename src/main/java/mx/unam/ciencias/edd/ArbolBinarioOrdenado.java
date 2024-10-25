package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

     /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
	    pila = new Pila<Vertice>();
	    Vertice  v = raiz;
	    while (v != null){
		pila.mete(v);
		v = v.izquierdo;
	    }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    if (pila.esVacia())
		return false;
	    return true;
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
	    Vertice v = pila.saca();
	    if (v.derecho != null){
		pila.mete(v.derecho);
		Vertice actual = v.derecho;
		actual = actual.izquierdo;
		while (actual != null){
		    pila.mete(actual);
		    actual = actual.izquierdo;
		}
	    }
	    return v.elemento;
		
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Algoritmo auxiliar recursivo pque compara el vertice actual con el 
     * vertice que sigue tanto de forma izquierda como derecha.
     * @param actual el vertice actual de referencia.
     * @param nuevo el vertice siguiente.
     */
    private void compara(Vertice actual, Vertice nuevo){
	if (nuevo.elemento.compareTo(actual.elemento) <= 0){
	    if (actual.izquierdo == null){
		actual.izquierdo = nuevo;
		nuevo.padre = actual;
	    }
	    else
		compara(actual.izquierdo, nuevo);
	}else if (nuevo.elemento.compareTo(actual.elemento) > 0){
	    if (actual.derecho == null){
		actual.derecho = nuevo;
		nuevo.padre = actual;
	    }
	    else
		compara(actual.derecho, nuevo);
	}
	
    }

    
    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException();
	Vertice vertice = nuevoVertice(elemento);
	ultimoAgregado = vertice;
	elementos++;
	if (raiz == null)
	    raiz = vertice;
	else
	    compara(this.raiz, vertice);
	
    }

    /**
     * Algoritmo auxiliar para buscar el vertice maximo en el subarbol
     * izquierdo, el recorrido es recursivo y parte de la izquierda hasta
     * la derecha, hasta llegar al vertice sin su hijo derecho.
     * @param vertice el vertice al que queremos su maximo.
     * @return <code>maixmoEnSubarbol<code> y <code>vertice<code> el vertice maximo.
     */
    private Vertice maximoEnSubarbol(Vertice vertice){
	if (vertice.derecho == null)
	    return vertice;
	return maximoEnSubarbol(vertice.derecho);
    }

    
    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.	
	Vertice verticeEliminar = vertice(busca(elemento));
	if (verticeEliminar.izquierdo != null && verticeEliminar.derecho != null){
	    Vertice verticeIntercambia = intercambiaEliminable(verticeEliminar);
	    eliminaVertice(verticeIntercambia);
	    elementos--;
	}else{
	    eliminaVertice(verticeEliminar);
	    elementos--;
	}
	
    }


    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
	Vertice maxVertice = maximoEnSubarbol(vertice.izquierdo);
	T temp = vertice.elemento;
	vertice.elemento = maxVertice.elemento;
	maxVertice.elemento = temp;
	return maxVertice;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
	Vertice hijo = (vertice.izquierdo != null ? vertice.izquierdo : vertice.derecho);
	if (vertice.padre != null){
	    
	    if (vertice.padre.izquierdo != null && vertice.padre.izquierdo.elemento.equals(vertice.elemento)){
		vertice.padre.izquierdo = hijo;
	    }
	    
	    else if (vertice.padre.derecho != null && vertice.padre.derecho.elemento.equals(vertice.elemento)){
		vertice.padre.derecho = hijo;
	    }
	}else {
	    raiz = hijo;
	}
	if (hijo != null){
	    hijo.padre = vertice.padre;
	}
    }

    private VerticeArbolBinario<T> busca(Vertice vertice, T elemento){
	if (vertice != null){
	    if (vertice.elemento.compareTo(elemento) == 0)
		return vertice;
	    else if (elemento.compareTo(vertice.elemento) < 0)
		return busca(vertice.izquierdo, elemento);
	    else
		return busca(vertice.derecho, elemento);
	}
	return null;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código. 
	return busca(this.raiz, elemento);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	if (vertice == null || !vertice.hayIzquierdo())
	    return;
	
	Vertice verticeRef = vertice(vertice);
	Vertice nuevoPadre = vertice(vertice.izquierdo());
	//Actualizamos el hijo dereecho del nuevo padre como el izq de vertice.
        verticeRef.izquierdo = nuevoPadre.derecho;
	
	if (nuevoPadre.derecho != null)
	    nuevoPadre.derecho.padre = verticeRef;
	
        nuevoPadre.derecho = verticeRef;
	nuevoPadre.padre = verticeRef.padre;
	verticeRef.padre = nuevoPadre;
	
	if (nuevoPadre.padre != null){
	    if (nuevoPadre.padre.izquierdo != null && nuevoPadre.padre.izquierdo == verticeRef)
		nuevoPadre.padre.izquierdo = nuevoPadre;
	    else if (nuevoPadre.padre.derecho != null && nuevoPadre.padre.derecho == verticeRef){
		nuevoPadre.padre.derecho = nuevoPadre;
	    }
	}
	else {
	    this.raiz = nuevoPadre;
	}
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
	if (vertice == null || !vertice.hayDerecho())
	    return;
        Vertice verticeRef = vertice(vertice);
	Vertice nuevoPadre = vertice(vertice.derecho());
	//Actualizamos el hijo izquierdo del nuevo padre como el der de vertice.
        verticeRef.derecho = nuevoPadre.izquierdo;
	
	if (nuevoPadre.izquierdo != null)
	    nuevoPadre.izquierdo.padre = verticeRef;
	
	nuevoPadre.izquierdo = verticeRef;
	nuevoPadre.padre = verticeRef.padre;
	verticeRef.padre = nuevoPadre;
	
	if (nuevoPadre.padre != null){
	    if (nuevoPadre.padre.izquierdo != null && nuevoPadre.padre.izquierdo == verticeRef)
		nuevoPadre.padre.izquierdo = nuevoPadre;
	    else if (nuevoPadre.padre.derecho != null && nuevoPadre.padre.derecho ==verticeRef){
		nuevoPadre.padre.derecho = nuevoPadre;
	    }
	}else{
	    this.raiz = nuevoPadre;
	}
        
    }

    
    private void dfsPreOrder(VerticeArbolBinario<T> vertice, AccionVerticeArbolBinario<T> accion){
	if (vertice == null)
	    return;
	accion.actua(vertice);
	if (vertice.hayIzquierdo())
	    dfsPreOrder(vertice.izquierdo(), accion);
	if (vertice.hayDerecho())
	    dfsPreOrder(vertice.derecho(), accion);
	
    }
    
    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPreOrder(this.raiz(), accion);
    }

    private void dfsInOrder(VerticeArbolBinario<T> vertice, AccionVerticeArbolBinario<T> accion){
	if (vertice == null)
	    return;
	if (vertice.hayIzquierdo())
	    dfsInOrder(vertice.izquierdo(), accion);
	accion.actua(vertice);
	if (vertice.hayDerecho())
	    dfsInOrder(vertice.derecho(), accion);
    }
    
    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
	dfsInOrder(this.raiz(), accion);
    }

    private void dfsPostOrder(VerticeArbolBinario<T> vertice, AccionVerticeArbolBinario<T> accion){
	if (vertice == null)
	    return;
	if (vertice.hayIzquierdo())
	    dfsPostOrder(vertice.izquierdo(), accion);
	if (vertice.hayDerecho())
	    dfsPostOrder(vertice.derecho(), accion);
	accion.actua(vertice);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
	dfsPostOrder(this.raiz(), accion);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
