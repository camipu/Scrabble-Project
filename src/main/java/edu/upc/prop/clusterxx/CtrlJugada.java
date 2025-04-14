package edu.upc.prop.clusterxx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CtrlJugada {
    private final Sac sac;

    public CtrlJugada(String idioma) {
        this.sac = new Sac();
        List<String> fitxes = llegirFitxesIdioma(idioma);
        carregarFitxesAlSac(fitxes);
    }

    public List<String> llegirFitxesIdioma(String idioma) {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        try {
            return Files.readAllLines(Paths.get(ruta));
        } catch (IOException e) {
            throw new IllegalArgumentException("No s'ha pogut llegir el fitxer de fitxes per l'idioma: " + idioma);
        }
    }

    public void carregarFitxesAlSac(List<String> linies) {
        for (String line : linies) {
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

                Fitxa fitxa = new Fitxa(lletra, punts);
                sac.afegirFitxaAmbQuantitat(fitxa, quantitat);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error de format numèric a la línia: " + line, e);
            }
        }
    }

    public Sac getSac() {
        return sac;
    }
}
