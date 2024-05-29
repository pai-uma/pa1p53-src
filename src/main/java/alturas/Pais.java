package alturas;

import java.util.Objects;
/**
 * La clase Pais mantiene información de un país, el continente al que pertenece
 * y la altura media de sus habitantes. Así un país tendrá un nombre de país
 * (String nombre), un nombre de continente (String continente), y la altura
 * media de sus habitantes (double altura).
 */
public class Pais implements Comparable<Pais> {
	// Variable privada que almacena el nombre del país.
    private String nombre;
    
    // Variable privada que almacena el nombre del continente al que pertenece el país.
    private String continente;
    
    // Variable privada que almacena la altura media de los habitantes.
    private double altura;

    
    /**
     * Constructor que crea países a partir de su nombre, el continente y la 
     * altura media de sus habitantes.
     * 
     * @param nombre	Nombre del país
     * @param continente	Nombre del continente
     * @param altura	Altura media
     */
    public Pais(String nombre, String continente, double altura) {
        this.nombre = nombre;
        this.continente = continente;
        this.altura = altura;
    }
    
    /**
     * Devuelve el nombre del país.
     * 
     * @return	Nombre del país
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Devuelve el nombre del continente.
     * 
     * @return Nombre del continente
     */
    public String getContinente() {
        return continente;
    }
    
    /**
     * Devuelve la altura media de los habitantes del país.
     * 
     * @return	Altura media
     */
    public double getAltura() {
        return altura;
    }
    
    /**
     * Un país es igual a otro si sus nombres son iguales, 
     * considerando que las letras mayúsculas y minúsculas son diferentes.
     */
    @Override
    public boolean equals(Object o) {
		boolean ok = false;
        if (o instanceof Pais) {
			Pais other = (Pais)o;
			ok = this.getNombre().equals(other.getNombre());
		}
		return ok;
    }
    
    /** 
     * Redefinición de hashCode adecuada a la definición de equals.
     */
    @Override
    public int hashCode() {
        //return getNombre().hashCode();
        return Objects.hash(this.getNombre());
    }
    
    /**
     * Un país es menor que otro cuando su nombre lo es alfabéticamente.
     */
    @Override
    public int compareTo(Pais p) {
        return this.getNombre().compareTo(p.getNombre());
    }
    
    /**
     * Representación textual de un país:
     * 	País(Nombre, Continente, AlturaMedia)
     */
    @Override
    public String toString() {
        return "Pais(" + getNombre() + ", " + getContinente() + ", "  + getAltura() + ")";
    }
}
