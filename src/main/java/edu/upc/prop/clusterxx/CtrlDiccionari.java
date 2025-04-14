package edu.upc.prop.clusterxx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CtrlDiccionari {
    private DAWG dawg;

    public CtrlDiccionari(String idioma) throws IOException {
        List<String> tokens = llegirTokens(idioma);
        List<String> paraules = llegirParaules(idioma);
        this.dawg = new DAWG(tokens, paraules);
    }

    private List<String> llegirTokens(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(ruta));
        List<String> tokens = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).trim().split("\\s+");
            if (parts.length >= 1) tokens.add(parts[0]);
        }

        tokens.sort((a, b) -> Integer.compare(b.length(), a.length()));
        return tokens;
    }

    private List<String> llegirParaules(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/" + idioma + ".txt";
        return Files.readAllLines(Paths.get(ruta));
    }

    public boolean esParaulaValida(String paraula) {
        return dawg.conteParaula(paraula);
    }

    public boolean esPrefixValid(String prefix) {
        return dawg.esPrefix(prefix);
    }

    public DAWG obtenirDAWG() {
        return dawg;
    }
}
