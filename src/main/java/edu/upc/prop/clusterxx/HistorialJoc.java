package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa l’historial d’una partida de joc.
 * Emmagatzema la llista de torns realitzats durant la partida,
 * així com la data en què es va jugar.
 *
 * Aquesta classe permet consultar o reconstruir el desenvolupament d’una partida.
 */
public class HistorialJoc {
    private List<Torn> torns;
    private Date dataJoc;

    /**
     * Crea un nou historial de joc amb la data especificada.
     * Inicialitza la llista de torns com a buida.
     *
     * @param date Data en què es va jugar la partida
     */
    public HistorialJoc(Date date) {
        torns = new ArrayList<>();
        dataJoc = date;
    }

    /**
     * Afegeix un torn a l’historial de joc.
     *
     * @param torn Torn que es vol afegir a la llista de torns
     */
    public void afegirTorn(Torn torn) {
        torns.add(torn);
    }

    /**
     * Elimina l’últim torn afegit a l’historial de joc.
     */
    public void retirarTorn() {
        if (torns.isEmpty()) {
            throw new IndexOutOfBoundsException("No hi ha torns per eliminar.");
        }
        torns.removeLast();
    }

    /**
     * Elimina els torns posteriors a un torn específic de l'historial.
     *
     * Aquest mètode s'utilitza principalment quan es fa una operació d'undo o
     * quan es vol retornar a un estat anterior de la partida, eliminant tots
     * els torns que s'han realitzat després del torn especificat.
     *
     * @param torn L'índex del torn a partir del qual s'han d'eliminar els torns següents
     * @throws IndexOutOfBoundsException Si no hi ha torns per eliminar
     */
    public void retirarTorns(int torn){
        if (torns.isEmpty()) {
            throw new IndexOutOfBoundsException("No hi ha torns per eliminar.");
        }
        for (int i = torn + 1; i < torns.size(); i++) {
            if (torns.isEmpty()) {
                break;
            }
            torns.removeLast();
            System.out.print("Retirant torn " + (i+1) + " de " + torns.size() + "\n");
        }
    }

    /**
     * Retorna el torn corresponent a una posició concreta de l’historial.
     * La numeració dels torns comença per 1 (no per 0).
     *
     * @param index Índex del torn a obtenir (començant per 1)
     * @return El torn situat a la posició indicada
     */
    public Torn obtenirTorn(int index) {
        if (index < 1 || index > torns.size()) {
            throw new IndexOutOfBoundsException("Índex fora de límits: " + index);
        }
        else return torns.get(index-1);
    }

    /**
     * Retorna el nombre total de torns registrats a l’historial.
     *
     * @return Nombre de torns actuals
     */
    public int obtenirMida() {
        return torns.size();
    }

    /**
     * Retorna la data en què es va jugar la partida.
     *
     * @return Data de la partida
     */
    public Date obtenirDataJoc() {
        return dataJoc;
    }
}
