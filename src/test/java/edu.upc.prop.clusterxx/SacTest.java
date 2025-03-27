package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SacTest {
    private Sac sac;
    private Fitxa a1, a2, b, x;

    @Before
    public void setUp() {
        // Simulem un sac amb fitxes predefinides
        sac = new Sac();
        a1 = new Fitxa('A', 1);
        a2 = new Fitxa('A', 1);
        b = new Fitxa('B', 2);
        x = new Fitxa('X', 5);

        sac.afegirFitxa(a1);
        sac.afegirFitxa(a2);
        sac.afegirFitxa(b);
    }

    @Test
    public void testAgafarFitxaA() {
        Fitxa f = sac.agafarFitxa(a1);
        assertNotNull(f);
        assertEquals('A', f.obtenirLletra());
        assertEquals(2, sac.getNumFitxes());
        assertFalse(sac.esBuit());
    }

    @Test
    public void testSacBuit() {
        Fitxa f = sac.agafarFitxa(a1);
        assertNotNull(f);
        assertEquals('A', f.obtenirLletra());
        f = sac.agafarFitxa(a2);
        assertNotNull(f);
        assertEquals('A', f.obtenirLletra());
        f = sac.agafarFitxa(b);
        assertNotNull(f);
        assertEquals('B', f.obtenirLletra());

        assertEquals(0, sac.getNumFitxes());
        assertTrue(sac.esBuit());
    }

    @Test(expected = NoSuchElementException.class)
    public void testAgafarFitxaInexistent() {
        sac.agafarFitxa(x);
    }

    @Test
    public void testAgafarFitxaAleatoria() {
        Fitxa f = sac.agafarFitxa();
        assertNotNull(f);
        assertTrue(f.obtenirLletra() == 'A' || f.obtenirLletra() == 'B');

        assertEquals(2, sac.getNumFitxes());
    }

    @Test(expected = IllegalStateException.class)
    public void testAgafarFitxaSacBuit() {
        sac.agafarFitxa();
        sac.agafarFitxa();
        sac.agafarFitxa();

        sac.agafarFitxa();
    }

    @Test
    public void testAfegirFitxaNova() {
        sac.afegirFitxa(x);

        assertEquals(1, sac.quantitatFitxes(x));
    }

    @Test
    public void testAfegirFitxaRepetida() {
        sac.afegirFitxa(x);
        sac.afegirFitxa(x);

        assertEquals(2, sac.quantitatFitxes(x));
    }
}