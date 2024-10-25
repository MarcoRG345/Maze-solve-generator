package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {
    
    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    private static <T> void quickSort(T[] arreglo, int izquierda, int derecha, Comparator<T> comparador){
	if (derecha <= izquierda)
	    return;
	int i = izquierda + 1;
	int j = derecha;
	T pivote = arreglo[izquierda];
	while (i < j){
	    if (comparador.compare(arreglo[i], pivote) > 0 && comparador.compare(arreglo[j], pivote) <= 0){
		intercambia(arreglo, i, j);
		i++;
		j--;
	    } else if (comparador.compare(arreglo[i], pivote) <= 0){
		i++;
	    } else{
		j--;
	    }
	}
	if (comparador.compare(pivote, arreglo[i]) < 0)
	    i--;
	intercambia(arreglo, izquierda, i);
	quickSort(arreglo, izquierda, i - 1, comparador);
	quickSort(arreglo, i + 1, derecha, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
	quickSort(arreglo, 0, arreglo.length - 1, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    
    /**
     * Metodo auxiliar para intercambiar las posiciones
     * de los algoritmos de ordenamiento.
     * @param arreglo el arreglo a ordenar.
     * @param i el indice del i-esimo elemento.
     * @param menor el menor del arreglo.
     */
    private static <T> void intercambia(T[] arreglo, int i, int j){
	T aux = arreglo[i];
	arreglo[i] = arreglo[j];
	arreglo[j] = aux;
    }
    
    /**
     * Metodo auxiliar para desarrollar el algoritmo selectionSort de forma general.
     * @param arreglo recibe el arreglo a ordenar.
     * @param n recibe la cantidad de elementos del arreglo.
     * @param comparador compara los elementos genericos del arreglo.
     */
    private static <T> void selectionSort(T[] arreglo, int n, Comparator<T> comparador){
	for (int i = 0; i < (n - 1); i++){
	    int menor = i;
	    for (int j = i + 1; j < n; j++){
		if (comparador.compare(arreglo[j], arreglo[menor]) < 0)
		    menor = j;
	    }
	    intercambia(arreglo, i, menor);
	}
    }
    
    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
	selectionSort(arreglo, arreglo.length, comparador);
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Metodo auxiliar para realizar la recursion sobre el arreglo en 
     * busqueda binaria.
     * @param arreglo recibe el arreglo ordenado.
     * @param izquierda el indice 0 de cualquier subarreglo del arreglo.
     * @param derecha el indice ultimo de cualquier subarreglo del arreglo.
     * @param comparador objeto para comparar los elementos de tipo generico
      comparables.
      * @param elemento el elemento que deseamos buscar.
      * @return -1 si no se encuentra el indice o esta vacio, m el indice buscado.
     */
    private static <T> int busquedaBinaria(T[] arreglo, int izquierda, int derecha, Comparator<T> comparador, T elemento){
	if (derecha < izquierda)
	    return -1;
	//Evitamos desborde.
	int m = izquierda + ((derecha - izquierda)/2);
	// si A[m] y elemento son iguales devolvemos el indice.
	if (comparador.compare(elemento, arreglo[m]) == 0)
	    return m;
	    //Si no. Dos casos:
	else{
	    if (comparador.compare(elemento, arreglo[m]) < 0){
		//el elemento se encuentra del lado izquierdo y hacemos recursion.
		return busquedaBinaria(arreglo, izquierda, m - 1, comparador, elemento);
	    }else if (comparador.compare(elemento, arreglo[m]) > 0){
		//el elemento se encuentra del lado derecho y hacemos recursion.
		return busquedaBinaria(arreglo, m + 1, derecha, comparador, elemento);
	    }
	}
	return -1;
	
    }
    
    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int  busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
	return busquedaBinaria(arreglo, 0, arreglo.length - 1, comparador, elemento);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
