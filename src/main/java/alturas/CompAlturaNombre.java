package alturas;

import java.util.Comparator;

/**
 * Clase que representa un orden alternativa para Pais. Un país es menor que otro
 * cuando su altura media es menor, hy en caso de igualdad, cuando su nobre lo sea.
 */
public class CompAlturaNombre implements Comparator<Pais> {
    public int compare(Pais p1, Pais p2) {
        int res = Double.compare(p1.getAltura(), p2.getAltura());
        if (res == 0) {
            res = p1.getNombre().compareToIgnoreCase(p2.getNombre());
        }
        return res;
    }
}
