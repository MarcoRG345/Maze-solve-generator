package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        String cadena = "";
	if (esVacia())
	    return "";
	else{
	    Nodo nodo = cabeza.siguiente;
	    cadena = cadena + cabeza.elemento + ",";
	    while (nodo != null){
		cadena = cadena + nodo.elemento + ",";
		nodo = nodo.siguiente;
	    }
	}
	return cadena;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException();
	Nodo nodo =  new Nodo(elemento);
	if (esVacia())
	    rabo = cabeza = nodo;
	else{
	    rabo.siguiente = nodo;
	    rabo = nodo;
	}
    }
}
