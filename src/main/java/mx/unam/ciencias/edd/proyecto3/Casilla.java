package mx.unam.ciencias.edd.proyecto3;
import java.util.Random;
import mx.unam.ciencias.edd.Diccionario;
import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Lista;

public class Casilla{

    /* Direccion norte.*/
    private boolean norte;
    /* Direccion sur. */
    private boolean sur;
    /* Direccion este.*/
    private boolean este;
    /* Direccion oeste. */
    private boolean oeste;
    /* El estado del cuarto visitado o no. */
    private boolean visitado;
    /* El puntaje del cuarto*/
    private int puntaje;
    /* Random para generar el puntaje*/
    private static final Random rand = new Random();
    /* Truco para identificar una casilla especial, entrada o salida.*/
    private boolean esEntrada;
    /* Constructor sin parametros de cada casilla. */
    public Casilla(){
	norte = true;
	sur = true;
	este = true;
	oeste = true;
	visitado = false;
	esEntrada = false;
	puntaje = getRandom();
    }
    /* Constructor con parametros. Crea una casilla a partir de su representacion binaria. */
    public Casilla(String bin){
	while (bin.length() < 4)
	    bin = "0"+ bin;
	if (bin.charAt(0) == '1'){
	    sur = true;
	}else{
	    sur = false;
	}
	if (bin.charAt(1) == '1'){
	    oeste = true;
	}else{
	    oeste = false;
	}
	if (bin.charAt(2) == '1'){
	    norte = true;
	}else{
	    norte = false;
	}
	if (bin.charAt(3) == '1'){
	    este = true;
	}else{
	    este = false;
	}
	esEntrada = false;
    }

    /* Genera un numero aleatorio para el puntaje.*/
    private int getRandom(){
	return rand.nextInt(15) + 1;
    }

    /** @return el estado de puerta norte. */
    public boolean getNorte(){ return norte; }
    /** @return el estado de la puerta sur. */
    public boolean getSur(){ return sur; }
    /** @return el estado de la puerta este. */
    public boolean getEste(){ return este; }
    /** @return el estado de la puerta oeste. */
    public boolean getOeste(){ return oeste; }
    /** @return el estado de la visita de la casilla.*/
    public boolean seVisito() { return visitado; }

    public int getPuntaje(){
	return puntaje;
    }
    public boolean getEsEntrada(){ return esEntrada; }

    /**
     * Regresa el estado de las puertas existentes
     * con su respectivo numero entero relacionado
     * al binario. 
     * <p> Nos ayudamos de un diccionario que mapea
     * la llave binaria a los valores enteros asociados
     * con todas las combinaciones posibles.
     * </p>
     * @return estado el entero relacionado con las
     * puertas existentes.
     */
    private int getEstado(){
	int estado = 0;
	Casilla actual = this;
	Diccionario<String, Integer> dicc = Mapea.combinaciones();
        
	String bin = "";
	if (actual.este){
	    bin = "1" + bin; 
	}else{
	    bin = "0" + bin;
	}
	if (actual.norte){
	    bin = "1" + bin;
	}else{
	    bin = "0" + bin;
	}
	if (actual.oeste){
	    bin = "1" + bin;
	}else{
	    bin = "0" + bin; 
	}
	if (actual.sur){
	    bin = "1" + bin;
	}else{
	    bin = "0" + bin;
	}
	try{
	    estado = dicc.get(bin);
	}catch (NoSuchElementException nse) {}
	
        return estado;  
    }
    
    /**
     * Regresa el byte de la casilla.
     * @return la mezcla de dos enteros; puntaje
     * y estado como tipo byte de la casilla.
     */
    public int getByte(){
	byte valor = 0x00;
	int first_byte = (0xFF & getEstado()) | valor;
	int sec_byte = (0xFF & puntaje) | valor;
	int sec_byte_1 = (byte) sec_byte << 4;
        return sec_byte_1 | first_byte;
    }

    /* Modifica la puerta norte de la casilla. */
    public void setNorte(boolean norte){
	this.norte = norte;
    }
    /* Modifica la puerta sur de la casilla.*/
    public void setSur(boolean sur){
	this.sur = sur;
    }
    /* Modifica la puerta este de la casilla. */
    public void setEste(boolean este){
	this.este = este;
    }
    /* Modifica la puerta oeste de la casilla. */
    public void setOeste(boolean oeste){
	this.oeste = oeste;
    }
    /*Modifica la estado de la entrada, es o no es.*/
    public void set_esEntrada(boolean esEntrada){
	this.esEntrada = esEntrada;
    }
    
    /**
     * Modifica la visita de la casilla. 
     * El metodo ayuda a registrar la visita cuando
     * se realiza DFS para generar el laberinto.
     * @param visitado la marca de visita.
     */
    public void marcarVisita(boolean visitado){
	this.visitado = visitado;
    }

 
}
