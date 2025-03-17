package edu.upc.prop.clusterxx;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Sac {
    private final Map<Fitxa, Integer> fitxes;

    public Sac(String idioma) {
        this.fitxes = new LinkedHashMap<>();
        try {
            inicialitzarSac(idioma);
        } catch (IOException e) {
            System.err.println("Error en llegir el fitxer de fitxes: " + e.getMessage());
        }
    }

    public Sac(Map<Fitxa, Integer> fitxesini) {
        this.fitxes = new LinkedHashMap<>(fitxesini);
    }

    // Constructora de sac buit
    public Sac() {
        this.fitxes = new LinkedHashMap<>();
    }

    public void inicialitzarSac(String idioma) throws IOException {
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

    public Fitxa agafarFitxa(char lletra) {
        for (Fitxa fitxa : fitxes.keySet()) {
            if (fitxa.getLletra() == lletra) {
                int quantitat = fitxes.get(fitxa);
                if (quantitat > 0) {
                    fitxes.put(fitxa, quantitat - 1);
                    if (fitxes.get(fitxa) == 0) fitxes.remove(fitxa);
                    return fitxa;
                }
            }
        }
        throw new NoSuchElementException("No hi ha fitxes disponibles amb la lletra '" + lletra + "'");
    }

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

        Random random = new Random();
        Fitxa seleccionada = disponibles.get(random.nextInt(disponibles.size()));

        fitxes.put(seleccionada, fitxes.get(seleccionada) - 1);
        if (fitxes.get(seleccionada) == 0) {
            fitxes.remove(seleccionada);
        }
        return seleccionada;
    }

    public void afegirFitxa(Fitxa f) {
        fitxes.put(f, fitxes.getOrDefault(f, 0) + 1);
    }

    public int getNumFitxes() {
        return fitxes.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    public Sac getSac() {
        return this;
    }

    public void mostrarContingut() {
        fitxes.forEach((fitxa, quantitat) ->
                System.out.println(fitxa.getLletra() + " -> " + quantitat + " fitxes, " + fitxa.getPunts() + " punts"));
    }
}
