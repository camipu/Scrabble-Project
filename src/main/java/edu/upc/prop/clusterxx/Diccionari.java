package edu.upc.prop.clusterxx;

import java.util.HashSet;
import java.util.Set;

public class Diccionari {
    // Set para almacenar las palabras válidas del diccionario
    private Set<String> palabras;

    // Constructor
    public Diccionari() {
        // Inicializa el conjunto de palabras
        palabras = new HashSet<>();
        // Puedes cargar las palabras desde un archivo o agregar algunas palabras predeterminadas
        cargarPalabras();
    }

    // Método para cargar palabras al diccionario (por ejemplo, desde un archivo)
    private void cargarPalabras() {
        // Agrega algunas palabras de ejemplo, o puedes cargar un archivo de texto con muchas palabras
        palabras.add("hola");
        palabras.add("mundo");
        palabras.add("java");
        palabras.add("programacion");
        palabras.add("scrabble");
        // Agrega más palabras según sea necesario...
    }

    // Método para agregar una palabra al diccionario
    public void agregarPalabra(String palabra) {
        palabras.add(palabra.toLowerCase()); // Asegurarse de que la palabra esté en minúsculas
    }

    // Método para comprobar si una palabra está en el diccionario
    public boolean esPalabraValida(String palabra) {
        return palabras.contains(palabra.toLowerCase()); // Convierte a minúsculas para comparaciones insensibles a mayúsculas
    }

    // Método para obtener el número de palabras en el diccionario
    public int obtenerCantidadDePalabras() {
        return palabras.size();
    }

    // Método para mostrar todas las palabras en el diccionario (para depuración o pruebas)
    public void mostrarPalabras() {
        for (String palabra : palabras) {
            System.out.println(palabra);
        }
    }
}
