package mx.unam.ciencias.edd.proyecto3;

public class GraficaCasilla implements Dibujo{
    /* Parametro x. */
    private int x;
    /* Parametro y. */
    private int y;
    /* Ratrea la solucion. */
    private boolean rastreador;
    /* Dimension de casilla. */
    private int dimension;
    /*La casilla actual. */
    private Casilla casilla;
    
    /**
     * Contructor por paremetros. Recibe la casilla actual y las coordenadas
     * (x,y), asi como la dimension requerida.
     * @param casilla la casilla actual.
     * @param x la coordenada x.
     * @param y la coordenada y.
     * @param dimension la dimension de la GraficaCasilla.
     */
    public GraficaCasilla(Casilla casilla, int x, int y, int dimension){
	this.x = x;
	this.y = y;
	this.dimension = dimension;
	this.casilla = casilla;
	rastreador = false;
    }

    /** @return la coordenda x. */
    public int getCoordX(){
	return x;
    }

    /** @return la coordenda y. */
    public int getCoordY(){
	return y;
    }
    /** @return la casilla que se envolvio. */
    public Casilla get(){
	return casilla;
    }
    /** @return la dimension. */
    public int getDimension(){
	return dimension;
    }
    

    /** @return el rastreador. */
    public boolean getRastreador(){
	return rastreador;
    }
    /* Modifica el estado del rastreador. */
    public void setRastreador(boolean rastreador){
	this.rastreador = rastreador;
    }

    /** @return la cadena de entrada. */
    public String esEntrada() {
	String str = "";
	if (casilla != null && casilla.getEsEntrada()){
	    double coordY = y - (dimension/2) - 0.5;
	    double coordX = x + (dimension/2) + 0.5;
	    str = circle(coordX, coordY, 0.5, "red");
	}
	return str;
    }

    /**
     * Regresa la representacion en cadena de la casilla.
     * @return la representacion en cadena.
     */
    @Override public String toString(){
	StringBuilder str  = new StringBuilder(); 
	str.append(esEntrada());
	if (casilla.getNorte()){
	    int coordY = y - dimension;
	    str.append(borde_rect(x, coordY, dimension, 0.1, "black"));
	}
	if (casilla.getOeste()){
	    int coordY = y - dimension;
	    str.append(borde_rect(x, coordY, 0.1, dimension, "black"));
	}
	if (casilla.getEste()){
	    int coordX = x + dimension;
	    int coordY = y - dimension;
	    str.append(borde_rect(coordX, coordY, 0.1, dimension, "black"));
	}
	
	if (casilla.getSur()){
	    str.append(borde_rect(x, y, dimension, 0.1, "black"));
	}
        
	return str.toString();
    }
}
