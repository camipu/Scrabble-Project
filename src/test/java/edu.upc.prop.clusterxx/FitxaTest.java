package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FitxaTest {

    private Fitxa fitxaA;
    private Fitxa fitxaZ;
    private Fitxa fitxaDigraf;
    private Fitxa fitxab;

    @Before
    public void setUp() {
        fitxaA = new Fitxa('A', 1);
        fitxaDigraf = new Fitxa('(', 5);
        fitxaZ = new Fitxa('Z', 5);
        fitxab = new Fitxa('b', 2);
    }

    @Test
    public void testCreacioFitxaLletraA() {
        assertEquals('A', fitxaA.obtenirLletra());
        assertEquals(1, fitxaA.obtenirPunts());
        assertFalse(fitxaA.esDigraf());
    }

    @Test
    public void testCreacioFitxaLletraZ() {
        assertEquals('Z', fitxaZ.obtenirLletra());
        assertEquals(5, fitxaZ.obtenirPunts());
        assertFalse(fitxaZ.esDigraf());
    }

    @Test
    public void testCreacioFitxaLletrab() {
        assertEquals('B', fitxab.obtenirLletra());
        assertEquals(2, fitxab.obtenirPunts());
        assertFalse(fitxab.esDigraf());
    }

    @Test
    public void testCreacioFitxaDigraf() {
        assertEquals('(', fitxaDigraf.obtenirLletra());
        assertEquals("CH", fitxaDigraf.toString());
        assertEquals(5, fitxaDigraf.obtenirPunts());
        assertTrue(fitxaDigraf.esDigraf());
    }
}
