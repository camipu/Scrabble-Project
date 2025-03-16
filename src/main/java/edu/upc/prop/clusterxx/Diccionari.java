package edu.upc.prop.clusterxx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Diccionari {
    // Set para almacenar las palabras válidas del diccionario
    private Set<String> palabras;

    // Constructor
    public Diccionari(String idioma) {
        // Inicializa el conjunto de palabras
        palabras = new HashSet<>();
        // Puedes cargar las palabras desde un archivo o agregar algunas palabras predeterminadas
        cargarPalabras(idioma);
    }

    // Método para cargar palabras al diccionario (por ejemplo, desde un archivo)
    private void cargarPalabras(String idioma) {
        String rutaArchivo = "src/main/java/edu/upc/prop/clusterxx/resources/" + idioma + "/" + idioma + ".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Agregar cada palabra (línea) al Set
                palabras.add(linea.trim());  // trim() para eliminar posibles espacios extra
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    // Método para comprobar si una palabra está en el diccionario
    public boolean esPalabraValida(String palabra) {
        return palabras.contains(palabra.toUpperCase()); // Convierte a minúsculas para comparaciones insensibles a mayúsculas
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
