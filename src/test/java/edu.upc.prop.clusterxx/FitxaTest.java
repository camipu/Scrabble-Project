package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test per la classe Fitxa.
 * Verifica les funcionalitats de la classe Fitxa com la creació, igualtat, excepcions, les funcions relacionades, i la nova funcionalitat de comodí.
 */
public class FitxaTest {

    private Fitxa fitxaA;
    private Fitxa fitxaZ;
    private Fitxa fitxaDigraf;
    private Fitxa fitxaB;
    private Fitxa fitxaCH2;
    private Fitxa fitxaA2;
    private Fitxa fitxaComodi;

    @Before
    public void setUp() {
        fitxaA = new Fitxa("A", 1);
        fitxaZ = new Fitxa("Z", 5);
        fitxaDigraf = new Fitxa("CH", 5);
        fitxaB = new Fitxa("B", 2);
        fitxaCH2 = new Fitxa("CH", 5);
        fitxaA2 = new Fitxa("A", 1);

        // Comodí amb punts 0
        fitxaComodi = new Fitxa("#", 0);
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
        assertTrue(fitxaA.equals(fitxaA2));
    }

    @Test
    public void testEqualsDiferentLletraB() {
        assertFalse(fitxaA.equals(fitxaB)); // Si la lletra és diferent, no són iguals
    }

    @Test
    public void testEqualsDiferentDigraf() {
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

    /**
     * Test per la creació d'una fitxa amb punts negatius, que hauria de llançar una excepció.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreacioFitxaAmbPuntsNegatius() {
        new Fitxa("A", -1);
    }

    /**
     * Test per la funció setLletraComodi, amb la comprovació de que només es poden modificar les fitxes comodí
     */
    @Test
    public void testSetLletraComodi() {
        Fitxa fitxaComodi = new Fitxa("#", 0);

        // Comodí es pot canviar de lletra
        fitxaComodi.setLletraComodi("A");
        assertEquals("A", fitxaComodi.obtenirLletra());

        // No es pot canviar una lletra que no sigui comodí
        try {
            fitxaA.setLletraComodi("B");
            fail("S'hauria de llançar una excepció per intentar canviar una lletra que no és comodí.");
        } catch (IllegalArgumentException e) {
            // Esperat
        }

        // No es pot posar un comodí de nou
        try {
            fitxaComodi.setLletraComodi("#");
            fail("S'hauria de llançar una excepció per intentar posar un comodí.");
        } catch (IllegalArgumentException e) {
            // Esperat
        }
    }

    /**
     * Test per la nova funció esComodi.
     * Verifica que una fitxa amb punts 0 sigui un comodí.
     */
    @Test
    public void testEsComodi() {
        assertTrue(fitxaComodi.esComodi()); // La fitxa amb punts 0 és un comodí
        assertFalse(fitxaA.esComodi()); // La fitxa amb punts 1 no és un comodí
        assertFalse(fitxaZ.esComodi()); // La fitxa amb punts 5 no és un comodí
    }

    /**
     * Test per la igualtat de fitxes amb la mateixa lletra i punts
     */
    @Test
    public void testEqualsSameLletraAndPunts() {
        Fitxa fitxa1 = new Fitxa("A", 1);
        Fitxa fitxa2 = new Fitxa("A", 1);
        assertEquals(fitxa1, fitxa2);
    }

    /**
     * Test per la igualtat de fitxes amb la mateixa lletra però punts diferents
     */
    @Test
    public void testEqualsDifferentPunts() {
        Fitxa fitxa1 = new Fitxa("A", 1);
        Fitxa fitxa2 = new Fitxa("A", 2);
        assertNotEquals(fitxa1, fitxa2);
    }

    /**
     * Test per la igualtat de fitxes amb diferent lletra
     */
    @Test
    public void testEqualsDifferentLletra() {
        Fitxa fitxa1 = new Fitxa("A", 1);
        Fitxa fitxa2 = new Fitxa("B", 1);
        assertNotEquals(fitxa1, fitxa2);
    }
}
