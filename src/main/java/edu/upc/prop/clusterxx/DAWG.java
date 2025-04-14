package edu.upc.prop.clusterxx;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class DAWG {
    private static class Node {
        Map<String, Node> fills = new HashMap<>();
        boolean esFinal = false;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return esFinal == node.esFinal && fills.equals(node.fills);
        }

        @Override
        public int hashCode() {
            return Objects.hash(esFinal, fills);
        }
    }

    private final Node arrel;
    private final Map<Node, Node> cache = new HashMap<>();
    private final List<String> tokens = new ArrayList<>();
    private final List<Node> pathAnterior = new ArrayList<>();

    public DAWG(String idioma) throws IOException {
        System.out.println("Carregant el DAWG per a l'idioma: " + idioma);
        inicialitzarTokens(idioma);
        List<String> paraules = llegirParaules(idioma);
        // SI AL TXT LES PARAULES ESTAN ORDENADES NO CAL
        // paraules.sort(Comparator.naturalOrder());
        arrel = new Node();
        generarDAWG(idioma, paraules);
        System.out.println("DAWG carregat i minimitzat amb èxit.");
    }

    private void inicialitzarTokens(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(ruta));
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).trim().split("\s+");
            if (parts.length >= 1) tokens.add(parts[0]);
        }
        tokens.sort((a, b) -> Integer.compare(b.length(), a.length()));
        System.out.println("Tokens carregats: " + tokens);
    }

    private List<String> llegirParaules(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/" + idioma + ".txt";
        System.out.println("Llegint paraules del fitxer: " + ruta);
        return Files.readAllLines(Paths.get(ruta));
    }

    private void generarDAWG(String idioma, List<String> paraules) throws IOException {
        String paraulaAnterior = "";
        int totalParaules = paraules.size();
        int progressInterval = totalParaules / 10; // Cada 10%

        for (int i = 0; i < totalParaules; i++) {
            String paraula = paraules.get(i);
            if (!paraula.isEmpty()) {
                afegirParaula(paraula, paraulaAnterior);
                paraulaAnterior = paraula;
            }
            if (progressInterval > 0 && i % progressInterval == 0) {
                System.out.println("Progres: " + (i * 100 / totalParaules) + "% completat.");
            }
        }
        minimitzarSufix(0);        
    }

    private void afegirParaula(String paraula, String paraulaAnterior) {
        List<String> tokensActual = tokenitzar(paraula);
        List<String> tokensAnt = tokenitzar(paraulaAnterior);

        int prefix = 0;
        while (prefix < tokensActual.size() && prefix < tokensAnt.size()
                && tokensActual.get(prefix).equals(tokensAnt.get(prefix))) {
            prefix++;
        }

        minimitzarSufix(prefix);

        Node nodeActual = (prefix == 0) ? arrel : pathAnterior.get(prefix - 1);
        while (pathAnterior.size() > prefix) pathAnterior.remove(pathAnterior.size() - 1);

        for (int i = prefix; i < tokensActual.size(); i++) {
            Node nou = new Node();
            nodeActual.fills.put(tokensActual.get(i), nou);
            nodeActual = nou;
            pathAnterior.add(nodeActual);
        }
        nodeActual.esFinal = true;
    }

    private void minimitzarSufix(int desDe) {
        for (int i = pathAnterior.size() - 1; i >= desDe; i--) {
            Node node = pathAnterior.get(i);
            if (!cache.containsKey(node)) cache.put(node, node);
            Node min = cache.get(node);
            if (i > 0) {
                Node pare = pathAnterior.get(i - 1);
                for (Map.Entry<String, Node> entry : pare.fills.entrySet()) {
                    if (entry.getValue() == node) {
                        pare.fills.put(entry.getKey(), min);
                        break;
                    }
                }
            } else {
                for (Map.Entry<String, Node> entry : arrel.fills.entrySet()) {
                    if (entry.getValue() == node) {
                        arrel.fills.put(entry.getKey(), min);
                        break;
                    }
                }
            }
        }
    }

    private List<String> tokenitzar(String paraula) {
        List<String> resultat = new ArrayList<>();
        int i = 0;
        while (i < paraula.length()) {
            boolean trobat = false;
            for (String token : tokens) {
                int len = token.length();
                if (i + len <= paraula.length() && paraula.substring(i, i + len).equals(token)) {
                    resultat.add(token);
                    i += len;
                    trobat = true;
                    break;
                }
            }
            if (!trobat) {
                resultat.add(paraula.substring(i, i + 1));
                i++;
            }
        }
        return resultat;
    }

    public boolean conteParaula(String paraula) {
        List<String> tokensParaula = tokenitzar(paraula);
        Node actual = arrel;
        for (String token : tokensParaula) {
            if (!actual.fills.containsKey(token)) return false;
            actual = actual.fills.get(token);
        }
        return actual.esFinal;
    }

    public boolean esPrefix(String prefix) {
        List<String> tokensPrefix = tokenitzar(prefix);
        Node actual = arrel;
        for (String token : tokensPrefix) {
            if (!actual.fills.containsKey(token)) return false;
            actual = actual.fills.get(token);
        }
        return true;
    }
}