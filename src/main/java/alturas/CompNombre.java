package alturas;

import java.util.Comparator;

/**
 * Clase que representa un orden alternativo para la clase País, de forma que 
 * un país es menor que otro cuando su nombre es menor, sin tener en cuenta mayúsculas
 * o minúsculas.
 */
public class CompNombre implements Comparator<Pais> {
    public int compare(Pais p1, Pais p2) {
		return p1.getNombre().compareToIgnoreCase(p2.getNombre());
    }
}
