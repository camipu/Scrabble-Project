package edu.upc.prop.clusterxx.controladors;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.prop.clusterxx.HistorialJoc;

import java.io.File;
import java.io.IOException;

/**
 * Classe que gestiona la persistència de l'Historial de Joc.
 */
public class CtrlPersistencia {

    /**
     * Guarda l'historial de joc en un fitxer JSON.
     *
     * @param historial Historial de la partida que es vol guardar
     * @param nomFitxer Nom del fitxer on es guardarà l'historial
     */
    public void guardarHistorial(HistorialJoc historial, String nomFitxer) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Guardem l'objecte HistorialJoc com a JSON
            objectMapper.writeValue(new File(nomFitxer), historial);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega un historial de joc des d'un fitxer JSON.
     *
     * @param nomFitxer Nom del fitxer des del qual es carregarà l'historial
     * @return L'objecte HistorialJoc carregat
     */
    public HistorialJoc carregarHistorial(String nomFitxer) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Carreguem el JSON com a objecte HistorialJoc
            return objectMapper.readValue(new File(nomFitxer), HistorialJoc.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
