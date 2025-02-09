package mx.unam.ciencias.edd.proyecto3;

/**
 * Interfaz para tener herramientas de dibujo SVG. 
 * <p> Un dibujo, se compone de lineas, rectangulos, etc.
 * en el formato SVG </p>
 */
public interface Dibujo{

    /**
     * Dibuja lineas con todo el formato de etiqueta SVG.
     * @param origen_x coordenada x origen.
     * @param destino_x coordenda x destino.
     * @param origen_y coordenda y origen.
     * @param destino_y coordenda y destino.
     * @return linea la linea.
     */
    default public String lineas(double origen_x, double destino_x, double origen_y, double destino_y) {
        String linea = " <line x1= \""+origen_x+"\""+" y1= \""+origen_y+"\"";
        linea += " x2= \""+destino_x+"\" y2=\""+destino_y+"\" stroke-width= \"0.3\" stroke= \"blue\"/>\n";
	return linea;
    }
    
    /**
     * Dibuja rectangulos con el formato de etiqueta SVG.
     * @param coordX
     * @param coordY
     * @param dimension
     * @param color
     * @param borde
     * @return str.toString() 
     */
    default public String rect(double coordX, double coordY, double dimension, String color, String borde){
	StringBuilder str = new StringBuilder();
	str.append("<rect "+"x= \""+coordX+"\" y=\""+coordY+"\" fill = \""+color+"\" ");
	str.append("width=\""+dimension+"\""+" height=\""+dimension+"\" stroke= \""+borde+"\" />\n");
	return str.toString();
    }

    /**
     * Dibuja un rectangulo de forma libre, donde el ancho y el alto son variables
     * independientes.
     */
    default public String borde_rect(double coordX, double coordY, double ancho, double alto, String color){
	StringBuilder str = new StringBuilder();
	str.append("  <rect"+" x= \""+coordX+"\" y=\""+coordY+"\" fill = \""+color+"\" ");
	str.append("width=\""+ancho+"\""+" height=\""+alto+ "\"/>\n");
	return str.toString();
    }

    default public String circle(double cx, double cy, double radio, String color){
	StringBuilder str = new StringBuilder();
	str.append("  <circle cx= \""+cx+"\" cy=\""+cy+"\" r=\""+radio+"\" fill= \""+color+"\" />");
	return str.toString();
    }
    
}
