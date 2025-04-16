package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.List;

public class HistorialJoc {
    private List<Torn> torns;

    public HistorialJoc() {
        torns = new ArrayList<>();
    }

    public void afegirTorn(Torn torn) {
        torns.add(torn);
    }
    public void retirarTorn() {torns.removeLast();}

    public Torn obtenirTorn(int index) {
        return torns.get(index-1);
    }

    public int obtenirMida() {
        return torns.size();
    }
}
