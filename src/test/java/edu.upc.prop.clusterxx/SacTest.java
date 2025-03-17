package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SacTest {
    private Sac sac;

    @Before
    public void setUp() {
        // Simulem un sac amb fitxes predefinides
        sac = new Sac();
        sac.afegirFitxa(new Fitxa('A', 1));
        sac.afegirFitxa(new Fitxa('A', 1));
        sac.afegirFitxa(new Fitxa('B', 1));
    }

    @Test
    public void testAgafarFitxaA() {
        Fitxa f = sac.agafarFitxa('A');
        assertNotNull(f);
        assertEquals('A', f.getLletra());
        assertEquals(2, sac.getNumFitxes()); // Abans 3, ara 2
        assertFalse(sac.esBuit());
    }

    @Test
    public void testSacBuit() {
        Fitxa f = sac.agafarFitxa('A');
        assertNotNull(f);
        assertEquals('A', f.getLletra());
        f = sac.agafarFitxa('A');
        assertNotNull(f);
        assertEquals('A', f.getLletra());
        f = sac.agafarFitxa('B');
        assertNotNull(f);
        assertEquals('B', f.getLletra());

        assertEquals(0, sac.getNumFitxes()); // Abans 3, ara 2
        assertTrue(sac.esBuit());
    }

    @Test(expected = NoSuchElementException.class)
    public void testAgafarFitxaInexistent() {
        sac.agafarFitxa('Z'); // La lletra 'Z' no està al sac, ha de llençar excepció
    }

    @Test
    public void testAgafarFitxaAleatoria() {
        // Intentem agafar una fitxa del sac (hauria de ser A o B)
        Fitxa f = sac.agafarFitxa();
        assertNotNull(f);
        assertTrue(f.getLletra() == 'A' || f.getLletra() == 'B');

        // Verifiquem que el nombre de fitxes ha disminuït
        assertEquals(2, sac.getNumFitxes()); // De 3 hem passat a 2
    }

    @Test(expected = IllegalStateException.class)
    public void testAgafarFitxaSacBuit() {
        // Buidem el sac
        sac.agafarFitxa();
        sac.agafarFitxa();
        sac.agafarFitxa(); // Aquí el sac queda buit

        // Ara hauria de llençar excepció
        sac.agafarFitxa();
    }

    @Test
    public void testAfegirFitxaNova() {
        Fitxa fitxa = new Fitxa('X', 5);

        sac.afegirFitxa(fitxa);

        assertEquals(1, sac.quantitatFitxes('X'));
    }

    @Test
    public void testAfegirFitxaRepetida() {
        Fitxa fitxa = new Fitxa('X', 5);

        sac.afegirFitxa(fitxa);
        sac.afegirFitxa(fitxa);

        assertEquals(2, sac.quantitatFitxes('X'));
    }


}
