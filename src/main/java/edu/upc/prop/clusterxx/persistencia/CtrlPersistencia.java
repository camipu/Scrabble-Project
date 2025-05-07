package edu.upc.prop.clusterxx.persistencia;

import edu.upc.prop.clusterxx.Torn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CtrlPersistencia {

    private static final String DIRECTORI_PARTIDES = "data/partides/";

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
}
