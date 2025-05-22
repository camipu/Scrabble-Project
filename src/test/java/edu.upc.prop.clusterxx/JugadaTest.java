package edu.upc.prop.clusterxx;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

public class JugadaTest {

    private Fitxa fitxa1, fitxa2, fitxa3, fitxa4, fitxa4Comodi;
    private Casella casella1, casella2, casella3, casella4, casella4Comodi;
    private List<Casella> caselles, casellesAmbComodi;
    private Jugada jugada, jugadaAmbComodi;

    @Before
    public void setUp() {
        // Creem mocks de Casella
        casella1 = mock(Casella.class);
        casella2 = mock(Casella.class);
        casella3 = mock(Casella.class);
        casella4 = mock(Casella.class);
        casella4Comodi = mock(Casella.class);
            fitxa1 = mock(Fitxa.class);
        fitxa2 = mock(Fitxa.class);
        fitxa3 = mock(Fitxa.class);
        fitxa4 = mock(Fitxa.class);
        fitxa4Comodi = mock(Fitxa.class);

        // Cal crear mocks per a les fitxes i configurar-los a les caselles
        org.mockito.Mockito.when(casella1.obtenirFitxa()).thenReturn(fitxa1);
        org.mockito.Mockito.when(casella2.obtenirFitxa()).thenReturn(fitxa2);
        org.mockito.Mockito.when(casella3.obtenirFitxa()).thenReturn(fitxa3);
        org.mockito.Mockito.when(casella4.obtenirFitxa()).thenReturn(fitxa4);
        org.mockito.Mockito.when(casella4Comodi.obtenirFitxa()).thenReturn(fitxa4Comodi);

        org.mockito.Mockito.when(fitxa1.obtenirLletra()).thenReturn("C");
        org.mockito.Mockito.when(fitxa2.obtenirLletra()).thenReturn("A");
        org.mockito.Mockito.when(fitxa3.obtenirLletra()).thenReturn("S");
        org.mockito.Mockito.when(fitxa4.obtenirLletra()).thenReturn("A");
        org.mockito.Mockito.when(fitxa4Comodi.obtenirLletra()).thenReturn("A");

        org.mockito.Mockito.when(fitxa1.esComodi()).thenReturn(false);
        org.mockito.Mockito.when(fitxa2.esComodi()).thenReturn(false);
        org.mockito.Mockito.when(fitxa3.esComodi()).thenReturn(false);
        org.mockito.Mockito.when(fitxa4.esComodi()).thenReturn(false);
        org.mockito.Mockito.when(fitxa4Comodi.esComodi()).thenReturn(true);

        caselles = Arrays.asList(casella1, casella2, casella3, casella4);
        casellesAmbComodi = Arrays.asList(casella1, casella2, casella3, casella4Comodi);

        // Inicialitzem una jugada amb el nou constructor (4 paràmetres)
        jugada = new Jugada("CASA", caselles, 12, true);
        jugadaAmbComodi = new Jugada("CASA", casellesAmbComodi, 12, true);
    }

    @Test
    public void testConstructorIGetters() {
        assertEquals("CASA", jugada.getParaulaFormada());
        assertEquals(12, jugada.getPuntuacio());
        assertEquals(caselles, jugada.getCasellesJugades());
        assertTrue(jugada.getJugadaValida());
        assertFalse(jugada.conteComodi());
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
        assertEquals(4, jugada.getCasellesJugades().size());
        assertTrue(jugada.getCasellesJugades().contains(casella1));
        assertTrue(jugada.getCasellesJugades().contains(casella2));
    }

    @Test
    public void testConteComodiAmbJugadaSenseComodi() {
        assertFalse(jugada.conteComodi());
        assertTrue(jugadaAmbComodi.conteComodi());
    }
}
