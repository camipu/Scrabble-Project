package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test per la classe Sac.
 * Verifica les funcionalitats de la classe Sac com l'afegir fitxes, agafar fitxes,
 * comprobar si el sac està buit, i l'ús d'excepcions.
 */
public class SacTest {

    private Sac sac;
    private Fitxa fitxaA;
    private Fitxa fitxaA2;
    private Fitxa fitxaB;
    private Fitxa fitxaC;
    private Fitxa fitxaComodi; // Afegeix un comodí

    @Before
    public void setUp() {
        sac = new Sac();
        fitxaA = new Fitxa("A", 1);
        fitxaA2 = new Fitxa("A", 1); // mateixa lletra i punts, diferent instància
        fitxaB = new Fitxa("B", 3);
        fitxaC = new Fitxa("C", 2);
        fitxaComodi = new Fitxa("#", 0); // comodí
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

    /**
     * Test per verificar la funció esComodi de la fitxa.
     * Comprova que les fitxes amb punts 0 són reconegudes com a comodins.
     */
    @Test
    public void testEsComodi() {
        sac.afegirFitxa(fitxaComodi); // afegir fitxa comodí
        assertTrue(fitxaComodi.esComodi()); // Ha de ser un comodí
        assertFalse(fitxaA.esComodi()); // No és comodí
    }

    /**
     * Test per verificar si el sac es comporta bé amb fitxes comodí.
     * Comprova si les fitxes comodí són afegides i extretes correctament.
     */
    @Test
    public void testAfegirFitxaComodi() {
        sac.afegirFitxa(fitxaComodi); // afegir comodí
        assertEquals(1, sac.obtenirNumFitxes(fitxaComodi)); // Només una fitxa comodí
        sac.agafarFitxa(fitxaComodi); // agafar la fitxa comodí
        assertEquals(0, sac.obtenirNumFitxes(fitxaComodi)); // Ja no queda cap comodí
    }

    /**
     * Test per comprovar si el sac detecta bé quan està buit.
     * Afegim i extraem fitxes per veure com es comporta el sac.
     */
    @Test
    public void testEsBuitAmbFitxes() {
        sac.afegirFitxa(fitxaA);
        assertFalse(sac.esBuit()); // El sac no ha de ser buit després d'afegir fitxa
        sac.agafarFitxa(); // Treiem la fitxa
        assertTrue(sac.esBuit()); // El sac ha de ser buit després de treure les fitxes
    }
}
