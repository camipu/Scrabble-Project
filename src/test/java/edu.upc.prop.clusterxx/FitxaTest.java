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
        assertEquals('A', fitxaA.getLletra());
        assertEquals(1, fitxaA.getPunts());
        assertFalse(fitxaA.esDigraf());
    }

    @Test
    public void testCreacioFitxaLletraZ() {
        assertEquals('Z', fitxaZ.getLletra());
        assertEquals(5, fitxaZ.getPunts());
        assertFalse(fitxaZ.esDigraf());
    }

    @Test
    public void testCreacioFitxaLletrab() {
        assertEquals('B', fitxab.getLletra());
        assertEquals(2, fitxab.getPunts());
        assertFalse(fitxab.esDigraf());
    }

    @Test
    public void testCreacioFitxaDigraf() {
        assertEquals('(', fitxaDigraf.getLletra());
        assertEquals("CH", fitxaDigraf.toString());
        assertEquals(5, fitxaDigraf.getPunts());
        assertTrue(fitxaDigraf.esDigraf());
    }
}
