package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FitxaTest {

    private Fitxa fitxaA;
    private Fitxa fitxaZ;
    private Fitxa fitxaDigraf;
    private Fitxa fitxaB;

    @Before
    public void setUp() {
        fitxaA = new Fitxa("A", 1);
        fitxaZ = new Fitxa("Z", 5);
        fitxaDigraf = new Fitxa("CH", 5);
        fitxaB = new Fitxa("B", 2);
    }

    @Test
    public void testCreacioFitxaLletraA() {
        assertEquals("A", fitxaA.obtenirLletra());
        assertEquals(1, fitxaA.obtenirPunts());
        assertFalse(fitxaA.esDigraf());
    }

    @Test
    public void testCreacioFitxaLletraZ() {
        assertEquals("Z", fitxaZ.obtenirLletra());
        assertEquals(5, fitxaZ.obtenirPunts());
        assertFalse(fitxaZ.esDigraf());
    }

    @Test
    public void testCreacioFitxaLletrab() {
        assertEquals("B", fitxaB.obtenirLletra());
        assertEquals("B", fitxaB.toString());
        assertEquals(2, fitxaB.obtenirPunts());
        assertFalse(fitxaB.esDigraf());
    }

    @Test
    public void testCreacioFitxaDigraf() {
        assertEquals("CH", fitxaDigraf.obtenirLletra());
        assertEquals("CH", fitxaDigraf.toString());
        assertEquals(5, fitxaDigraf.obtenirPunts());
        assertTrue(fitxaDigraf.esDigraf());
    }


    @Test
    public void testToString() {
        assertEquals("A", fitxaA.toString());
        assertEquals("CH", fitxaDigraf.toString());
    }

    @Test
    public void testEqualsMateixaLletra() {
        Fitxa fitxaA2 = new Fitxa("A", 1);
        assertTrue(fitxaA.equals(fitxaA2));
    }

    @Test
    public void testEqualsDiferentLletraB() {
        assertFalse(fitxaA.equals(fitxaB)); // Si la lletra és diferent, no són iguals
    }


    @Test
    public void testEqualsDiferentDigraf() {
        Fitxa fitxaCH2 = new Fitxa("CH", 5);
        assertTrue(fitxaDigraf.equals(fitxaCH2)); // Dos dígrafs iguals han de ser iguals
    }

    @Test
    public void testObtenirPunts() {
        assertEquals(1, fitxaA.obtenirPunts());
        assertEquals(5, fitxaZ.obtenirPunts());
        assertEquals(5, fitxaDigraf.obtenirPunts());
        assertEquals(2, fitxaB.obtenirPunts());
    }

    @Test
    public void testEsDigraf() {
        assertFalse(fitxaA.esDigraf()); // No és un dígraf
        assertFalse(fitxaZ.esDigraf()); // No és un dígraf
        assertTrue(fitxaDigraf.esDigraf()); // Sí és un dígraf
    }


}
