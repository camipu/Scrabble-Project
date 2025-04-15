package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SacTest {

    private Sac sac;
    private Fitxa fitxaA;
    private Fitxa fitxaA2;
    private Fitxa fitxaB;
    private Fitxa fitxaC;

    @Before
    public void setUp() {
        sac = new Sac();
        fitxaA = new Fitxa("A", 1);
        fitxaA2 = new Fitxa("A", 1); // mateixa lletra i punts, diferent instància
        fitxaB = new Fitxa("B", 3);
        fitxaC = new Fitxa("C", 2);
    }

    @Test
    public void testAfegirFitxa() {
        sac.afegirFitxa(fitxaA);
        assertEquals(1, sac.obtenirNumFitxes(fitxaA));
        assertEquals(1, sac.obtenirNumFitxes());
    }

    @Test
    public void testAfegirFitxaRepetida() {
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaA2);
        assertEquals(2, sac.obtenirNumFitxes(fitxaA));
        assertEquals(2, sac.obtenirNumFitxes());
    }

    @Test
    public void testObtenirNumFitxesEspecifica() {
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);
        sac.afegirFitxa(fitxaB);
        assertEquals(1, sac.obtenirNumFitxes(fitxaA));
        assertEquals(2, sac.obtenirNumFitxes(fitxaB));
    }

    @Test
    public void testObtenirNumFitxesTotal() {
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);
        sac.afegirFitxa(fitxaC);
        assertEquals(3, sac.obtenirNumFitxes());
    }

    @Test
    public void testAgafarFitxaPerLletra() {
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaA2);
        Fitxa agafada = sac.agafarFitxa(fitxaA);
        assertEquals(1, sac.obtenirNumFitxes(fitxaA)); // n'hi quedava una
        assertEquals("A", agafada.obtenirLletra());
    }

    @Test(expected = ExcepcioSacNoConteLaFitxa.class)
    public void testAgafarFitxaNoDisponible() {
        sac.agafarFitxa(fitxaB); // no existeix
    }

    @Test
    public void testAgafarFitxaAleatoria() {
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);
        Fitxa seleccionada = sac.agafarFitxa();
        assertNotNull(seleccionada);
        assertTrue(seleccionada.obtenirLletra().equals("A") || seleccionada.obtenirLletra().equals("B"));
        assertEquals(1, sac.obtenirNumFitxes()); // se n’ha tret una
    }

    @Test(expected = ExcepcioSacBuit.class)
    public void testAgafarFitxaSacBuit() {
        sac.agafarFitxa(); // no hi ha res
    }

    @Test
    public void testEsBuit() {
        assertTrue(sac.esBuit());
        sac.afegirFitxa(fitxaA);
        assertFalse(sac.esBuit());
    }
}
