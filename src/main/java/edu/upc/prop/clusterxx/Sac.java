package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;

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

    public Sac() {
        this.fitxes = new LinkedHashMap<>();
    }

    private void inicialitzarSac(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(ruta));

        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Format incorrecte al fitxer: " + line);
            }

            try {
                String lletra = parts[0];
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

    public Fitxa agafarFitxa(Fitxa fitxa) {
        Fitxa f = obtenirFitxa(fitxa);
        reduirQuantitat(f);
        return f;
    }

    public Fitxa agafarFitxa() {
        if (esBuit()) {
            throw new ExcepcioSacBuit("No es pot agafar una fitxa: el sac està buit.");
        }

        List<Fitxa> disponibles = new ArrayList<>();
        for (Map.Entry<Fitxa, Integer> entry : fitxes.entrySet()) {
            disponibles.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        }

        Fitxa seleccionada = disponibles.get(new Random().nextInt(disponibles.size()));
        reduirQuantitat(seleccionada);
        return seleccionada;
    }

    private Fitxa obtenirFitxa(Fitxa fitxa) {
        for (Fitxa f : fitxes.keySet()) {
            if (f.obtenirLletra().equals(fitxa.obtenirLletra())) {
                return f;
            }
        }
        throw new ExcepcioSacNoConteLaFitxa("No hi ha fitxes disponibles amb la lletra '" + fitxa.obtenirLletra() + "'");
    }

    private void reduirQuantitat(Fitxa fitxa) {
        int quantitat = fitxes.getOrDefault(fitxa, 0);
        if (quantitat <= 1) {
            fitxes.remove(fitxa);
        } else {
            fitxes.put(fitxa, quantitat - 1);
        }
    }

    public void afegirFitxa(Fitxa f) {
        fitxes.put(f, fitxes.getOrDefault(f, 0) + 1);
    }

    public int obtenirNumFitxes() {
        int sumaTotal = 0;
        for (Integer q : fitxes.values()) {
            sumaTotal += q;
        }
        return sumaTotal;
    }

    public boolean esBuit() {
        return fitxes.isEmpty();
    }

    public int quantitatFitxes(Fitxa fitxa) {
        return fitxes.getOrDefault(fitxa, 0);
    }


    public void mostrarContingut() {
        fitxes.forEach((fitxa, quantitat) ->
                System.out.println(fitxa.obtenirLletra() + " -> " + quantitat + " fitxes, " + fitxa.obtenirPunts() + " punts"));
    }
}
