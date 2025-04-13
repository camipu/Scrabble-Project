package edu.upc.prop.clusterxx;

public class CtrDiccionari {
    private Diccionari diccionari;

    public CtrDiccionari(String idioma) {
        this.diccionari = new Diccionari(idioma);
    }

    public boolean esParaulaValida(String paraula) {
        return diccionari.esParaulaValida(paraula);
    }


    // Mètode per obtenir el nombre de paraules en el diccionari
    public int obtenirQuantitatDeParaules() {
        return diccionari.obtenirQuantitatDeParaules();
    }

    // Mètode per mostrar totes les paraules en el diccionari (per depuració o proves)
    public void mostrarParaules() {
        diccionari.mostrarParaules();
    }
}
