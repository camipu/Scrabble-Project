package edu.upc.prop.clusterxx;

/*
    * Colors.java
    *
    * Aquesta classe conté constants per a codis de colors ANSI per a la consola.
    * S'utilitza per a imprimir text amb colors en la consola.
    *
 */
public class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";


    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String GREEN_TEXT = "\u001B[32m";
    public static final String YELLOW_TEXT = "\u001B[33m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String PURPLE_TEXT = "\u001B[35m";
    public static final String CYAN_TEXT = "\u001B[36m";
    public static final String WHITE_TEXT = "\u001B[37m";

    private Colors() {
        // Constructor privat per evitar instanciació
    }
}

