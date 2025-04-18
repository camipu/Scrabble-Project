
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
    public void testCalcularPuntuacioParaulaSimple() {
        Fitxa fitxaA = new Fitxa("A", 1);
        Fitxa fitxaB = new Fitxa("B", 3);
        Casella c1 = taulell.getCasella(7, 7);
        Casella c2 = taulell.getCasella(7, 8);
        c1.colocarFitxa(fitxaA);
        c2.colocarFitxa(fitxaB);
        List<Casella> caselles = Arrays.asList(c1, c2);
        int puntuacio = taulell.calcularPuntuacioTotal(caselles);
        assertEquals(4, puntuacio);
    }

    @Test
    public void testValidarPrimeraJugadaCentre() {
        DAWG mockDawg = mock(DAWG.class);
        when(mockDawg.conteParaula("AB")).thenReturn(true);
        Fitxa f1 = new Fitxa("A", 1);
        Fitxa f2 = new Fitxa("B", 3);
        Casella c1 = new Casella(7, 7, 15);
        Casella c2 = new Casella(7, 8, 15);
        c1.colocarFitxa(f1);
        c2.colocarFitxa(f2);
        List<Casella> caselles = Arrays.asList(c1, c2);
        Jugada jugada = taulell.construirJugada(caselles, mockDawg);
        assertTrue(jugada.getJugadaValida());
    }

    @Test
    public void testValidarJugadaNoTocaParaulaDesprésPrimera() {
        DAWG mockDawg = mock(DAWG.class);
        when(mockDawg.conteParaula("CD")).thenReturn(true);
        taulell.colocarFitxa(new Fitxa("X", 5), 7, 7);
        Casella c1 = new Casella(0, 0, 15);
        Casella c2 = new Casella(0, 1, 15);
        c1.colocarFitxa(new Fitxa("C", 2));
        c2.colocarFitxa(new Fitxa("D", 2));
        List<Casella> caselles = Arrays.asList(c1, c2);
        Jugada jugada = taulell.construirJugada(caselles, mockDawg);
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
        assertEquals(9, puntuacio);
    }

    @Test
    public void testPuntuacioAmbDobleParaulaConcret() {
        Casella c1 = new Casella(0, 0, 15);
        Casella c2 = new Casella(0, 1, 15);
        Fitxa f1 = new Fitxa("M", 3);
        Fitxa f2 = new Fitxa("A", 1);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(2));
        c1.colocarFitxa(f1);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        c2.colocarFitxa(f2);
        List<Casella> jugada = Arrays.asList(c1, c2);
        int puntuacio = taulell.calcularPuntuacioTotal(jugada);
        assertEquals(8, puntuacio);
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
        assertEquals(7, puntuacio);
    }
}