
package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaBuida;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TaulellTest {
    private Taulell taulell;
    private Fitxa mockFitxa;

    @Before
    public void setUp() {
        taulell = new Taulell(15); // mida imparella vàlida
        mockFitxa = mock(Fitxa.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAmbMidaParell() {
        new Taulell(10);
    }

    @Test
    public void testGetSize() {
        assertEquals(15, taulell.getSize());
    }

    @Test
    public void testTaulellInicialEsBuit() {
        assertTrue(taulell.esBuit());
    }

    @Test
    public void testColocarFitxaICanviarEsBuit() {
        when(mockFitxa.obtenirLletra()).thenReturn("A");
        when(mockFitxa.obtenirPunts()).thenReturn(1);
        taulell.colocarFitxa(mockFitxa, 7, 7);
        assertFalse(taulell.esBuit());
        assertEquals(mockFitxa, taulell.obtenirFitxa(7, 7));
    }

    @Test(expected = ExcepcioCasellaBuida.class)
    public void testRetirarFitxa() {
        when(mockFitxa.obtenirLletra()).thenReturn("B");
        when(mockFitxa.obtenirPunts()).thenReturn(3);
        taulell.colocarFitxa(mockFitxa, 5, 5);
        assertNotNull(taulell.obtenirFitxa(5, 5));
        taulell.retirarFitxa(5, 5);
        taulell.obtenirFitxa(5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColocarFitxaForaLimits() {
        taulell.colocarFitxa(mockFitxa, -1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetirarFitxaForaLimits() {
        taulell.retirarFitxa(100, 0);
    }

    @Test
    public void testGetCasella() {
        Casella casella = taulell.getCasella(2, 2);
        assertNotNull(casella);
        assertEquals(2, casella.obtenirX());
        assertEquals(2, casella.obtenirY());
    }

    @Test
    public void testPrimeraJugadaHaDePassarPelCentre() {
        DAWG dawg = new DAWG(List.of("A", "B", "C"), List.of("ABC"));
        Fitxa a = new Fitxa("A", 1);
        Fitxa b = new Fitxa("B", 1);
        Fitxa c = new Fitxa("C", 1);

        List<Casella> caselles = List.of(
            new Casella(7, 6, 15),
            new Casella(7, 7, 15),
            new Casella(7, 8, 15)
        );
        caselles.get(0).colocarFitxa(a);
        caselles.get(1).colocarFitxa(b);
        caselles.get(2).colocarFitxa(c);

        Jugada jugada = taulell.construirJugada(caselles, dawg);
        assertTrue(jugada.getJugadaValida());
    }

    @Test
    public void testPrimeraJugadaSenseCentreEsInvalida() {
        DAWG dawg = new DAWG(List.of("A", "B", "C"), List.of("ABC"));
        Fitxa a = new Fitxa("A", 1);
        Fitxa b = new Fitxa("B", 1);
        Fitxa c = new Fitxa("C", 1);

        List<Casella> caselles = List.of(
            new Casella(7, 5, 15),
            new Casella(7, 6, 15),
            new Casella(7, 8, 15)
        );
        caselles.get(0).colocarFitxa(a);
        caselles.get(1).colocarFitxa(b);
        caselles.get(2).colocarFitxa(c);

        Jugada jugada = taulell.construirJugada(caselles, dawg);
        assertFalse(jugada.getJugadaValida());
    }

    @Test
    public void testJugadaTocaParaulaEsValida() {
        DAWG dawg = new DAWG(List.of("A", "B", "C", "D"), List.of("ABC", "CD"));
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);
        taulell.colocarFitxa(new Fitxa("B", 1), 7, 8);

        List<Casella> caselles = List.of(
            new Casella(7, 9, 15),
            new Casella(8, 9, 15)
        );
        caselles.get(0).colocarFitxa(new Fitxa("C", 1));
        caselles.get(1).colocarFitxa(new Fitxa("D", 1));

        Jugada jugada = taulell.construirJugada(caselles, dawg);
        assertTrue(jugada.getJugadaValida());
    }

    @Test
    public void testJugadaSenseTocarCapParaulaEsInvalida() {
        DAWG dawg = new DAWG(List.of("A", "B", "C", "D"), List.of("CD"));
        taulell.getTaulell()[7][7].colocarFitxa(new Fitxa("A", 1));

        List<Casella> caselles = List.of(
            new Casella(0, 0, 15),
            new Casella(0, 1, 15)
        );
        caselles.get(0).colocarFitxa(new Fitxa("C", 1));
        caselles.get(1).colocarFitxa(new Fitxa("D", 1));

        Jugada jugada = taulell.construirJugada(caselles, dawg);
        assertFalse(jugada.getJugadaValida());
    }


    @Test
    public void testPuntuacioAmbDobleLletraConcret() {
        Casella c1 = new Casella(0, 0, 15);
        Casella c2 = new Casella(0, 1, 15);
        Fitxa f1 = new Fitxa("H", 4); // DL
        Fitxa f2 = new Fitxa("I", 1);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(2));
        c1.colocarFitxa(f1);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        c2.colocarFitxa(f2);
        List<Casella> jugada = Arrays.asList(c1, c2);
        int puntuacio = taulell.calcularPuntuacioTotal(jugada);
        assertEquals(23, puntuacio);
    }

    @Test
    public void testPuntuacioAmbTripleParaulaConcret() {
        Casella c1 = new Casella(0, 0, 15);
        Casella c2 = new Casella(0, 1, 15);
        Fitxa f1 = new Fitxa("M", 3);
        Fitxa f2 = new Fitxa("A", 1);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(3));
        c1.colocarFitxa(f1);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        c2.colocarFitxa(f2);
        List<Casella> jugada = Arrays.asList(c1, c2);
        int puntuacio = taulell.calcularPuntuacioTotal(jugada);
        
        assertEquals(12, puntuacio);
    }

    @Test
    public void testPuntuacioAmbPerpendiculars() {
        taulell.colocarFitxa(new Fitxa("T", 1), 7, 6);
        taulell.colocarFitxa(new Fitxa("E", 1), 7, 7);
        taulell.colocarFitxa(new Fitxa("N", 1), 7, 8);
        Casella c1 = new Casella(6, 7, 15);
        Casella c2 = new Casella(8, 7, 15);
        Fitxa f1 = new Fitxa("A", 1);
        Fitxa f2 = new Fitxa("O", 1);
        c1.colocarFitxa(f1);
        c2.colocarFitxa(f2);
        List<Casella> jugada = Arrays.asList(c1, c2);
        int puntuacio = taulell.calcularPuntuacioTotal(jugada);
        assertEquals(9, puntuacio);
    }


}