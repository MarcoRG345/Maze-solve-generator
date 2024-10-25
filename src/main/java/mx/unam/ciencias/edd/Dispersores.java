package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}


    /**
     * Mezcla 4 bytes para formar un entero. Aplicamos una mascara 0xFF
     * que equivale a 1111 1111 en binario por lo que al hacer AND con 
     * el byte correspondiente nos quedamos con los bits menos significativos,
     * luego recorremos x bits a la izquierda, por lo que en total nos quedamos
     * con el byte mas significativo, en formato big-indian.
     * @param a primer byte.
     * @param b segundo byte.
     * @param c tercer byte.
     * @param d cuarto byte.
     * @return el entero combinado con 4 bytes.  
     */
    private static int combina(byte a, byte b, byte c, byte d){
	return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));
    }
    
    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
	int r = 0;
	int i = 0;
	if (llave.length == 4)
	    return combina(llave[0], llave[1], llave[2], llave[3]);
 
        if (llave.length > 4){
	    r = combina(llave[i], llave[i + 1], llave[i + 2], llave[i +3]);
	    i = 4;
	    while (llave.length >= (i + 4)){	    
		r ^= combina(llave[i], llave[i+1], llave[i+2], llave[i+3]);
		i = i + 4;
	    }
	}
	int aux = llave.length - i;
	byte cero = 0;
	switch (aux){
	case 3:
	    r ^= combina(llave[i], llave[i + 1], llave[i + 2], cero);
	    break;
	case 2:
	    r ^= combina(llave[i], llave[i + 1], cero, cero);
	    break;
	case 1:
	    r ^= combina(llave[i], cero, cero, cero);
	    break;
	}
	return r;
    }


    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;
        int i = 0;
	boolean flag = true;
	while (flag) {
	    a += l_Indi(get(llave, i), get(llave, i+1), get(llave, i+2), get(llave, i+3));
	    i += 4;
	    b += l_Indi(get(llave, i), get(llave, i+1), get(llave, i+2), get(llave, i+3));
	    i += 4;
	    int aux = llave.length - i;
	    if (aux >= 4){
		 c += l_Indi(get(llave, i), get(llave, i+1), get(llave, i+2), get(llave, i+3));
	    }else {
		flag = false;
		c += llave.length;
		c += l_Indi(0, get(llave, i), get(llave, i+1), get(llave, i+2));
	    }
	    i+= 4;
	    /* Mezcla primera vez*/
	    a -= b + c;
	    a ^= (c >>> 13);
	    b -= c + a;
	    b ^= (a << 8);
	    c -= b + a;
	    c ^= (b >>> 13);
	    
	    /* Mezcla segunda vez.*/
	    a -= b + c;
	    a ^= (c >>> 12);
	    b -= c + a;
	    b ^= (a << 16);
	    c -= a + b;
	    c ^= (b >>> 5);
	    
	    /* Mezcla tercera vez. */
	    a -= b + c;
	    a ^= (c >>> 3);
	    b -= c + a;
	    b ^= (a << 10);
	    c -= a + b;
	    c ^= (b >>> 15);
	}
        return c;
    }
    
    /**
     * Para fines de practicidad, recibe 3 enteros, en formato little indian
     * recorremos bit a bit haciendo OR de enteros.
     * @return la mezcla de enteros en formato little-indian.
     */
    private static int l_Indi(int a, int b, int c, int d){
	return a | (b << 8) | (c << 16) | (d << 24); 
    }

    /**
     * Obtiene los bits menos signifcativos al hacer AND de 0xFF si y solo si
     * el indice es menor a la longitud de la llave, caso contrario solo regresa 0.
     * @return AND de bytes en caso que el indice sea menor a la longitud de la llave.
     * Caso contrario regresa 0.
     */
    private static int get(byte[] llave, int i) {
        if (i < llave.length)
            return (0xFF & llave[i]);
        return 0;
    }
    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
	int h = 5381;
	for (int i = 0; i < llave.length; i++)
	    h = h + (h << 5) + (llave[i] & 0xFF);
	return h;
    }
}
