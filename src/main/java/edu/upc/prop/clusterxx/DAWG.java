package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioCaracterNoReconegut;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;


/**
 * DAWG (Directed Acyclic Word Graph) que representa un diccionari de paraules
 * a on cada node és un token (caràcter o dígraf) que condueix als altres nodes
 * tot formant paraules.
 */
public class DAWG {

    // Node intern del DAWG
    public static class Node {
        Map<String, Node> fills = new HashMap<>(); // Tokens cap a nodes fills
        boolean esFinal = false; // Indica si el camí fins aquí forma una paraula vàlida

        // Compara aquest node amb un altre objecte per determinar si són iguals
        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // Si són el mateix objecte, són iguals
            if (o == null || getClass() != o.getClass()) return false; // Si no són de la mateixa classe, no són iguals
            Node node = (Node) o;
            // Compara si el camp 'esFinal' i els fills són iguals
            return esFinal == node.esFinal && fills.equals(node.fills);
        }

        // Genera un codi hash per aquest node basat en els seus camps
        @Override
        public int hashCode() {
            return Objects.hash(esFinal, fills); // Utilitza 'esFinal' i 'fills' per calcular el hash
        }

        // Retorna el node fill associat a un token donat
        public Node getFill(String token) {
            return fills.get(token);
        }

        // Retorna tots els fills d'aquest node com un mapa
        public Map<String, Node> getFills() {
            return fills;
        }

        // Comprova si aquest node conté un token específic com a fill
        public boolean conteToken(String token) {
            return fills.containsKey(token);
        }

        // Indica si aquest node marca el final d'una paraula vàliday
        public boolean esFinal() {
            return esFinal;
        }
    }

    private final Node arrel; // Node Arrel del DAWG
    private final Map<Node, Node> nodesMinimitzats = new HashMap<>(); // Nodes ja construïts i minimitzats
    private final List<String> tokens; // Tokens vàlids per tokenitzar paraules
    private final List<Node> pathAnterior = new ArrayList<>(); // Ruta anterior paraula per minimitzar el DAWG

    /**
     * Constructor. Crea i construeix el DAWG a partir de tokens i paraules donades.
     * Precondition: Llista de paraules ordenada alfabèticament
     * @param tokens Llista de tokens (dígrafs, lletres, etc.)
     * @param paraules Llista de paraules vàlides ORDENADES ALFABÈTICAMENT
     */
    public DAWG(List<String> tokens, List<String> paraules) {
        // Crea una còpia mutable de la llista de tokens
        this.tokens = new ArrayList<>(tokens);
        
        // Ordeno per longitud --> Alfabèticament
        this.tokens.sort((s1, s2) -> {
            // Si les longituds són diferents, ordenar per longitud
            if (s1.length() != s2.length()) {
                return s2.length() - s1.length();
            }
            // Si les longituds són iguals, ordenar alfabèticament
            return s1.compareTo(s2);
        });

        arrel = new Node();

        String paraulaAnterior = "";
        for (String paraula : paraules) {
            if (!paraula.isEmpty()) {
                afegirParaula(paraula, paraulaAnterior);
                paraulaAnterior = paraula;
            }
        }

        // Minimitza el graf un cop s'han afegit totes les paraules
        minimitzarSufix(0);
    }

    /**
     * Afegeix una nova paraula al DAWG comparant-la amb la paraula anterior
     * per detectar el prefix comú i reutilitzar-lo.
     * En cas que sigui diferent el prefix crearà una nova ruta amb un node diferent
     */
    private void afegirParaula(String paraula, String paraulaAnterior) {
        List<String> tokensActual = tokenitzar(paraula);
        List<String> tokensAnt = tokenitzar(paraulaAnterior);

        // Detecta quant prefix coincideix entre la nova paraula i l'anterior
        int prefix = 0;
        while (prefix < tokensActual.size() && prefix < tokensAnt.size()
                && tokensActual.get(prefix).equals(tokensAnt.get(prefix))) {
            prefix++;
        }

        // Minimitza els sufixos a partir del primer punt de divergència
        minimitzarSufix(prefix);

        // Recupera node on continuar la construcció
        Node nodeActual = (prefix == 0) ? arrel : pathAnterior.get(prefix - 1);

        // Retalla el path anterior per adequar-lo al nou camí
        while (pathAnterior.size() > prefix) pathAnterior.remove(pathAnterior.size() - 1);

        // Afegeix nous nodes per al sufix restant de la paraula
        for (int i = prefix; i < tokensActual.size(); i++) {
            Node nou = new Node();
            nodeActual.fills.put(tokensActual.get(i), nou);
            nodeActual = nou;
            pathAnterior.add(nodeActual);
        }

        // Marca el darrer node com a final de paraula
        nodeActual.esFinal = true;
    }

    /**
     * Minimitza els nodes de la ruta afegida recentment (pathAnterior) començant des de la posició 'desDe'.
     * Aquest mètode substitueix subgràfics equivalents per versions ja existents usant el mapa de nodes minimitzats.
     * Això assegura que el DAWG sigui minimal, reutilitzant subestructures comunes.
     */
    private void minimitzarSufix(int desDe) {
        for (int i = pathAnterior.size() - 1; i >= desDe; i--) {
            Node node = pathAnterior.get(i); // Obté el node actual que es vol minimitzar

            // Si aquest node no està al mapa de minimitzats, s'hi afegeix com a referència
            if (!nodesMinimitzats.containsKey(node)) nodesMinimitzats.put(node, node);

            // S'obté el node minimitzat (el que ja existeix si és equivalent)
            Node min = nodesMinimitzats.get(node);

            // Si no estem a l'arrel
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

    /**
     * Divideix una paraula en una llista de tokens (Caràcters/Dígrafs)
     * segons els tokens càrregats al DAWG.
     */
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

            // Si cap token coincideix, afegeix el caràcter sol
            if (!trobat) {
                throw new ExcepcioCaracterNoReconegut(paraula.substring(i, i + 1));
            }
        }

        return resultat;
    }

    /**
     * Comprova si una paraula completa existeix dins del DAWG.
     * @param paraula paraula a verificar
     * @return true si és vàlida, false si no ho és
     */
    public boolean conteParaula(String paraula) {
        List<String> tokensParaula = tokenitzar(paraula);
        Node actual = arrel;

        for (String token : tokensParaula) {
            if (!actual.fills.containsKey(token)) return false;
            actual = actual.fills.get(token);
        }

        return actual.esFinal;
    }

    public Node getArrel() {
        return arrel;
    }

    /**
     * Comprova si un prefix donat existeix en l'estructura DAWG i
     * retorna el node corresponent al final del prefix si existeix.
     *
     * @param prefix El prefix que es vol comprovar dins de l'estructura.
     * @return El node corresponent al final del prefix si aquest existeix,
     *         o null si el prefix no es troba a l'estructura.
     */
    public Node esPrefix(String prefix) {
        List<String> tokensPrefix = tokenitzar(prefix);
        Node actual = arrel;
        for (String token : tokensPrefix) {
            if (!actual.fills.containsKey(token)) return null;
            actual = actual.fills.get(token);
        }
        return actual;
    }
}
