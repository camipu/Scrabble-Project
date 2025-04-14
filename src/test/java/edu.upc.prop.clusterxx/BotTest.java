package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BotTest {

    private Bot botFacil;
    private Bot botMig;
    private Bot botDificil;
    private DAWG dawg;
    private Taulell taulell;

    @Before
    public void setUp() {
        // Tokens i paraules bàsiques per crear el DAWG
        List<String> tokens = Arrays.asList("A", "B", "C", "S");
        List<String> paraules = Arrays.asList("CAS", "SAC", "CASA", "ACAB");
        dawg = new DAWG(tokens, paraules);
        taulell = new Taulell(15);  // Tauler buit

        // Crear faristols amb les mateixes fitxes per a cada bot
        Faristol f1 = new Faristol(7);
        f1.afegirFitxa(new Fitxa("C", 3));
        f1.afegirFitxa(new Fitxa("A", 1));
        f1.afegirFitxa(new Fitxa("S", 1));

        Faristol f2 = new Faristol(7);
        f2.afegirFitxa(new Fitxa("C", 3));
        f2.afegirFitxa(new Fitxa("A", 1));
        f2.afegirFitxa(new Fitxa("S", 1));

        Faristol f3 = new Faristol(7);
        f3.afegirFitxa(new Fitxa("C", 3));
        f3.afegirFitxa(new Fitxa("A", 1));
        f3.afegirFitxa(new Fitxa("S", 1));

        // Crear bots amb diferents nivells de dificultat
        botFacil = new Bot("BotFacil", f1, 1);
        botMig = new Bot("BotMig", f2, 2);
        botDificil = new Bot("BotDificil", f3, 3);
    }

    @Test
    public void testBotFacilJugada() {
        Jugada jugada = botFacil.calcularJugada(taulell, dawg);
        assertNotNull(jugada);
        System.out.println("Bot fàcil ha triat: " + jugada.getParaula());
    }

    @Test
    public void testBotMigJugada() {
        Jugada jugada = botMig.calcularJugada(taulell, dawg);
        assertNotNull(jugada);
        System.out.println("Bot mitjà ha triat: " + jugada.getParaula());
    }

    @Test
    public void testBotDificilJugada() {
        Jugada jugada = botDificil.calcularJugada(taulell, dawg);
        assertNotNull(jugada);
        System.out.println("Bot difícil ha triat: " + jugada.getParaula());
        assertEquals("CAS", jugada.getParaula()); // assumim que és la millor
    }
}
