package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

     /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
	    cola = new Cola<Vertice>();
	    if (raiz != null)
		cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    if (!cola.esVacia())
		return true;
	    return false;
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
	    Vertice vertice = cola.saca();
	    if (vertice.izquierdo != null)
		cola.mete(vertice.izquierdo);
	    if (vertice.derecho != null)
		cola.mete(vertice.derecho);
	    return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException();
	Vertice vertice = nuevoVertice(elemento);
	elementos++;
	if (raiz == null)
	    raiz = vertice;
	else{
	    Cola<VerticeArbolBinario<T>> cola = new Cola<>();
	    cola.mete(this.raiz());
	    while (!cola.esVacia()){
		VerticeArbolBinario<T> v = cola.saca();
		Vertice verticeReferencia = vertice(v);
		if (v.hayIzquierdo())
		    cola.mete(v.izquierdo());
		else{
		    verticeReferencia.izquierdo = vertice;
		    vertice.padre = verticeReferencia;
		    break;
		}
		if (v.hayDerecho())
		    cola.mete(v.derecho());
		else{
		    verticeReferencia.derecho = vertice;
		    vertice.padre = verticeReferencia;
		    break;
		}
	    }
	}
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	Vertice verticeBuscado = vertice(this.busca(elemento));
	if (verticeBuscado == null)
	    return;
	elementos--;
	if (elementos == 0){
	    raiz = null;
	    return;
	}
	Cola<VerticeArbolBinario<T>> cola = new Cola<>();
	Vertice verticeUltimo = null;
        cola.mete(this.raiz());
	while (!cola.esVacia()){
	    VerticeArbolBinario<T> v = cola.saca();
	    if (v.hayIzquierdo())
		cola.mete(v.izquierdo());
	    if (v.hayDerecho())
		cola.mete(v.derecho());
	     verticeUltimo = vertice(v);
	}
	
	T aux = verticeBuscado.elemento;
	verticeBuscado.elemento = verticeUltimo.elemento;
	verticeUltimo.elemento = aux;
	
	if (verticeUltimo.padre.izquierdo.equals(verticeUltimo))
	    verticeUltimo.padre.izquierdo = null;
	else if (verticeUltimo.padre.derecho.equals(verticeUltimo))
	    verticeUltimo.padre.derecho = null;
    }


    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
	if (this.esVacia())
	    return -1;
	return (int) (Math.floor(Math.log(elementos)/Math.log(2)));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
	if (this.esVacia())
	    return;
	Cola<VerticeArbolBinario<T>> cola = new Cola<>();
	cola.mete(raiz);
	while (!cola.esVacia()){
	    VerticeArbolBinario<T> v = cola.saca();
	    accion.actua(v);
	    if (v.hayIzquierdo())
		cola.mete(v.izquierdo());
	    if (v.hayDerecho())
		cola.mete(v.derecho());
	}
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
