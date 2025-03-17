package edu.upc.prop.clusterxx.fitxa;

import edu.upc.prop.clusterxx.FitxaTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class FitxaTestRunner {
    public static void main(String[] args) {
        // Executem les proves de la classe FitxaTest
        Result result = JUnitCore.runClasses(FitxaTest.class);

        // Mostrem els resultats de les proves
        System.out.println("Resultats de les proves:");
        System.out.println("Total de proves: " + result.getRunCount());
        System.out.println("Proves fallides: " + result.getFailureCount());

        // Mostrem les fallades, si n'hi ha
        for (Failure failure : result.getFailures()) {
            System.out.println("Fallada: " + failure.toString());
        }

        // Si totes les proves han passat, mostrem un missatge d'èxit
        if (result.wasSuccessful()) {
            System.out.println("Totes les proves han passat correctament!");
        } else {
            System.out.println("Algunes proves han fallat.");
        }
    }
}
