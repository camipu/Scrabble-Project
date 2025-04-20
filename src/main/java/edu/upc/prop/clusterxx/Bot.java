package edu.upc.prop.clusterxx;

public class Bot extends Jugador {
    private int nivellDificultat; // 1: fàcil, 2: mitjà, 3: difícil

    /**
     * Constructor amb nom, sac i nivell de dificultat
     * @param nom Nom del bot
     * @param sac Sac de fitxes
     * @param nivellDificultat Nivell de dificultat del bot (1-3)
     */
    public Bot(String nom, Faristol faristol, int nivellDificultat) {
        super(nom, faristol);

        if (nivellDificultat < 1 || nivellDificultat > 3) {
            throw new IllegalArgumentException("Nivell de dificultat ha de ser entre 1 i 3.");        }
        this.nivellDificultat = nivellDificultat;
    }

    public int obtenirDificultat() {
        return nivellDificultat;
    }

    @Override
    public boolean esBot() {return true;}
}