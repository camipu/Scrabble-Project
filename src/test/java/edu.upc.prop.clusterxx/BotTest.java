package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BotTest {

    private Faristol faristol;
    private Bot botFacil;
    private Bot botMitja;
    private Bot botDificil;

    @Before
    public void setUp() { // mida 4 per exemple
        // Creem els bots amb diferents nivells de dificultat
        botFacil = new Bot("BotFàcil", mock(Faristol.class), 1);
        botMitja = new Bot("BotMitjà", mock(Faristol.class), 2);
        botDificil = new Bot("BotDifícil", mock(Faristol.class), 3);
    }

    @Test
    public void testCrearBotCorrectament() {
        assertNotNull(botFacil);
        assertNotNull(botMitja);
        assertNotNull(botDificil);
        assertEquals("BotFàcil", botFacil.obtenirNom());
        assertEquals("BotMitjà", botMitja.obtenirNom());
        assertEquals("BotDifícil", botDificil.obtenirNom());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNivellDificultatIncorrecte() {
        new Bot("BotIncorrecte", mock(Faristol.class), 0);  // Nivell incorrecte (per sota de l'interval)
    }

    @Test
    public void testObtenirDificultat() {
        assertEquals(1, botFacil.obtenirDificultat());
        assertEquals(2, botMitja.obtenirDificultat());
        assertEquals(3, botDificil.obtenirDificultat());
    }

    @Test
    public void testCopiaBot() {
        Bot botCopia = new Bot(botFacil);
        assertNotNull(botCopia);
        assertEquals(botFacil.obtenirNom(), botCopia.obtenirNom());
        assertEquals(botFacil.obtenirDificultat(), botCopia.obtenirDificultat());
    }

    @Test
    public void testEsBot() {
        assertTrue(botFacil.esBot());
        assertTrue(botMitja.esBot());
        assertTrue(botDificil.esBot());
    }
}
