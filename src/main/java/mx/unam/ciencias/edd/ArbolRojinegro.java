package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
	    super(elemento);
	    color = Color.NINGUNO;
        }
        
	

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            // Aquí va su código.
	    if (getColor(this) == Color.ROJO)
		return "R{"+ super.toString() +"}";
	    return "N{"+ super.toString() +"}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
	    return  (color == vertice.color && super.equals(objeto));
        }
    }
    private boolean esRojo(VerticeRojinegro vertice){
	return (vertice != null && vertice.color == Color.ROJO);
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        // Aquí va su código.
        super(coleccion);
    }
    

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
	return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        
	VerticeRojinegro v = (VerticeRojinegro)vertice;
	return v.color;
	    
    }
    /**
     * Regresa un nuevo vertice, usando la instancia de {@link VerticeRojinegro}.
     * Un vertice rojinegro siempre es un vertice, pero un vertice no es un vertice
     * rojinegro por conceptos de herencia, por lo que audicionamos.
     * @param vertice el vertice que queremos audicionar.
     * @return el vertice rojinegro.
     */
    private VerticeRojinegro verticeRN(Vertice vertice){
	VerticeRojinegro v = (VerticeRojinegro)vertice;
	return v;
    }
    /**
     * Metodo auxiliar para balancear el subarbol rojinegro de ser
     * necesario despues de agregar un elemento. El algoritmo consite de 
     * 4 casos. Es importante señalar que entre cada caso no inferimos que es
     * la unica alternativa, ya que hay llamada recursiva, por lo que se checan
     * los 4 casos uno por uno, a excepcion del primero el trivial.
     * @param vertice el vertice que fue agregado, entra con color rojo. 
     */
    private void balanceo(VerticeRojinegro vertice){
	
	if (vertice.padre == null){
	    //Caso 1: el padre es null.
	    vertice.color = Color.NEGRO;
	    
	}else {

	    VerticeRojinegro padre_rn = verticeRN(vertice.padre);
	    VerticeRojinegro abuelo = verticeRN(padre_rn.padre);
	    
	    if (esRojo(padre_rn)){
		//Caso 3: el padre es rojo y el tio es rojo. El abuelo es negro.
		VerticeRojinegro tio_izq  = null;
		VerticeRojinegro tio_der = null;
		
	        //tio izquierdo
		if (abuelo.derecho != null && abuelo.derecho.izquierdo != null && abuelo.derecho.izquierdo ==vertice)
		    tio_izq = verticeRN(abuelo.izquierdo);
	        else if (abuelo.derecho != null && abuelo.derecho.derecho != null && abuelo.derecho.derecho==vertice)
		    tio_izq = verticeRN(abuelo.izquierdo);
		else{
		    //tio derecho
		    if (abuelo.izquierdo != null && abuelo.izquierdo.derecho != null && abuelo.izquierdo.derecho ==vertice)
			tio_der = verticeRN(abuelo.derecho);
		    else if (abuelo.izquierdo != null && abuelo.izquierdo.izquierdo != null && abuelo.izquierdo.izquierdo==vertice)
			tio_der = verticeRN(abuelo.derecho);
		}

		if (esRojo(tio_izq)){
		    padre_rn.color = Color.NEGRO;
		    tio_izq.color = Color.NEGRO;
		    abuelo.color = Color.ROJO;
		    balanceo(abuelo);
		    
		}else if (esRojo(tio_der)){
		    padre_rn.color = Color.NEGRO;
		    tio_der.color = Color.NEGRO;
		    abuelo.color = Color.ROJO;
		    balanceo(abuelo);
		    
		}
		
	    }//Caso 4: estan cruzados.
	    if (esRojo(padre_rn) && abuelo != null && abuelo.derecho != null && abuelo.derecho==padre_rn && padre_rn.izquierdo != null && padre_rn.izquierdo==vertice){
		super.giraDerecha(padre_rn);
		VerticeRojinegro aux = vertice;
		vertice = padre_rn;
		padre_rn = aux;
		
	    }else if (esRojo(padre_rn) && abuelo != null && abuelo.izquierdo != null && abuelo.izquierdo==padre_rn && padre_rn.derecho != null && padre_rn.derecho==vertice){
		super.giraIzquierda(padre_rn);
		VerticeRojinegro aux = vertice;
		vertice = padre_rn;
		padre_rn = aux;
		
	    }//Caso 5: No estan cruzados
	    if (esRojo(padre_rn) && abuelo != null && abuelo.derecho != null && abuelo.derecho==padre_rn && padre_rn.derecho != null && padre_rn.derecho==vertice){
		padre_rn.color = Color.NEGRO;
		abuelo.color = Color.ROJO;
		super.giraIzquierda(abuelo);
		
	    }else if (esRojo(padre_rn) && abuelo != null && abuelo.izquierdo != null && abuelo.izquierdo==padre_rn && padre_rn.izquierdo != null && padre_rn.izquierdo==vertice){
		padre_rn.color = Color.NEGRO;
		abuelo.color = Color.ROJO;
		super.giraDerecha(abuelo);
		
	    }
	}
	
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	super.agrega(elemento);
	VerticeRojinegro vertice = (VerticeRojinegro) ultimoAgregado;
	vertice.color = Color.ROJO;
	balanceo(vertice);
    }
    
    /**
     * Algoritmo auxiliar para encontrar el hermano de un vertice rojinegro.
     * @param vertice el vertice al cual queremos encontrar su hermano.
     * @return <code>null<code> su es la raiz o no hay vertice, en cualquier otro
     * caso retorna su hermano.
     */
    private VerticeRojinegro encontrarHermano(VerticeRojinegro vertice) {
	if (vertice == null || vertice.padre == null) 
	    return null; 
        
	if (vertice.padre.izquierdo == vertice) {
	   
	    return verticeRN(vertice.padre.derecho);
	}else{
	    return verticeRN(vertice.padre.izquierdo);
	}
    }
    
    
    
    /**
     * Algoritmo auxiliar para balancear el arbol rojinegro despues de eliminar
     * un vertice. Los arboles rojinegros se autobalancean por lo que el algoritmo
     * consta de 6 casos.
     * @param vertice recibe el hijo del vertice eliminado que ahora ocupa su lugar.
     * @return si es la raiz.
     */
    private void balanceoEliminar(VerticeRojinegro vertice){
	//Caso 1: vertice no tiene padre.
	
	if (vertice.padre == null)
	    //Nada
	    return;
	    
	/*Si no se cumple, sabemos que existe el padre. Ademas, como el vertice eliminado
	  era distinto de null y negro, entonces existe el hermano distinto de null */
	VerticeRojinegro padre_rn = verticeRN(vertice.padre);
	VerticeRojinegro hermano = encontrarHermano(vertice);
	//Caso 2: el hermano es rojo
	if (esRojo(hermano)){
	    padre_rn.color = Color.ROJO;
	    hermano.color = Color.NEGRO;
	    if (padre_rn.izquierdo == vertice){
		super.giraIzquierda(padre_rn);
	    }else{
		super.giraDerecha(padre_rn);
	    }
	    hermano = encontrarHermano(vertice);
	    
	}
	VerticeRojinegro hi = verticeRN(hermano.izquierdo);
	VerticeRojinegro hd = verticeRN(hermano.derecho);
	
      
	Color colorHi = (hi != null ? hi.color : Color.NEGRO);
	Color colorHd = (hd != null ? hd.color : Color.NEGRO);
	//Caso 3: todos son negros.
	if (padre_rn.color == Color.NEGRO && hermano.color == Color.NEGRO && colorHi == Color.NEGRO && colorHd == Color.NEGRO){
	    hermano.color = Color.ROJO;
	    balanceoEliminar(padre_rn);
	}
	
	//Caso 4: Todos son negros excepto el padre.
	if (esRojo(padre_rn) && hermano.color == Color.NEGRO && colorHi == Color.NEGRO && colorHd == Color.NEGRO){
	    hermano.color = Color.ROJO;
	    padre_rn.color = Color.NEGRO;
	}
	
	//Caso 5: Se divide en dos subcasos.
	if (padre_rn.izquierdo != null && padre_rn.izquierdo ==vertice && esRojo(hi) && colorHd == Color.NEGRO){
	    hermano.color = Color.ROJO;
	    hi.color = Color.NEGRO;
	    super.giraDerecha(hermano);
	    hermano = encontrarHermano(vertice);
	    hi = verticeRN(hermano.izquierdo);
	    hd = verticeRN(hermano.derecho);
	    
	}else if (padre_rn.derecho != null && padre_rn.derecho == vertice && esRojo(hd) && colorHi == Color.NEGRO){
	    hermano.color = Color.ROJO;
	    hd.color = Color.NEGRO;
	    super.giraIzquierda(hermano);
	    hermano = encontrarHermano(vertice);
	    hi = verticeRN(hermano.izquierdo);
	    hd = verticeRN(hermano.derecho);
	}
        
	//Caso 6: similar al 5.
	if (padre_rn.izquierdo != null && padre_rn.izquierdo ==vertice && esRojo(hd)){
	    hermano.color = padre_rn.color;
	    padre_rn.color = Color.NEGRO;
	    hd.color = Color.NEGRO;
	    super.giraIzquierda(padre_rn);
	    
	} else if ( padre_rn.derecho != null && padre_rn.derecho ==vertice && esRojo(hi)){
	    hermano.color = padre_rn.color;
	    padre_rn.color = Color.NEGRO;
	    hi.color = Color.NEGRO;
	    super.giraDerecha(padre_rn);
	}
    }
    
    
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	VerticeRojinegro vertice = (VerticeRojinegro) busca(elemento);
	VerticeRojinegro nil = null;
	VerticeRojinegro hijo = null;
	if (vertice == null)
	    return;
	
	/* Nos aseguramos que vertice tenga exactamente un hijo antes de eliminarlo. 
	 Dos casos posibles: Es hoja o tiene dos hijos, puede darse los dos casos 
	seguidos, es decir, uno no implica al otro.*/

	//Caso 1: Dos hijos distintos de null.
	if (vertice.hayIzquierdo() && vertice.hayDerecho()){
	    vertice = (VerticeRojinegro) intercambiaEliminable(vertice);
	}
	
	//Caso 2: Es hoja, le creamos un vertice fantasma.
	if (!vertice.hayIzquierdo()  && !vertice.hayDerecho()){
	    nil = verticeRN(nuevoVertice(null)); 
	    nil.color = Color.NEGRO;
	    vertice.izquierdo = nil;
	    nil.padre = vertice;
	    hijo = nil;
	}else {
	    hijo = verticeRN(vertice.hayIzquierdo() ? vertice.izquierdo : vertice.derecho);
	}
	//El hijo puede ser tanto el fantasma como el cambio de posicion o bien otro arbitrario.
	
	eliminaVertice(vertice);
	elementos--;
	
	/*Sea el hijo de vertice que ahora ocupa su lugar o bien el fantasma, vertice intercambiado
	 o bien vertice arbitrario pero con un solo hijo. Tenemos tres posibilidades.*/
        
        if (esRojo(hijo)){
	    hijo.color = Color.NEGRO;
	}else{
	    if (hijo.color == Color.NEGRO && vertice.color == Color.NEGRO)
		balanceoEliminar(hijo);
	}

	//Eliminamos al fantasma si existe.
	if (nil != null){
	    if (nil.padre == null){
		limpia();
	    }else if (nil.padre.izquierdo != null && nil.padre.izquierdo.elemento == null)
		nil.padre.izquierdo = null;
	    else if (nil.padre.derecho != null && nil.padre.derecho.elemento == null)
		nil.padre.derecho = null;
	}
	    
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
