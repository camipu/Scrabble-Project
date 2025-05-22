package edu.upc.prop.clusterxx.presentacio;

import java.awt.Color;

public class ColorLoader {

    // Definir els colors com a constants
    private final Color colorFons = new Color( 255, 244, 206); // Color fusta clar
    private final Color colorFonsFitxa = new Color(255, 220, 160); // Color fusta clar
    private final Color colorSeleccionada = new Color(255, 255, 150); // Groc clar per selecció
    private final Color colorText = Color.DARK_GRAY; // Text negre
    private final Color colorAccent = new Color(220, 100, 50); // Color accent per elements destacats


    // Colors per als diferents tipus de caselles
    public static final Color colorNormal = new Color(240, 240, 240);
    public static final Color colorML2 = new Color(135, 206, 250); // Blau clar per multiplicador lletra x2
    public static final Color colorML3 = new Color(65, 105, 225);  // Blau fosc per multiplicador lletra x3
    public static final Color colorMP2 = new Color(255, 192, 203); // Rosa clar per multiplicador paraula x2
    public static final Color colorMP3 = new Color(220, 20, 60);   // Vermell per multiplicador paraula x3

    // Instància única de ColorLoader (Singleton)
    private static ColorLoader instance;

    // Constructor privat per evitar la creació d'instàncies fora de la classe
    private ColorLoader() {}

    // Mètode per obtenir la instància única de ColorLoader
    public static ColorLoader getInstance() {
        if (instance == null) {
            instance = new ColorLoader();
        }
        return instance;
    }

    // Getters per obtenir els colors
    public Color getColorFons() {
        return colorFons;
    }

    public Color getColorSeleccionada() {
        return colorSeleccionada;
    }

    public Color getColorText() {
        return colorText;
    }

    public Color getColorFonsFitxa() {
        return colorFonsFitxa;
    }
    /**
     * Retorna el color d'accent utilitzat per elements destacats de la interfície.
     *
     * @return Color d'accent
     */
    public Color getColorAccent() {
        return colorAccent;
    }

    public Color getColorNormal() {
        return colorNormal;
    }
    public Color getColorML2() {
        return colorML2;
    }
    public Color getColorML3() {
        return colorML3;
    }
    public Color getColorMP2() {
        return colorMP2;
    }
    public Color getColorMP3() {
        return colorMP3;
    }

}
