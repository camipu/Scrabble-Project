package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolNoConteLaFitxa;
import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolPle;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FaristolTest {

    private Faristol faristol;
    private Fitxa mockFitxa1;
    private Fitxa mockFitxa2;

    @Before
    public void setUp() {
        faristol = new Faristol(2); // mida petita per poder-lo omplir fàcilment
        mockFitxa1 = mock(Fitxa.class);
        when(mockFitxa1.toString()).thenReturn("A");
        when(mockFitxa1.obtenirPunts()).thenReturn(1);

        mockFitxa2 = mock(Fitxa.class);
        when(mockFitxa2.toString()).thenReturn("B");
        when(mockFitxa2.obtenirPunts()).thenReturn(3);
    }

    @Test
    public void testFaristolInicialBuit() {
        assertEquals(0, faristol.obtenirNumFitxes());
        assertFalse(faristol.esPle());
        assertTrue(faristol.obtenirFitxes().isEmpty());
    }

    @Test
    public void testAfegirFitxaCorrectament() {
        faristol.afegirFitxa(mockFitxa1);
        assertEquals(1, faristol.obtenirNumFitxes());
        assertFalse(faristol.esPle());
        faristol.afegirFitxa(mockFitxa2);
        assertEquals(2, faristol.obtenirNumFitxes());
        assertTrue(faristol.esPle());
    }

    @Test(expected = ExcepcioFaristolPle.class)
    public void testAfegirFitxaFaristolPleLlançaExcepcio() {
        faristol.afegirFitxa(mockFitxa1);
        faristol.afegirFitxa(mockFitxa2);
        faristol.afegirFitxa(mock(Fitxa.class)); // excedeix mida = 2
    }

    @Test
    public void testObtenirFitxaPerIndex() {
        faristol.afegirFitxa(mockFitxa1);
        assertEquals(mockFitxa1, faristol.obtenirFitxa(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenirFitxaIndexInvalidLlançaExcepcio() {
        faristol.obtenirFitxa(0); // encara no hi ha cap fitxa
    }

    @Test
    public void testEliminarFitxaCorrectament() {
        faristol.afegirFitxa(mockFitxa1);
        faristol.eliminarFitxa(mockFitxa1);
        assertEquals(0, faristol.obtenirNumFitxes());
    }

    @Test(expected = ExcepcioFaristolNoConteLaFitxa.class)
    public void testEliminarFitxaInexistentLlançaExcepcio() {
        faristol.eliminarFitxa(mockFitxa1);
    }

    @Test
    public void testBarrejarFitxes() {
        faristol.afegirFitxa(mockFitxa1);
        faristol.afegirFitxa(mockFitxa2);

        List<Fitxa> abans = faristol.obtenirFitxes();
        faristol.barrejarFitxes();
        List<Fitxa> despres = faristol.obtenirFitxes();

        // No podem garantir que l'ordre canviï, però almenys no ha de llençar errors
        assertEquals(2, despres.size());
        assertTrue(despres.contains(mockFitxa1));
        assertTrue(despres.contains(mockFitxa2));
    }
}