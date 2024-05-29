package alturas;

import java.util.Comparator;

/**
 * Clase que representa un orden alternativo para la clase Pais, de forma que 
 * un país es menor que otro cuando su nombre es menor, sin tener en cuenta mayúsculas
 * o minúsculas.
 */
public class CompAltura implements Comparator<Pais> {
    public int compare(Pais p1, Pais p2) {
        return Double.compare(p1.getAltura(), p2.getAltura());
    }
}
