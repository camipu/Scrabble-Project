package edu.upc.prop.clusterxx;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Sac {
    private final Map<Fitxa, Integer> fitxes;

    // Constructor per defecte
    public Sac(String idioma) {
        this.fitxes = new LinkedHashMap<>();
        inicialitzarSac(idioma); // Inicialitzem el sac en crear l'objecte
    }

    // Constructor amb fitxes prèviament inicialitzades
    public Sac(Map<Fitxa, Integer> fitxesini) {
        this.fitxes = new LinkedHashMap<>(fitxesini);
    }

    // Mètode per inicialitzar el sac amb les fitxes i les seves quantitats
    public void inicialitzarSac(String idioma) {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(ruta));
            for (String line : lines) {
                String[] parts = line.trim().split("\\s+"); // Divideix per espais

                if (parts.length == 3) {
                    try {
                        char lletra = parts[0].charAt(0);
                        int quantitat = Integer.parseInt(parts[1]);
                        int punts = Integer.parseInt(parts[2]);

                        if (quantitat > 0 && punts >= 0) { // Validació
                            fitxes.put(new Fitxa(lletra, punts), quantitat);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error de format a la línia: " + line);
                    }
                } else {
                    System.err.println("Error: línia incorrecta -> " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error llegint el fitxer: " + e.getMessage());
        }
    }

    public Fitxa agafarFitxa(char lletra) {
        for (Fitxa fitxa : fitxes.keySet()) {
            if (fitxa.getLletra() == lletra) { // Comprova si és la fitxa buscada
                int quantitat = fitxes.get(fitxa);
                if (quantitat > 0) {
                    fitxes.put(fitxa, quantitat - 1); // Resta una unitat
                    if (fitxes.get(fitxa) == 0) fitxes.remove(fitxa); // Elimina si arriba a 0
                    return fitxa; // Retorna la fitxa trobada
                }
            }
        }
        return null; // No hi ha fitxes d'aquesta lletra
    }


    // Mètode per agafar una fitxa aleatòria del sac
    public Fitxa agafarFitxa() {
        if (esBuit()) return null;

        List<Fitxa> disponibles = new ArrayList<>();
        for (Map.Entry<Fitxa, Integer> entry : fitxes.entrySet()) {
            Fitxa fitxa = entry.getKey();
            int quant = entry.getValue();
            disponibles.addAll(Collections.nCopies(quant, fitxa)); // Millora d'eficiència
        }

        Random random = new Random();
        Fitxa seleccionada = disponibles.get(random.nextInt(disponibles.size()));

        // Reduïm la quantitat de la fitxa agafada
        fitxes.put(seleccionada, fitxes.get(seleccionada) - 1);
        if (fitxes.get(seleccionada) == 0) {
            fitxes.remove(seleccionada);
        }
        return seleccionada;
    }

    // Mètode per afegir una fitxa al sac
    public void afegirFitxa(Fitxa f) {
        fitxes.put(f, fitxes.getOrDefault(f, 0) + 1);
    }

    // Mètode per saber el nombre total de fitxes al sac
    public int getNumFitxes() {
        return fitxes.values().stream().mapToInt(Integer::intValue).sum();
    }

    // Mètode per comprovar si el sac està buit
    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    public Sac getSac() {return this;}

    // Mètode per mostrar el contingut del sac (per depuració)
    public void mostrarContingut() {
        fitxes.forEach((fitxa, quantitat) ->
                System.out.println(fitxa.getLletra() + " -> " + quantitat + " fitxes, " + fitxa.getPunts() + " punts"));
    }
}
