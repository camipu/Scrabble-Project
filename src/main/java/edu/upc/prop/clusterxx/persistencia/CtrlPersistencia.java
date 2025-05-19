package edu.upc.prop.clusterxx.persistencia;

import edu.upc.prop.clusterxx.Estadistiques;
import edu.upc.prop.clusterxx.Torn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CtrlPersistencia {

    private static CtrlPersistencia instance;

    private static final String DIRECTORI_PARTIDES = "data/partides/";
    private static final String DIRECTORI_ESTADISTIQUES = "data/estadistiques/";
    private static final String FITXER_ESTADISTIQUES = DIRECTORI_ESTADISTIQUES + "estadistiques.scrabble";

    private CtrlPersistencia() {
    }

    public static CtrlPersistencia getInstance() {
        if (instance == null) {
            instance = new CtrlPersistencia();
        }
        return instance;
    }

    public static void guardarTorn(String nomFitxer, Torn torn) throws IOException {
        File directori = new File(DIRECTORI_PARTIDES);
        if (!directori.exists()) directori.mkdirs();

        File fitxer = new File(DIRECTORI_PARTIDES + nomFitxer + ".scrabble");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fitxer));
        out.writeObject(torn);
        out.close();
    }

    public static Torn carregarTorn(String nomFitxer) throws IOException, ClassNotFoundException {
        File fitxer = new File(DIRECTORI_PARTIDES + nomFitxer + ".scrabble");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fitxer));
        Torn torn = (Torn) in.readObject();
        in.close();
        return torn;
    }

    public static List<String> llistarPartidesGuardades() {
        File directori = new File(DIRECTORI_PARTIDES);
        File[] fitxers = directori.listFiles((dir, nom) -> nom.endsWith(".scrabble"));
        List<String> noms = new ArrayList<>();
        if (fitxers != null) {
            for (File f : fitxers) noms.add(f.getName().replace(".scrabble", ""));
        }
        return noms;
    }

    public static void guardarEstadistiques(Estadistiques estadistiques) throws IOException {
        File directori = new File(DIRECTORI_ESTADISTIQUES);
        if (!directori.exists()) directori.mkdirs();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FITXER_ESTADISTIQUES))) {
            out.writeObject(estadistiques);
        }
    }

    public static Estadistiques carregarEstadistiques() {
        File fitxer = new File(FITXER_ESTADISTIQUES);
        if (!fitxer.exists()) return Estadistiques.getInstance();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fitxer))) {
            Estadistiques carregades = (Estadistiques) in.readObject();
            Estadistiques instance = Estadistiques.getInstance();

            // Copiar els valors des de 'carregades' a 'instance'
            instance.carregarDes(carregades); // <-- Aquest mètode el crearem

            return instance;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error carregant estadístiques: " + e.getMessage());
            return Estadistiques.getInstance();
        }
    }

}
