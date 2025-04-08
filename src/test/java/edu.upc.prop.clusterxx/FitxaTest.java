package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FitxaTest {

    private Fitxa fitxaA;
    private Fitxa fitxaZ;
    private Fitxa fitxaDigraf;
    private Fitxa fitxab;
    private Fitxa fitxaLlarg;

    @Before
    public void setUp() {
        fitxaA = new Fitxa("A", 1);
        fitxaZ = new Fitxa("Z", 5);
        fitxaDigraf = new Fitxa("CH", 5);
        fitxab = new Fitxa("B", 2);
        fitxaLlarg = new Fitxa("ZYX", 10);  // Cas extrem amb una lletra més llarga
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
        assertEquals("B", fitxab.obtenirLletra());
        assertEquals("B", fitxab.toString());
        assertEquals(2, fitxab.obtenirPunts());
        assertFalse(fitxab.esDigraf());
    }

    @Test
    public void testCreacioFitxaDigraf() {
        assertEquals("CH", fitxaDigraf.obtenirLletra());
        assertEquals("CH", fitxaDigraf.toString());
        assertEquals(5, fitxaDigraf.obtenirPunts());
        assertTrue(fitxaDigraf.esDigraf());
    }

    @Test
    public void testCreacioFitxaAmbLletraLLarga() {
        assertEquals("ZYX", fitxaLlarg.obtenirLletra());
        assertEquals(10, fitxaLlarg.obtenirPunts());
        assertTrue(fitxaLlarg.esDigraf()); // Considerant que una cadena més llarga es considera un dígraf
    }

    @Test
    public void testCreacioFitxaAmbLletraMinúscula() {
        Fitxa fitxaMinuscule = new Fitxa("a", 3);
        assertEquals("A", fitxaMinuscule.obtenirLletra()); // La lletra es converteix a majúscula
        assertEquals(3, fitxaMinuscule.obtenirPunts());
        assertFalse(fitxaMinuscule.esDigraf());
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
    public void testEqualsDiferentLletra() {
        Fitxa fitxaZ2 = new Fitxa("Z", 5);
        assertTrue(fitxaZ.equals(fitxaZ2)); // Les fitxes amb la mateixa lletra i punts són iguals
    }

    @Test
    public void testEqualsDiferentPunts() {
        Fitxa fitxaA2 = new Fitxa("A", 2);
        assertFalse(fitxaA.equals(fitxaA2)); // Si els punts són diferents, no són iguals
    }

    @Test
    public void testEqualsDiferentLletraB() {
        Fitxa fitxaB = new Fitxa("B", 1);
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
        assertEquals(2, fitxab.obtenirPunts());
    }

    @Test
    public void testEsDigraf() {
        assertFalse(fitxaA.esDigraf()); // No és un dígraf
        assertFalse(fitxaZ.esDigraf()); // No és un dígraf
        assertTrue(fitxaDigraf.esDigraf()); // Sí és un dígraf
    }

    @Test
    public void testCaseInsensitive() {
        // Comprovem que la classe és insensible a majúscules i minúscules
        Fitxa fitxaLower = new Fitxa("a", 1);
        assertEquals(fitxaA, fitxaLower); // La lletra 'a' es converteix a 'A', així que són iguals
    }

}
