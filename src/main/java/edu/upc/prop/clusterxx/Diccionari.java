package edu.upc.prop.clusterxx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Diccionari {
    // Set per emmagatzemar les paraules vàlides del diccionari
    private Set<String> paraules;

    // Constructor
    public Diccionari(String idioma) {
        // Inicialitza el conjunt de paraules
        paraules = new HashSet<>();
        // Pots carregar les paraules des d'un fitxer o afegir algunes paraules predeterminades
        carregarParaules(idioma);
    }

    // Mètode per carregar paraules al diccionari (per exemple, des d'un fitxer)
    private void carregarParaules(String idioma) {
        String rutaFitxer = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/" + idioma + ".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFitxer))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                // Afegir cada paraula (linia) al Set
                paraules.add(linia.trim());  // trim() per eliminar espais extra
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mètode per comprovar si una paraula està en el diccionari
    public boolean esParaulaValida(String paraula) {
        return paraules.contains(paraula.toUpperCase()); // Converteix a majúscules per comparacions insensibles a majúscules
    }

    // Mètode per obtenir el nombre de paraules en el diccionari
    public int obtenirQuantitatDeParaules() {
        return paraules.size();
    }

    // Mètode per mostrar totes les paraules en el diccionari (per depuració o proves)
    public void mostrarParaules() {
        for (String paraula : paraules) {
            System.out.println(paraula);
        }
    }
}
