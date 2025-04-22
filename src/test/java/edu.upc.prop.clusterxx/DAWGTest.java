package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DAWGTest {

    private DAWG dawg;

    @Before
    public void setUp() {
        // Exemple de tokens (incloent un dígraf com "CH")
        List<String> tokens = Arrays.asList("CH", "A", "B", "C", "H", "I", "O", "P", "S", "T", "X");
        List<String> paraules = Arrays.asList("CASA", "CAS", "CHIC", "CHIP", "SABATA", "CH");
        paraules.sort(String::compareTo);
        dawg = new DAWG(tokens, paraules);
    }

    @Test
    public void testParaulaSimpleValida() {
        assertTrue(dawg.conteParaula("CASA"));
        assertTrue(dawg.conteParaula("CAS"));
        assertTrue(dawg.conteParaula("SABATA"));
    }

    @Test
    public void testParaulaInexistent() {
        assertFalse(dawg.conteParaula("CASO"));
        assertFalse(dawg.conteParaula("SABA"));
        assertFalse(dawg.conteParaula("CHICO"));
    }

    @Test
    public void testParaulaAmbDigraf() {
        assertTrue(dawg.conteParaula("CHIC"));
        assertTrue(dawg.conteParaula("CH"));
        assertFalse(dawg.conteParaula("C")); // no existeix sola
    }

    @Test
    public void testPrefixValid() {
        assertNotNull(dawg.esPrefix("CAS"));
        assertNotNull(dawg.esPrefix("SA"));
        assertNotNull(dawg.esPrefix("CHI")); // Prefix de CHIC i CHIP
    }

    @Test
    public void testPrefixInvalid() {
        assertNull(dawg.esPrefix("XO"));
        assertNull(dawg.esPrefix("BA"));
        assertNull(dawg.esPrefix("XCH"));
    }

    @Test
    public void testPrefixAmbDigraf() {
        assertNotNull(dawg.esPrefix("CH"));
        assertNotNull(dawg.esPrefix("CHI"));
        assertNull(dawg.esPrefix("CI"));
    }
}