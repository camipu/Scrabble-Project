package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JugadaTest {

    private Casella casella1;
    private Casella casella2;
    private List<Casella> caselles;
    private Jugada jugada;

    @Before
    public void setUp() {
        // Creem mocks de Casella
        casella1 = mock(Casella.class);
        casella2 = mock(Casella.class);
        caselles = Arrays.asList(casella1, casella2);

        // Inicialitzem una jugada amb el nou constructor (4 paràmetres)
        jugada = new Jugada("CASA", caselles, 12, true);
    }

    @Test
    public void testConstructorIGetters() {
        assertEquals("CASA", jugada.getParaulaFormada());
        assertEquals(12, jugada.getPuntuacio());
        assertEquals(caselles, jugada.getCasellesJugades());
        assertTrue(jugada.getJugadaValida());
    }

    @Test
    public void testSetParaulaFormada() {
        jugada.setParaulaFormada("CASER");
        assertEquals("CASER", jugada.getParaulaFormada());
    }

    @Test
    public void testSetPuntuacio() {
        jugada.setPuntuacio(20);
        assertEquals(20, jugada.getPuntuacio());
    }

    @Test
    public void testSetJugadaValida() {
        jugada.setJugadaValida(false);
        assertFalse(jugada.getJugadaValida());
    }

    @Test
    public void testCasellesJugadesNoModificables() {
        assertEquals(2, jugada.getCasellesJugades().size());
        assertTrue(jugada.getCasellesJugades().contains(casella1));
        assertTrue(jugada.getCasellesJugades().contains(casella2));
    }
}
