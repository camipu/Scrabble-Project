package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SacTest {
    private Sac sac;

    @Before
    public void setUp() {
        sac = new Sac();  // Inicialitza un sac buit
    }

    @Test
    public void testAfegirUnaFitxa() {
        Fitxa fitxaA = new Fitxa("A", 1);
        sac.afegirFitxa(fitxaA);

        assertEquals(1, sac.quantitatFitxes(fitxaA));
    }

    @Test
    public void testAfegirFitxaRepetida() {
        Fitxa fitxaA = new Fitxa("A", 1);
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaA);

        assertEquals(2, sac.quantitatFitxes(fitxaA));
    }

    @Test
    public void testAfegirDiversesFitxes() {
        Fitxa fitxaA = new Fitxa("A", 1);
        Fitxa fitxaB = new Fitxa("B", 3);

        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);

        assertEquals(1, sac.quantitatFitxes(fitxaA));
        assertEquals(1, sac.quantitatFitxes(fitxaB));
    }

    @Test
    public void testAfegirMúltiplesFitxesDiferents() {
        Fitxa fitxaA = new Fitxa("A", 1);
        Fitxa fitxaB = new Fitxa("B", 3);
        Fitxa fitxaC = new Fitxa("C", 5);

        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);
        sac.afegirFitxa(fitxaC);

        assertEquals(1, sac.quantitatFitxes(fitxaA));
        assertEquals(1, sac.quantitatFitxes(fitxaB));
        assertEquals(1, sac.quantitatFitxes(fitxaC));
    }

    @Test
    public void testQuantitatZeroSiNoExisteix() {
        Fitxa fitxaX = new Fitxa("X", 10);

        //No la introduim, inexistent al sac
        assertEquals(0, sac.quantitatFitxes(fitxaX));
    }

    @Test
    public void testAgafarFitxaRedueixQuantitat() {
        Fitxa fitxaA = new Fitxa("A", 1);
        sac.afegirFitxa(fitxaA);
        sac.agafarFitxa(fitxaA);

        //Quantitat de la fitxa "A" és 0 després de ser agafada
        assertEquals(0, sac.quantitatFitxes(fitxaA));
        assertEquals(0, sac.obtenirNumFitxes());

    }

    @Test(expected = NoSuchElementException.class)
    public void testAgafarFitxaNoDisponible() {
        Fitxa fitxaA = new Fitxa("A", 1);
        sac.agafarFitxa(fitxaA);

        // Ha de llançar una NoSuchElementException perquè no hi ha cap fitxa "A"
    }

    @Test
    public void testAgafarFitxaAleatoria() {
        Fitxa fitxaA = new Fitxa("A", 1);
        Fitxa fitxaB = new Fitxa("B", 3);
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);

        // Agafa una fitxa aleatòria
        Fitxa fitxaSeleccionada = sac.agafarFitxa();

        assertTrue(fitxaSeleccionada.equals(fitxaA) || fitxaSeleccionada.equals(fitxaB));
    }

    @Test
    public void testSacBuitDespresDeTot() {
        Fitxa fitxaA = new Fitxa("A", 1);
        sac.afegirFitxa(fitxaA);
        assertEquals(1, sac.quantitatFitxes(fitxaA));
        sac.agafarFitxa(fitxaA);

        // Comprovar que el sac està buit després de retirar totes les fitxes
        assertTrue(sac.esBuit());
    }

    @Test
    public void testEsBuitInicialment() {
        // Comprovar que un sac creat sense fitxes està buit
        assertTrue(sac.esBuit());
        assertEquals(0, sac.obtenirNumFitxes());

    }

    @Test
    public void testQuantitatFitxesDelSac() {
        Fitxa fitxaA = new Fitxa("A", 1);
        Fitxa fitxaB = new Fitxa("B", 3);
        sac.afegirFitxa(fitxaA);
        sac.afegirFitxa(fitxaB);

        assertEquals(2, sac.obtenirNumFitxes());
    }

    @Test
    public void testMostrarContingut() {
        Fitxa fitxaA = new Fitxa("A", 1);
        Fitxa fitxaB = new Fitxa("B", 3);
        sac.afegirFitxa(fitxaA);  // Afegeix una fitxa "A"
        sac.afegirFitxa(fitxaB);  // Afegeix una fitxa "B"

        // Comprovar que les fitxes s'han afegit correctament
        sac.mostrarContingut();  // Aquesta línia només imprimeix el contingut al terminal
        // No es fa una comprovació directa, però es podria capturar la sortida amb un "System.setOut" per verificar-ho
    }
}
