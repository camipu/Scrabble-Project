package edu.upc.prop.clusterxx;

import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Classe que representa un sac de fitxes utilitzat en el joc.
 */
public class Sac {
    private final Map<Fitxa, Integer> fitxes;

    /**
     * Crea un sac i l'inicialitza amb fitxes d'un idioma especificat.
     * @param idioma L'idioma per carregar les fitxes.
     */
    public Sac(String idioma) {
        this.fitxes = new LinkedHashMap<>();
        try {
            inicialitzarSac(idioma);
        } catch (IOException e) {
            System.err.println("Error en llegir el fitxer de fitxes: " + e.getMessage());
        }
    }

    /**
     * Crea un sac amb un conjunt inicial de fitxes.
     * @param fitxesini El conjunt inicial de fitxes amb les seves quantitats.
     */
    public Sac(Map<Fitxa, Integer> fitxesini) {
        this.fitxes = new LinkedHashMap<>(fitxesini);
    }

    /**
     * Crea un sac buit.
     */
    public Sac() {
        this.fitxes = new LinkedHashMap<>();
    }

    /**
     * Inicialitza el sac llegint fitxes des d'un fitxer segons l'idioma.
     * @param idioma L'idioma a carregar.
     * @throws IOException Si hi ha un error en la lectura del fitxer.
     */
    private void inicialitzarSac(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(ruta));

        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Format incorrecte al fitxer: " + line);
            }

            try {
                char lletra = parts[0].charAt(0);
                int quantitat = Integer.parseInt(parts[1]);
                int punts = Integer.parseInt(parts[2]);

                if (quantitat <= 0 || punts < 0) {
                    throw new IllegalArgumentException("Quantitat o punts invàlids a la línia: " + line);
                }

                fitxes.put(new Fitxa(lletra, punts), quantitat);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error de format numèric a la línia: " + line, e);
            }
        }
    }

    /**
     * Retorna una fitxa específica si està disponible.
     * @param fitxa La lletra de la fitxa que es vol obtenir.
     * @return La fitxa corresponent.
     * @throws NoSuchElementException Si la fitxa no està disponible.
     */
    public Fitxa agafarFitxa(Fitxa fitxa) {
        Fitxa f = obtenirFitxa(fitxa).orElseThrow(() ->
                new NoSuchElementException("No hi ha fitxes disponibles amb la lletra '" + fitxa.getLletra() + "'"));
        reduirQuantitat(fitxa);
        return fitxa;
    }

    /**
     * Retorna una fitxa aleatòria si el sac no està buit.
     * @return Una fitxa aleatòria.
     * @throws IllegalStateException Si el sac està buit.
     */
    public Fitxa agafarFitxa() {
        if (esBuit()) {
            throw new IllegalStateException("No es pot agafar una fitxa: el sac està buit.");
        }

        List<Fitxa> disponibles = new ArrayList<>();
        for (Map.Entry<Fitxa, Integer> entry : fitxes.entrySet()) {
            Fitxa fitxa = entry.getKey();
            int quant = entry.getValue();
            disponibles.addAll(Collections.nCopies(quant, fitxa));
        }

        Fitxa seleccionada = disponibles.get(new Random().nextInt(disponibles.size()));
        reduirQuantitat(seleccionada);
        return seleccionada;
    }

    /**
     * Retorna una fitxa si existeix.
     * @param fitxa La lletra de la fitxa a cercar.
     * @return Una instància Optional amb la fitxa si existeix.
     */
    private Optional<Fitxa> obtenirFitxa(Fitxa fitxa) {
        return fitxes.keySet().stream().filter(f -> f == fitxa).findFirst();
    }

    /**
     * Redueix la quantitat d'una fitxa i l'elimina si arriba a 0.
     * @param fitxa La fitxa a modificar.
     */
    private void reduirQuantitat(Fitxa fitxa) {
        if (fitxes.containsKey(fitxa)) {
            int quantitat = fitxes.get(fitxa);
            if (quantitat == 1) {
                fitxes.remove(fitxa);
            } else {
                fitxes.put(fitxa, quantitat - 1);
            }
        }

    }

    /**
     * Afegeix una fitxa al sac.
     * @param f La fitxa a afegir.
     */
    public void afegirFitxa(Fitxa f) {
        fitxes.put(f, fitxes.getOrDefault(f, 0) + 1);
    }

    /**
     * Retorna el nombre total de fitxes al sac.
     * @return El nombre total de fitxes.
     */
    public int getNumFitxes() {
        return fitxes.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Comprova si el sac està buit.
     * @return true si està buit, false en cas contrari.
     */
    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    /**
     * Retorna la quantitat de fitxes d'una lletra específica.
     * @param fitxa La lletra de les fitxes a comptar.
     * @return El nombre de fitxes disponibles amb la lletra donada.
     */
    public int quantitatFitxes(Fitxa fitxa) {
        Fitxa f = obtenirFitxa(fitxa).orElse(null);
        return (f != null) ? fitxes.get(f) : 0;
    }

    /**
     * Mostra el contingut del sac per pantalla.
     */
    public void mostrarContingut() {
        fitxes.forEach((fitxa, quantitat) ->
                System.out.println(fitxa.getLletra() + " -> " + quantitat + " fitxes, " + fitxa.getPunts() + " punts"));
    }
}
