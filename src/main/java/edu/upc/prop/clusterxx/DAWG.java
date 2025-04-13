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
            // Retorna true si tots dos nodes són final + tenen els fills iguals
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
    
    private Node arrel;
    private Map<Node, Node> nodesUnics = new HashMap<>();
    private Set<String> tokens;

    /**
     * Constructor que inicialitza i carrega el DAWG per a l'idioma especificat.
     * @param idioma L'idioma pel qual es carregarà el DAWG.
     * @throws IOException Si hi ha un error en la lectura dels fitxers.
     */
    public DAWG(String idioma) throws IOException {
        arrel = new Node();
        tokens = new HashSet<>();
        carregar(idioma);
    }

     /**
     * Inicialitza els tokens (lletres i dígrafs) llegint des del fitxer de fitxes.
     * @param idioma L'idioma a carregar.
     * @throws IOException Si hi ha un error en la lectura del fitxer.
     */
    private void inicialitzarTokens(String idioma) throws IOException {
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/fitxes" + idioma + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(ruta));
        
        // Saltem la primera línia si conté capçaleres
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 1) {
                // Agafem només la primera columna que conté la lletra o dígraf
                tokens.add(parts[0]);
            }
        }
        
        // Ordenem els tokens per longitud (descendent) per a la tokenització correcta
        System.out.println("Tokens carregats: " + tokens);
    }
    
    /**
     * Carrega el DAWG a partir d'un fitxer de paraules ordenades alfabèticament.
     * @param idioma L'idioma del fitxer a carregar.
     * @throws IOException Si hi ha un error en la lectura del fitxer.
     */
    private void carregar(String idioma) throws IOException {
        System.out.println("Carregant DAWG en "+idioma);
        inicialitzarTokens(idioma);

        System.out.println("Carregant paraules...");
        String ruta = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/" + idioma + ".txt";
        List<String> paraules = Files.readAllLines(Paths.get(ruta));
        
        int comptador = 0;
        int totalParaules = paraules.size();
        System.out.println("Construint DAWG amb " + totalParaules + " paraules...");
        
        String paraulaAnterior = "";
        for (String paraula : paraules) {
            comptador++;
            if (comptador % (totalParaules/20) == 0) {
                System.out.println("Processant paraula " + comptador + "/" + totalParaules +
                                  " (" + (comptador * 100 / totalParaules) + "%)");
            }
            if (!paraula.isEmpty()) {
                afegirParaula(paraula, paraulaAnterior);
                paraulaAnterior = paraula;
            }
        }
        
        // Minimitzar el DAWG després de carregar totes les paraules
        System.out.println("Minimització final del DAWG...");
        minimitzar(arrel);
        System.out.println("DAWG Carregat! Nodes únics: " + nodesUnics.size());
    }
    
    /**
     * Afegeix una paraula al DAWG, considerant els dígrafs.
     * @param paraula La paraula a afegir.
     * @param paraulaAnterior La paraula anterior afegida al DAWG.
     */
    private void afegirParaula(String paraula, String paraulaAnterior) {
        // Convertir la paraula en llista de dígrafs i caràcters
        List<String> tokensParaula = tokenitzar(paraula);
        List<String> tokensAnteriors = tokenitzar(paraulaAnterior);
        
        // Trobar el prefix comú amb la paraula anterior
        int i = 0;
        while (i < tokensParaula.size() && i < tokensAnteriors.size() && 
               tokensParaula.get(i).equals(tokensAnteriors.get(i))) {
            i++;
        }
        
        // Minimitzar la part que no es comparteix amb la paraula actual
        if (!paraulaAnterior.isEmpty()) {
            minimitzarSufix(arrel, tokensAnteriors, i);
        }
        // MIRAR
        
        // Afegir la part nova de la paraula actual
        Node actual = arrel;
        for (int j = i; j < tokensParaula.size(); j++) {
            String token = tokensParaula.get(j);
            if (!actual.fills.containsKey(token)) {
                actual.fills.put(token, new Node());
            }
            actual = actual.fills.get(token);
        }
        actual.esFinal = true;
    }
    
    /**
     * Converteix una paraula en una llista de tokens (caràcters o dígrafs).
     * @param paraula La paraula a tokenitzar.
     * @return Llista de tokens.
     */
