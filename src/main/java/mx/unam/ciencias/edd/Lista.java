package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            // Aquí va su código.
	    this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            // Aquí va su código.
	    start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return this.siguiente != null; 
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
	    if (hasNext()){
		anterior = siguiente;
		siguiente = siguiente.siguiente;
		return anterior.elemento;
	    }
	    throw new NoSuchElementException();
	    
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
	    return this.anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
	    if (hasPrevious()){
		siguiente = anterior;
		anterior = anterior.anterior;
		return siguiente.elemento;
	    }
	    throw new NoSuchElementException();
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
	    anterior = null;
	    siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
	    siguiente = null;
	    anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
	return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return getLongitud();
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
	if (cabeza == null || rabo == null || this.getLongitud() == 0)
	    return true;
	return false;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException();
	Nodo nodo = new Nodo(elemento);
	longitud++;
	if (this.esVacia()){
	    cabeza = rabo = nodo;
	}else{
	    rabo.siguiente = nodo;
	    nodo.anterior = rabo;
	    rabo = nodo;
	}
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException();
	Nodo nodo = new Nodo(elemento);
	longitud++;
	if (this.esVacia())
	    rabo = cabeza = nodo;
	else{
	    cabeza.anterior = nodo;
	    nodo.siguiente = cabeza;
	    cabeza = nodo;
	}
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException();
	else if ( i < 1){
	    agregaInicio(elemento);
	}else if (i >= getElementos()){
	    agregaFinal(elemento);
	}else{
	    longitud++;
	    Nodo nodo = new Nodo(elemento);
	    Nodo actual = cabeza;
	    int cont = 0;
	    while (actual != null){
		if (cont == i){
		    nodo.siguiente = actual;
		    nodo.anterior = actual.anterior;
		    actual.anterior.siguiente = nodo;
		    actual.anterior = nodo;
		}
		actual = actual.siguiente;
		cont++;
	    }
	}
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	//verficamos que el elemento este en la lista.
	if (this.contiene(elemento)){
	    // caso 1: la cabeza es igual al elemento
	    if (cabeza.elemento.equals(elemento))
		this.eliminaPrimero();
	    // caso 2: recorremos la lista.
	    else{
		Nodo actual = cabeza.siguiente;
		//contador para garantizar que el elemento se haya eliminado.
		int cont  = 0;
		//mientras el actual sea diferente de null y el siguiente tambien lo sea.
		while (actual != null && actual.siguiente != null){
		    if (actual.elemento.equals(elemento)){
			actual.siguiente.anterior = actual.anterior;
			actual.anterior.siguiente = actual.siguiente;
			longitud--;
			cont++;
			break;
		    }else
			actual = actual.siguiente;
		}
		//si el contador es 0, no esta en el cuerpo de la lista, por lo tanto, es eñ rabo.
		if (cont == 0)
		    this.eliminaUltimo();
	    }
	}
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
	if (esVacia())
	    throw new NoSuchElementException();
	T t = cabeza.elemento;
	if (cabeza.siguiente == null)
	    limpia();
	else{
	    Nodo nuevaCabeza = cabeza.siguiente;
	    cabeza.siguiente = cabeza.siguiente.siguiente;
	    cabeza = nuevaCabeza;
	    cabeza.anterior = null;
	    longitud--;
	}
	return t;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
	if (esVacia())
	    throw new NoSuchElementException();
	T t = rabo.elemento;
	if (rabo.equals(cabeza))
	    limpia();
	else{
	    Nodo raboActual = rabo.anterior;
	    rabo.anterior = rabo.anterior.anterior;
	    rabo = raboActual;
	    rabo.siguiente = null;
	    longitud--;
	}
	return t;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	Nodo actual = cabeza;
	while (actual != null){
	    if (actual.elemento.equals(elemento))
		return true;
	    actual = actual.siguiente;
	}
	return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
	IteradorLista<T> iterador = this.iteradorLista();
	Lista<T> reversa =  new Lista<T>();
	iterador.end();
	while (iterador.hasPrevious()){
	    T t  = iterador.previous();
	    reversa.agrega(t);
	}
	return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
	Lista<T> listaCopia =  new Lista<T>();
	for (T t : this){
	    listaCopia.agrega(t);
	}
	return listaCopia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
	cabeza = rabo = null;
	longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
	if (esVacia())
	    throw new NoSuchElementException();
	return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
	if (esVacia())
	    throw new NoSuchElementException();
	return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
	if ( i < 0 || i >= getElementos())
	    throw new ExcepcionIndiceInvalido();
	Nodo actual = cabeza;
	int cont = 0;
	while (actual != null){
	    if (cont == i)
		return actual.elemento;
	    actual =  actual.siguiente;
	    cont++;
	}
	return null;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
	int cont = 0;
	Nodo actual = cabeza;
	while (actual != null){
	    if (actual.elemento.equals(elemento))
		return cont;
	    actual = actual.siguiente;
	    cont++;
	}
	return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
	if (esVacia())
	    return "[]";
	Nodo actual =  cabeza;
	String cadena = "["+ actual.elemento;
	actual = actual.siguiente;
	while (actual != null){
	    cadena =  cadena + ", " + actual.elemento;
	    actual = actual.siguiente;
	}
	cadena = cadena + "]";
	return cadena;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
	Nodo actual = this.cabeza;
	Nodo _actual_ = lista.cabeza;
	
	if (this.getLongitud() != lista.getLongitud())
	    return false;
	while (actual != null && _actual_ != null){
	    if (!actual.elemento.equals(_actual_.elemento))
		return false;
	    actual = actual.siguiente;
	    _actual_ = _actual_.siguiente;
	}
	return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }


     /**
     * Algoritmo para mezclar dos listas ya ordenadas.
     * @param listaIzquierda sublista ordenada.
     * @param listaDerecha sublista ordenada.
     * @param comparador objeto comparador para tipos genericos.
     * @return listaOrdenada regresa la lista concatenada y ordenada.
     */
    private Lista<T> merge(Lista<T> listaIzquierda, Lista<T> listaDerecha, Comparator<T> comparador){
	Lista<T> listaOrdenada = new Lista<T>();
	Nodo izquierda = listaIzquierda.cabeza;
	Nodo derecha = listaDerecha.cabeza;
	while (izquierda != null && derecha != null){
	    if (comparador.compare(izquierda.elemento, derecha.elemento) <= 0){
		listaOrdenada.agrega(izquierda.elemento);
		izquierda = izquierda.siguiente;
	    } else {
		listaOrdenada.agrega(derecha.elemento);
		derecha = derecha.siguiente;
	    }
	}
	while (derecha != null){
	    listaOrdenada.agrega(derecha.elemento);
	    derecha = derecha.siguiente;
	}
	while (izquierda != null){
	    listaOrdenada.agrega(izquierda.elemento);
	    izquierda = izquierda.siguiente;
	}
	
	return listaOrdenada;
    }


    /**
     * Metodo auxiliar para la implementacion del algoritmo mergSort.
     * @param lista recibe una lista sobre la cual se realiza recursion
     * en mitades.
     * @param comparador el objeto comparador de tipos genericos.
     * @return lista si la longitud es 1 o 0, regresa la mezcla de
     * dos listas caso contrario.
     */
    private Lista<T> mergeSort(Lista<T> lista, Comparator<T> comparador){
	//Clausla de escape.
	if (lista.getLongitud() <= 1){
	    return lista;
	}
     
	Lista <T> lista1 = new Lista<T>();
	Lista<T> lista2 = new Lista<T>();
	
	if ((lista.getLongitud()%2) == 0){
	    //Es divisible entre dos.
	    int indice = lista.getElementos()/2;
	    Nodo nodo = lista.cabeza;
	    
	    for (int i = 0; i < indice; i++){
		lista1.agrega(nodo.elemento);
		nodo = nodo.siguiente;
	    }
	    
	    while (nodo!=null){
		lista2.agrega(nodo.elemento);
		nodo = nodo.siguiente;
	    }
	}else{
	    //No es divisible entre dos.
	    int indice = (lista.getElementos() - 1)/2;
	    Nodo nodo = lista.cabeza;
	    
	    for (int i = 0; i < indice; i++){
		lista1.agrega(nodo.elemento);
		nodo = nodo.siguiente;
	    }
	    
	    while (nodo != null){
		lista2.agrega(nodo.elemento);
		nodo = nodo.siguiente;
	    }
	    
	}
	/*Realizamos recursion sobre cada una de lista. Como cada vez se actualiza lista1
	 lista2, actualizamos sus valores en cada llamada recursiva.*/
	lista1 = mergeSort(lista1, comparador);
	lista2 = mergeSort(lista2, comparador);
	// regresamos la mezcla de las dos listas.
	return merge(lista1, lista2, comparador);
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
	return  mergeSort(this, comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código.
	Nodo nodo = cabeza;
	while (nodo != null){
	    if (comparador.compare(elemento, nodo.elemento) == 0)
		return true;
	    nodo = nodo.siguiente;
	}
	return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
