package edu.upc.prop.clusterxx;

public class CtrlPersona {
    private final Persona persona;
    private final Taulell tauler;

    public CtrlPersona(Persona persona, Taulell tauler) {
        this.persona = persona;
        this.tauler = tauler;
    }

    public void imprimirInformacioJugador() {
        persona.imprimirInfo();
    }

    public void mostrarFaristol() {
        persona.imprimirFaristol();
    }

    public void colocarFitxa(int posFaristol, int x, int y) {
        if (posFaristol < 0 || posFaristol >= 7) {
            System.out.println("Posició del faristol no vàlida.");
        }

        Fitxa fitxa = persona.obtenirFitxa(posFaristol);
        if (fitxa == null) {
            System.out.println("No hi ha cap fitxa en aquesta posició del faristol.");

        }

        tauler.colocarFitxa(fitxa, x, y);
    }
}