private List<String> tokenitzar(String paraula) {
        List<String> resultat = new ArrayList<>();
        if (paraula.isEmpty()) return resultat;
        
        int i = 0;
        while (i < paraula.length()) {
            boolean trobat = false;
            
            // Examinar els tokens més llargs primer
            for (String token : tokens) {
                int tokenLen = token.length();
                if (i + tokenLen <= paraula.length()) {
                    String subStr = paraula.substring(i, i + tokenLen);
                    // Busquem el token normalitzat (més ràpid que equalsIgnoreCase)
                    if (tokens.contains(subStr)) {
                        resultat.add(token); // Usem el token original
                        i += tokenLen;
                        trobat = true;
                        break;
                    }
                }
            }
            
            // Si no s'ha trobat cap token corresponent, tractar com a caràcter individual
            if (!trobat) {
                resultat.add(paraula.substring(i, i + 1));
                ++i;
            }
        }
        
        return resultat;
    }
    
    /**
     * Minimitza el sufix d'una paraula que comença en l'índex donat.
     * @param node El node actual en el recorregut.
     * @param tokens Els tokens que estem processant.
     * @param index L'índex on comença el sufix.
     */
    private void minimitzarSufix(Node node, List<String> tokens, int index) {
        if (index == tokens.size()) {
            return;
        }
        
        String token = tokens.get(index);
        if (node.fills.containsKey(token)) {
            minimitzarSufix(node.fills.get(token), tokens, index + 1);
            minimitzar(node.fills.get(token));
        }
    }
    
    /**
     * Minimitza el DAWG identificant nodes duplicats.
     * @param node El node actual a minimitzar.
     * @return El node minimitzat.
     */
    private Node minimitzar(Node node) {
        if (node.fills.isEmpty()) {
            return node;
        }
        
        // Minimitzar cada fill
        for (String token : new ArrayList<>(node.fills.keySet())) {
            Node fill = node.fills.get(token);
            // Minimitzar recursivament primer
            minimitzar(fill);
            
            // Buscar un node equivalent existent
            Node fillMinimitzat = nodesUnics.get(fill);
            if (fillMinimitzat != null) {
                node.fills.put(token, fillMinimitzat);
            } else {
                nodesUnics.put(fill, fill);
            }
        }
        
        return node;
    }
    
    /**
     * Comprova si una paraula està al DAWG.
     * @param paraula La paraula a comprovar.
     * @return true si la paraula està al DAWG, false altrament.
     */
    public boolean conteParaula(String paraula) {
        List<String> tokensParaula = tokenitzar(paraula);
        Node actual = arrel;
        
        for (String token : tokensParaula) {
            // Intentem primer amb el token tal qual
            if (!actual.fills.containsKey(token)) {
                return false;
            } else {
                actual = actual.fills.get(token);
            }
        }
        return actual.esFinal;
    }

        /**
     * Comprova si una cadena és prefix d'alguna paraula vàlida al DAWG.
     * @param prefix La cadena a comprovar si és prefix d'alguna paraula vàlida.
     * @return true si la cadena és prefix d'alguna paraula vàlida, false altrament.
     */
    public boolean esPrefix(String prefix) {
        if (prefix.isEmpty()) return true; // La cadena buida és prefix de qualsevol paraula
        
        List<String> tokensPrefix = tokenitzar(prefix);
        Node actual = arrel;
        
        // Recorrem el DAWG seguint els tokens del prefix
        for (String token : tokensPrefix) {
            if (!actual.fills.containsKey(token)) {
                return false; // Si no trobem un camí per aquest token, no és prefix
            }
            actual = actual.fills.get(token);
        }
        
        // Hem arribat al final del prefix, ara hem de comprovar si existeix algun camí
        // que porti a un node final a partir d'aquí
        return actual.esFinal || teDescendentFinal(actual);
    }
    
    /**
     * Comprova si un node té algun descendent que sigui un node final.
     * @param node El node a partir del qual es comença la comprovació.
     * @return true si hi ha algun descendent que és un node final, false altrament.
     */
    private boolean teDescendentFinal(Node node) {
        if (node.esFinal) {
            return true;
        }
        
        // Recorrem tots els fills recursivament
        for (Node fill : node.fills.values()) {
            if (teDescendentFinal(fill)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Obté una llista de totes les paraules al DAWG.
     * @return Llista de paraules.
     */
    public List<String> getParaules() {
        List<String> paraules = new ArrayList<>();
        recorregutProfunditat(arrel, "", paraules);
        return paraules;
    }
    
    private void recorregutProfunditat(Node node, String prefix, List<String> paraules) {
        if (node.esFinal) {
            paraules.add(prefix);
        }
        
        for (Map.Entry<String, Node> entrada : node.fills.entrySet()) {
            recorregutProfunditat(entrada.getValue(), prefix + entrada.getKey(), paraules);
        }
    }
}