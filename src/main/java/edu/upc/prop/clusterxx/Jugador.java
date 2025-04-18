package edu.upc.prop.clusterxx;
/**
 * Representa un jugador de la partida.
 * Conté el nom del jugador, la seva puntuació acumulada i el seu faristol amb fitxes disponibles.
 */
public class Jugador {
    String nom;
    int punts;
    Faristol faristol;

    /**
     * Crea un nou jugador amb el nom especificat i un faristol assignat.
     * La puntuació inicial del jugador és 0.
     *
     * @param nom Nom del jugador
     * @param faristol Faristol amb les fitxes inicials del jugador
     */
    public Jugador(String nom, Faristol faristol) {
        this.nom = nom;
        this.punts = 0;
        this.faristol = faristol;
    }

    /**
     * Inicialitza aquest jugador com la copia d'un altre.
     * Es copien tots els atributs.
     *
     * @param copiaJugador Jugador original del qual es vol fer la còpia
     */
    public Jugador(Jugador copiaJugador) {
        this.nom = copiaJugador.obtenirNom();
        this.punts = copiaJugador.obtenirPunts();
        this.faristol = new Faristol(copiaJugador.obtenirFaristol());
    }

    /**
     * Retorna el nom del jugador.
     *
     * @return Nom del jugador
     */
    public String obtenirNom() {
        return nom;
    }

    /**
     * Retorna la puntuació actual del jugador.
     *
     * @return Punts acumulats pel jugador
     */
    public int obtenirPunts() {
        return punts;
    }

    /**
     * Retorna el faristol del jugador, que conté les fitxes disponibles.
     *
     * @return Faristol del jugador
     */
    public Faristol obtenirFaristol() {
        return faristol;
    }

    /**
     * Comprova si el faristol del jugador està ple.
     *
     * @return {@code true} si el faristol conté el nombre màxim de fitxes,
     *         {@code false} altrament
     */
    public boolean faristolPle() {return faristol.esPle();}

    /**
     * Barreja aleatòriament les fitxes del faristol del jugador.
     */
    public void barrejarFaristol() { faristol.barrejarFitxes();}

    /**
     * Afegeix punts a la puntuació del jugador.
     *
     * @param nousPunts Nombre de punts a afegir
     */
    public void afegirPunts(int nousPunts) {
        this.punts += nousPunts;
    }

    /**
     * Resta punts de la puntuació del jugador.
     *
     * @param nousPunts Nombre de punts a restar
     */
    public void eliminarPunts(int nousPunts) {
        this.punts -= nousPunts;
    }

    public Fitxa obtenirFitxa(int index) {
        return faristol.obtenirFitxa(index);
    }

    public Fitxa obtenirFitxa(String lletra) {
        return faristol.obtenirFitxa(lletra);
    }

    /**
     * Afegeix una fitxa al faristol del jugador.
     *
     * @param fitxa Fitxa a afegir
     */
    public void afegirFitxa(Fitxa fitxa) {
        faristol.afegirFitxa(fitxa);
    }

    /**
     * Elimina una fitxa del faristol del jugador.
     *
     * @param fitxa Fitxa a eliminar
     */
    public void eliminarFitxa(Fitxa fitxa) {
        faristol.eliminarFitxa(fitxa);
    }

    /**
     * Indica si el jugador és un bot.
     * Per defecte, retorna {@code false}.
     *
     * @return {@code true} si el jugador és un bot, {@code false} altrament
     */
    public boolean esBot() {return false;} // Per defecte, no és un bot
    /**
     * Imprimeix per consola el contingut actual del faristol del jugador.
     * Aquest mètode delega la crida al faristol.
     */
    public void imprimirFaristol() {
        faristol.imprimirFaristol();
    }

    /**
     * Imprimeix per consola tota la informació del jugador:
     * nom, puntuació i contingut del faristol.
     */
    public void imprimirInfo() {
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "======== INFORMACIÓ DEL JUGADOR ========" + Colors.RESET);
        System.out.println(Colors.CYAN_TEXT + "Nom: " + Colors.RESET + nom);
        System.out.println(Colors.GREEN_TEXT + "Punts: " + Colors.RESET + punts);
        System.out.print(Colors.PURPLE_TEXT + "Faristol: " + Colors.RESET);
        faristol.imprimirFaristol(); // Crida directament la funció de Faristol
        System.out.println(Colors.YELLOW_BACKGROUND + Colors.BLACK_TEXT + "========================================" + Colors.RESET);
    }
}