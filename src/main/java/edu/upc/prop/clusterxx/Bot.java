package edu.upc.prop.clusterxx;

public class Bot extends Jugador {
    private int nivellDificultat; // 1: fàcil, 2: mitjà, 3: difícil

    /**
     * Constructor amb nom, sac i nivell de dificultat
     * @param nom Nom del bot
     * @param faristol Faristol de fitxes
     * @param nivellDificultat Nivell de dificultat del bot (1-3)
     */
    public Bot(String nom, Faristol faristol, int nivellDificultat) {
        super(nom, faristol);

        if (nivellDificultat < 1 || nivellDificultat > 3) {
            throw new IllegalArgumentException("Nivell de dificultat ha de ser entre 1 i 3.");        }
        this.nivellDificultat = nivellDificultat;
    }

    /**
     * Inicialitza aquest bot com una còpia d'un altre.
     * Es copien tots els atributs del bot original, inclòs el nivell de dificultat.
     *
     * @param copiaBot Bot original del qual es vol fer la còpia
     */
    public Bot(Bot copiaBot) {
        // Copia el nom i el faristol del bot original (superclase Jugador)
        super(copiaBot.obtenirNom(), copiaBot.obtenirFaristol());

        // Copia el nivell de dificultat del bot original
        this.nivellDificultat = copiaBot.nivellDificultat;
    }

    /**
     * Retorna el nivell de dificultat del bot.
     *
     * @return Nivell de dificultat (1: fàcil, 2: mitjà, 3: difícil)
     */
    public int obtenirDificultat() {
        return nivellDificultat;
    }

    /**
     * Sobreescriu el mètode de la classe pare per indicar que aquest jugador és un bot.
     *
     * @return {@code true} sempre, ja que aquesta classe representa un bot
     */
    @Override
    public boolean esBot() {return true;}
}