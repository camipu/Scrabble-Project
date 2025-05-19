package edu.upc.prop.clusterxx.persistencia;

import edu.upc.prop.clusterxx.Estadistiques;
import edu.upc.prop.clusterxx.Torn;
import edu.upc.prop.clusterxx.exceptions.ExcepcioLectura;
import edu.upc.prop.clusterxx.exceptions.ExcepcioEscriptura;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe CtrlPersistencia
 *
 * Controlador encarregat de gestionar la persistència de dades del sistema.
 * Permet desar i carregar partides de Scrabble i estadístiques globals del joc
 * mitjançant fitxers binaris
 */

public class CtrlPersistencia {

    private static CtrlPersistencia instance;

    private static final String DIRECTORI_PARTIDES = "data/partides/";
    private static final String DIRECTORI_ESTADISTIQUES = "data/estadistiques/";
    private static final String FITXER_ESTADISTIQUES = DIRECTORI_ESTADISTIQUES + "estadistiques.scrabble";

    /**
     * Constructor privat per implementar el patró singleton.
     */
    private CtrlPersistencia() {}

    /**
     * Retorna la instància única de CtrlPersistencia.
     *
     * @return Instància única del controlador de persistència
     */
    public static CtrlPersistencia getInstance() {
        if (instance == null) {
            instance = new CtrlPersistencia();
        }
        return instance;
    }

    /**
     * Desa l’estat d’un torn de partida en un fitxer.
     *
     * @param nomFitxer Nom del fitxer (sense extensió)
     * @param torn Torn que es vol guardar
     * @throws ExcepcioEscriptura si hi ha un error durant el procés de guardat
     */
    public static void guardarTorn(String nomFitxer, Torn torn) throws ExcepcioEscriptura {
        try {
            File directori = new File(DIRECTORI_PARTIDES);
            if (!directori.exists()) directori.mkdirs();

            File fitxer = new File(DIRECTORI_PARTIDES + nomFitxer + ".scrabble");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fitxer))) {
                out.writeObject(torn);
            }
        } catch (IOException e) {
            throw new ExcepcioEscriptura("No s'ha pogut guardar la partida: " + nomFitxer, e);
        }
    }

    /**
     * Carrega un torn de partida des d’un fitxer.
     *
     * @param nomFitxer Nom del fitxer (sense extensió)
     * @return Torn desat en el fitxer
     * @throws ExcepcioLectura si no es troba el fitxer o hi ha un error en llegir-lo
     */
    public static Torn carregarTorn(String nomFitxer) throws ExcepcioLectura {
        File fitxer = new File(DIRECTORI_PARTIDES + nomFitxer + ".scrabble");

        if (!fitxer.exists()) {
            throw new ExcepcioLectura("No s'ha trobat la partida: " + nomFitxer);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fitxer))) {
            return (Torn) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ExcepcioLectura("Error en carregar la partida: " + nomFitxer, e);
        }
    }

    /**
     * Retorna la llista de noms de totes les partides guardades.
     *
     * @return Llista de noms de fitxers de partida (sense extensió)
     */
    public static List<String> llistarPartidesGuardades() {
        File directori = new File(DIRECTORI_PARTIDES);
        File[] fitxers = directori.listFiles((dir, nom) -> nom.endsWith(".scrabble"));
        List<String> noms = new ArrayList<>();
        if (fitxers != null) {
            for (File f : fitxers) noms.add(f.getName().replace(".scrabble", ""));
        }
        return noms;
    }

    /**
     * Desa les estadístiques globals en un fitxer.
     *
     * @param estadistiques Objecte d’estadístiques a desar
     * @throws ExcepcioEscriptura si hi ha un error durant el procés de guardat
     */
    public static void guardarEstadistiques(Estadistiques estadistiques) throws ExcepcioEscriptura {
        try {
            File directori = new File(DIRECTORI_ESTADISTIQUES);
            if (!directori.exists()) directori.mkdirs();

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FITXER_ESTADISTIQUES))) {
                out.writeObject(estadistiques);
            }
        } catch (IOException e) {
            throw new ExcepcioEscriptura("No s'han pogut guardar les estadístiques", e);
        }
    }

    /**
     * Carrega les estadístiques globals des d’un fitxer.
     * Si el fitxer no existeix, es retorna una nova instància buida.
     *
     * @return Objecte Estadistiques carregat o nou si el fitxer no existeix
     * @throws ExcepcioLectura si hi ha un error durant la lectura del fitxer
     */
    public static Estadistiques carregarEstadistiques() throws ExcepcioLectura {
        File fitxer = new File(FITXER_ESTADISTIQUES);
        if (!fitxer.exists()) return Estadistiques.getInstance();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fitxer))) {
            Estadistiques carregades = (Estadistiques) in.readObject();
            Estadistiques instance = Estadistiques.getInstance();
            instance.carregarDes(carregades); // Aquest mètode ja l’has creat
            return instance;
        } catch (IOException | ClassNotFoundException e) {
            throw new ExcepcioLectura("No s'han pogut carregar les estadisitquqes", e);
        }
    }
}
