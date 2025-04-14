package edu.upc.prop.clusterxx;

import java.util.HashMap;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    // Crear partida
    String idioma = "english";
    String[] noms = {"Bot", "Joan"};
    Joc joc = new Joc(noms.length, idioma, noms);

    // Substituir el jugador pel bot amb dificultat màxima (3)
    Bot bot = new Bot("Bot", joc.obtenirSac(), 3);
    joc.jugadors[0] = bot; // sobrescriu el jugador inicial

    // Crear el DAWG a partir del fitxer de paraules
    DAWG dawg = new DAWG(idioma);

    // COMENÇAR PARTIDA
    joc.imprimirInfoJugadors();
    Fitxa f1 = new Fitxa("C", 3);
    Fitxa f2 = new Fitxa("A", 3);
    Fitxa f3 = new Fitxa("S", 3);
    Fitxa f4 = new Fitxa("A", 3);
    Fitxa f5 = new Fitxa("S", 3);
    //Col·locar CASAS (vertical)
    joc.colocarFitxa(f1, 7, 3);
    joc.colocarFitxa(f2, 7, 4);
    joc.colocarFitxa(f3, 7, 5);
    joc.colocarFitxa(f4, 7, 6);
    joc.colocarFitxa(f5, 7, 7);

    joc.obtenirTaulell().imprimirTaulell();
    // Mostrar faristol del bot abans de jugar

    while (true) {
      System.out.println("\nFaristol inicial del bot:");
      bot.imprimirFaristol();

      // Executar el torn del bot
      boolean haJugat = bot.executarTorn(joc.obtenirTaulell(), dawg, joc.obtenirSac());

      // Mostrar resultat
      if (haJugat) {
        System.out.println("\nTauler després del torn del bot:");
        joc.obtenirTaulell().imprimirTaulell();
        System.out.println("\nInformació del bot després de jugar:");
        bot.imprimirInfo();
      } else {
        System.out.println("El bot no ha pogut fer cap jugada.");
        break; // Sortir del bucle si el bot no pot fer més jugades
      }
    }
  }
}