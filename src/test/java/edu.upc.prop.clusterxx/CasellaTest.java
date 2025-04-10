package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CasellaTest {

    private Casella casellaNormal;
    private Casella casellaMultiplicadorLletra;
    private Casella casellaMultiplicadorParaula;

    // Mocks de la classe Fitxa
    private Fitxa fitxaAMock;
    private Fitxa fitxaBMock;

    @Before
    public void setUp() {
        // Crear mocks de la classe Fitxa
        fitxaAMock = mock(Fitxa.class);
        fitxaBMock = mock(Fitxa.class);

        // Simular els valors dels punts per a les fitxes mockejades
        when(fitxaAMock.obtenirPunts()).thenReturn(1);  // Fitxa A té un valor de 1
        when(fitxaBMock.obtenirPunts()).thenReturn(3);  // Fitxa B té un valor de 3

        // Inicialitzar les caselles amb les estratègies
        casellaNormal = new Casella(0, 0, new EstrategiaNormal());
        casellaMultiplicadorLletra = new Casella(1, 1, new EstrategiaMultiplicadorLletra(2));
        casellaMultiplicadorParaula = new Casella(2, 2, new EstrategiaMultiplicadorParaula(3));
    }

    @Test
    public void testCalcularPuntsCasellaNormal() {
        // Col·locar la fitxa "A" a la casella normal (utilitzant el mock)
        casellaNormal.colocarFitxa(fitxaAMock);
        // Comprovem que els punts de la casella normal són els esperats (el valor de la fitxa)
        assertEquals("Els punts de la casella normal han de ser 1", 1, casellaNormal.calcularPunts());
    }

    @Test
    public void testCalcularPuntsMultiplicadorLletra() {
        // Col·locar la fitxa "B" a la casella amb multiplicador de lletra (utilitzant el mock)
        casellaMultiplicadorLletra.colocarFitxa(fitxaBMock);
        // Comprovem que els punts de la casella amb multiplicador de lletra són els esperats (multiplicat per 2)
        assertEquals("Els punts de la casella amb multiplicador de lletra han de ser 6", 6, casellaMultiplicadorLletra.calcularPunts());
    }

    @Test
    public void testCalcularPuntsMultiplicadorParaula() {
        // Col·locar la fitxa "A" a la casella amb multiplicador de paraula (utilitzant el mock)
        casellaMultiplicadorParaula.colocarFitxa(fitxaAMock);
        // Comprovem que els punts de la casella amb multiplicador de paraula són els esperats (sense multiplicar, només el valor de la fitxa)
        assertEquals("Els punts de la casella amb multiplicador de paraula han de ser 1", 1, casellaMultiplicadorParaula.calcularPunts());
    }

    @Test
    public void testMultiplicadorParaula() {
        // Comprovem que una casella amb multiplicador de paraula retorna el multiplicador correcte
        assertEquals("El multiplicador de paraula ha de ser 3", 3, casellaMultiplicadorParaula.obtenirMultiplicador());
    }

    @Test
    public void testMultiplicadorLletra() {
        // Comprovem que una casella amb multiplicador de lletra retorna el multiplicador correcte
        assertEquals("El multiplicador de lletra ha de ser 2", 2, casellaMultiplicadorLletra.obtenirMultiplicador());
    }

    @Test
    public void testCasellaBuida() {
        // Comprovem que una casella buida retorna 0 punts
        Casella casellaBuida = new Casella(3, 3, new EstrategiaNormal());
        assertTrue("La casella ha d'estar buida", casellaBuida.esBuida());
        assertEquals("Els punts de la casella buida han de ser 0", 0, casellaBuida.calcularPunts());
    }

    @Test
    public void testColocarFitxa() {
        // Col·locar una fitxa a una casella buida (utilitzant el mock)
        assertTrue("S'ha de poder col·locar una fitxa en una casella buida", casellaNormal.colocarFitxa(fitxaAMock));
    }

    @Test
    public void testNoColocarFitxaSiJaEstàOcupada() {
        // Intentar col·locar una fitxa en una casella ja ocupada (utilitzant el mock)
        casellaNormal.colocarFitxa(fitxaAMock);
        assertFalse("No s'ha de poder col·locar una fitxa en una casella ja ocupada", casellaNormal.colocarFitxa(fitxaBMock));
    }

    @Test
    public void testCasellaJugada() {
        // Comprovem si la casella es marca com a jugada
        casellaNormal.jugarCasella();
        assertTrue("La casella hauria de ser marcada com a jugada", casellaNormal.casellaJugada());
    }

    @Test
    public void testCasellaNoJugadaInicialment() {
        // Comprovem que la casella inicialment no ha estat jugada
        assertFalse("La casella no hauria de ser jugada inicialment", casellaNormal.casellaJugada());
    }
}
