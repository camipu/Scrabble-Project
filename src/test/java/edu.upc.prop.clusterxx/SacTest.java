package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
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
        assertEquals(1, sac.getNumFitxes()); // Abans 2, ara 1
    }


}
