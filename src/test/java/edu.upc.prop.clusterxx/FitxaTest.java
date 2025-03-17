package edu.upc.prop.clusterxx;

import org.junit.Test;
import static org.junit.Assert.*;

public class FitxaTest {

    @Test
    public void testCreacioFitxaLletra() {
        Fitxa f = new Fitxa('A', 1);
        assertEquals('A', f.getLletra());
        assertEquals(1, f.getPunts());
        assertFalse(f.esDigraf()); // Les lletres normals no són dígrafs
    }

    @Test
    public void testCreacioFitxaCaracterEspecial() {
        Fitxa f = new Fitxa('*', 5);
        assertEquals('*', f.getLletra());
        assertEquals(5, f.getPunts());
        assertTrue(f.esDigraf()); // Un caràcter especial hauria de ser considerat dígraf
    }

    @Test
    public void testCreacioFitxaDigit() {
        Fitxa f = new Fitxa('3', 2);
        assertEquals('3', f.getLletra());
        assertEquals(2, f.getPunts());
        assertTrue(f.esDigraf()); // Un número també ha de ser dígraf
    }

    @Test
    public void testToStringLletraNormal() {
        Fitxa f = new Fitxa('B', 3);
        assertEquals("B", f.toString());
    }

    @Test
    public void testToStringDigrafEspecial() {
        Fitxa f = new Fitxa('(', 0);
        assertEquals("CH", f.toString()); // Test per assegurar que '(' es converteix en "CH"
    }

    @Test
    public void testToStringCaracterEspecial() {
        Fitxa f = new Fitxa('#', 4);
        assertEquals("#", f.toString()); // Els caràcters especials es representen tal qual
    }

    @Test
    public void testFitxaAmbPuntsNegatius() {
        Fitxa f = new Fitxa('X', -5);
        assertEquals(-5, f.getPunts()); // Ha de mantenir els punts negatius si es permet
    }

    @Test
    public void testFitxaAmbEspai() {
        Fitxa f = new Fitxa(' ', 0);
        assertEquals(' ', f.getLletra());
        assertTrue(f.esDigraf()); // Els espais també són dígrafs
    }

    @Test
    public void testDiferentsTipusDeLletres() {
        Fitxa f1 = new Fitxa('z', 10);
        Fitxa f2 = new Fitxa('Z', 10);
        assertEquals('z', f1.getLletra());
        assertEquals('Z', f2.getLletra());
        assertFalse(f1.esDigraf());
        assertFalse(f2.esDigraf());
    }

    @Test
    public void testFitxaAmbCaractersASCII() {
        Fitxa f = new Fitxa('@', 8);
        assertEquals('@', f.getLletra());
        assertEquals(8, f.getPunts());
        assertTrue(f.esDigraf()); // '@' no és una lletra, per tant és dígraf
    }

    @Test
    public void testFitxaAmbPuntuacioAlta() {
        Fitxa f = new Fitxa('Q', 50);
        assertEquals(50, f.getPunts());
    }

    @Test
    public void testFitxaAmbPuntuacioZero() {
        Fitxa f = new Fitxa('T', 0);
        assertEquals(0, f.getPunts());
    }

    @Test
    public void testComparacioDeFitxes() {
        Fitxa f1 = new Fitxa('K', 5);
        Fitxa f2 = new Fitxa('K', 5);
        assertEquals(f1.getLletra(), f2.getLletra());
        assertEquals(f1.getPunts(), f2.getPunts());
    }
}
