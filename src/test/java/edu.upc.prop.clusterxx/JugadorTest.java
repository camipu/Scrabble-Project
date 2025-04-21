package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JugadorTest {

    private Jugador jugador;
    private Faristol mockFaristol;
    private Fitxa mockFitxa;

    @Before
    public void setUp() {
        mockFaristol = mock(Faristol.class);
        jugador = new Jugador("Tarrinero", mockFaristol);
        mockFitxa = mock(Fitxa.class);
    }

    @Test
    public void testObtenirNomIPunts() {
        assertEquals("Tarrinero", jugador.obtenirNom());
        assertEquals(0, jugador.obtenirPunts());
    }

    @Test
    public void testObtenirFaristol() {
        assertEquals(mockFaristol, jugador.obtenirFaristol());
    }

    @Test
    public void testAfegirIEliminarPunts() {
        jugador.afegirPunts(10);
        assertEquals(10, jugador.obtenirPunts());

        jugador.eliminarPunts(4);
        assertEquals(6, jugador.obtenirPunts());
    }

    @Test
    public void testAfegirFitxaDelegatAlFaristol() {
        jugador.afegirFitxa(mockFitxa);
        verify(mockFaristol, times(1)).afegirFitxa(mockFitxa);
    }

    @Test
    public void testEliminarFitxaDelegatAlFaristol() {
        jugador.eliminarFitxa(mockFitxa);
        verify(mockFaristol, times(1)).eliminarFitxa(mockFitxa);
    }
}
