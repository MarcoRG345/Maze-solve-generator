package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
	    super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
	    return altura;
        }

	/**
	 * Metodo auxiliar para regresar el balance de 
	 * vertices. Por definicion el balance de vertice
	 * es la diferencia de alturas entre su subarbol
	 * izquierdo y derecho.
	 * @return T(vi) -T(vd) el balance de vertice.
	 */
	private int balance(){
	    if (izquierdo != null && derecho != null)
		return (izquierdo.altura() - derecho.altura());
	    else{
		if (izquierdo != null && derecho == null)
		    return (izquierdo.altura() - (-1));
		else if (izquierdo == null && derecho != null)
		    return (-1 - derecho.altura());
	    }
	    return 0;
	}

	private int alturaRecursiva(){
	    return super.altura();
	}

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
	    return elemento + " " + altura() + "/" + balance();
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
	    return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
	return new VerticeAVL(elemento);
    }
    private VerticeAVL verticeAVL(Vertice vertice){
	return (VerticeAVL)vertice;
    }

    /**
     * Algoritmo para rebalancear el arbol AVL, dependiendo del factor de
     * equilibrio veremos los giros necesarios para rebalancearlo. Los arboles
     * AVL son arboles que dependen de un factor de equilibrio para estar
     * balanceados, por ende, tenemos que ver los distintos casos en que esto
     * ocurre, los arboles AVL solo se desbalancean cuando b(v) = 2 o -2.
     * @param vertice el vertice sobre el cual haremos el balanceo.
     */
    private void balancear(VerticeAVL vertice){
	if (vertice == null)
	    return;
	vertice.altura = vertice.alturaRecursiva();
	int balance = vertice.balance();
	VerticeAVL padreAVL = verticeAVL(vertice.padre);
	boolean aux = false;
	//Caso 1: b(v) = -2
	if (balance == -2){
	    //El vertice derecho es distinto de null.
	    VerticeAVL derechoAVL = verticeAVL(vertice.derecho);
	    VerticeAVL derechoXAVL = verticeAVL(derechoAVL.izquierdo);
	    VerticeAVL derechoYAVL = verticeAVL(derechoAVL.derecho);
	    balance = derechoAVL.balance();
	    if (balance == 1){
		super.giraDerecha(derechoAVL);
		derechoAVL.altura = derechoAVL.alturaRecursiva();
		derechoXAVL.altura = derechoAVL.alturaRecursiva();
		
	    }
	    derechoAVL = verticeAVL(vertice.derecho);
	    derechoXAVL = verticeAVL(derechoAVL.izquierdo);
	    derechoYAVL = verticeAVL(derechoAVL.derecho);
	    balance = derechoAVL.balance();
	    if (balance == 0 || balance == -1 || balance == -2){
		super.giraIzquierda(vertice);
		vertice.altura = vertice.alturaRecursiva();
		derechoAVL.altura = derechoAVL.alturaRecursiva();
		aux = true;
	    }
	    if (aux)
		padreAVL = verticeAVL(derechoAVL.padre);
	    else
		padreAVL = verticeAVL(vertice.padre);
	}
	//Caso 2: b(v) = 2.
	else if (balance == 2){
	    VerticeAVL izquierdoAVL = verticeAVL(vertice.izquierdo);
	    VerticeAVL izquierdoXAVL = verticeAVL(izquierdoAVL.izquierdo);
	    VerticeAVL izquierdoYAVL = verticeAVL(izquierdoAVL.derecho);
	    balance = izquierdoAVL.balance();
	    if (balance == -1){
		super.giraIzquierda(izquierdoAVL);
		izquierdoAVL.altura = izquierdoAVL.alturaRecursiva();
		izquierdoYAVL.altura = izquierdoYAVL.alturaRecursiva();
	    }
	    izquierdoAVL = verticeAVL(vertice.izquierdo);
	    izquierdoXAVL = verticeAVL(izquierdoAVL.izquierdo);
	    izquierdoYAVL = verticeAVL(izquierdoAVL.derecho);
	    balance = izquierdoAVL.balance();
	    if (balance == 0 || balance == 1 || balance == 2){
		super.giraDerecha(vertice);
		vertice.altura = vertice.alturaRecursiva();
		izquierdoAVL.altura = izquierdoAVL.alturaRecursiva();
		aux = true;
	    }
	    if (aux)
		padreAVL = verticeAVL(izquierdoAVL.padre);
	    else
		padreAVL = verticeAVL(vertice.padre);
	}
	balancear(padreAVL);
    }
    
    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	super.agrega(elemento);
	VerticeAVL padreAVL = verticeAVL(ultimoAgregado.padre);
	balancear(padreAVL);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	VerticeAVL vertice = (VerticeAVL) busca(elemento);
	if (vertice != null){
	    elementos--;
	    if (vertice.hayIzquierdo() && vertice.hayDerecho())
		vertice = verticeAVL(intercambiaEliminable(vertice));
	    if (!vertice.hayIzquierdo() && vertice.hayDerecho() || vertice.hayIzquierdo() && !vertice.hayDerecho())
		eliminaVertice(vertice);
	    else if (!vertice.hayDerecho() && !vertice.hayIzquierdo()){
		if (vertice.padre == null)
		    limpia();
		else{
		    if (vertice.padre.izquierdo == vertice)
			vertice.padre.izquierdo = null;
		    else
			vertice.padre.derecho = null;
		}
	    }
	    VerticeAVL padreAVL = verticeAVL(vertice.padre);
	    balancear(padreAVL);
	}
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
