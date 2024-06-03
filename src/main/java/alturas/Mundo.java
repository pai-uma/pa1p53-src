package alturas;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.PrintWriter;
import java.util.*;

/**
 * La clase Mundo mantiene información de los países del mundo mediante una lista
 * de países (List<Pais> paises), y define los siguientes constructores y métodos:
 */
public class Mundo {
	//------------------------------------------------------------------
    /**
     * Método para presentar sobre un PrintWriter (primer argumento) cualquier correspondencia.
     * Como la clave y el valor pueden ser cualesquiera, se pasan como parámetros genéricos <K,V>.
     * 
     * @param <K>	Clave para la correspondencia
     * @param <V>	Valor para la correspondencia
     * @param pw	PrintWriter
     * @param map	Correspondencia
     */
	public static <K,V> void presentaEnPW(PrintWriter pw, Map<K,V> map) {
        for (Map.Entry<K,V> e : map.entrySet()) {
            pw.println(e.getKey() + "\t" + e.getValue());
        }
    }
	
	/**
	 * Método para presentar en consola cualquier correspondencia.
	 *  Como la clave y el valor pueden ser cualesquiera, se pasan como parámetros genéricos <K,V>.
     * 
     * @param <K>	Clave para la correspondencia
     * @param <V>	Valor para la correspondencia
     * @param map	Correspondencia
	 */
    public static <K,V> void presentaEnConsola(Map<K,V> map) {
    	presentaEnPW(new PrintWriter(System.out,true),map);
    }
	//------------------------------------------------------------------
    
    // Variable privada que contiene la lista de países del mundo.
    private List<Pais> paises;

    /**
     * Constructor para inicializar la lista de países con la que se pasa como argumento.
     */
	public Mundo() {
        paises = new LinkedList<>();
    }
	
	/**
	 * Devuelve la lista de países.
	 * 
	 * @return	Lista de países
	 */
    public List<Pais> getPaises() {
		return paises;
	}

    /**
     * Lee los países del fichero cuyo nombre se recibe como argumento. 
     * Devuelve un objeto Mundo con los países leídos. El fichero tiene 
     * el siguiente formato por cada línea: 
     * 		nombrePaís,continente,altura
     * Si una línea del fichero no es correcta, se ignora y se sigue con la siguiente.
     * 
     * @param fich	Nombre del fichero
     * @throws IOException
     */
	public void cargar(String fich) throws IOException {
		paises.clear();
        try (Scanner sc = new Scanner(Path.of(fich))){
            while (sc.hasNextLine())  {
                String linea = sc.nextLine();
				cargarLinea(linea);
            }
        }
	}
	
	/**
	 * Lectura alternativa de países, utilizando BufferedReader en vez de Scanner.
	 * 
	 * @param fich	Nombre del fichero
	 * @throws IOException
	 */
	public void cargarAlternativo(String fich) throws IOException {
		paises.clear();
		try (BufferedReader buffReader = Files.newBufferedReader(Path.of(fich))) {
			String linea = buffReader.readLine();
			while (linea != null) {
				cargarLinea(linea);
				linea = buffReader.readLine();
			}
		}
	}

	/**
	 * Método pricado auxiliar que carga la información de una línea con el formato:
	 * 		nombrePaís,continente,altura
	 * 
	 * @param linea	Información de un país
	 */
	private void cargarLinea(String linea) {
		try (Scanner scPais  = new Scanner(linea)) {
			scPais.useDelimiter("\\s*,\\s*");
			scPais.useLocale(Locale.ENGLISH);
			Pais pais = new Pais(scPais.next(), scPais.next(), scPais.nextDouble());
			paises.add(pais);
		} catch (NoSuchElementException e) { // Incluye también InputMismatchException
			// Se ignora la línea
		} 
	}

	/**
	 * Devuelve una correspondencia que asocia a cada continente
	 * el número de países que tiene.
	 * 
	 * @return	Correspondencia entre continentes y número de países
	 */
    public Map<String, Integer> numeroDePaisesPorContinente() {
    	// Creamos un TreeMap para que se almacenen los continentes ordenados por nombre
        Map<String, Integer> map = new TreeMap<>();
        for (Pais pais : paises) {
        	String continente = pais.getContinente();
			int np = map.getOrDefault(continente,0);
			map.put(continente, np+1);
        }
        return map;
    }

    /**
     * Devuelve una correspondencia que asocia cada altura con una lista de
     * países que tienen esa altura media. Las alturas se deben truncar a un 
     * solo decimal.
     * 
     * @return	Correspondencia entre alturas medias y listas de países
     */
    public Map<Double, List<Pais>> paisesPorAltura() {
    	// Creamos un TeeMap para que se almacenen ordenados por la altura
        Map<Double, List<Pais>> map = new TreeMap<>();
        for (Pais pais : paises) {
            double altura = pais.getAltura();
            double altura1Dec = (int)(altura*10)/10.0;
            
            List<Pais> list = map.get(altura1Dec);
            if (list == null) {
                list = new ArrayList<>();
                map.put(altura1Dec, list);
            }
            list.add(pais);
            
            //List<Pais> list = map.computeIfAbsent(altura1Dec, (alt) -> new ArrayList<>());
            //list.add(pais);
        }
        return map;
    }

    /**
     * Devuelve una correspondencia ordenada por el orden natural, en la que
     * cada continente se asocia con un conjunto de países que contiene, ordenados
     * por el orden natural.
     * 
     * @return	Correspondencia entre continentes y los conjuntos de países que contienen
     */
    public SortedMap<String, SortedSet<Pais>> paisesPorContinente() {
        SortedMap<String, SortedSet<Pais>> map = new TreeMap<>();
        for (Pais pais : paises) {
            String continente = pais.getContinente();
            SortedSet<Pais> set = map.get(continente);
            if (set == null) {
                set = new TreeSet<>();
                map.put(continente, set);
            }
            set.add(pais);
        }
        return map;
    }

