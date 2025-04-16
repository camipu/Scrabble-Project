package edu.upc.prop.clusterxx.controladors;

public class CtrlJugadaBot {
    private static CtrlJugadaBot instance = null;


    public static CtrlJugadaBot getInstance() {
        if (instance == null) {
            instance = new CtrlJugadaBot();
        }
        return instance;
    }

    private CtrlJugadaBot() {
    }

    public void inicialitzarDificultat(int dificultat) {
        // Inicialitzar la dificultat del bot
        // Aquí puedes implementar la lógica para inicializar la dificultad del bot
    }
}
