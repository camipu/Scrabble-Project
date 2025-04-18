package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaBuida;
import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaOcupada;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CasellaTest {

    private Casella casella;
    private Fitxa mockFitxaA;
    private Fitxa mockFitxaB;

    @Before
    public void setUp() {
        casella = new Casella(3, 3, 15);
        mockFitxaA = mock(Fitxa.class);
        when(mockFitxaA.toString()).thenReturn("A");
        when(mockFitxaA.obtenirLletra()).thenReturn("A");
        when(mockFitxaA.obtenirPunts()).thenReturn(1);
        when(mockFitxaA.esDigraf()).thenReturn(false);

        mockFitxaB = mock(Fitxa.class);
        when(mockFitxaB.toString()).thenReturn("B");
        when(mockFitxaB.obtenirLletra()).thenReturn("B");
        when(mockFitxaB.obtenirPunts()).thenReturn(3);
        when(mockFitxaB.esDigraf()).thenReturn(false);
    }

    @Test(expected = ExcepcioCasellaBuida.class)
    public void testCasellaInicialBuida() {
        assertTrue(casella.esBuida());
        assertFalse(casella.esJugada());
        casella.obtenirFitxa();
    }

    @Test
    public void testColocarFitxaCorrectament() {
        casella.colocarFitxa(mockFitxaA);
        assertFalse(casella.esBuida());
        assertEquals(mockFitxaA, casella.obtenirFitxa());
    }

    @Test(expected = ExcepcioCasellaOcupada.class)
    public void testColocarFitxaCasellaOcupada() {
        casella.colocarFitxa(mockFitxaA);
        casella.colocarFitxa(mockFitxaB); // ha de fallar
    }

    @Test
    public void testRetirarFitxaCorrectament() {
        casella.colocarFitxa(mockFitxaA);
        casella.retirarFitxa();
        assertTrue(casella.esBuida());
    }

    @Test(expected = ExcepcioCasellaBuida.class)
    public void testRetirarFitxaCasellaBuida() {
        casella.retirarFitxa();
    }

    @Test
    public void testJugarCasella() {
        casella.jugarCasella();
        assertTrue(casella.esJugada());
    }

    @Test
    public void testToStringCasellaBuida() {
        assertEquals("  ", casella.toString());
    }

    @Test
    public void testToStringFitxaSimple() {
        casella.colocarFitxa(mockFitxaA);
        assertEquals(" A", casella.toString());
    }

    @Test
    public void testToStringFitxaDigraf() {
        when(mockFitxaA.esDigraf()).thenReturn(true);
        casella.colocarFitxa(mockFitxaA);
        assertEquals("A", casella.toString()); // retorna directament toString()
    }

    @Test
    public void testEstrategiaTripleParaula() {
        Casella tripleParaula = new Casella(0, 0, 15);
        tripleParaula.colocarFitxa(mockFitxaA);
        assertEquals(3, tripleParaula.obtenirMultiplicador());
        assertTrue(tripleParaula.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula);
    }

    @Test
    public void testEstrategiaDobleParaula() {
        Casella dobleParaula = new Casella(2, 2, 15); // i == j
        dobleParaula.colocarFitxa(mockFitxaA);
        assertEquals(2, dobleParaula.obtenirMultiplicador());
        assertTrue(dobleParaula.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorParaula);
    }

    @Test
    public void testEstrategiaTripleLletra() {
        Casella tripleLletra = new Casella(3, 3, 15); // 3,3
        tripleLletra.colocarFitxa(mockFitxaA);
        assertEquals(3, tripleLletra.obtenirMultiplicador());
        assertTrue(tripleLletra.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra);
    }

    @Test
    public void testEstrategiaDobleLletra() {
        Casella dobleLletra = new Casella(7, 4, 15); // 7 == centre, j != centre
        dobleLletra.colocarFitxa(mockFitxaA);
        assertEquals(2, dobleLletra.obtenirMultiplicador());
        assertTrue(dobleLletra.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaMultiplicadorLletra);
    }

    @Test
    public void testEstrategiaNormal() {
        Casella normal = new Casella(1, 2, 15); // No coincideix amb cap altra
        normal.colocarFitxa(mockFitxaA);
        assertEquals(1, normal.obtenirMultiplicador());
        assertTrue(normal.obtenirEstrategia() instanceof EstrategiaPuntuacio.EstrategiaNormal);
    }

}