    /**
     * Devuelve una correspondencia ordenada por el orden natural, en la que
     * cada inicial se asocia un conjunto ordenado de países que empiezan por ella. 
     * 
     * @return	Correspondencia entre letras y los conjuntos de países que empiezan por ella
     */
    public SortedMap<Character, SortedSet<Pais>> paisesPorInicial() {
        SortedMap<Character, SortedSet<Pais>> map = new TreeMap<>();
        for (Pais pais : paises) {
            char inicial = pais.getNombre().charAt(0);
            SortedSet<Pais> set = map.get(inicial);
            if (set == null) {
                set = new TreeSet<>();
                map.put(inicial, set);
            }
            set.add(pais);
        }
        return map;
    }

    /**
     * Devuelve una correspondencia ordenada, en la que
     * a cada continente se le asocia la media de altura de sus países.
     * 
     * @return	Correspondencia entre continentes y la altura media de países
     */
    public SortedMap<String, Double> mediaPorContinente() {
        SortedMap<String, SortedSet<Pais>> map =  this.paisesPorContinente();
        SortedMap<String, Double> medias = new TreeMap<>();
        for (Map.Entry<String, SortedSet<Pais>> e: map.entrySet()) {
            double suma =  0;
			if (e.getValue().size() > 0) {
				for (Pais pais: e.getValue()) {
					suma += pais.getAltura();
				}
				suma = suma / e.getValue().size();
			}
			medias.put(e.getKey(), suma);
        }
        return medias;
    }

    /**
     * Devuelve la lista de los contienentes que tengan el número mayor de contienentes.
     * 
     * @return	Lista de continentes
     */
    public List<String> continentesConMasPaises() {
        Map<String, Integer> map = numeroDePaisesPorContinente();
        List<String> res = new LinkedList<>();
		Collection<Integer> values = map.values();
		if (values.size() > 0) {
			int maxValor = Collections.max(values);
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() == maxValor) {
					res.add(entry.getKey());
				}
			}
		}
        return res;
    }

    /**
     * Devuelve la lista de los contienentes con más países, utilizando un método alternativo.
     * 
     * @return Lista de continentes 
     */
    public List<String> continentesConMasPaises_Alternativo_1() {
        Map<String, Integer> map = numeroDePaisesPorContinente();
        SortedMap<Integer, List<String>> mapI = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            List<String> paises = mapI.get(entry.getValue());
            if (paises == null) {
                paises = new LinkedList<>();
                mapI.put(entry.getValue(), paises);
            }
            paises.add(entry.getKey());
        }
        return mapI.isEmpty() ? new LinkedList<>() : mapI.get(mapI.lastKey());
    }

    /**
     * Devuelve la lista de los contienentes con más países, utilizando un método alternativo.
     * 
     * @return Lista de continentes 
     */
    public List<String> continentesConMasPaises_Alternativo_2() {
        Map<String, Integer> map = numeroDePaisesPorContinente();
		int maxValor = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() > maxValor) {
				maxValor = entry.getValue();
			}
        }
        List<String> res = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == maxValor) {
				res.add(entry.getKey());
			}
        }
        return res;
    }

	//------------------------------------------------------------------
	//-- Resultados con orden alternativo ------------------------------
	//------------------------------------------------------------------
	
    /**
     * Devuelve el conjunto ordenado de países, utilizando como criterio, 
     * primero la altura y luego el nombre.
     * 
     * @return	Conjunto ordenado de países
     */
    public SortedSet<Pais> paisesOrdenadosPorAltura() {
        //Comparator<Pais> ordenAltura = new CompAlturaNombre();
    	//Comparator<Pais> ordenAltura = (Comparator.comparing((Pais pais) -> pais.getAltura())).thenComparing(Comparator.naturalOrder());
    	Comparator<Pais> ordenAltura = new CompAltura().thenComparing(new CompNombre());
        SortedSet<Pais> set = new TreeSet<>(ordenAltura);
        set.addAll(paises);
        return set;
    }

    /**
     * Devuelve una correspondencia que asocia a cada continente el conjunto de paises que tiene, 
     * pero ordenados por la altura media. La correspondencia se ordena según el orden natural.
     * 
     * @return Corresponencia entre continentes y conjuntos ordenados de países
     */
    public SortedMap<String, SortedSet<Pais>> paisesPorContinenteAltura() {
        SortedMap<String, SortedSet<Pais>> map = new TreeMap<>();
        for (Pais pais : paises) {
            String continente = pais.getContinente();
            SortedSet<Pais> set = map.get(continente);
            if (set == null) {
                // Ver método paisesOrdenadosPorAltura()
				set = new TreeSet<>(new CompAltura().thenComparing(Comparator.naturalOrder()));
                map.put(continente, set);
            }
            set.add(pais);
        }
        return map;
    }

    /**
     * Devuelve una correspondencia que asocia a cada contienente el conjunto de países ordenadao por 
     * altura de forma decreciente. La correspondencia se ordena según el orden natural.
     * 
     * @return
     */
    public SortedMap<String, SortedSet<Pais>> paisesPorContinenteAlturaDec() {
        SortedMap<String, SortedSet<Pais>> map = new TreeMap<>();
        for (Pais pais : paises) {
            String continente = pais.getContinente();
            SortedSet<Pais> set = map.get(continente);
            if (set == null) {
                //set = new TreeSet<>(new CompAlturaNombre().reversed());
				set = new TreeSet<>(new CompAltura().thenComparing(new CompNombre()).reversed());
                map.put(continente, set);
            }
            set.add(pais);
        }
        return map;
    }

}
