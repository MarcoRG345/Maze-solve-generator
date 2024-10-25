package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return (indice < elementos);
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
	    if (indice >= elementos)
		throw new NoSuchElementException();
	    int i = indice;
	    indice++;
	    return arbol[i];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
	    this.elemento = elemento;
	    indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
	    return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
	    this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
	    return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
	arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
	arbol = nuevoArreglo(n);
	int index = 0;
	for (T e : iterable){
	    arbol[index] = e;
	    e.setIndice(index);
	    index++;
	}
	elementos = index;
	for (int i = (n/2) - 1; i >= 0; i--){
	    heapifyDown(i);
	}
    }


    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elementos == arbol.length){
	    T[] nuevo = nuevoArreglo(arbol.length*2);
	    int index = 0;
	    for (int i = 0; i < elementos; i++){
		nuevo[i] = arbol[index];
		index++;
	    }
	    arbol = nuevo;
	}
	arbol[elementos] = elemento;
	elemento.setIndice(elementos);
	elementos++;
	heapifyUp(elementos - 1);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
	if (esVacia())
	    throw new IllegalStateException();
	
        T elimina = arbol[0];
	int index = 0;
	int index_2 = elementos - 1;
	
	arbol[0] = arbol[elementos - 1];
	arbol[elementos - 1] = elimina;
	
	arbol[0].setIndice(0);
	arbol[index_2].setIndice(index_2);
	
	arbol[index_2].setIndice(-1);
	elementos--;
	heapifyDown(0);
	
	return elimina;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	if (elemento.getIndice() < 0 || elemento.getIndice() >= elementos )
	    return;
	
	int index = elemento.getIndice();
	int index_2 = elementos - 1;
	
        T remplaza = arbol[elementos - 1];
	actualizaObjetosIndices(index, index_2);
	    
	arbol[elementos - 1].setIndice(-1);
	elementos--;
	if (index < elementos)
	    reordena(remplaza);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	for (int i = 0; i < arbol.length; i++)
	    if (arbol[i] != null && arbol[i].equals(elemento))
		return true;
	return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
	if (elementos > 0)
	    return false;
	return true;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
	arbol  = nuevoArreglo(elementos);
	elementos = 0;
    }


    /**
     * Implementa el algoritmo acomodo hacia arriba. Sea indice 
     * el elemento que vamos acomodar de forma ascendente.
     * La implementacion es recursiva por lo que la complejidad
     * en espacio es O(logn).
     * @parama indice el indice del elemento.
     */
    private void heapifyUp(int indice){
	int indicePadre = (indice - 1)/2;
        if (indice > 0){
	    if (arbol[indice].compareTo(arbol[indicePadre]) < 0){
	        actualizaObjetosIndices(indice, indicePadre);
		heapifyUp(indicePadre);
	    }
	}
    }

    /**
     * Implementa el algoritmo acomodo hacia abajo. Sea indice el indice
     * del elemento que vamos acomodar de forma descendente.
     * La implementacion es recursiva por lo que la complejidad en espacio
     * es O(logn).
     * @param indice el indice del elemento.
     */
    private void heapifyDown(int index){
	int izquierdo = (2*index) + 1;
	int derecho = (2*index) + 2;
	if (izquierdo < elementos && arbol[izquierdo].compareTo(arbol[index]) < 0){
	    actualizaObjetosIndices(izquierdo, index);
	    heapifyDown(izquierdo);
	}
	if (derecho < elementos && arbol[derecho].compareTo(arbol[index]) < 0){
	    actualizaObjetosIndices(derecho, index);
	    heapifyDown(derecho);
	}
    }

    /**
     * Actualiza los indices respecto al objeto en el indice indicado. Los indices
     * en los arreglos no se pueden cambiar por lo que para respetar eso 
     * y  acoplarlo en la Orientacion Objetos, vamos actualizando los indices del 
     * monticulo.
     * @param indice el primer indice.
     * @param indice_2 el segundo indice.
     */
    private void actualizaObjetosIndices(int indice, int indice_2){
	T temp = arbol[indice];
	arbol[indice] = arbol[indice_2];
	arbol[indice_2] = temp;
	arbol[indice].setIndice(indice);
	arbol[indice_2].setIndice(indice_2);
    }
    
   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
	heapifyUp(elemento.getIndice());
	heapifyDown(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
	if (i < 0 || i >= elementos)
	    throw new NoSuchElementException();
	return arbol[i];
    }
    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
	String cadena = "";
	for (int i = 0; i < arbol.length; i++){
	    if (arbol[i] != null){
		cadena += arbol[i] + ", ";
	    }
	}
	return cadena;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código
	if (elementos != monticulo.elementos)
	    return false;
	int i = 0;
	int j = 0;
	while (i < elementos && j < elementos){
	    if (!arbol[i].equals(monticulo.arbol[j]))
		return false;
	    i++;
	    j++;
	}
	return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
	Lista<Adaptador<T>> listaAdaptadores = new Lista<>();
	Lista<T> listaDeColeccion = new Lista<T>();
	for (T e : coleccion){
	    Adaptador<T> ad = new Adaptador<>(e);
	    listaAdaptadores.agrega(ad);
	}
	MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<>(listaAdaptadores);
	while (!monticulo.esVacia()){
	    Adaptador<T> raiz = monticulo.elimina();
	    listaDeColeccion.agrega(raiz.elemento);
	}
	return listaDeColeccion;
    }
}
